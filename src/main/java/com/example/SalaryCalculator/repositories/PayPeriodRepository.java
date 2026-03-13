package com.example.SalaryCalculator.repositories;

import com.example.SalaryCalculator.entities.PayPeriod;
import com.example.SalaryCalculator.entities.PeriodStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayPeriodRepository extends JpaRepository<PayPeriod, Long> {

    List<PayPeriod> findByStatus(PeriodStatus status);

    Optional<PayPeriod> findFirstByStatusOrderByStartDateDesc(PeriodStatus status);

    @Query("SELECT p FROM PayPeriod p WHERE :date BETWEEN p.startDate AND p.endDate")
    Optional<PayPeriod> findByDate(@Param("date") LocalDate date);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PayPeriod p " +
           "WHERE p.startDate <= :endDate AND p.endDate >= :startDate AND p.idPeriod != :excludeId")
    boolean existsOverlappingPeriod(@Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate,
                                    @Param("excludeId") Long excludeId);

    List<PayPeriod> findAllByOrderByStartDateDesc();
}
