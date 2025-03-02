package com.example.SalaryCalculator.controllers;

import com.example.SalaryCalculator.Services.ServiceCategory;
import com.example.SalaryCalculator.Services.ServiceEmployee;
import com.example.SalaryCalculator.entities.Category;
import com.example.SalaryCalculator.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("Employee")
public class ControllerEmployee {

    private ServiceEmployee serviceEmployee;
    private ServiceCategory serviceCategory;

    @Autowired
    public ControllerEmployee(ServiceCategory serviceCategory, ServiceEmployee serviceEmployee) {
        this.serviceCategory = serviceCategory;
        this.serviceEmployee = serviceEmployee;
    }

    @GetMapping("/status")
    public String status(){
        return "good";
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){

         employee.setCategory(serviceCategory.searchById(employee.getCategory().getId()).orElse(null));
       return serviceEmployee.saveEmployee(employee);
    }

    @GetMapping("/listEmployees")
    public ArrayList<Employee> listEmployees(){
        return serviceEmployee.listEmployee();
    }

    @GetMapping("/Employee/{id}")
    public Employee getEmployee(@PathVariable Long id){
        return serviceEmployee.findById(id);
    }

}
