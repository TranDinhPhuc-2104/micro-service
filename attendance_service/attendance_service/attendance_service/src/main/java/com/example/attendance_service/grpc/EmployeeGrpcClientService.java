package com.example.attendance_service.grpc;

import com.example.employee.grpc.EmployeeRequest;
import com.example.employee.grpc.EmployeeResponse;
import com.example.employee.grpc.EmployeeServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class EmployeeGrpcClientService {

    @GrpcClient("employee-service")
    private EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeStub;

    public EmployeeResponse getEmployeeInfo(long employeeId) {
        try {
            EmployeeRequest request = EmployeeRequest.newBuilder()
                    .setEmployeeId(employeeId)
                    .build();
            return employeeStub.getEmployeeBasicInfo(request);
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi Employee Service: " + e.getMessage());
            return null; // Hoặc ném ra một Custom Exception tùy bạn
        }
    }
}
