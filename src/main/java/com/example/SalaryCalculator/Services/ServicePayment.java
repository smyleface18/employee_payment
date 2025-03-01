package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.PaymentRecord;
import com.example.SalaryCalculator.repositories.IRepositoryPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ServicePayment {

    private final IRepositoryPayment iRepositoryPayment;

    @Autowired
    public ServicePayment(IRepositoryPayment iRepositoryPayment) {
        this.iRepositoryPayment = iRepositoryPayment;
    }

    public List<PaymentRecord> findAll() {
        return iRepositoryPayment.findAll();
    }

    public PaymentRecord findById(Long id) {
        return iRepositoryPayment.getReferenceById(id);
    }

    public ResponseEntity<?> savePayment(PaymentRecord paymentRecord) {
        try {
            iRepositoryPayment.saveAndFlush(paymentRecord);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Se registro el pago"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "no se puedo registrar el pago"));
        }
    }
}
