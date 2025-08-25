/*
Perfect! I've created a complete iBATIS 2.x setup with all the components you requested. Here's what I've provided:
Summary of Components:

Employee.java - Enhanced POJO with additional fields (email, gender) and full getter/setter methods
EmployeeMapper.java - Interface with all CRUD operations plus additional methods based on your reference
EmployeeService.java - Service interface with business logic methods
EmployeeServiceImpl.java - Service implementation with transaction management and validation
applicationContext.xml - Non-Spring Boot XML configuration with iBATIS setup
pom.xml - Maven dependencies for iBATIS 2.x, Spring 4.x, MySQL, and other required libraries
employee_schema.sql - Enhanced database schema with indexes and sample data
Employee.xml - Complete iBATIS SQL Map with all queries including dynamic SQL
SqlMapConfig.xml - iBATIS configuration file
EmployeeMapperImpl.java - iBATIS style DAO implementation using SqlMapClientTemplate
Application.java - Main class demonstrating all functionality
/////////////
Important Notes:
This is iBATIS 2.x - Uses the older sql-map-2.dtd DTD and XML-based configuration
Spring Integration - Uses SqlMapClientTemplate for iBATIS integration
Transaction Management - Configured with Spring's transaction annotations
Connection Pooling - Uses Apache Commons DBCP2
Parameter Mapping - Uses #parameter# syntax (iBATIS 2.x style)
Result Mapping - Proper result maps for object mapping
/////////////////
5. Declarative Transaction Management:
XML-based transaction configuration with <tx:advice>
Read-only methods: get*, find*, search*, count*, is*
Write methods: create*, update*, delete*, give*
Proper propagation and rollback rules
/////////////////
6. Key AOP Elements Added:
<aop:aspectj-autoproxy /> - Enables AspectJ-style AOP
<aop:config> - AOP configuration blocks
<aop:pointcut> - Pointcut expressions with various patterns
<aop:aspect> - Aspect definitions linking aspects to pointcuts
<aop:before>, <aop:after-returning>, <aop:after-throwing>, <aop:around> - Different advice types
<aop:advisor> - For declarative transaction management
/////////////////
8. Transaction Management:
Declarative via <tx:advice> with method name patterns
Read-only methods: get*, find*, search*, count*, is*
Write methods: create*, update*, delete*, give*
Propagation and rollback rules configured
/////////////////
9. Additional Components Created:
LoggingAspect.java - Comprehensive method logging
PerformanceAspect.java - Execution time monitoring with statistics
SecurityAspect.java - Role-based access control with audit logging
AOPDemoApplication.java - Demonstration of all AOP features
application.properties - Externalized configuration
Updated pom.xml - Added AspectJ dependencies
/////////////////
*/
package com.example;

