package com.example.department_service.Controller;

import com.example.department_service.DTO.Request.DepartmentLocationRequest;
import com.example.department_service.DTO.Request.DepartmentRequest;
import com.example.department_service.DTO.Response.ApiResponse;
import com.example.department_service.DTO.Response.DepartmentLocationResponse;
import com.example.department_service.DTO.Response.DepartmentResponse;
import com.example.department_service.Service.DepartmentService;
import com.example.department_service.Service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/location")
public class LocationController {
    LocationService service;

    @PostMapping
    public ApiResponse<DepartmentLocationResponse> createNew(@RequestBody DepartmentLocationRequest request){
        return ApiResponse.<DepartmentLocationResponse>builder()
                .result(service.createNew(request))
                .code(200)
                .build();
    }

    @GetMapping
    public ApiResponse<List<DepartmentLocationResponse>> getAll(){
        return ApiResponse.<List<DepartmentLocationResponse>>builder()
                .result(service.getAll())
                .code(200)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DepartmentLocationResponse> getById(@PathVariable int id){
        return ApiResponse.<DepartmentLocationResponse>builder()
                .result(service.getById(id))
                .code(200)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteById(@PathVariable int id){
        service.delete(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .build();
    }

}
