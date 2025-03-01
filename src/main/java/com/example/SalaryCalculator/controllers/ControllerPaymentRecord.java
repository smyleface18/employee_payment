package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.ServicePayment;
import com.example.SalaryCalculator.entities.PaymentRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Payments")
public class ControllerPaymentRecord {

    private final ServicePayment servicePayment;

    @Autowired
    public ControllerPaymentRecord(ServicePayment servicePayment) {
        this.servicePayment = servicePayment;
    }

    @GetMapping("/listPaymentRecords")
    public List<PaymentRecord> listPaymentRecord(){
        return servicePayment.findAll();
    }

    @PostMapping("/addPayment")
    public ResponseEntity<?> savePayment(@RequestBody PaymentRecord paymentRecord ){
        return servicePayment.savePayment(paymentRecord);
    }
}
