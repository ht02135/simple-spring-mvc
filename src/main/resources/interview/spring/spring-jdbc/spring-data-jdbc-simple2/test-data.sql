// File: src/test/resources/test-data.sql
/*
-- Insert test departments
INSERT INTO departments (id, name, budget) VALUES 
(1, 'Engineering', 500000.00),
(2, 'Marketing', 200000.00),
(3, 'HR', 150000.00),
(4, 'Sales', 300000.00);

-- Insert test users
INSERT INTO users (id, name, email, age, salary, active, dept_id, created_date) VALUES 
(1, 'John Doe', 'john.doe@example.com', 30, 75000.00, true, 1, '2024-01-01 10:00:00'),
(2, 'Jane Smith', 'jane.smith@company.com', 28, 65000.00, true, 1, '2024-01-15 09:30:00'),
(3, 'Bob Johnson', 'bob.johnson@example.com', 35, 80000.00, false, 2, '2023-12-01 14:20:00'),
(4, 'Alice Brown', 'alice.brown@company.com', 32, 70000.00, true, 2, '2024-02-10 11:15:00'),
(5, 'Charlie Wilson', 'charlie.wilson@example.com', 29, 72000.00, true, 1, '2024-01-20 16:45:00'),
(6, 'Diana Davis', 'diana.davis@gmail.com', 26, 58000.00, true, 3, '2024-01-25 08:30:00'),
(7, 'Frank Miller', 'frank.miller@example.com', 40, 95000.00, true, 1, '2023-11-15 13:10:00'),
(8, 'Grace Lee', 'grace.lee@company.com', 27, 62000.00, false, 4, '2024-01-30 12:00:00');
*/