import com.example.aspect.PerformanceAspect;
import com.example.aspect.SecurityAspect;
import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class AOPDemoApplication {
    
    public static void main(String[] args) {
        // Load Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Get the employee service (will be proxied with AOP)
        EmployeeService employeeService = (EmployeeService) context.getBean("employeeServiceImpl");
        PerformanceAspect performanceAspect = (PerformanceAspect) context.getBean("performanceAspect");
        
        try {
            System.out.println("=== AOP Demonstration with iBATIS Employee System ===\n");
            
            // Demonstrate different user contexts for security
            demonstrateSecurityAspect(employeeService);
            
            // Demonstrate performance monitoring
            demonstratePerformanceAspect(employeeService, performanceAspect);
            
            // Demonstrate logging aspect with exception handling
            demonstrateLoggingAspectWithException(employeeService);
            
            // Demonstrate transaction management
            demonstrateTransactionManagement(employeeService);
            
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Close the context
        if (context instanceof ClassPathXmlApplicationContext) {
            ((ClassPathXmlApplicationContext) context).close();
        }
    }
    
    private static void demonstrateSecurityAspect(EmployeeService employeeService) {
        System.out.println("=== 1. SECURITY ASPECT DEMONSTRATION ===\n");
        
        // Test with ADMIN user (should work)
        System.out.println("--- Testing with ADMIN user ---");
        SecurityAspect.setCurrentUser("admin", "ADMIN", "USER");
        
        try {
            Employee adminEmployee = new Employee("Admin", "User", 10000, "admin@company.com", "OTHER");
            employeeService.createEmployeeFull(adminEmployee);
            System.out.println("✓ ADMIN user successfully created employee: " + adminEmployee.getId());
        } catch (SecurityException e) {
            System.out.println("✗ Security error: " + e.getMessage());
        }
        
        // Test with regular USER (create should work, delete should fail)
        System.out.println("\n--- Testing with regular USER ---");
        SecurityAspect.setCurrentUser("john.doe", "USER");
        
        try {
            Employee userEmployee = new Employee("User", "Created", 5000, "user@company.com", "MALE");
            employeeService.createEmployeeFull(userEmployee);
            System.out.println("✓ USER successfully created employee: " + userEmployee.getId());
            
            // Try to delete (should fail)
            employeeService.deleteEmployee(userEmployee.getId());
            System.out.println("✗ USER should not be able to delete!");
        } catch (SecurityException e) {
            System.out.println("✓ Expected security error: " + e.getMessage());
        }
        
        // Test salary update (should require ADMIN)
        System.out.println("\n--- Testing salary update with USER (should fail) ---");
        try {
            employeeService.giveRaise(1, 1000);
            System.out.println("✗ USER should not be able to give salary raise!");
        } catch (SecurityException e) {
            System.out.println("✓ Expected security error for salary operation: " + e.getMessage());
        }
        
        // Reset to ADMIN for remaining tests
        SecurityAspect.setCurrentUser("admin", "ADMIN", "USER");
        System.out.println();
    }
    
    private static void demonstratePerformanceAspect(EmployeeService employeeService, PerformanceAspect performanceAspect) {
        System.out.println("=== 2. PERFORMANCE ASPECT DEMONSTRATION ===\n");
        
        // Clear previous stats
        performanceAspect.clearStats();
        
        System.out.println("--- Performing multiple operations to collect performance data ---");
        
        // Perform various operations to generate performance data
        for (int i = 0; i < 5; i++) {
            try {
                // Read operations (should be fast)
                List<Employee> allEmployees = employeeService.getAllEmployees();
                System.out.println("Iteration " + (i+1) + ": Retrieved " + allEmployees.size() + " employees");
                
                // Simulate some processing delay
                Thread.sleep(50);
                
                // Find by ID operation
                Employee employee = employeeService.getEmployeeById(1);
                if (employee != null) {
                    System.out.println("Iteration " + (i+1) + ": Found employee: " + employee.getFirstName());
                }
                
                // Simulate variable delay for demonstration
                if (i == 3) {
                    System.out.println("Simulating slow operation...");
                    Thread.sleep(1200); // This should trigger slow method warning
                }
                
            } catch (Exception e) {
                System.out.println("Error in iteration " + (i+1) + ": " + e.getMessage());
            }
        }
        
        // Display performance statistics
        System.out.println("\n--- Performance Statistics ---");
        Map<String, PerformanceAspect.MethodStats> stats = performanceAspect.getAllMethodStats();
        stats.forEach((method, stat) -> {
            System.out.println("Method: " + method);
            System.out.println("  " + stat);
            System.out.println();
        });
    }
    
    private static void demonstrateLoggingAspectWithException(EmployeeService employeeService) {
        System.out.println("=== 3. LOGGING ASPECT WITH EXCEPTION HANDLING ===\n");
        
        System.out.println("--- Testing normal operation (check logs for before/after advice) ---");
        try {
            int totalEmployees = employeeService.getTotalEmployeeCount();
            System.out.println("Total employees: " + totalEmployees);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n--- Testing exception handling (check logs for after-throwing advice) ---");
        try {
            // This should trigger exception handling in logging aspect
            Employee nonExistentEmployee = employeeService.getEmployeeById(-1);
            System.out.println("This should not print if validation works");
        } catch (Exception e) {
            System.out.println("✓ Expected exception caught: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    private static void demonstrateTransactionManagement(EmployeeService employeeService) {
        System.out.println("=== 4. TRANSACTION MANAGEMENT DEMONSTRATION ===\n");
        
        System.out.println("--- Testing read-only transaction (findAll) ---");
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            System.out.println("✓ Read-only operation completed. Found " + employees.size() + " employees");
        } catch (Exception e) {
            System.out.println("✗ Read operation failed: " + e.getMessage());
        }
        
        System.out.println("\n--- Testing transactional write operation ---");
        try {
            Employee transactionalEmployee = new Employee();
            transactionalEmployee.setFirstName("Transaction");
            transactionalEmployee.setLastName("Test");
            transactionalEmployee.setSalary(6000);
            transactionalEmployee.setEmail("transaction.test@company.com");
            transactionalEmployee.setGender("OTHER");
            
            employeeService.createEmployeeFull(transactionalEmployee);
            System.out.println("✓ Transactional create completed. Employee ID: " + transactionalEmployee.getId());
            
            // Update the employee (another transaction)
            employeeService.giveRaise(transactionalEmployee.getId(), 500);
            System.out.println("✓ Transactional update completed. New salary: " + 
                             employeeService.getEmployeeById(transactionalEmployee.getId()).getSalary());
            
        } catch (Exception e) {
            System.out.println("✗ Transactional operation failed: " + e.getMessage());
        }
        
        System.out.println("\n--- Testing transaction rollback scenario ---");
        try {
            // Try to create employee with duplicate email (should rollback)
            Employee duplicateEmployee = new Employee();
            duplicateEmployee.setFirstName("Duplicate");
            duplicateEmployee.setLastName("Email");
            duplicateEmployee.setSalary(5000);
            duplicateEmployee.setEmail("zara.ali@example.com"); // This email already exists
            duplicateEmployee.setGender("OTHER");
            
            employeeService.createEmployeeFull(duplicateEmployee);
            System.out.println("✗ Should not reach here - duplicate email should fail");
            
        } catch (Exception e) {
            System.out.println("✓ Expected rollback due to constraint violation: " + e.getMessage());
        }
        
        System.out.println();
    }
}