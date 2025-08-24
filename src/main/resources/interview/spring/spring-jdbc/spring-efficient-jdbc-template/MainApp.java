/*
5. Test Runner (Bootstrap Spring)
*/
package com.example;

import com.example.model.Customer;
import com.example.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        CustomerService service = ctx.getBean(CustomerService.class);

        Customer c1 = new Customer();
        c1.setName("Alice");
        c1.setEmail("alice@example.com");
        c1.setGender("F");
        service.registerCustomer(c1);

        Customer c2 = new Customer();
        c2.setName("Bob");
        c2.setEmail("bob@example.com");
        c2.setGender("M");
        service.registerCustomer(c2);

        service.listAll().forEach(c ->
                System.out.println(c.getId() + " " + c.getName() + " " + c.getEmail()));
        
    }
}
