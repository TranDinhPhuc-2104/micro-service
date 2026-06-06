package com.example.attendance_service.service;

import com.example.attendance_service.entity.AttendanceRecord;
import com.example.attendance_service.repository.AttendanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceRecordRepository.findAll();
    }

    public AttendanceRecord getAttendanceRecordById(Long id) {
        return attendanceRecordRepository.findById(id).orElse(null);
    }

    public AttendanceRecord createAttendanceRecord(AttendanceRecord attendanceRecord) {
        return attendanceRecordRepository.save(attendanceRecord);
    }

    public AttendanceRecord updateAttendanceRecord(Long id, AttendanceRecord attendanceRecord) {
        attendanceRecord.setId(id);
        return attendanceRecordRepository.save(attendanceRecord);
    }

    public void deleteAttendanceRecord(Long id) {
        attendanceRecordRepository.deleteById(id);
    }

    public AttendanceRecord getMyAttendance(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Number number = jwt.getClaim("id");
        Long id = number.longValue();
        AttendanceRecord record = attendanceRecordRepository.getAttendanceRecordsByEmployeeId(id);
        return record;
    }
}