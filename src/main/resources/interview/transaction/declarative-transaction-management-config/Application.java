/*
4. Spring Bootstrapping

If you’re running Spring Boot, just add:
*/
package x.y;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // includes @EnableAutoConfiguration, @ComponentScan, @Configuration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}