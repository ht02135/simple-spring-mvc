/*
want a full example of Spring setup showing both XML-based configuration 
and Java-based configuration, along with usage of the core annotations:
@Autowired, @Qualifier, @Component, @Service, @Repository, @Controller.
/////////////////////////
AppConfig.java
4. Java-based Configuration (Alternative to XML)
@ComponentScan(basePackages = "com.example")
will scan @Autowired, @Qualifier, @Component, @Service, @Repository, @Controller.

@Configuration and @ComponentScan: The key is to use the @Configuration 
annotation on a class to declare it as a Spring configuration class and 
@ComponentScan to tell Spring where to look for components (beans) 
annotated with @Component, @Service, @Repository, and @Controller.
The basePackages attribute specifies the package(s) to scan.
//////////////////////
6. Quick Summary of Annotations
@Component → Generic Spring bean.
@Service → Service-layer bean.
@Repository → DAO/Data access layer bean.
@Controller → Web layer controller.
@Autowired → Injects dependency automatically.
@Qualifier → Used with @Autowired to choose a specific bean among multiple candidates.
*/
package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
    // You can define beans here if needed
}
