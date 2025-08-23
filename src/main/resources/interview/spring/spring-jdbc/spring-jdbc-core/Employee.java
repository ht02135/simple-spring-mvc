// Employee.java - Model Class
package com.example.model;

public class Employee {
    private Long id;
    private String name;
    private String email;
    private String department;
    private Double salary;
    
    public Employee() {}
    
    public Employee(String name, String email, String department, Double salary) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    
    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', email='" + email + 
               "', department='" + department + "', salary=" + salary + "}";
    }
}