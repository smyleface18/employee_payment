package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.PayrollServiceImpl;
import com.example.SalaryCalculator.entities.Payroll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollServiceImpl service;

    @GetMapping("/period/{periodId}")
    public ResponseEntity<List<Payroll>> findByPeriod(@PathVariable Long periodId) {
        return ResponseEntity.ok(service.findByPeriod(periodId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Payroll>> findByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.findByEmployee(employeeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payroll> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /** Generate payroll for a single employee in a period */
    @PostMapping("/generate/employee/{employeeId}/period/{periodId}")
    public ResponseEntity<Payroll> generateForEmployee(
            @PathVariable Long employeeId, @PathVariable Long periodId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.generateForEmployee(employeeId, periodId));
    }

    /** Generate payroll for ALL employees in a period */
    @PostMapping("/generate/period/{periodId}")
    public ResponseEntity<List<Payroll>> generateForPeriod(@PathVariable Long periodId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.generateForPeriod(periodId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
