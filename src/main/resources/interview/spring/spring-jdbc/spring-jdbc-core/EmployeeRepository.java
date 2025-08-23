// EmployeeRepository.java - Repository Interface
package com.example.repository;

import com.example.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Employee save(Employee employee);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    List<Employee> findByDepartment(String department);
    void update(Employee employee);
    void deleteById(Long id);
    int getEmployeeCount();
    Double getAverageSalaryByDepartment(String department);
    void updateSalaryByPercentage(String department, double percentage);
    String callStoredProcedure(Long employeeId);
}