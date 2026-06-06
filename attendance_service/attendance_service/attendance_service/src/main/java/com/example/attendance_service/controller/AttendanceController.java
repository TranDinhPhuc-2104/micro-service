package com.example.attendance_service.controller;

import com.example.attendance_service.entity.AttendanceRecord;
import com.example.attendance_service.entity.OvertimeRecord;
import com.example.attendance_service.grpc.EmployeeGrpcClientService;
import com.example.attendance_service.service.AttendanceService;
import com.example.employee.grpc.EmployeeResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@AllArgsConstructor
public class AttendanceController {

    EmployeeGrpcClientService employeeGrpcClientService;


    AttendanceService attendanceService;

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

    @GetMapping("/test-grpc/employee/{id}")
    public String testGetEmployee(@PathVariable long id) {
        System.out.println("=== ĐANG GỌI SANG EMPLOYEE SERVICE ĐỂ LẤY DỮ LIỆU... ===");

        // Gọi service gRPC của bạn
        EmployeeResponse response = employeeGrpcClientService.getEmployeeInfo(id);

        if (response != null) {
            // Định dạng kết quả trả về trình duyệt cho dễ đọc
            return String.format(
                    "✅ Lấy dữ liệu thành công qua gRPC!<br>" +
                            "Mã NV: %s <br>" +
                            "Họ tên: %s %s <br>" +
                            "Email: %s <br>" +
                            "Phòng ban (ID): %d",
                    response.getEmployeeCode(),
                    response.getFirstName(),
                    response.getLastName(),
                    response.getEmail(),
                    response.getDepartmentId()
            );
        } else {
            return "❌ Lỗi: Không lấy được dữ liệu hoặc Nhân viên không tồn tại!";
        }
    }

    @GetMapping("/myAttendance")
    public AttendanceRecord getMyAttendance(){
        return attendanceService.getMyAttendance();
    }
}