package com.example.department_service.DTO.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentLocationRequest {
    private int department_id;
    private String officeName;
    private String floor;
    private String building;
}
