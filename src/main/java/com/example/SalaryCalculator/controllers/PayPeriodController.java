package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.PayPeriodServiceImpl;
import com.example.SalaryCalculator.entities.PayPeriod;
import com.example.SalaryCalculator.entities.PeriodStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pay-periods")
@RequiredArgsConstructor
public class PayPeriodController {

    private final PayPeriodServiceImpl service;

    @GetMapping
    public ResponseEntity<List<PayPeriod>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayPeriod> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PayPeriod>> findByStatus(@PathVariable PeriodStatus status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/current-open")
    public ResponseEntity<PayPeriod> findCurrentOpen() {
        return service.findCurrentOpen()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<PayPeriod> create(@RequestBody PayPeriod payPeriod) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(payPeriod));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayPeriod> update(@PathVariable Long id, @RequestBody PayPeriod payPeriod) {
        return ResponseEntity.ok(service.update(id, payPeriod));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<PayPeriod> close(@PathVariable Long id) {
        return ResponseEntity.ok(service.closePeriod(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
