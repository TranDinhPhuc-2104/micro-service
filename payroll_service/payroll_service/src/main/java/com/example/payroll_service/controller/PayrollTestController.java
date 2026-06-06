package com.example.payroll_service.controller;

import com.example.payroll_service.service.PayrollGrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayrollTestController {

    @Autowired
    private PayrollGrpcClientService payrollGrpcClientService;

    @GetMapping("/test-grpc/{id}")
    public String testGrpc(@PathVariable long id) {
        payrollGrpcClientService.fetchEmployeeDataAndCalculateSalary(id);
        return "Đã gọi gRPC xong, kiểm tra Terminal để xem kết quả!";
    }
}