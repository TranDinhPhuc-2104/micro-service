package com.example.employee.service;

import com.example.employee.client.adapter.DepartmentAdapter;
import com.example.employee.model.dto.DepartmentExternalDTO;
import com.example.employee.model.dto.EmployeeAddressDTO;
import com.example.employee.model.dto.EmployeeDTO;
import com.example.employee.model.dto.EmployeeResponse;
import com.example.employee.model.entity.Employee;
import com.example.employee.model.entity.EmployeeAddress;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentAdapter departmentAdapter;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentAdapter departmentAdapter) {
        this.employeeRepository = employeeRepository;
        this.departmentAdapter = departmentAdapter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        
        EmployeeResponse response = convertToResponse(employee);
        
        if (employee.getDepartmentId() != null) {
            DepartmentExternalDTO dept = departmentAdapter.getDepartment(employee.getDepartmentId());
            response.setDepartment(dept);
        }
        
        return response;
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsByEmployeeCode(employeeDTO.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists: " + employeeDTO.getEmployeeCode());
        }
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + employeeDTO.getEmail());
        }
        
        Employee employee = convertToEntity(employeeDTO);
        Employee saved = employeeRepository.save(employee);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existingEmployee.setFirstName(employeeDTO.getFirstName());
        existingEmployee.setLastName(employeeDTO.getLastName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setPhone(employeeDTO.getPhone());
        existingEmployee.setGender(employeeDTO.getGender());
        existingEmployee.setDateOfBirth(employeeDTO.getDateOfBirth());
        existingEmployee.setHireDate(employeeDTO.getHireDate());
        existingEmployee.setDepartmentId(employeeDTO.getDepartmentId());
        existingEmployee.setPosition(employeeDTO.getPosition());
        
        if (employeeDTO.getStatus() != null) {
            existingEmployee.setStatus(employeeDTO.getStatus());
        }

        existingEmployee.getAddresses().clear();
        if (employeeDTO.getAddresses() != null) {
            for (EmployeeAddressDTO addressDTO : employeeDTO.getAddresses()) {
                EmployeeAddress address = new EmployeeAddress();
                address.setId(addressDTO.getId());
                address.setStreet(addressDTO.getStreet());
                address.setCity(addressDTO.getCity());
                address.setCountry(addressDTO.getCountry());
                address.setPostalCode(addressDTO.getPostalCode());
                address.setEmployee(existingEmployee);
                existingEmployee.getAddresses().add(address);
            }
        }

        Employee saved = employeeRepository.save(existingEmployee);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateEmployee(Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        return employeeOpt.isPresent() && "ACTIVE".equalsIgnoreCase(employeeOpt.get().getStatus());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Map<String, String>> getEmployeesSummary(List<Long> employeeIds) {
        Map<Long, Map<String, String>> result = new HashMap<>();
        if (employeeIds == null || employeeIds.isEmpty()) {
            return result;
        }

        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        for (Employee employee : employees) {
            Map<String, String> summary = new HashMap<>();
            summary.put("id", String.valueOf(employee.getId()));
            summary.put("employee_code", employee.getEmployeeCode());
            summary.put("full_name", employee.getFirstName() + " " + employee.getLastName());
            result.put(employee.getId(), summary);
        }

        return result;
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        if (employee == null) return null;
        
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmployeeCode(employee.getEmployeeCode());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setGender(employee.getGender());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setHireDate(employee.getHireDate());
        dto.setDepartmentId(employee.getDepartmentId());
        dto.setPosition(employee.getPosition());
        dto.setStatus(employee.getStatus());
        
        if (employee.getAddresses() != null) {
            List<EmployeeAddressDTO> addressDTOs = employee.getAddresses().stream()
                    .map(this::convertAddressToDTO)
                    .toList();
            dto.setAddresses(addressDTOs);
        }
        
        return dto;
    }

    private EmployeeAddressDTO convertAddressToDTO(EmployeeAddress address) {
        if (address == null) return null;
        return new EmployeeAddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode()
        );
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        if (dto == null) return null;
        
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setEmployeeCode(dto.getEmployeeCode());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setGender(dto.getGender());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setHireDate(dto.getHireDate());
        employee.setDepartmentId(dto.getDepartmentId());
        employee.setPosition(dto.getPosition());
        if (dto.getStatus() != null) {
            employee.setStatus(dto.getStatus());
        }
        
        if (dto.getAddresses() != null) {
            List<EmployeeAddress> addresses = dto.getAddresses().stream()
                    .map(addressDTO -> {
                        EmployeeAddress address = new EmployeeAddress();
                        address.setId(addressDTO.getId());
                        address.setStreet(addressDTO.getStreet());
                        address.setCity(addressDTO.getCity());
                        address.setCountry(addressDTO.getCountry());
                        address.setPostalCode(addressDTO.getPostalCode());
                        address.setEmployee(employee);
                        return address;
                    })
                    .toList();
            employee.setAddresses(addresses);
        }
        
        return employee;
    }

    private EmployeeResponse convertToResponse(Employee employee) {
        if (employee == null) return null;
        
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setEmployeeCode(employee.getEmployeeCode());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());
        response.setGender(employee.getGender());
        response.setDateOfBirth(employee.getDateOfBirth());
        response.setHireDate(employee.getHireDate());
        response.setPosition(employee.getPosition());
        response.setStatus(employee.getStatus());
        
        if (employee.getAddresses() != null) {
            List<EmployeeAddressDTO> addressDTOs = employee.getAddresses().stream()
                    .map(this::convertAddressToDTO)
                    .toList();
            response.setAddresses(addressDTOs);
        }
        
        return response;
    }


}
