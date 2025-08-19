package com.example.client;

import com.example.service.ReportService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        ReportService service = context.getBean("reportService", ReportService.class);

        try {
            service.generateReport();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}