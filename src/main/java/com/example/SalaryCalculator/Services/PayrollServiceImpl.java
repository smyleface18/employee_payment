package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.*;
import com.example.SalaryCalculator.repositories.DeductionRepository;
import com.example.SalaryCalculator.repositories.PayrollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayrollServiceImpl {

    /** Regular hours threshold per period (e.g. 160h/month). Adjust as needed. */
    private static final BigDecimal REGULAR_HOURS_LIMIT = new BigDecimal("160");

    /** Single overtime starts after REGULAR_HOURS_LIMIT, multiplier 1.5x */
    private static final BigDecimal SINGLE_OT_LIMIT = new BigDecimal("180");
    private static final BigDecimal SINGLE_OT_MULTIPLIER = new BigDecimal("1.5");

    /** Double overtime after SINGLE_OT_LIMIT, multiplier 2.0x */
    private static final BigDecimal DOUBLE_OT_MULTIPLIER = new BigDecimal("2.0");

    private final PayrollRepository payrollRepository;
    private final DeductionRepository deductionRepository;
    private final EmployeeServiceImpl employeeService;
    private final PayPeriodServiceImpl payPeriodService;
    private final AttendanceServiceImpl attendanceService;

    // ── Public API ───────────────────────────────────────────────


    @Transactional
    public Payroll generateForEmployee(Long idEmployee, Long idPeriod) {
        log.info("Generating payroll for employee {} in period {}", idEmployee, idPeriod);

        Employee employee = employeeService.findById(idEmployee);
        PayPeriod period  = payPeriodService.findById(idPeriod);

        validatePeriodIsOpen(period);

        if (payrollRepository.existsByEmployeeIdEmployeeAndPayPeriodIdPeriod(idEmployee, idPeriod)) {
            throw new BusinessException("Payroll already generated for employee " + idEmployee + " in period " + idPeriod);
        }

        BigDecimal totalHours = attendanceService.getTotalHoursWorked(idEmployee, idPeriod);
        if (totalHours.compareTo(BigDecimal.ZERO) == 0) {
            throw new BusinessException("No attendance records found for employee " + idEmployee + " in period " + idPeriod);
        }

        BigDecimal hourlyRate = employee.getCategory().getHourlyRate();

        // ── Hour breakdown ───────────────────────────────────────
        HourBreakdown hours = calculateHours(totalHours);

        // ── Gross salary ─────────────────────────────────────────
        BigDecimal grossSalary = computeGrossSalary(hours, hourlyRate);

        // ── Build payroll entity ─────────────────────────────────
        Payroll payroll = Payroll.builder()
                .employee(employee)
                .payPeriod(period)
                .totalHoursWorked(totalHours)
                .regularHours(hours.regular)
                .singleOvertimeHours(hours.singleOt)
                .doubleOvertimeHours(hours.doubleOt)
                .grossSalary(grossSalary)
                .build();

        payroll = payrollRepository.save(payroll);

        // ── Apply all deductions ─────────────────────────────────
        List<Deduction> deductions = deductionRepository.findAll();
        List<PayrollDeduction> payrollDeductions = new ArrayList<>();

        for (Deduction deduction : deductions) {
            BigDecimal amount = grossSalary
                    .multiply(deduction.getPercentage())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

            payrollDeductions.add(PayrollDeduction.builder()
                    .payroll(payroll)
                    .deduction(deduction)
                    .amount(amount)
                    .build());
        }

        payroll.setDeductions(payrollDeductions);
        return payrollRepository.save(payroll);
    }


    @Transactional
    public List<Payroll> generateForPeriod(Long idPeriod) {
        log.info("Generating payroll for all employees in period {}", idPeriod);
        PayPeriod period = payPeriodService.findById(idPeriod);
        validatePeriodIsOpen(period);

        List<Employee> employees = employeeService.findAll();
        List<Payroll> results = new ArrayList<>();

        for (Employee employee : employees) {
            try {
                Payroll payroll = generateForEmployee(employee.getIdEmployee(), idPeriod);
                results.add(payroll);
            } catch (BusinessException ex) {
                // Log and skip employees with no attendance or already processed
                log.warn("Skipping employee {}: {}", employee.getIdEmployee(), ex.getMessage());
            }
        }

        if (results.isEmpty()) {
            throw new BusinessException("No payrolls could be generated for period " + idPeriod);
        }

        return results;
    }


    @Transactional(readOnly = true)
    public Payroll findById(Long idPayroll) {
        return payrollRepository.findByIdWithDeductions(idPayroll)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll", idPayroll));
    }


    @Transactional(readOnly = true)
    public List<Payroll> findByPeriod(Long idPeriod) {
        return payrollRepository.findByPeriodWithDetails(idPeriod);
    }


    @Transactional(readOnly = true)
    public List<Payroll> findByEmployee(Long idEmployee) {
        return payrollRepository.findByEmployeeIdEmployee(idEmployee);
    }


    @Transactional
    public void delete(Long idPayroll) {
        log.info("Deleting payroll id: {}", idPayroll);
        Payroll payroll = findById(idPayroll);
        if (payroll.getPayPeriod().getStatus() == PeriodStatus.PROCESSED) {
            throw new BusinessException("Cannot delete a payroll from a processed period.");
        }
        payrollRepository.delete(payroll);
    }

    // ── Private helpers ──────────────────────────────────────────

    private void validatePeriodIsOpen(PayPeriod period) {
        if (period.getStatus() != PeriodStatus.OPEN) {
            throw new BusinessException("Payroll can only be generated for OPEN periods. Period " +
                    period.getIdPeriod() + " is " + period.getStatus());
        }
    }

    private HourBreakdown calculateHours(BigDecimal totalHours) {
        BigDecimal regular  = totalHours.min(REGULAR_HOURS_LIMIT);
        BigDecimal singleOt = BigDecimal.ZERO;
        BigDecimal doubleOt = BigDecimal.ZERO;

        if (totalHours.compareTo(REGULAR_HOURS_LIMIT) > 0) {
            BigDecimal extraHours = totalHours.subtract(REGULAR_HOURS_LIMIT);
            singleOt = extraHours.min(SINGLE_OT_LIMIT.subtract(REGULAR_HOURS_LIMIT));

            if (totalHours.compareTo(SINGLE_OT_LIMIT) > 0) {
                doubleOt = totalHours.subtract(SINGLE_OT_LIMIT);
            }
        }

        return new HourBreakdown(regular, singleOt, doubleOt);
    }

    private BigDecimal computeGrossSalary(HourBreakdown hours, BigDecimal hourlyRate) {
        BigDecimal regularPay  = hours.regular.multiply(hourlyRate);
        BigDecimal singleOtPay = hours.singleOt.multiply(hourlyRate).multiply(SINGLE_OT_MULTIPLIER);
        BigDecimal doubleOtPay = hours.doubleOt.multiply(hourlyRate).multiply(DOUBLE_OT_MULTIPLIER);
        return regularPay.add(singleOtPay).add(doubleOtPay).setScale(2, RoundingMode.HALF_UP);
    }

    /** Simple value object to carry the three hour buckets. */
    private record HourBreakdown(BigDecimal regular, BigDecimal singleOt, BigDecimal doubleOt) {}
}
