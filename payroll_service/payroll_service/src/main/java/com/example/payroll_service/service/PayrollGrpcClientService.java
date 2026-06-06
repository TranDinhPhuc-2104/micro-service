package com.example.payroll_service.service; // ⚠️ Nhớ sửa lại dòng này cho khớp với tên thư mục thực tế của bạn

// Bắt buộc import đúng cái tên đã khai báo trong file employee.proto
import com.example.employee.grpc.EmployeeRequest;
import com.example.employee.grpc.EmployeeResponse;
import com.example.employee.grpc.EmployeeServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class PayrollGrpcClientService {

    // Tiêm gRPC client. Chữ "employee-service" này sẽ được ánh xạ với cấu hình port trong application.properties
    @GrpcClient("employee-service")
    private EmployeeServiceGrpc.EmployeeServiceBlockingStub employeeStub;

    public void fetchEmployeeDataAndCalculateSalary(long employeeId) {
        System.out.println("=== BẮT ĐẦU GỌI GRPC TỪ PAYROLL SANG EMPLOYEE ===");

        try {
            // 1. Đóng gói Request mang theo employeeId
            EmployeeRequest request = EmployeeRequest.newBuilder()
                    .setEmployeeId(employeeId)
                    .build();

            // 2. Bắn luồng gRPC sang Employee Service và chờ nhận kết quả
            EmployeeResponse response = employeeStub.getEmployeeBasicInfo(request);

            // 3. Xử lý kết quả nhận được
            System.out.println("✅ Thành công! Đã lấy được dữ liệu của nhân viên ID: " + employeeId);
            System.out.println(" - Mã NV: " + response.getEmployeeCode());
            System.out.println(" - Họ tên: " + response.getFirstName() + " " + response.getLastName());
            System.out.println(" - Email: " + response.getEmail());
            System.out.println(" - Trạng thái: " + response.getStatus());

            // TODO: Viết tiếp logic tính toán lương, trừ thuế, cộng OT ở đây...

        } catch (Exception e) {
            System.err.println("❌ Lỗi kết nối: Không thể gọi sang Employee Service!");
            System.err.println("Chi tiết lỗi: " + e.getMessage());
        }
    }
}