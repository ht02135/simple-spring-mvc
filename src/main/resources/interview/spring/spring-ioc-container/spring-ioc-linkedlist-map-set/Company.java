package com.example;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Company {
    private List<Employee> employees;
    private Set<String> departments;
    private Map<String, String> projects;

    // Default constructor (for property injection)
    public Company() {}

    // Constructor injection
    public Company(List<Employee> employees, Set<String> departments, Map<String, String> projects) {
        this.employees = employees;
        this.departments = departments;
        this.projects = projects;
    }

    // Setters (for property injection)
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
    public void setDepartments(Set<String> departments) { this.departments = departments; }
    public void setProjects(Map<String, String> projects) { this.projects = projects; }

    public void displayInfo() {
        System.out.println("=== Company Info ===");

        System.out.println("Employees:");
        for (Employee e : employees) {
            System.out.println(" - " + e.getId() + ": " + e.getName());
        }

        System.out.println("Departments:");
        for (String d : departments) {
            System.out.println(" - " + d);
        }

        System.out.println("Projects:");
        for (Map.Entry<String, String> entry : projects.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
