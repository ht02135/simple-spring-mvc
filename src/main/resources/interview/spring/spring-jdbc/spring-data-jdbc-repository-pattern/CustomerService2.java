/*
Notes on the Service:
1>All write operations (addCustomer, updateCustomer, 
updateActiveStatusByDepartment) are annotated with @Transactional.
2>All read operations don’t need @Transactional unless 
you require a transactional read.
Includes both derived methods and custom SQL queries.
*/
package com.example.service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerService2 {

    private final CustomerRepository2 repository;

    public CustomerService2(CustomerRepository2 repository) {
        this.repository = repository;
    }

    // ===== Basic CRUD =====

    @Transactional
    public Customer addCustomer(Customer customer) {
        return repository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return (List<Customer>) repository.findAll();
    }

    public List<String> getAllCustomerNames() {
        return ((List<Customer>) repository.findAll())
                .stream()
                .map(c -> c.getFirstName() + " " + c.getLastName())
                .collect(Collectors.toList());
    }

    // ===== Derived Queries =====

    public List<Customer> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }

    public List<Customer> findByGender(String gender) {
        return repository.findByGender(gender);
    }

    public List<Customer> findByGenderFirstLastName(String gender, String firstName, String lastName) {
        return repository.findByGenderAndFirstNameAndLastName(gender, firstName, lastName);
    }

    public List<Customer> findByAddress(String street, String city, String state) {
        return repository.findByStreetAndCityAndState(street, city, state);
    }

    // ===== Custom Queries =====

    public List<Customer> findRecentCustomers(LocalDateTime date) {
        return repository.findRecentCustomers(date);
    }

    public List<Customer> findActiveBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return repository.findActiveBySalaryRange(minSalary, maxSalary);
    }

    public List<Customer> findByDepartmentNameAndActive(String deptName, Boolean active) {
        return repository.findByDepartmentNameAndActive(deptName, active);
    }

    @Transactional
    public void updateActiveStatusByDepartment(Long deptId, Boolean status) {
        repository.updateActiveStatusByDepartment(deptId, status);
    }

    public int countByDepartmentAndSalaryThreshold(Long deptId, BigDecimal salary) {
        return repository.countByDepartmentAndSalaryThreshold(deptId, salary);
    }

    // ===== Complex SQL Queries =====

    // Count customers per gender
    public List<Map<String, Object>> countCustomersByGender() {
        return repository.countCustomersByGender();
    }

    // Sum of salary per department
    public List<Map<String, Object>> sumSalaryByDepartment() {
        return repository.sumSalaryByDepartment();
    }

    // Rank customers by salary per department
    public List<Map<String, Object>> rankCustomersByDepartment() {
        return repository.rankCustomersByDepartment();
    }

    // Top 3 salaries per department
    public List<Map<String, Object>> top3SalariesPerDepartment() {
        return repository.top3SalariesPerDepartment();
    }

    // ===== Helper method to print Map results nicely =====
    public void printMapResults(List<Map<String, Object>> results) {
        for (Map<String, Object> row : results) {
            System.out.println(row.entrySet()
                                  .stream()
                                  .map(e -> e.getKey() + "=" + e.getValue())
                                  .collect(Collectors.joining(", ")));
        }
    }
}
