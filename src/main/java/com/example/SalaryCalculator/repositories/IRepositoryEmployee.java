package com.example.SalaryCalculator.repositories;


import com.example.SalaryCalculator.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRepositoryEmployee extends JpaRepository<Employee,Long> {


}
