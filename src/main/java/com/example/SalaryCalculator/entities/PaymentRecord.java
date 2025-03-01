package com.example.SalaryCalculator.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long hour;

    @Column
    private Double money;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    @JsonIgnoreProperties("payments")
    private Employee employee;

}
