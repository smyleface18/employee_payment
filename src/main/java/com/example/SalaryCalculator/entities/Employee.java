package com.example.SalaryCalculator.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String lastname;


    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String img;


    public Employee(String name, String lastname, Category category) {
        this.name = name;
        this.lastname = lastname;
        this.category = category;
    }
}
