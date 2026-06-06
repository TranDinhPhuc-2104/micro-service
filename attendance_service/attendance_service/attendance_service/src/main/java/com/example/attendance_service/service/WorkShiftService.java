package com.example.attendance_service.service;

import com.example.attendance_service.entity.WorkShift;
import com.example.attendance_service.repository.WorkShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkShiftService {

    @Autowired
    private WorkShiftRepository workShiftRepository;

    public List<WorkShift> getAllWorkShifts() {
        return workShiftRepository.findAll();
    }

    public WorkShift getWorkShiftById(Long id) {
        return workShiftRepository.findById(id).orElse(null);
    }

    public WorkShift createWorkShift(WorkShift workShift) {
        return workShiftRepository.save(workShift);
    }

    public WorkShift updateWorkShift(Long id, WorkShift workShift) {
        workShift.setId(id);
        return workShiftRepository.save(workShift);
    }

    public void deleteWorkShift(Long id) {
        workShiftRepository.deleteById(id);
    }

}