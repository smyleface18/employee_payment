package com.example.SalaryCalculator.repositories;

import com.example.SalaryCalculator.entities.PayrollDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollDeductionRepository extends JpaRepository<PayrollDeduction, Long> {

    List<PayrollDeduction> findByPayrollIdPayroll(Long idPayroll);

    List<PayrollDeduction> findByDeductionIdDeduction(Long idDeduction);

    boolean existsByPayrollIdPayrollAndDeductionIdDeduction(Long idPayroll, Long idDeduction);

    @Query("SELECT SUM(pd.amount) FROM PayrollDeduction pd " +
           "WHERE pd.payroll.idPayroll = :payrollId")
    Optional<BigDecimal> sumAmountByPayroll(@Param("payrollId") Long payrollId);
}
