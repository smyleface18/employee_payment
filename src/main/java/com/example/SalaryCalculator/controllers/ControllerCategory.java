package com.example.SalaryCalculator.controllers;


import com.example.SalaryCalculator.Services.ServiceCategory;
import com.example.SalaryCalculator.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Category")
public class ControllerCategory {

    private final ServiceCategory serviceCategory;

    @Autowired
    public ControllerCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    @GetMapping("/listCategorys")
    public List<Category> listCategory(){
        return serviceCategory.findAll();
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        return serviceCategory.save(category);
    }
}
