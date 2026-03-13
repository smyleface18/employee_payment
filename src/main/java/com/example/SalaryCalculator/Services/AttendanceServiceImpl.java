package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.Attendance;
import com.example.SalaryCalculator.repositories.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl  {

    private static final BigDecimal MAX_DAILY_HOURS = new BigDecimal("24");


    private final AttendanceRepository attendanceRepository;
    private final EmployeeServiceImpl employeeService;
    private final PayPeriodServiceImpl payPeriodService;



    @Transactional
    public Attendance create(Attendance attendance) {
        log.info("Recording attendance for employee {} on {}", attendance.getEmployee().getIdEmployee(), attendance.getDate());
        validateAttendance(attendance, null);
        return attendanceRepository.save(attendance);
    }


    @Transactional
    public Attendance update(Long id, Attendance attendance) {
        log.info("Updating attendance id: {}", id);
        Attendance existing = findById(id);
        validateAttendance(attendance, id);
        existing.setDate(attendance.getDate());
        existing.setHoursWorked(attendance.getHoursWorked());
        existing.setEmployee(attendance.getEmployee());
        existing.setPayPeriod(attendance.getPayPeriod());
        return attendanceRepository.save(existing);
    }


    @Transactional(readOnly = true)
    public Attendance findById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance", id));
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByEmployee(Long idEmployee) {
        return attendanceRepository.findByEmployeeIdEmployee(idEmployee);
    }


    @Transactional(readOnly = true)
    public List<Attendance> findByEmployeeAndPeriod(Long idEmployee, Long idPeriod) {
        return attendanceRepository.findByEmployeeIdEmployeeAndPayPeriodIdPeriod(idEmployee, idPeriod);
    }


    @Transactional(readOnly = true)
    public List<Attendance> findByEmployeeAndDateRange(Long idEmployee, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByEmployeeAndDateRange(idEmployee, startDate, endDate);
    }


    @Transactional(readOnly = true)
    public BigDecimal getTotalHoursWorked(Long idEmployee, Long idPeriod) {
        return attendanceRepository.sumHoursWorkedByEmployeeAndPeriod(idEmployee, idPeriod)
                .orElse(BigDecimal.ZERO);
    }


    @Transactional
    public void delete(Long id) {
        log.info("Deleting attendance id: {}", id);
        Attendance attendance = findById(id);
        attendanceRepository.delete(attendance);
    }

    // ── Private helpers ──────────────────────────────────────────
    private void validateAttendance(Attendance attendance, Long excludeId) {
        if (attendance.getHoursWorked().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Hours worked must be greater than zero.");
        }
        if (attendance.getHoursWorked().compareTo(MAX_DAILY_HOURS) > 0) {
            throw new BusinessException("Hours worked cannot exceed 24 hours per day.");
        }

        Long employeeId = attendance.getEmployee().getIdEmployee();
        // Validate employee exists
        employeeService.findById(employeeId);

        // Duplicate date check
        attendanceRepository.findByEmployeeIdEmployeeAndDate(employeeId, attendance.getDate())
                .filter(a -> !a.getIdAttendance().equals(excludeId))
                .ifPresent(a -> { throw new BusinessException(
                        "Attendance already registered for employee " + employeeId + " on " + attendance.getDate()); });

        // Validate pay period if provided
        if (attendance.getPayPeriod() != null && attendance.getPayPeriod().getIdPeriod() != null) {
            payPeriodService.findById(attendance.getPayPeriod().getIdPeriod());
        }
    }
}
