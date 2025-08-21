package com.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
full working Spring IoC example with applicationContext.xml and Java 
classes that show how to inject a <list>, <set>, and <map> into a 
bean (like a Company object holding employees, departments, and projects).

This shows list of Employee objects, set of department names, and map of 
project codes to names injected via XML
*/

public class MainApp {
    public static void main(String[] args) {
        // Load Spring container
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get Company bean
        Company company = (Company) context.getBean("company");

        // Display injected data
        company.displayInfo();

        // Closing IoC container (cleanup resources)
        context.close();
    }
}
