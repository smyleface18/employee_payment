package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.EmployeeCategoryServiceImpl;
import com.example.SalaryCalculator.entities.EmployeeCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class EmployeeCategoryController {

    private final EmployeeCategoryServiceImpl service;

    @GetMapping
    public ResponseEntity<List<EmployeeCategory>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeCategory> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeCategory> create(@RequestBody EmployeeCategory category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeCategory> update(@PathVariable Long id, @RequestBody EmployeeCategory category) {
        return ResponseEntity.ok(service.update(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
