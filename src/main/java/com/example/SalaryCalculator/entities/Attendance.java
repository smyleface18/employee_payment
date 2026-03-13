package com.example.SalaryCalculator.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"employee", "payPeriod"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attendance")
    @EqualsAndHashCode.Include
    private Long idAttendance;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "hours_worked", nullable = false, precision = 5, scale = 2)
    private BigDecimal hoursWorked;

    // ── Relationships ────────────────────────────────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee", nullable = false)
    private Employee employee;

    /**
     * Improvement: linking attendance to a pay period allows
     * accurate hour aggregation per payroll cycle.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_period")
    private PayPeriod payPeriod;
}
