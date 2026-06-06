package com.example.employee.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class DepartmentClient {

    private final RestClient restClient;

    public DepartmentClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ExternalDepartmentResponse getDepartmentById(Long id) {
        return restClient.get()
                .uri("/departments/{id}", id)
                .retrieve()
                .body(ExternalDepartmentResponse.class);
    }

    public static class ExternalDepartmentResponse {
        private Long id;
        private String deptName;
        private String description;
        private Long managerId;
        private List<ExternalLocation> locations;

        public ExternalDepartmentResponse() {}

        public ExternalDepartmentResponse(Long id, String deptName, String description, Long managerId, List<ExternalLocation> locations) {
            this.id = id;
            this.deptName = deptName;
            this.description = description;
            this.managerId = managerId;
            this.locations = locations;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getManagerId() {
            return managerId;
        }

        public void setManagerId(Long managerId) {
            this.managerId = managerId;
        }

        public List<ExternalLocation> getLocations() {
            return locations;
        }

        public void setLocations(List<ExternalLocation> locations) {
            this.locations = locations;
        }
    }

    public static class ExternalLocation {
        private String officeName;
        private String floor;
        private String building;

        public ExternalLocation() {}

        public ExternalLocation(String officeName, String floor, String building) {
            this.officeName = officeName;
            this.floor = floor;
            this.building = building;
        }

        public String getOfficeName() {
            return officeName;
        }

        public void setOfficeName(String officeName) {
            this.officeName = officeName;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }
    }
}
