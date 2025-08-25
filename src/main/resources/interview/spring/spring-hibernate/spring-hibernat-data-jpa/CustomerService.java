package com.example.service;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Customer> findByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    @Transactional(readOnly = true)
    public List<Customer> findRecentCustomers(LocalDateTime date) {
        return customerRepository.findRecentCustomers(date);
    }

    @Transactional(readOnly = true)
    public List<Customer> findActiveBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        return customerRepository.findActiveBySalaryRange(minSalary, maxSalary);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public void updateActiveStatusByDepartment(Long departmentId, Boolean status) {
        customerRepository.updateActiveStatusByDepartment(departmentId, status);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getCustomerStatsByGender() {
        return customerRepository.countCustomersByGender();
    }
}