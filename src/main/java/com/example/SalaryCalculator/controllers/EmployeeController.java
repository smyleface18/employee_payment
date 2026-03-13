package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.EmployeeServiceImpl;
import com.example.SalaryCalculator.entities.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl service;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<Employee> findByDocument(@PathVariable String documentNumber) {
        return ResponseEntity.ok(service.findByDocumentNumber(documentNumber));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> search(@RequestParam String q) {
        return ResponseEntity.ok(service.search(q));
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<List<Employee>> findByDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByDepartment(id));
    }

    @GetMapping("/position/{id}")
    public ResponseEntity<List<Employee>> findByPosition(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByPosition(id));
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(service.update(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
