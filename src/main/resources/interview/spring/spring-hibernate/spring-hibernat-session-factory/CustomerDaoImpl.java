/*
Native Hibernate SessionFactory Benefits:

Full control over Hibernate Session API
Custom DAO implementations for complex business logic
Direct access to Hibernate-specific features
Better performance tuning capabilities
Legacy system compatibility
*/
package com.example.dao.impl;

import com.example.dao.CustomerDao;
import com.example.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Customer save(Customer customer) {
        getCurrentSession().saveOrUpdate(customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Customer customer = getCurrentSession().get(Customer.class, id);
        return Optional.ofNullable(customer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Customer> findAll() {
        Query<Customer> query = getCurrentSession().createQuery("FROM Customer", Customer.class);
        return query.getResultList();
    }

    @Override
    public void delete(Long id) {
        Customer customer = getCurrentSession().get(Customer.class, id);
        if (customer != null) {
            getCurrentSession().delete(customer);
        }
    }

    @Override
    public void update(Customer customer) {
        getCurrentSession().update(customer);
    }

    // ================= Custom Query Methods =================

    @Override
    @SuppressWarnings("unchecked")
    public List<Customer> findByLastName(String lastName) {
        Query<Customer> query = getCurrentSession()
                .createQuery("FROM Customer c WHERE c.lastName = :lastName", Customer.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Customer> findByGender(String gender) {
        Query<Customer> query = getCurrentSession()
                .createQuery("FROM Customer c WHERE c.gender = :gender", Customer.class);
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unc