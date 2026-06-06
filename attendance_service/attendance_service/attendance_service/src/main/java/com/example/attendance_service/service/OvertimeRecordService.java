package com.example.attendance_service.service;

import com.example.attendance_service.entity.OvertimeRecord;
import com.example.attendance_service.entity.WorkShift;
import com.example.attendance_service.repository.OvertimeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OvertimeRecordService {

    @Autowired
    private OvertimeRecordRepository overtimeRecordRepository;

    public List<OvertimeRecord> getAllOvertimeRecords() {
        return overtimeRecordRepository.findAll();
    }

    public OvertimeRecord getOvertimeRecordById(Long id) {
        return overtimeRecordRepository.findById(id).orElse(null);
    }

    public OvertimeRecord createOvertimeRecord(OvertimeRecord overtimeRecord) {
        return overtimeRecordRepository.save(overtimeRecord);
    }

    public OvertimeRecord updateOvertimeRecord(Long id, OvertimeRecord overtimeRecord) {
        overtimeRecord.setId(id);
        return overtimeRecordRepository.save(overtimeRecord);
    }

    public void deleteOvertimeRecord(Long id) {
        overtimeRecordRepository.deleteById(id);
    }

    public OvertimeRecord getMyOvertime(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Number number = jwt.getClaim("id");
        Long id = number.longValue();

        OvertimeRecord record = overtimeRecordRepository.getWorkShiftByEmployeeId(id);
        return record;
    }
}