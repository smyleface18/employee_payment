package com.example.SalaryCalculator.repositories;


import com.example.SalaryCalculator.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryCategory extends MongoRepository<Category, String> {

}