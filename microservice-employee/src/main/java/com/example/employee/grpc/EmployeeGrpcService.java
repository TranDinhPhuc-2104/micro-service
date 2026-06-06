package com.example.employee.grpc;

import com.example.employee.model.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.grpc.attendance.Attendance;
import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import io.grpc.Status;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@GrpcService
public class EmployeeGrpcService extends EmployeeServiceGrpc.EmployeeServiceImplBase {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void getEmployeeBasicInfo(EmployeeRequest request, StreamObserver<EmployeeResponse> responseObserver) {

        long employeeId = request.getEmployeeId();
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if (employeeOpt.isPresent()) {
            Employee emp = employeeOpt.get();

            EmployeeResponse response = EmployeeResponse.newBuilder()
                    .setId(emp.getId())
                    .setEmployeeCode(emp.getEmployeeCode() != null ? emp.getEmployeeCode() : "")
                    .setFirstName(emp.getFirstName() != null ? emp.getFirstName() : "")
                    .setLastName(emp.getLastName() != null ? emp.getLastName() : "")
                    .setEmail(emp.getEmail() != null ? emp.getEmail() : "")
                    .setPosition(emp.getPosition() != null ? emp.getPosition() : "")
                    .setStatus(emp.getStatus() != null ? emp.getStatus() : "")
                    .setDepartmentId(emp.getDepartmentId() != null ? emp.getDepartmentId() : 0)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } else {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription("Không tìm thấy nhân viên với ID: " + employeeId)
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void getAttendanceHistory(EmployeeRequest request, StreamObserver<AttendanceResponse> responseObserver) {
        List<Attendance> history = attendanceRepository.findByEmployeeId(request.getEmployeeId());

        for (Attendance item : history) {
            AttendanceResponse res = AttendanceResponse.newBuilder()
                    .setDate(item.getDate() != null ? item.getDate() : "")
                    .setCheckIn(item.getCheckIn() != null ? item.getCheckIn() : "")
                    .setCheckOut(item.getCheckOut() != null ? item.getCheckOut() : "") // Thêm cả check_out vì file proto có
                    .build();

            responseObserver.onNext(res);
        }
        responseObserver.onCompleted();
    }
}