package com.example.SalaryCalculator.repositories;



import com.example.SalaryCalculator.entities.PaymentRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface IRepositoryPayment extends MongoRepository<PaymentRecord,String> {
    List<PaymentRecord> findByEmployee_Id(String employeeId);

}
