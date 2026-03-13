package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.DeductionServiceImpl;
import com.example.SalaryCalculator.entities.Deduction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deductions")
@RequiredArgsConstructor
public class DeductionController {

    private final DeductionServiceImpl service;

    @GetMapping
    public ResponseEntity<List<Deduction>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deduction> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Deduction> create(@RequestBody Deduction deduction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(deduction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deduction> update(@PathVariable Long id, @RequestBody Deduction deduction) {
        return ResponseEntity.ok(service.update(id, deduction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
