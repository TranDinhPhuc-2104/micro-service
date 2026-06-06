CREATE DATABASE employee_service;
CREATE DATABASE department_service;
CREATE DATABASE attendance_service;
CREATE DATABASE payroll_service;
CREATE DATABASE auth_service;

USE employee_service;

CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_code VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(20),
    gender VARCHAR(20),
    date_of_birth DATE,
    hire_date DATE,
    department_id BIGINT,
    position VARCHAR(100),
    status VARCHAR(50) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employee_addresses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    street VARCHAR(255),
    city VARCHAR(100),
    country VARCHAR(100),
    postal_code VARCHAR(20),
    
    CONSTRAINT fk_employee_address
        FOREIGN KEY (employee_id)
        REFERENCES employees(id)
        ON DELETE CASCADE
);


USE department_service;

CREATE TABLE departments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) UNIQUE NOT NULL,
    description TEXT,
    manager_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE department_locations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department_id BIGINT NOT NULL,
    office_name VARCHAR(100),
    floor VARCHAR(50),
    building VARCHAR(100),

    CONSTRAINT fk_department_location
        FOREIGN KEY (department_id)
        REFERENCES departments(id)
        ON DELETE CASCADE
);

USE attendance_service;

CREATE TABLE attendance_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    check_in_time DATETIME,
    check_out_time DATETIME,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE work_shifts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shift_name VARCHAR(100),
    start_time TIME,
    end_time TIME
);

CREATE TABLE overtime_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    overtime_date DATE,
    hours DECIMAL(5,2),
    reason TEXT
);

USE payroll_service;

