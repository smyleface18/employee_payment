package com.example.SalaryCalculator.entities;

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
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @Column
    private Long hour;

    @Column
    private Double money;


}
