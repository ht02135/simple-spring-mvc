/*
Service with Transaction
*/
package com.example.service;

import com.example.dao.CustomerDao;
import com.example.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao dao) {
        this.customerDao = dao;
    }

    @Transactional
    public void registerCustomer(Customer c) {
        customerDao.save(c);
        // Example: if something fails here, rollback happens automatically
    }

    public List<Customer> listAll() {
        return customerDao.findAll();
    }
}
