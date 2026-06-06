package com.example.payroll_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bonuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "bonus_amount", precision = 12, scale = 2)
    private BigDecimal bonusAmount;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "bonus_date")
    private LocalDate bonusDate;
}