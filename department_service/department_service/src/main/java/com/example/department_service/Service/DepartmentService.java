package com.example.department_service.Service;


import com.example.department_service.DTO.Request.DepartmentRequest;
import com.example.department_service.DTO.Response.DepartmentResponse;
import com.example.department_service.Entity.Departments;
import com.example.department_service.Mapper.DepartmentMapper;
import com.example.department_service.Repository.DepartmentsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {
    DepartmentMapper mapper;
    DepartmentsRepo repo;

    public DepartmentResponse createNew(DepartmentRequest request){
        Departments departments = mapper.toDepartment(request);
        repo.save(departments);
        return mapper.toDepartmentResponse(departments);
    }

    public List<DepartmentResponse> getAll(){
        List<Departments> list = repo.findAll();
        return list.stream().map(mapper::toDepartmentResponse).toList();
    }

    public DepartmentResponse getById(int id){
        Departments departments = repo.findById(id).orElseThrow(()-> new RuntimeException("ko tim thay"));

        return mapper.toDepartmentResponse(departments);
    }


    public void delete(int id){
        repo.deleteById(id);
    }
}
