package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.Category;
import com.example.SalaryCalculator.repositories.IRepositoryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceCategory {

    private IRepositoryCategory iRepositoryCategory;

    @Autowired
    public ServiceCategory(IRepositoryCategory iRepositoryCategory) {
        this.iRepositoryCategory = iRepositoryCategory;
    }

    public Optional<Category> searchById(Long id){
        System.out.println(iRepositoryCategory.findById(id));
      return  iRepositoryCategory.findById(id);
    }
}
