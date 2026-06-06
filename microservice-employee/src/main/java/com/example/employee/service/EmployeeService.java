package com.example.employee.service;

import com.example.employee.model.dto.EmployeeDTO;
import com.example.employee.model.dto.EmployeeResponse;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeResponse getEmployeeById(Long id);
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
    
    boolean validateEmployee(Long id);
    Map<Long, Map<String, String>> getEmployeesSummary(List<Long> employeeIds);
}
