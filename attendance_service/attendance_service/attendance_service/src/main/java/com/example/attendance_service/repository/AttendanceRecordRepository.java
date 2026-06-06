package com.example.attendance_service.repository;

import com.example.attendance_service.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    AttendanceRecord getAttendanceRecordsByEmployeeId(Long id);

}