package com.example.employee.repository;

import com.example.employee.model.entity.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, Long> {
    EmployeeAddress findByEmployee_Id(Long id);
}
