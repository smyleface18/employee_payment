package com.example.SalaryCalculator.repositories;

import com.example.SalaryCalculator.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeIdEmployee(Long idEmployee);

    List<Attendance> findByPayPeriodIdPeriod(Long idPeriod);

    List<Attendance> findByEmployeeIdEmployeeAndPayPeriodIdPeriod(Long idEmployee, Long idPeriod);

    Optional<Attendance> findByEmployeeIdEmployeeAndDate(Long idEmployee, LocalDate date);

    boolean existsByEmployeeIdEmployeeAndDate(Long idEmployee, LocalDate date);

    @Query("SELECT SUM(a.hoursWorked) FROM Attendance a " +
           "WHERE a.employee.idEmployee = :employeeId " +
           "AND a.payPeriod.idPeriod = :periodId")
    Optional<BigDecimal> sumHoursWorkedByEmployeeAndPeriod(@Param("employeeId") Long employeeId,
                                                            @Param("periodId") Long periodId);

    @Query("SELECT a FROM Attendance a " +
           "WHERE a.employee.idEmployee = :employeeId " +
           "AND a.date BETWEEN :startDate AND :endDate " +
           "ORDER BY a.date ASC")
    List<Attendance> findByEmployeeAndDateRange(@Param("employeeId") Long employeeId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}
