package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.PositionServiceImpl;
import com.example.SalaryCalculator.entities.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionServiceImpl service;

    @GetMapping
    public ResponseEntity<List<Position>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Position> create(@RequestBody Position position) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(position));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> update(@PathVariable Long id, @RequestBody Position position) {
        return ResponseEntity.ok(service.update(id, position));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
