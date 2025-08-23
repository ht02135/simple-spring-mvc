// Application.java - Main Application Class
package com.example;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Application {
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        EmployeeService employeeService = context.getBean(EmployeeService.class);
        
        // Demonstrate various operations
        System.out.println("=== JDBC Template Examples ===");
        
        // Create employees using SimpleJdbcInsert
        System.out.println("\n1. Creating employees (SimpleJdbcInsert):");
        Employee emp1 = new Employee("John Doe", "john@example.com", "IT", 75000.0);
        Employee emp2 = new Employee("Jane Smith", "jane@example.com", "HR", 65000.0);
        Employee emp3 = new Employee("Mike Wilson", "mike@example.com", "IT", 80000.0);
        
        emp1 = employeeService.createEmployee(emp1);
        emp2 = employeeService.createEmployee(emp2);
        emp3 = employeeService.createEmployee(emp3);
        
        System.out.println("Created: " + emp1);
        System.out.println("Created: " + emp2);
        System.out.println("Created: " + emp3);
        
        // Find by ID using JdbcTemplate
        System.out.println("\n2. Finding employee by ID (JdbcTemplate):");
        employeeService.getEmployeeById(1L).ifPresent(System.out::println);
        
        // Find all using JdbcTemplate
        System.out.println("\n3. Finding all employees (JdbcTemplate):");
        List<Employee> allEmployees = employeeService.getAllEmployees();
        allEmployees.forEach(System.out::println);
        
        // Find by department using NamedParameterJdbcTemplate
        System.out.println("\n4. Finding IT employees (NamedParameterJdbcTemplate):");
        List<Employee> itEmployees = employeeService.getEmployeesByDepartment("IT");
        itEmployees.forEach(System.out::println);
        
        // Update employee using NamedParameterJdbcTemplate
        System.out.println("\n5. Updating employee (NamedParameterJdbcTemplate):");
        emp1.setSalary(82000.0);
        employeeService.updateEmployee(emp1);
        employeeService.getEmployeeById(emp1.getId()).ifPresent(System.out::println);
        
        // Aggregate queries
        System.out.println("\n6. Aggregate operations:");
        System.out.println("Total employees: " + employeeService.getTotalEmployeeCount());
        System.out.println("Average IT salary: " + employeeService.getAverageDepartmentSalary("IT"));
        
        // Bulk update using NamedParameterJdbcTemplate
        System.out.println("\n7. Giving 10% raise to IT department:");
        employeeService.giveRaise("IT", 10.0);
        employeeService.getEmployeesByDepartment("IT").forEach(System.out::println);
        
        // Delete employee
        System.out.println("\n8. Deleting employee:");
        employeeService.deleteEmployee(emp2.getId());
        System.out.println("Remaining employees: " + employeeService.getTotalEmployeeCount());
        
        ((ClassPathXmlApplicationContext) context).close();
    }
}