package com.example.attendance_service.repository;

import com.example.attendance_service.entity.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkShiftRepository extends JpaRepository<WorkShift, Long> {

}