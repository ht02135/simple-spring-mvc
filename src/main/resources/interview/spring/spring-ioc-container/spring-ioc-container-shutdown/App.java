package com.example;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	/*
	shows how the container shutdown (close()) method works, and how beans 
	clean up resources when destroyed.
	////////////////////////
	Expected Console Output
	ResourceBean initialized: Opening resource...
	ResourceBean is working...
	ResourceBean cleanup: Closing resource...
	////////////////////////
	This demonstrates:
	IoC container startup → bean initialized.
	Bean usage.
	Container shutdown via close() → bean cleanup method is called.
	*/
    public static void main(String[] args) {
        // Load context
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Get bean
        ResourceBean bean = context.getBean("resourceBean", ResourceBean.class);
        bean.doWork();

        // Explicit shutdown (important for standalone apps)
        context.close();
    }
}
