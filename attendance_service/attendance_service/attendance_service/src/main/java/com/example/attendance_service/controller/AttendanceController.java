package com.example.attendance_service.controller;

import com.example.attendance_service.entity.AttendanceRecord;
import com.example.attendance_service.entity.OvertimeRecord;
import com.example.attendance_service.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public List<AttendanceRecord> getAllAttendanceRecords() {
        return attendanceService.getAllAttendanceRecords();
    }

    @GetMapping("/{id}")
    public AttendanceRecord getAttendanceRecordById(@PathVariable Long id) {
        return attendanceService.getAttendanceRecordById(id);
    }

    @PostMapping
    public AttendanceRecord createAttendanceRecord(
            @RequestBody AttendanceRecord attendanceRecord) {

        return attendanceService.createAttendanceRecord(attendanceRecord);
    }

    @PutMapping("/{id}")
    public AttendanceRecord updateAttendanceRecord(
            @PathVariable Long id,
            @RequestBody AttendanceRecord attendanceRecord) {

        return attendanceService.updateAttendanceRecord(id, attendanceRecord);
    }

    @DeleteMapping("/{id}")
    public String deleteAttendanceRecord(@PathVariable Long id) {

        attendanceService.deleteAttendanceRecord(id);

        return "Attendance record deleted successfully";
    }

    @GetMapping("/myAttendance")
    public AttendanceRecord getMyAttendance(){
        return attendanceService.getMyAttendance();
    }
}