# Hướng Dẫn Thử Nghiệm API Employee Microservice Bằng Postman

Tài liệu này hướng dẫn chi tiết cách chạy ứng dụng và kiểm thử toàn bộ các API của Employee Microservice bằng công cụ Postman (bao gồm cả chức năng lấy token xác thực Mock JWT).

> [!TIP]
> Mình đã chuẩn bị sẵn file Collection cho Postman tại: [Employee_Microservice.postman_collection.json](file:///Users/thanhhhtt/repos/2533/sa/employee/Employee_Microservice.postman_collection.json)
> Bạn chỉ cần mở Postman, chọn **Import** -> Chọn file này để nạp toàn bộ các API mẫu vào sử dụng ngay nhé!
> **Lưu ý về Xác thực (Authorization)**: File collection này **không để sẵn bất kỳ header Authorization nào**. Khi gọi các API bảo mật (Client & Internal), các thành viên trong nhóm cần tự thiết lập token bằng tay trong Postman (chọn tab **Authorization** -> chọn Type là **Bearer Token** -> dán chuỗi token thu được sau khi login vào ô **Token**).

---

## 1. Khởi Động Ứng Dụng

Đảm bảo rằng bạn đã bật cơ sở dữ liệu MySQL trên máy cục bộ, tạo database `employee_service` và import dữ liệu từ file `script.sql`.

Chạy lệnh sau tại thư mục gốc của project để khởi động:
```bash
./mvnw spring-boot:run
```
Ứng dụng sẽ chạy tại địa chỉ mặc định: `http://localhost:8080`

---

## 2. Xác Thực & Lấy Token (Mock Authentication)

Để gửi yêu cầu tới các API dành cho Client (bảo mật), trước tiên bạn cần thực hiện yêu cầu đăng nhập để lấy Token.

* **HTTP Method**: `POST`
* **URL**: `http://localhost:8080/api/auth/login`
* **Headers**: 
  * `Content-Type`: `application/json`
* **Body (JSON)**:
  ```json
  {
    "username": "admin",
    "password": "password"
  }
  ```
  *(Có thể thay đổi `"username"` thành `"jane"`, `"john"`, `"michael"`, hoặc `"emily"`. Mật khẩu có thể nhập bất kỳ vì đây là cơ chế Mock).*

* **Response nhận được**:
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiJ9.mocksignature",
    "tokenType": "Bearer"
  }
  ```

---

## 3. Thử Nghiệm Các API Dành Cho Client (Yêu Cầu Token)

Trong mỗi request dưới đây, bạn cần thêm Header sau vào Postman:
* **Key**: `Authorization`
* **Value**: `Bearer <token_nhận_được_ở_bước_2>` (Ví dụ: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiJ9.mocksignature`)

### A. GET Danh Sách Nhân Viên (Tất Cả)
* **HTTP Method**: `GET`
* **URL**: `http://localhost:8080/api/employees`

### B. GET Chi Tiết Một Nhân Viên (Gồm Địa Chỉ và Thông Tin Department từ Adapter)
* **HTTP Method**: `GET`
* **URL**: `http://localhost:8080/api/employees/1`

### C. POST Thêm Nhân Viên Mới (Hỗ trợ insert đồng thời các địa chỉ liên quan)
* **HTTP Method**: `POST`
* **URL**: `http://localhost:8080/api/employees`
* **Body (JSON)**:
  ```json
  {
    "employeeCode": "EMP006",
    "firstName": "John",
    "lastName": "Wick",
    "email": "john.wick@example.com",
    "phone": "0987654321",
    "gender": "MALE",
    "dateOfBirth": "1990-09-02",
    "hireDate": "2026-05-26",
    "departmentId": 1,
    "position": "Contractor",
    "status": "ACTIVE",
    "addresses": [
      {
        "street": "Continental Hotel, 12th Street",
        "city": "New York",
        "country": "USA",
        "postalCode": "10005"
      }
    ]
  }
  ```

### D. PUT Cập Nhật Thông Tin Nhân Viên
* **HTTP Method**: `PUT`
* **URL**: `http://localhost:8080/api/employees/1`
* **Body (JSON)**:
  ```json
  {
    "firstName": "Johnathan",
    "lastName": "Doe",
    "email": "john.doe.new@example.com",
    "phone": "123456789",
    "gender": "MALE",
    "dateOfBirth": "1995-05-10",
    "hireDate": "2024-01-15",
    "departmentId": 2,
    "position": "Senior Backend Developer",
    "status": "ACTIVE",
    "addresses": [
      {
        "street": "789 New St",
        "city": "New York",
        "country": "USA",
        "postalCode": "10003"
      }
    ]
  }
  ```

### E. DELETE Xóa Nhân Viên (Tự động xóa cascade các địa chỉ liên quan)
* **HTTP Method**: `DELETE`
* **URL**: `http://localhost:8080/api/employees/6`

---

## 4. Thử Nghiệm Các API Nội Bộ (Internal - Yêu Cầu Xác Thực Token)

*Các API này cũng yêu cầu thêm Header `Authorization` như các API dành cho Client ở trên.*

### A. GET Kiểm Tra Trạng Thái Hoạt Động (Validate)
* **HTTP Method**: `GET`
* **URL**: `http://localhost:8080/api/internal/employees/1/validate`
* **Response**: `true` hoặc `false`

### B. POST Lấy Bản Tóm Tắt Nhân Viên (Summary)
* **HTTP Method**: `POST`
* **URL**: `http://localhost:8080/api/internal/employees/summary`
* **Body (JSON)**:
  ```json
  [1, 2, 3]
  ```
* **Response**: Trả về danh sách tóm tắt chứa `id`, `employee_code`, và `full_name`.
