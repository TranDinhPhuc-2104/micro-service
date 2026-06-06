package com.example.payroll_service.controller;

import com.example.payroll_service.entity.Salary;
import com.example.payroll_service.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @GetMapping("/{id}")
    public Salary getSalaryById(@PathVariable Long id) {
        return salaryService.getSalaryById(id);
    }

    @PostMapping
    public Salary createSalary(@RequestBody Salary salary) {
        return salaryService.createSalary(salary);
    }

    @PutMapping("/{id}")
    public Salary updateSalary(
            @PathVariable Long id,
            @RequestBody Salary salary) {

        return salaryService.updateSalary(id, salary);
    }

    @DeleteMapping("/{id}")
    public String deleteSalary(@PathVariable Long id) {

        salaryService.deleteSalary(id);

        return "Salary deleted successfully";
    }

    @GetMapping("/mysalary")
    public Salary getMySalary(){
        return salaryService.getMySalary();
    }

}