package com.example.employee.controller;

import com.example.employee.model.dto.EmployeeDTO;
import com.example.employee.model.dto.EmployeeResponse;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;
    private final EmployeeServiceImpl service;

    public EmployeeController(EmployeeService employeeService, EmployeeServiceImpl service) {
        this.employeeService = employeeService;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        logger.info("Request received: Fetching all employees");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        logger.info("Request received: Fetching details for employee ID: {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        logger.info("Request received: Creating new employee with code: {}", employeeDTO.getEmployeeCode());
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        logger.info("Request received: Updating employee details for ID: {}", id);
        EmployeeDTO updated = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        logger.info("Request received: Deleting employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/myprofile")
//    public ResponseEntity<EmployeeResponse> getMyProfile(){
//        EmployeeResponse dto = service.getMyProfile();
//        return ResponseEntity.ok(dto);
//    }
}
