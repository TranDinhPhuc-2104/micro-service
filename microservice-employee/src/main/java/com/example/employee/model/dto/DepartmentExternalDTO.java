package com.example.employee.model.dto;

public class DepartmentExternalDTO {
    private Long id;
    private String name;
    private String description;
    private Long managerId;
    private String officeName;
    private String floor;
    private String building;

    public DepartmentExternalDTO() {}

    public DepartmentExternalDTO(Long id, String name, String description, Long managerId, String officeName, String floor, String building) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.managerId = managerId;
        this.officeName = officeName;
        this.floor = floor;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
