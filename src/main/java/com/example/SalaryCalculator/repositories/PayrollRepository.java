package com.example.SalaryCalculator.repositories;

import com.example.SalaryCalculator.entities.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByPayPeriodIdPeriod(Long idPeriod);

    List<Payroll> findByEmployeeIdEmployee(Long idEmployee);

    Optional<Payroll> findByEmployeeIdEmployeeAndPayPeriodIdPeriod(Long idEmployee, Long idPeriod);

    boolean existsByEmployeeIdEmployeeAndPayPeriodIdPeriod(Long idEmployee, Long idPeriod);

    @Query("SELECT p FROM Payroll p " +
           "JOIN FETCH p.employee e " +
           "JOIN FETCH e.category " +
           "JOIN FETCH p.payPeriod " +
           "WHERE p.payPeriod.idPeriod = :periodId " +
           "ORDER BY e.lastName ASC, e.firstName ASC")
    List<Payroll> findByPeriodWithDetails(@Param("periodId") Long periodId);

    @Query("SELECT p FROM Payroll p " +
           "JOIN FETCH p.deductions pd " +
           "JOIN FETCH pd.deduction " +
           "WHERE p.idPayroll = :payrollId")
    Optional<Payroll> findByIdWithDeductions(@Param("payrollId") Long payrollId);
}
