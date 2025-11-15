package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.Employee;
import com.example.SalaryCalculator.repositories.IRepositoryEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class ServiceEmployee {

    private final IRepositoryEmployee iRepositoryEmployee;
    private  final ServicePayment servicePayment;
    @Autowired
    public ServiceEmployee(IRepositoryEmployee iRepositoryEmployee, ServicePayment servicePayment) {

        this.iRepositoryEmployee = iRepositoryEmployee;
        this.servicePayment = servicePayment;
    }

    public ResponseEntity<?> saveEmployee(Employee employee){
        try {
            iRepositoryEmployee.save(employee);
            return ResponseEntity.ok().body(Collections.singletonMap("message","Se registro el empleado correctamente"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("message","No se puedo registrar el empleado"));
        }
    }

    public ArrayList<Employee> listEmployee(){
        return (ArrayList<Employee>) iRepositoryEmployee.findAll();
    }

    public Employee findById(String id){
        Employee employee = iRepositoryEmployee.findById(id).orElse(null);

        if (employee != null) {
            employee.setPayments(this.servicePayment.findByEmployee(id));
        }

        return  employee;


    }
}
