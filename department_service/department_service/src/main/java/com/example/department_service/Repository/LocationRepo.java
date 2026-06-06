package com.example.department_service.Repository;

import com.example.department_service.Entity.DepartmentLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends JpaRepository<DepartmentLocation , Integer> {
}
