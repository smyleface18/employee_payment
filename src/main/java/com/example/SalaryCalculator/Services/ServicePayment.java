package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.Dtos.PaymentDto;
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

    public List<PaymentDto> findByEmployee(String id) {
        List<PaymentRecord> data =  iRepositoryPayment.findByEmployee_Id(id);
        List<PaymentDto> response = List.of();
        for (PaymentRecord payment : data) {
            PaymentDto paymentDto = new PaymentDto(payment.getId(),payment.getHour(), payment.getMoney());
            response.add(paymentDto);
        }
        return response;
    }

    public PaymentRecord findById(String id) {
        return iRepositoryPayment.findById(id).orElse(null);
    }

    public ResponseEntity<?> savePayment(PaymentRecord paymentRecord) {
        try {
            iRepositoryPayment.save(paymentRecord);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Se registro el pago"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "no se puedo registrar el pago"));
        }
    }
}
