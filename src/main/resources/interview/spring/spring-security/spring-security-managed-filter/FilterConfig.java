/*
It's important to note the distinction between 
a filter managed by the Spring container 
and one managed by the servlet container. 

A filter registered as a regular servlet filter is managed by the servlet 
container, which limits its ability to leverage Spring's dependency injection 
features. To bridge this gap, Spring provides mechanisms like 
FilterRegistrationBean or DelegatingFilterProxy to integrate servlet filters 
with the Spring application context, allowing them to be managed as Spring beans.
*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import jakarta.servlet.*;
import java.io.IOException;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SpringContainerManagedFilter> springFilterRegistration() {
        FilterRegistrationBean<SpringContainerManagedFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SpringContainerManagedFilter());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1); // Set the order for the filter chain
        return registrationBean;
    }
}