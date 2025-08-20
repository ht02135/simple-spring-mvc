package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.Resource;

@Component
public class Employee {
    // 3> Field Injection
    @Autowired
    private MessageService messageService;

    // 5> Value Injection
    @Value("John Doe")
    private String name;
    
    // 1> Constructor Injection
    public Employee(String department, String name) {
        this.department = department;
        this.name = name;
    }

    // 2> Setter Injection (using @Resource for JSR-250 spec)
    private MessageService messageServiceBySetter;
    @Resource
    public void setMessageServiceBySetter(MessageService messageServiceBySetter) {
        this.messageServiceBySetter = messageServiceBySetter;
    }

    private String department;
    private int employeeId;

    // Getters and a method to demonstrate injection
    public void showDetails() {
        System.out.println("Employee Name (Value Injection): " + name);
        System.out.println("Department (Constructor/Value Injection): " + department);
        System.out.println("Using Field Injection: ");
        messageService.sendMessage("Welcome to the company!");
        System.out.println("Using Setter Injection: ");
        messageServiceBySetter.sendMessage("You have a new task.");
    }
}