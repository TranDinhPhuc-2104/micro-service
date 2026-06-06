package com.example.department_service.Service;


import com.example.department_service.DTO.Request.DepartmentLocationRequest;
import com.example.department_service.DTO.Request.DepartmentRequest;
import com.example.department_service.DTO.Response.DepartmentLocationResponse;
import com.example.department_service.DTO.Response.DepartmentResponse;
import com.example.department_service.Entity.DepartmentLocation;
import com.example.department_service.Entity.Departments;
import com.example.department_service.Mapper.DepartmentMapper;
import com.example.department_service.Mapper.LocationMapper;
import com.example.department_service.Repository.DepartmentsRepo;
import com.example.department_service.Repository.LocationRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {
   LocationRepo repo;
    LocationMapper mapper;

    public DepartmentLocationResponse createNew(DepartmentLocationRequest request){
        DepartmentLocation departments = mapper.toLocation(request);
        repo.save(departments);
        return mapper.toLocationResponse(departments);
    }

    public List<DepartmentLocationResponse> getAll(){
        List<DepartmentLocation> list = repo.findAll();
        return list.stream().map(mapper::toLocationResponse).toList();
    }

    public DepartmentLocationResponse getById(int id){
        DepartmentLocation departments = repo.findById(id).orElseThrow(()-> new RuntimeException("ko tim thay"));

        return mapper.toLocationResponse(departments);
    }


    public void delete(int id){
        repo.deleteById(id);
    }

}
