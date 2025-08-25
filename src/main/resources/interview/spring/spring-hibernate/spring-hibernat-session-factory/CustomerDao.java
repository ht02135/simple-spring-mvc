package com.example.dao;

import com.example.entity.Customer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerDao {
    
    // Basic CRUD operations
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    void delete(Long id);
    void update(Customer customer);
    
    // Custom query methods matching the repository interface
    List<Customer> findByLastName(String lastName);
    List<Customer> findByGender(String gender);
    List<Customer> findByGenderAndFirstNameAndLastName(String gender, String firstName, String lastName);
    List<Customer> findByStreetAndCityAndState(String street, String city, String state);
    List<Customer> findRecentCustomers(LocalDateTime date);
    List<Customer> findActiveBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary);
    List<Customer> findByDepartmentNameAndActive(String departmentName, Boolean active);
    void updateActiveStatusByDepartment(Long departmentId, Boolean status);
    int countByDepartmentAndSalaryThreshold(Long departmentId, BigDecimal salary);
    
    // Complex queries
    List<Map<String, Object>> countCustomersByGender();
    List<Map<String, Object>> sumSalaryByDepartment();
    List<Map<String, Object>> rankCustomersByDepartment();
    List<Map<String, Object>> top3SalariesPerDepartment();
}