package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.Department;
import com.example.SalaryCalculator.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl  {

    private final DepartmentRepository repository;


    @Transactional
    public Department create(Department department) {
        log.info("Creating department: {}", department.getDepartmentName());
        if (repository.existsByDepartmentNameIgnoreCase(department.getDepartmentName())) {
            throw new BusinessException("Department name already exists: " + department.getDepartmentName());
        }
        return repository.save(department);
    }


    @Transactional
    public Department update(Long id, Department department) {
        log.info("Updating department id: {}", id);
        Department existing = findById(id);
        repository.findByDepartmentNameIgnoreCase(department.getDepartmentName())
                .filter(d -> !d.getIdDepartment().equals(id))
                .ifPresent(d -> { throw new BusinessException("Department name already in use: " + department.getDepartmentName()); });

        existing.setDepartmentName(department.getDepartmentName());
        return repository.save(existing);
    }


    @Transactional(readOnly = true)
    public Department findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
    }


    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return repository.findAllByOrderByDepartmentNameAsc();
    }


    @Transactional
    public void delete(Long id) {
        log.info("Deleting department id: {}", id);
        Department department = findById(id);
        if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
            throw new BusinessException("Cannot delete department with assigned employees.");
        }
        repository.delete(department);
    }
}
