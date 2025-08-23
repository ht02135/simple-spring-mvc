// JdbcTemplateTest.java - Unit Test Example
package com.example.repository;

import com.example.config.DatabaseConfig;
import com.example.model.Employee;
import com.example.repository.impl.EmployeeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(DatabaseConfig.class)
@Transactional
public class JdbcTemplateTest {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;