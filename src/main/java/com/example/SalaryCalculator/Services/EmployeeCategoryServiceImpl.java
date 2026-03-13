package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.EmployeeCategory;
import com.example.SalaryCalculator.repositories.EmployeeCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeCategoryServiceImpl {

    private final EmployeeCategoryRepository repository;


    @Transactional
    public EmployeeCategory create(EmployeeCategory category) {
        log.info("Creating employee category: {}", category.getCategoryName());
        if (repository.existsByCategoryNameIgnoreCase(category.getCategoryName())) {
            throw new BusinessException("Category name already exists: " + category.getCategoryName());
        }
        return repository.save(category);
    }


    @Transactional
    public EmployeeCategory update(Long id, EmployeeCategory category) {
        log.info("Updating employee category id: {}", id);
        EmployeeCategory existing = findById(id);
        repository.findByCategoryNameIgnoreCase(category.getCategoryName())
                .filter(c -> !c.getIdCategory().equals(id))
                .ifPresent(c -> { throw new BusinessException("Category name already in use: " + category.getCategoryName()); });

        existing.setCategoryName(category.getCategoryName());
        existing.setHourlyRate(category.getHourlyRate());
        existing.setDescription(category.getDescription());
        return repository.save(existing);
    }


    @Transactional(readOnly = true)
    public EmployeeCategory findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeCategory", id));
    }


    @Transactional(readOnly = true)
    public List<EmployeeCategory> findAll() {
        return repository.findAllByOrderByCategoryNameAsc();
    }


    @Transactional
    public void delete(Long id) {
        log.info("Deleting employee category id: {}", id);
        EmployeeCategory category = findById(id);
        if (category.getEmployees() != null && !category.getEmployees().isEmpty()) {
            throw new BusinessException("Cannot delete category with assigned employees.");
        }
        repository.delete(category);
    }
}
