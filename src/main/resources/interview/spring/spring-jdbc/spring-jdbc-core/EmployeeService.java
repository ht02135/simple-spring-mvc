// EmployeeService.java - Service Layer
package com.example.service;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
    
    public Employee updateEmployee(Employee employee) {
        employeeRepository.update(employee);
        return employee;
    }
    
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    
    public int getTotalEmployeeCount() {
        return employeeRepository.getEmployeeCount();
    }
    
    public Double getAverageDepartmentSalary(String department) {
        return employeeRepository.getAverageSalaryByDepartment(department);
    }
    
    public void giveRaise(String department, double percentage) {
        employeeRepository.updateSalaryByPercentage(department, percentage);
    }
}