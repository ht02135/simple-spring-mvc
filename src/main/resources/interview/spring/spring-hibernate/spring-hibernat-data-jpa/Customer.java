/*
You don't strictly need Hibernate mapping files (.hbm.xml) 
since you're using JPA annotations (@Entity, @Table, @Column, etc.) 
on your Customer class
*/
package com.example.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "street")
    private String street;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;
    
    @Column(name = "active")
    private Boolean active;
    
    @Column(name = "dept_id")
    private Long deptId;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    // Constructors
    public Customer() {}
    
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = LocalDateTime.now();
        this.active = true;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}