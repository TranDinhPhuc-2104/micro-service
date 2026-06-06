package com.example.payroll_service.service;


import com.example.payroll_service.entity.Salary;
import com.example.payroll_service.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public Salary getSalaryById(Long id) {
        return salaryRepository.findById(id).orElse(null);
    }

    public Salary createSalary(Salary salary) {
        return salaryRepository.save(salary);
    }

    public Salary updateSalary(Long id, Salary salary) {
        salary.setId(id);
        return salaryRepository.save(salary);
    }

    public Salary getMySalary(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        Number claimId = jwt.getClaim("id");
        Long id = claimId.longValue();

        Salary salary = salaryRepository.findByEmployeeId(id);
        return salary;
    }

    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }
}
