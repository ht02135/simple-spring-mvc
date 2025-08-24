/*
4. Spring Data JDBC Repository

If you want to use Spring Data JDBC instead of writing DAO:
*/
package com.example.repository;

import com.example.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
