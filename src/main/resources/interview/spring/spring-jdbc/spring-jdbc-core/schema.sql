-- schema.sql
-- Database schema initialization script
DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_employee_department ON employees(department);
CREATE INDEX idx_employee_email ON employees(email);

-- Example stored procedure for SimpleJdbcCall
CREATE ALIAS IF NOT EXISTS GET_EMPLOYEE_INFO AS $$
String getEmployeeInfo(Long empId) {
    return "Employee Info for ID: " + empId + " - Retrieved from stored procedure";
}
$$;