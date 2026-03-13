package com.example.SalaryCalculator.repositories;

import com.example.SalaryCalculator.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByDocumentNumber(String documentNumber);

    boolean existsByDocumentNumber(String documentNumber);

    List<Employee> findByDepartmentIdDepartment(Long idDepartment);

    List<Employee> findByPositionIdPosition(Long idPosition);

    List<Employee> findByCategoryIdCategory(Long idCategory);

    @Query("SELECT e FROM Employee e " +
           "WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(e.lastName)  LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(e.documentNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT e FROM Employee e " +
           "JOIN FETCH e.category " +
           "JOIN FETCH e.position " +
           "JOIN FETCH e.department " +
           "WHERE e.idEmployee = :id")
    Optional<Employee> findByIdWithDetails(@Param("id") Long id);

    @Query("""
SELECT e 
FROM Employee e
JOIN FETCH e.position
JOIN FETCH e.department
JOIN FETCH e.category
ORDER BY e.lastName, e.firstName
""")
    List<Employee> findAllByOrderByLastNameAscFirstNameAs();
}
