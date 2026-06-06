package com.example.payroll_service.repository;

import com.example.payroll_service.entity.Payslip;
import com.example.payroll_service.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayslipRepository extends JpaRepository<Payslip, Long> {
    Payslip findByEmployeeId(Long userId);
}