/*
It's important to note the distinction between 
a filter managed by the Spring container 
and one managed by the servlet container. 

A filter registered as a regular servlet filter is managed by the servlet 
container, which limits its ability to leverage Spring's dependency injection 
features. To bridge this gap, Spring provides mechanisms like 
FilterRegistrationBean or DelegatingFilterProxy to integrate servlet filters 
with the Spring application context, allowing them to be managed as Spring beans.
///////////////////////
Filter Managed by the Servlet Container
When a filter is managed directly by the servlet container, it's defined 
and configured in a standard way, often using annotations from the Jakarta 
Servlet specification like @WebFilter or by configuring it in a web.xml 
file. The servlet container handles its lifecycle, including instantiation, 
initialization (init() method), and destruction (destroy() method). 
This approach does not use the Spring container's features, so it is not 
possible to inject Spring beans directly into the filter.
*/
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/api/*")
public class ServletContainerManagedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ServletContainerManagedFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("ServletContainerManagedFilter is filtering a request.");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("ServletContainerManagedFilter destroyed.");
    }
}