-- Create database
CREATE DATABASE IF NOT EXISTS testdb;
USE testdb;

-- Drop table if exists
DROP TABLE IF EXISTS EMPLOYEE;

-- Create EMPLOYEE table with additional fields
CREATE TABLE EMPLOYEE (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) DEFAULT NULL,
    last_name VARCHAR(50) DEFAULT NULL,
    salary INT DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    gender ENUM('MALE', 'FEMALE', 'OTHER') DEFAULT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY unique_email (email),
    INDEX idx_last_name (last_name),
    INDEX idx_salary (salary),
    INDEX idx_gender (gender)
);

-- Insert sample data
INSERT INTO EMPLOYEE (first_name, last_name, salary, email, gender) VALUES
('Zara', 'Ali', 5000, 'zara.ali@example.com', 'FEMALE'),
('Roma', 'Ali', 3000, 'roma.ali@example.com', 'FEMALE'),
('Noha', 'Ali', 7000, 'noha.ali@example.com', 'FEMALE'),
('John', 'Doe', 6000, 'john.doe@example.com', 'MALE'),
('Jane', 'Smith', 5500, 'jane.smith@example.com', 'FEMALE'),
('Michael', 'Johnson', 4500, 'michael.johnson@example.com', 'MALE'),
('Sarah', 'Williams', 8000, 'sarah.williams@example.com', 'FEMALE'),
('David', 'Brown', 4000, 'david.brown@example.com', 'MALE'),
('Emily', 'Davis', 6500, 'emily.davis@example.com', 'FEMALE'),
('Chris', 'Wilson', 3500, 'chris.wilson@example.com', 'OTHER');

-- Verify data
SELECT * FROM EMPLOYEE;

-- Show table structure
DESCRIBE EMPLOYEE;