CREATE TABLE salaries (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    base_salary DECIMAL(12,2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'USD',
    effective_from DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bonuses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    bonus_amount DECIMAL(12,2),
    reason TEXT,
    bonus_date DATE
);

CREATE TABLE payslips (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    month INT,
    year INT,
    gross_salary DECIMAL(12,2),
    tax_amount DECIMAL(12,2),
    net_salary DECIMAL(12,2),
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

USE auth_service;

CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    employee_id BIGINT,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,

    PRIMARY KEY(user_id, role_id),

    CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_role_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE CASCADE
);


-- =========================================
-- EMPLOYEE SERVICE DATA
-- =========================================

USE employee_service;

INSERT INTO employees (
    employee_code,
    first_name,
    last_name,
    email,
    phone,
    gender,
    date_of_birth,
    hire_date,
    department_id,
    position,
    status
)
VALUES
('EMP001', 'John', 'Doe', 'john.doe@example.com', '123456789', 'MALE', '1995-05-10', '2024-01-15', 1, 'Backend Developer', 'ACTIVE'),
('EMP002', 'Jane', 'Smith', 'jane.smith@example.com', '987654321', 'FEMALE', '1997-08-22', '2024-02-01', 2, 'HR Specialist', 'ACTIVE'),
('EMP003', 'Michael', 'Brown', 'michael.brown@example.com', '555111222', 'MALE', '1993-03-18', '2023-11-10', 1, 'DevOps Engineer', 'ACTIVE'),
('EMP004', 'Emily', 'Davis', 'emily.davis@example.com', '444777888', 'FEMALE', '1998-12-05', '2024-03-20', 3, 'Accountant', 'ACTIVE'),
('EMP005', 'David', 'Wilson', 'david.wilson@example.com', '999888777', 'MALE', '1992-09-14', '2022-06-11', 1, 'Engineering Manager', 'ACTIVE');

INSERT INTO employee_addresses (
    employee_id,
    street,
    city,
    country,
    postal_code
)
VALUES
(1, '123 Main St', 'New York', 'USA', '10001'),
(2, '45 Lake Road', 'Chicago', 'USA', '60601'),
(3, '99 Silicon Ave', 'San Francisco', 'USA', '94105'),
(4, '17 Ocean Drive', 'Miami', 'USA', '33101'),
(5, '77 Business Park', 'Seattle', 'USA', '98101');

-- =========================================
-- DEPARTMENT SERVICE DATA
-- =========================================

USE department_service;

INSERT INTO departments (
    name,
    description,
    manager_id
)
VALUES
('Engineering', 'Software development department', 5),
('Human Resources', 'Handles employee relations', 2),
('Finance', 'Manages company finances', 4);

INSERT INTO department_locations (
    department_id,
    office_name,
    floor,
    building
)
VALUES
(1, 'Engineering HQ', '5', 'Building A'),
(2, 'HR Office', '2', 'Building B'),
(3, 'Finance Center', '3', 'Building C');

-- =========================================
-- ATTENDANCE SERVICE DATA
-- =========================================

USE attendance_service;

INSERT INTO work_shifts (
    shift_name,
    start_time,
    end_time
)
VALUES
('Morning Shift', '08:00:00', '17:00:00'),
('Night Shift', '17:00:00', '01:00:00');

INSERT INTO attendance_records (
    employee_id,
    attendance_date,
    check_in_time,
    check_out_time,
    status
)
VALUES
(1, '2026-05-20', '2026-05-20 08:01:00', '2026-05-20 17:05:00', 'PRESENT'),
(2, '2026-05-20', '2026-05-20 08:10:00', '2026-05-20 17:00:00', 'LATE'),
(3, '2026-05-20', '2026-05-20 07:58:00', '2026-05-20 17:20:00', 'PRESENT'),
(4, '2026-05-20', NULL, NULL, 'ABSENT'),
(5, '2026-05-20', '2026-05-20 08:00:00', '2026-05-20 18:00:00', 'PRESENT');

INSERT INTO overtime_records (
    employee_id,
    overtime_date,
    hours,
    reason
)
VALUES
(1, '2026-05-20', 2.5, 'Project deadline'),
(3, '2026-05-20', 1.5, 'Server maintenance'),
(5, '2026-05-20', 3.0, 'Management meeting');

-- =========================================
-- PAYROLL SERVICE DATA
-- =========================================

USE payroll_service;

INSERT INTO salaries (
    employee_id,
    base_salary,
    currency,
    effective_from
)
VALUES
(1, 4000.00, 'USD', '2026-01-01'),
(2, 3500.00, 'USD', '2026-01-01'),
(3, 4500.00, 'USD', '2026-01-01'),
(4, 3800.00, 'USD', '2026-01-01'),
(5, 7000.00, 'USD', '2026-01-01');

INSERT INTO bonuses (
    employee_id,
    bonus_amount,
    reason,
    bonus_date
)
VALUES
(1, 500.00, 'Excellent performance', '2026-05-01'),
(3, 700.00, 'Infrastructure optimization', '2026-05-01'),
(5, 1200.00, 'Leadership achievement', '2026-05-01');

INSERT INTO payslips (
    employee_id,
    month,
    year,
    gross_salary,
    tax_amount,
    net_salary
)
VALUES
(1, 5, 2026, 4500.00, 450.00, 4050.00),
(2, 5, 2026, 3500.00, 350.00, 3150.00),
(3, 5, 2026, 5200.00, 520.00, 4680.00),
(4, 5, 2026, 3800.00, 380.00, 3420.00),
(5, 5, 2026, 8200.00, 820.00, 7380.00);

-- =========================================
-- AUTH SERVICE DATA
-- =========================================

USE auth_service;

INSERT INTO roles (role_name)
VALUES
('ADMIN'),
('HR'),
('EMPLOYEE');

INSERT INTO users (
    username,
    password_hash,
    employee_id,
    enabled
)
VALUES
('admin', '$2a$10$examplehashedpassword1', NULL, TRUE),
('john', '$2a$10$examplehashedpassword2', 1, TRUE),
('jane', '$2a$10$examplehashedpassword3', 2, TRUE),
('michael', '$2a$10$examplehashedpassword4', 3, TRUE),
('emily', '$2a$10$examplehashedpassword5', 4, TRUE);

INSERT INTO user_roles (
    user_id,
    role_id
)
VALUES
(1, 1),
(2, 3),
(3, 2),
(4, 3),
(5, 3);
