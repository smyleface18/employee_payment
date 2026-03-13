package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.AttendanceServiceImpl;
import com.example.SalaryCalculator.entities.Attendance;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceServiceImpl service;

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> findByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.findByEmployee(employeeId));
    }

    @GetMapping("/employee/{employeeId}/period/{periodId}")
    public ResponseEntity<List<Attendance>> findByEmployeeAndPeriod(
            @PathVariable Long employeeId, @PathVariable Long periodId) {
        return ResponseEntity.ok(service.findByEmployeeAndPeriod(employeeId, periodId));
    }

    @GetMapping("/employee/{employeeId}/range")
    public ResponseEntity<List<Attendance>> findByDateRange(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(service.findByEmployeeAndDateRange(employeeId, start, end));
    }

    @GetMapping("/employee/{employeeId}/period/{periodId}/total-hours")
    public ResponseEntity<BigDecimal> getTotalHours(
            @PathVariable Long employeeId, @PathVariable Long periodId) {
        return ResponseEntity.ok(service.getTotalHoursWorked(employeeId, periodId));
    }

    @PostMapping
    public ResponseEntity<Attendance> create(@RequestBody Attendance attendance) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(attendance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> update(@PathVariable Long id, @RequestBody Attendance attendance) {
        return ResponseEntity.ok(service.update(id, attendance));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
