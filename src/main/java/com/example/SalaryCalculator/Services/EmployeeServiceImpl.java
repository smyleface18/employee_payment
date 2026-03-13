package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.Employee;
import com.example.SalaryCalculator.repositories.DepartmentRepository;
import com.example.SalaryCalculator.repositories.EmployeeCategoryRepository;
import com.example.SalaryCalculator.repositories.EmployeeRepository;
import com.example.SalaryCalculator.repositories.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl {

    private final EmployeeRepository employeeRepository;
    private final EmployeeCategoryRepository categoryRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;


    @Transactional
    public Employee create(Employee employee) {
        log.info("Creating employee: {} {}", employee.getFirstName(), employee.getLastName());
        if (employeeRepository.existsByDocumentNumber(employee.getDocumentNumber())) {
            throw new BusinessException("Document number already registered: " + employee.getDocumentNumber());
        }
        validateRelations(employee);
        return employeeRepository.save(employee);
    }


    @Transactional
    public Employee update(Long id, Employee employee) {
        log.info("Updating employee id: {}", id);
        Employee existing = findById(id);

        employeeRepository.findByDocumentNumber(employee.getDocumentNumber())
                .filter(e -> !e.getIdEmployee().equals(id))
                .ifPresent(e -> { throw new BusinessException("Document number already in use: " + employee.getDocumentNumber()); });

        validateRelations(employee);

        existing.setFirstName(employee.getFirstName());
        existing.setLastName(employee.getLastName());
        existing.setDocumentNumber(employee.getDocumentNumber());
        existing.setHireDate(employee.getHireDate());
        existing.setCategory(employee.getCategory());
        existing.setPosition(employee.getPosition());
        existing.setDepartment(employee.getDepartment());
        return employeeRepository.save(existing);
    }


    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }


    @Transactional(readOnly = true)
    public Employee findByDocumentNumber(String documentNumber) {
        return employeeRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with document: " + documentNumber));
    }


    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByLastNameAscFirstNameAs();
    }


    @Transactional(readOnly = true)
    public List<Employee> findByDepartment(Long idDepartment) {
        return employeeRepository.findByDepartmentIdDepartment(idDepartment);
    }


    @Transactional(readOnly = true)
    public List<Employee> findByPosition(Long idPosition) {
        return employeeRepository.findByPositionIdPosition(idPosition);
    }


    @Transactional(readOnly = true)
    public List<Employee> search(String keyword) {
        return employeeRepository.searchByKeyword(keyword);
    }


    @Transactional
    public void delete(Long id) {
        log.info("Deleting employee id: {}", id);
        Employee employee = findById(id);
        if (employee.getPayrolls() != null && !employee.getPayrolls().isEmpty()) {
            throw new BusinessException("Cannot delete employee with existing payroll records.");
        }
        employeeRepository.delete(employee);
    }

    // ── Private helpers ──────────────────────────────────────────
    private void validateRelations(Employee employee) {
        if (employee.getCategory() == null || employee.getCategory().getIdCategory() == null) {
            throw new BusinessException("Employee category is required.");
        }
        if (employee.getPosition() == null || employee.getPosition().getIdPosition() == null) {
            throw new BusinessException("Employee position is required.");
        }
        if (employee.getDepartment() == null || employee.getDepartment().getIdDepartment() == null) {
            throw new BusinessException("Employee department is required.");
        }
        categoryRepository.findById(employee.getCategory().getIdCategory())
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeCategory", employee.getCategory().getIdCategory()));
        positionRepository.findById(employee.getPosition().getIdPosition())
                .orElseThrow(() -> new ResourceNotFoundException("Position", employee.getPosition().getIdPosition()));
        departmentRepository.findById(employee.getDepartment().getIdDepartment())
                .orElseThrow(() -> new ResourceNotFoundException("Department", employee.getDepartment().getIdDepartment()));
    }
}
