package com.example.payroll_service.repository;


import com.example.payroll_service.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus, Long> {
    Bonus findByEmployeeId(Long userId);
}
