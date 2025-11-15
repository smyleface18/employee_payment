package com.example.SalaryCalculator.repositories;


import com.example.SalaryCalculator.entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface IRepositoryEmployee extends MongoRepository<Employee,String> {

}
