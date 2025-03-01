package com.example.SalaryCalculator.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String identityNumber;

    @Column
    private String name;

    @Column
    private String lastname;


    @OneToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @Column
    private String img;

    @OneToMany
    @JoinColumn(name = "id_employee")
    @JsonIgnoreProperties("employee")
    private List<PaymentRecord> payments;


    public Employee( String identityNumber, String lastname, String name) {
        this.identityNumber = identityNumber;
        this.lastname = lastname;
        this.name = name;
    }

}
