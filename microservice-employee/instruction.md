# INSTRUCTION: DEVELOPMENT OF SPRING BOOT EMPLOYEE MICROSERVICE

## 1. TECH STACK & SYSTEM CONSTRAINTS
- **Language & Framework:** Java 21, Spring Boot 4.0.6
- **Build Tool:** Maven
- **Database:** MySQL (Database Name: `employee_service`)
- **Security:** Spring Security (Stateless, JWT-based using a Mock validation approach)
- **Communication Tool:** Spring `RestClient` (HTTP Client integration for cross-service calls)
- **Data Integrity:** Keep all database fields exactly as specified in the SQL script. Do NOT truncate, ignore, or simplify any entity attributes.

---

## 2. CORE DESIGN PATTERN: ADAPTER PATTERN
- **Context:** The `employees` table contains a `department_id` field. To return full employee details to the frontend, this service must call the external `Department Service`.
- **Implementation:**
  - `DepartmentClient`: Executes HTTP GET requests to `http://api/departments/{id}`.
  - `DepartmentAdapter` (Interface) & `DepartmentAdapterImpl` (Class): Transforms the external JSON/DTO schema returned by the Department Service into a standardized internal object (`DepartmentExternalDTO`) used by the Employee Service. This decouples our service from structure changes in other microservices.

---

## 3. MOCK JWT AUTHENTICATION SETUP
- Implement a `MockJwtFilter` extending `OncePerRequestFilter`.
- Intercept the `Authorization` header matching the format: `Bearer <token>`.
- **Validation Logic:** If the header contains a non-empty token (e.g., `mock-admin-token`, `mock-hr-token`), create an authenticated `UsernamePasswordAuthenticationToken` context and inject it into `SecurityContextHolder`. No explicit role authorizations are enforced, but a valid Bearer token structure is mandatory for all client-facing endpoints.

---

## 4. API ENDPOINTS SPECS (NO VERSIONING)

### Client/Frontend Endpoints (Require Mock JWT Authentication)
- `GET /api/employees` - Retrieve all employees with their base profiles.
- `GET /api/employees/{id}` - Retrieve complete profile of 1 employee. Must invoke `DepartmentAdapter` to pull department data and aggregate it into the response.
- `POST /api/employees` - Create a new employee along with their addresses (Support transactional nested inserts for `employee_addresses`).
- `PUT /api/employees/{id}` - Update existing employee details.
- `DELETE /api/employees/{id}` - Delete employee profile (Cascade deletion of related addresses).

### Internal Cross-Service Endpoints (Bypass Mock JWT Filtering)
- `GET /api/internal/employees/{id}/validate` - Used by other services to check if an employee exists and is active (`status = 'ACTIVE'`). Returns a simple Boolean.
- `POST /api/internal/employees/summary` - Accepts a List of `employee_id` values, returns a Map containing `id`, `employee_code`, and `full_name` for rapid UI mapping in other services.

---

## 5. DIRECTORY STRUCTURE
Ensure all files are generated precisely within the following package structure:

com.example.employee
├── config
│   ├── SecurityConfig.java
│   └── RestClientConfig.java
├── controller
│   ├── EmployeeController.java
│   └── InternalEmployeeController.java
├── model
│   ├── entity
│   │   ├── Employee.java
│   │   └── EmployeeAddress.java
│   └── dto
│       ├── EmployeeDTO.java
│       ├── EmployeeAddressDTO.java
│       ├── EmployeeResponse.java
│       └── DepartmentExternalDTO.java
├── repository
│   ├── EmployeeRepository.java
│   └── EmployeeAddressRepository.java
├── service
│   ├── EmployeeService.java
│   └── EmployeeServiceImpl.java
├── client
│   ├── DepartmentClient.java
│   └── adapter
│       ├── DepartmentAdapter.java
│       └── DepartmentAdapterImpl.java
└── security
    ├── MockJwtFilter.java
    └── MockUserPrincipal.java