package com.example.attendance_service.repository;

import com.example.attendance_service.entity.OvertimeRecord;
import com.example.attendance_service.entity.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OvertimeRecordRepository extends JpaRepository<OvertimeRecord, Long> {
    OvertimeRecord getWorkShiftByEmployeeId(Long id);
}