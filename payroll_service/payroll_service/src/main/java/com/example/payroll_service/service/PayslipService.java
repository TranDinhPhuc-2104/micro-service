package com.example.payroll_service.service;

import com.example.payroll_service.entity.Payslip;
import com.example.payroll_service.repository.PayslipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayslipService {

    @Autowired
    private PayslipRepository payslipRepository;

    public List<Payslip> getAllPayslips() {
        return payslipRepository.findAll();
    }

    public Payslip getPayslipById(Long id) {
        return payslipRepository.findById(id).orElse(null);
    }

    public Payslip createPayslip(Payslip payslip) {
        return payslipRepository.save(payslip);
    }

    public Payslip updatePayslip(Long id, Payslip payslip) {
        payslip.setId(id);
        return payslipRepository.save(payslip);
    }

    public void deletePayslip(Long id) {
        payslipRepository.deleteById(id);
    }

    public Payslip getMyPayslip(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Number claim = jwt.getClaim("id");
        Long id = claim.longValue();

        Payslip payslip = payslipRepository.findByEmployeeId(id);
        return payslip;
    }
}