package com.example.employee.controller;

import com.example.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/internal/employees")
public class InternalEmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(InternalEmployeeController.class);
    private final EmployeeService employeeService;

    public InternalEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}/validate")
    public ResponseEntity<Boolean> validateEmployee(@PathVariable Long id) {
        logger.info("Internal request received: Validating employee ID: {}", id);
        return ResponseEntity.ok(employeeService.validateEmployee(id));
    }

    @PostMapping("/summary")
    public ResponseEntity<Map<Long, Map<String, String>>> getEmployeesSummary(@RequestBody List<Long> employeeIds) {
        logger.info("Internal request received: Fetching summaries for employee IDs: {}", employeeIds);
        return ResponseEntity.ok(employeeService.getEmployeesSummary(employeeIds));
    }
}
