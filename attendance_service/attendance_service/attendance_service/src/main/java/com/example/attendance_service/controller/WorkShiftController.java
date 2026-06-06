package com.example.attendance_service.controller;

import com.example.attendance_service.entity.OvertimeRecord;
import com.example.attendance_service.entity.WorkShift;
import com.example.attendance_service.service.WorkShiftService;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-shifts")
public class WorkShiftController {

    @Autowired
    private WorkShiftService workShiftService;

    @GetMapping
    public List<WorkShift> getAllWorkShifts() {
        return workShiftService.getAllWorkShifts();
    }

    @GetMapping("/{id}")
    public WorkShift getWorkShiftById(@PathVariable Long id) {
        return workShiftService.getWorkShiftById(id);
    }

    @PostMapping
    public WorkShift createWorkShift(@RequestBody WorkShift workShift) {
        return workShiftService.createWorkShift(workShift);
    }

    @PutMapping("/{id}")
    public WorkShift updateWorkShift(
            @PathVariable Long id,
            @RequestBody WorkShift workShift) {

        return workShiftService.updateWorkShift(id, workShift);
    }

    @DeleteMapping("/{id}")
    public String deleteWorkShift(@PathVariable Long id) {

        workShiftService.deleteWorkShift(id);

        return "Work shift deleted successfully";
    }
}