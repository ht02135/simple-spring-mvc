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
    public List<Customer> findByLastName(String lastName) {
        Query<Customer> query = getCurrentSession()
                .createQuery("FROM Customer c WHERE c.lastName = :lastName", Customer.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<Customer> findByGender(String gender) {
        Query<Customer> query = getCurrentSession()
                .createQuery("FROM Customer c WHERE c.gender = :gender", Customer.class);
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    @Override
    public List<Customer> findByGenderAndFirstNameAndLastName(String gender, String firstName, String lastName) {
        Query<Customer> query = getCurrentSession().createQuery(
                "FROM Customer c WHERE c.gender = :gender AND c.firstName = :firstName AND c.lastName = :lastName",
                Customer.class);
        query.setParameter("gender", gender);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<Customer> findByStreetAndCityAndState(String street, String city, String state) {
        Query<Customer> query = getCurrentSession().createQuery(
                "FROM Customer c WHERE c.street = :street AND c.city = :city AND c.state = :state",
                Customer.class);
        query.setParameter("street", street);
        query.setParameter("city", city);
        query.setParameter("state", state);
        return query.getResultList();
    }

    @Override
    public List<Customer> findRecentCustomers(LocalDateTime date) {
        Query<Customer> query = getCurrentSession().createQuery(
                "FROM Customer c WHERE c.createdDate > :date",
                Customer.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public List<Customer> findActiveBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        Query<Customer> query = getCurrentSession().createQuery(
                "FROM Customer c WHERE c.salary BETWEEN :minSalary AND :maxSalary AND c.active = true",
                Customer.class);
        query.setParameter("minSalary", minSalary);
        query.setParameter("maxSalary", maxSalary);
        return query.getResultList();
    }

    @Override
    public List<Customer> findByDepartmentNameAndActive(String departmentName, Boolean active) {
        Query<Customer> query = getCurrentSession().createQuery(
                "SELECT c FROM Customer c " +
                "JOIN Department d ON c.deptId = d.id " +
                "WHERE d.name = :deptName AND c.active = :active " +
                "ORDER BY c.salary DESC",
                Customer.class);
        query.setParameter("deptName", departmentName);
        query.setParameter("active", active);
        return query.getResultList();
    }

    @Override
    public int countByDepartmentAndSalaryThreshold(Long departmentId, BigDecimal salary) {
        Query<Long> query = getCurrentSession().createQuery(
                "SELECT COUNT(c) FROM Customer c WHERE c.deptId = :deptId AND c.salary > :salary",
                Long.class);
        query.setParameter("deptId", departmentId);
        query.setParameter("salary", salary);
        return query.getSingleResult().intValue();
    }
}
