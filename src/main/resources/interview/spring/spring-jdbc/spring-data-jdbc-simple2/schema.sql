/ ============================================================================
// 1. TEST DATA SETUP SQL SCRIPTS
// ============================================================================

// File: src/test/resources/schema.sql
/*
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    budget DECIMAL(15,2)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    age INT,
    salary DECIMAL(10,2),
    active BOOLEAN DEFAULT TRUE,
    dept_id BIGINT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dept_id) REFERENCES departments(id)
);
*/