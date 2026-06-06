package com.example.department_service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentLocationResponse {
    private int id;
    private int department_id;
    private String officeName;
    private String floor;
    private String building;
}
