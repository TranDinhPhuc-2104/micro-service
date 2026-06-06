package com.example.attendance_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "overtime_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "overtime_date")
    private LocalDate overtimeDate;

    @Column(precision = 5, scale = 2)
    private BigDecimal hours;

    @Column(columnDefinition = "TEXT")
    private String reason;
}