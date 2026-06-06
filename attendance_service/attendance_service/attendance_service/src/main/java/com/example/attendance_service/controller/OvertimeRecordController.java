package com.example.attendance_service.controller;

import com.example.attendance_service.entity.OvertimeRecord;
import com.example.attendance_service.service.OvertimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/overtimes")
public class OvertimeRecordController {

    @Autowired
    private OvertimeRecordService overtimeRecordService;

    @GetMapping
    public List<OvertimeRecord> getAllOvertimeRecords() {
        return overtimeRecordService.getAllOvertimeRecords();
    }

    @GetMapping("/{id}")
    public OvertimeRecord getOvertimeRecordById(@PathVariable Long id) {
        return overtimeRecordService.getOvertimeRecordById(id);
    }

    @PostMapping
    public OvertimeRecord createOvertimeRecord(
            @RequestBody OvertimeRecord overtimeRecord) {

        return overtimeRecordService.createOvertimeRecord(overtimeRecord);
    }

    @PutMapping("/{id}")
    public OvertimeRecord updateOvertimeRecord(
            @PathVariable Long id,
            @RequestBody OvertimeRecord overtimeRecord) {

        return overtimeRecordService.updateOvertimeRecord(id, overtimeRecord);
    }

    @DeleteMapping("/{id}")
    public String deleteOvertimeRecord(@PathVariable Long id) {

        overtimeRecordService.deleteOvertimeRecord(id);

        return "Overtime record deleted successfully";
    }

    @GetMapping("/myRecord")
    public OvertimeRecord getMyOvertime(){
        return overtimeRecordService.getMyOvertime();
    }
}