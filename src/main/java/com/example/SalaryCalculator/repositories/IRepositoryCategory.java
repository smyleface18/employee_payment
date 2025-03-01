package com.example.SalaryCalculator.repositories;


import com.example.SalaryCalculator.entities.Category;
import com.example.SalaryCalculator.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryCategory extends JpaRepository<Category,Long> {


}
