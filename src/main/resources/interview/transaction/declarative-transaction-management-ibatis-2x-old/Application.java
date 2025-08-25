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
6. Key AOP Elements:
<aop:aspectj-autoproxy /> - Enables AspectJ-style AOP
<aop:config> - AOP configuration block
<aop:pointcut> - Pointcut expressions
<aop:aspect> - Aspect definitions
<aop:before>, <aop:after-returning>, <aop:after-throwing>, `<aop
*/
package com.example;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class Application {
    
    public static void main(String[] args) {
        // Load Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Get the employee service
        EmployeeService employeeService = (EmployeeService) context.getBean("employeeServiceImpl");
        
        try {
            // Demonstrate various operations
            System.out.println("=== iBATIS Employee Management System Demo ===\n");
            
            // 1. Get all employees
            System.out.println("1. All Employees:");
            List<Employee> allEmployees = employeeService.getAllEmployees();
            allEmployees.forEach(System.out::println);
            System.out.println("Total employees: " + employeeService.getTotalEmployeeCount() + "\n");
            
            // 2. Find employee by ID
            System.out.println("2. Employee with ID 1:");
            Employee employee = employeeService.getEmployeeById(1);
            if (employee != null) {
                System.out.println(employee);
            }
            System.out.println();
            
            // 3. Find employee by email
            System.out.println("3. Employee with email 'zara.ali@example.com':");
            Employee empByEmail = employeeService.getEmployeeByEmail("zara.ali@example.com");
            if (empByEmail != null) {
                System.out.println(empByEmail);
            }
            System.out.println();
            
            // 4. Find employees by salary range
            System.out.println("4. Employees with salary between 4000 and 6000:");
            List<Employee> empsBySalary = employeeService.getEmployeesBySalaryRange(4000, 6000);
            empsBySalary.forEach(System.out::println);
            System.out.println();
            
            // 5. Find employees by gender and name
            System.out.println("5. Female employees with first name 'Sarah':");
            List<Employee> empsByGenderName = employeeService.getEmployeesByGenderAndName("FEMALE", "Sarah", "Williams");
            empsByGenderName.forEach(System.out::println);
            System.out.println();
            
            // 6. Count employees by gender
            System.out.println("6. Employee count by gender:");
            List<Map<String, Object>> genderCounts = employeeService.getEmployeeCountByGender();
            genderCounts.forEach(map -> {
                System.out.println("Gender: " + map.get("gender") + ", Count: " + map.get("cnt"));
            });
            System.out.println();
            
            // 7. Find highest paid employee
            System.out.println("7. Highest paid employee:");
            Employee highestPaid = employeeService.getHighestPaidEmployee();
            if (highestPaid != null) {
                System.out.println(highestPaid);
            }
            System.out.println();
            
            // 8. Create new employee
            System.out.println("8. Creating new employee:");
            Employee newEmployee = new Employee();
            newEmployee.setFirstName("Alice");
            newEmployee.setLastName("Johnson");
            newEmployee.setSalary(7500);
            newEmployee.setEmail("alice.johnson@example.com");
            newEmployee.setGender("FEMALE");
            
            employeeService.createEmployeeFull(newEmployee);
            System.out.println("Created employee with ID: " + newEmployee.getId());
            System.out.println();
            
            // 9. Give raise to an employee
            System.out.println("9. Giving raise to employee ID 1:");
            Employee empBeforeRaise = employeeService.getEmployeeById(1);
            System.out.println("Before raise: " + empBeforeRaise);
            
            employeeService.giveRaise(1, 1000);
            
            Employee empAfterRaise = employeeService.getEmployeeById(1);
            System.out.println("After raise: " + empAfterRaise);
            System.out.println();
            
            // 10. Find employees by last name
            System.out.println("10. Employees with last name 'Ali':");
            List<Employee> empsByLastName = employeeService.getEmployeesByLastName("Ali");
            empsByLastName.forEach(System.out::println);
            System.out.println();
            
            System.out.println("=== Demo completed successfully ===");
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Close the context (if using ClassPathXmlApplicationContext)
        if (context instanceof ClassPathXmlApplicationContext) {
            ((ClassPathXmlApplicationContext) context).close();
        }
    }
}