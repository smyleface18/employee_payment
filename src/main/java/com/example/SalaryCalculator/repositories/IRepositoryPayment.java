package com.example.SalaryCalculator.repositories;


import com.example.SalaryCalculator.entities.Employee;
import com.example.SalaryCalculator.entities.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IRepositoryPayment extends JpaRepository<PaymentRecord,Long> {


}
