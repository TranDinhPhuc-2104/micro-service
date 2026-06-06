package com.example.department_service.Mapper;

import com.example.department_service.DTO.Request.DepartmentLocationRequest;
import com.example.department_service.DTO.Response.DepartmentLocationResponse;
import com.example.department_service.DTO.Response.DepartmentResponse;
import com.example.department_service.Entity.DepartmentLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "department.id", source = "department_id")
    DepartmentLocation toLocation(DepartmentLocationRequest request);

    @Mapping(target = "department_id", source = "department.id")
    DepartmentLocationResponse toLocationResponse(DepartmentLocation location);
}
