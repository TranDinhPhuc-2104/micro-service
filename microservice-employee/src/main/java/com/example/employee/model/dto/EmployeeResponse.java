package com.example.employee.model.dto;

import java.time.LocalDate;
import java.util.List;

public class EmployeeResponse {
    private Long id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDate hireDate;
    private String position;
    private String status;
    private List<EmployeeAddressDTO> addresses;
    private DepartmentExternalDTO department;

    public EmployeeResponse() {}

    public EmployeeResponse(Long id, String employeeCode, String firstName, String lastName, String email, String phone, 
                            String gender, LocalDate dateOfBirth, LocalDate hireDate, String position, String status, 
                            List<EmployeeAddressDTO> addresses, DepartmentExternalDTO department) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.hireDate = hireDate;
        this.position = position;
        this.status = status;
        this.addresses = addresses;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EmployeeAddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<EmployeeAddressDTO> addresses) {
        this.addresses = addresses;
    }

    public DepartmentExternalDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentExternalDTO department) {
        this.department = department;
    }
}
