package com.example.SalaryCalculator.repositories;


import com.example.SalaryCalculator.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRepositoryEmployee extends JpaRepository<Employee,Long> {

}
