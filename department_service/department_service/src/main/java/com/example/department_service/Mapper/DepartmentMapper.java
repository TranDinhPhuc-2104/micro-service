package com.example.department_service.Mapper;


import com.example.department_service.DTO.Request.DepartmentRequest;
import com.example.department_service.DTO.Response.DepartmentResponse;
import com.example.department_service.Entity.Departments;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Departments toDepartment(DepartmentRequest request);

    DepartmentResponse toDepartmentResponse(Departments departments);
}
