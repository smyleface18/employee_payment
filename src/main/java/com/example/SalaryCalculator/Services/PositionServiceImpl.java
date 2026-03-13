package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.Position;
import com.example.SalaryCalculator.repositories.PositionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PositionServiceImpl {

    private final PositionRepository repository;


    @Transactional
    public Position create(Position position) {
        log.info("Creating position: {}", position.getPositionName());
        if (repository.existsByPositionNameIgnoreCase(position.getPositionName())) {
            throw new BusinessException("Position name already exists: " + position.getPositionName());
        }
        return repository.save(position);
    }


    @Transactional
    public Position update(Long id, Position position) {
        log.info("Updating position id: {}", id);
        Position existing = findById(id);
        repository.findByPositionNameIgnoreCase(position.getPositionName())
                .filter(p -> !p.getIdPosition().equals(id))
                .ifPresent(p -> { throw new BusinessException("Position name already in use: " + position.getPositionName()); });

        existing.setPositionName(position.getPositionName());
        existing.setDescription(position.getDescription());
        return repository.save(existing);
    }


    @Transactional(readOnly = true)
    public Position findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position", id));
    }


    @Transactional(readOnly = true)
    public List<Position> findAll() {
        return repository.findAllByOrderByPositionNameAsc();
    }


    @Transactional
    public void delete(Long id) {
        log.info("Deleting position id: {}", id);
        Position position = findById(id);
        if (position.getEmployees() != null && !position.getEmployees().isEmpty()) {
            throw new BusinessException("Cannot delete position with assigned employees.");
        }
        repository.delete(position);
    }
}
