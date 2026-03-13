package com.example.SalaryCalculator.repositories;

import com.example.SalaryCalculator.entities.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeductionRepository extends JpaRepository<Deduction, Long> {

    Optional<Deduction> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Deduction> findAllByOrderByNameAsc();
}
