package com.example.payroll_service.repository;

import com.example.payroll_service.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository
        extends JpaRepository<Salary, Long> {
    Salary findByEmployeeId(Long userId);
}