package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.Deduction;
import com.example.SalaryCalculator.repositories.DeductionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeductionServiceImpl {

    private final DeductionRepository repository;


    @Transactional
    public Deduction create(Deduction deduction) {
        log.info("Creating deduction: {}", deduction.getName());
        if (repository.existsByNameIgnoreCase(deduction.getName())) {
            throw new BusinessException("Deduction name already exists: " + deduction.getName());
        }
        return repository.save(deduction);
    }


    @Transactional
    public Deduction update(Long id, Deduction deduction) {
        log.info("Updating deduction id: {}", id);
        Deduction existing = findById(id);
        repository.findByNameIgnoreCase(deduction.getName())
                .filter(d -> !d.getIdDeduction().equals(id))
                .ifPresent(d -> { throw new BusinessException("Deduction name already in use: " + deduction.getName()); });

        existing.setName(deduction.getName());
        existing.setPercentage(deduction.getPercentage());
        return repository.save(existing);
    }


    @Transactional(readOnly = true)
    public Deduction findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deduction", id));
    }


    @Transactional(readOnly = true)
    public List<Deduction> findAll() {
        return repository.findAllByOrderByNameAsc();
    }


    @Transactional
    public void delete(Long id) {
        log.info("Deleting deduction id: {}", id);
        Deduction deduction = findById(id);
        if (deduction.getPayrollDeductions() != null && !deduction.getPayrollDeductions().isEmpty()) {
            throw new BusinessException("Cannot delete deduction already applied to payrolls.");
        }
        repository.delete(deduction);
    }
}
