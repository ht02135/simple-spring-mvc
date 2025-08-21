package com.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n--- Using Property Injection ---");
        Company company1 = (Company) context.getBean("companyProperty");
        company1.displayInfo();

        System.out.println("\n--- Using Constructor Injection ---");
        Company company2 = (Company) context.getBean("companyConstructor");
        company2.displayInfo();

        System.out.println("\n--- Using Separate Collection Beans ---");
        Company company3 = (Company) context.getBean("companyRefCollections");
        company3.displayInfo();

        context.close();
    }
}
