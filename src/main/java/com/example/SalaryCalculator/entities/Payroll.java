package com.example.SalaryCalculator.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "payroll")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"employee", "payPeriod", "deductions"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payroll")
    @EqualsAndHashCode.Include
    private Long idPayroll;

    @Column(name = "total_hours_worked", nullable = false, precision = 7, scale = 2)
    private BigDecimal totalHoursWorked;

    @Column(name = "regular_hours", nullable = false, precision = 7, scale = 2)
    private BigDecimal regularHours;

    @Column(name = "single_overtime_hours", precision = 7, scale = 2)
    private BigDecimal singleOvertimeHours;

    @Column(name = "double_overtime_hours", precision = 7, scale = 2)
    private BigDecimal doubleOvertimeHours;

    @Column(name = "gross_salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal grossSalary;

    // ── Relationships ────────────────────────────────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_period", nullable = false)
    private PayPeriod payPeriod;

    @OneToMany(mappedBy = "payroll", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PayrollDeduction> deductions;
}
