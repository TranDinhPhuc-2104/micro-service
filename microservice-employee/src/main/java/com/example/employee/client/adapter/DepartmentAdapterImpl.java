package com.example.employee.client.adapter;

import com.example.employee.client.DepartmentClient;
import com.example.employee.model.dto.DepartmentExternalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DepartmentAdapterImpl implements DepartmentAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentAdapterImpl.class);

    private final DepartmentClient departmentClient;

    public DepartmentAdapterImpl(DepartmentClient departmentClient) {
        this.departmentClient = departmentClient;
    }

    @Override
    public DepartmentExternalDTO getDepartment(Long id) {
        if (id == null) {
            return null;
        }

        try {
            DepartmentClient.ExternalDepartmentResponse response = departmentClient.getDepartmentById(id);
            return transform(response);
        } catch (Exception e) {
            logger.warn("Failed to fetch department info for ID {} from external service. Falling back to mock data. Reason: {}", id, e.getMessage());
            return getMockDepartment(id);
        }
    }

    private DepartmentExternalDTO transform(DepartmentClient.ExternalDepartmentResponse response) {
        if (response == null) {
            return null;
        }

        DepartmentExternalDTO dto = new DepartmentExternalDTO();
        dto.setId(response.getId());
        dto.setName(response.getDeptName());
        dto.setDescription(response.getDescription());
        dto.setManagerId(response.getManagerId());

        if (response.getLocations() != null && !response.getLocations().isEmpty()) {
            DepartmentClient.ExternalLocation primaryLoc = response.getLocations().get(0);
            dto.setOfficeName(primaryLoc.getOfficeName());
            dto.setFloor(primaryLoc.getFloor());
            dto.setBuilding(primaryLoc.getBuilding());
        }

        return dto;
    }

    private DepartmentExternalDTO getMockDepartment(Long id) {
        if (id == 1) {
            return new DepartmentExternalDTO(1L, "Engineering", "Software development department", 5L, "Engineering HQ", "5", "Building A");
        } else if (id == 2) {
            return new DepartmentExternalDTO(2L, "Human Resources", "Handles employee relations", 2L, "HR Office", "2", "Building B");
        } else if (id == 3) {
            return new DepartmentExternalDTO(3L, "Finance", "Manages company finances", 4L, "Finance Center", "3", "Building C");
        } else {
            return new DepartmentExternalDTO(id, "Mock Department " + id, "Mock description", 1L, "Mock Office", "1", "Mock Building");
        }
    }
}
