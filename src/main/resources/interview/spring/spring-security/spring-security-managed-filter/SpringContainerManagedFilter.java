/*
It's important to note the distinction between 
a filter managed by the Spring container 
and one managed by the servlet container. 

A filter registered as a regular servlet filter is managed by the servlet 
container, which limits its ability to leverage Spring's dependency injection 
features. To bridge this gap, Spring provides mechanisms like 
FilterRegistrationBean or DelegatingFilterProxy to integrate servlet filters 
with the Spring application context, allowing them to be managed as Spring beans.
///////////////////////////
Filter Managed by the Spring Container
To have a filter managed by the Spring container, it must be defined as a 
Spring bean. Spring Boot provides an easy way to do this using @Component and 
@Order annotations, or by explicitly registering the filter with a 
FilterRegistrationBean. This method allows you to inject other Spring beans 
into your filter.
*/
import java.io.IOException;

public class SpringContainerManagedFilter implements Filter {

    // You can now inject other Spring beans here, e.g., using @Autowired
    // @Autowired
    // private MyService myService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("SpringContainerManagedFilter is filtering a request.");
        // myService.doSomething(); // You can call methods on injected beans
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic
    }

    @Override
    public void destroy() {
        // Cleanup logic
    }
}