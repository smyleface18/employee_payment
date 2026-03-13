package com.example.SalaryCalculator.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "deduction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "payrollDeductions")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Deduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deduction")
    @EqualsAndHashCode.Include
    private Long idDeduction;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentage;

    @OneToMany(mappedBy = "deduction", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PayrollDeduction> payrollDeductions;
}
