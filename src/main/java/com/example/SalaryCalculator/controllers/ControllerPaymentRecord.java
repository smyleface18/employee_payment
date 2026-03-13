package com.example.SalaryCalculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
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

    @GetMapping("/listPaymentRecords/{idEmployee}")
    public List<PaymentRecord> listPaymentRecord(@PathVariable String idEmployee){
        return servicePayment.findByEmployee(idEmployee);
    }
}
 */
