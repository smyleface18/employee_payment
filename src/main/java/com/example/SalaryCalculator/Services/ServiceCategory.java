package com.example.SalaryCalculator.Services;

import com.example.SalaryCalculator.entities.Category;
import com.example.SalaryCalculator.repositories.IRepositoryCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCategory {

    private IRepositoryCategory iRepositoryCategory;

    @Autowired
    public ServiceCategory(IRepositoryCategory iRepositoryCategory) {
        this.iRepositoryCategory = iRepositoryCategory;
    }

    public Optional<Category> searchById(Long id){
      return  iRepositoryCategory.findById(id);
    }

    public List<Category> findAll(){
        return iRepositoryCategory.findAll();
    }

    public ResponseEntity<?> save(Category category){
        try {
            iRepositoryCategory.save(category);
            return ResponseEntity.ok(Collections.singletonMap("message","Se creo el cargo correctamente"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Collections.singletonMap("message","No se pudo crear el cargo"));
        }
    }
}
