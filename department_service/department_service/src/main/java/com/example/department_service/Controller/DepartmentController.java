package com.example.department_service.Controller;

import com.example.department_service.DTO.Request.DepartmentRequest;
import com.example.department_service.DTO.Response.ApiResponse;
import com.example.department_service.DTO.Response.DepartmentResponse;
import com.example.department_service.Service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    DepartmentService service;

    @PostMapping
    public ApiResponse<DepartmentResponse> createNew(@RequestBody DepartmentRequest request){
        return ApiResponse.<DepartmentResponse>builder()
                .result(service.createNew(request))
                .code(200)
                .build();
    }

    @GetMapping
    public ApiResponse<List<DepartmentResponse>> getAll(){
        return ApiResponse.<List<DepartmentResponse>>builder()
                .result(service.getAll())
                .code(200)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DepartmentResponse> getById(@PathVariable int id){
        return ApiResponse.<DepartmentResponse>builder()
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
