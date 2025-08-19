/*
To implement a custom filter in Spring Security, you'll need to create a 
filter class, configure it to be managed by Spring, and then add it to 
the filter chain at the appropriate position. A custom filter is essential 
when you need to perform actions like API key validation, token-based 
authentication, or logging before the request reaches the application's endpoints.
/////////////////////
Step 1: Create the Custom Filter Class
First, create a class that extends OncePerRequestFilter. This abstract class 
guarantees that your filter's doFilterInternal method will only be called 
once per request, which prevents redundant processing.

This example filter CustomAuthenticationFilter checks for a header named X-API-Key. If the header 
exists and its value is "my-secret-key", the filter allows the request 
to proceed. Otherwise, it sends an HTTP 401 Unauthorized response.
/////////////////////
Step 2: Configure the Filter in Spring Security
Next, you need to register your custom filter CustomAuthenticationFilterConfig.java with Spring's security 
configuration. You'll use the SecurityFilterChain bean to define 
the order of the filters.
//////////////////////
In the SecurityConfig class:
a>We inject our CustomAuthenticationFilter as a dependency.
b>The key part is http.addFilterBefore(customAuthenticationFilter, 
UsernamePasswordAuthenticationFilter.class). This method tells 
Spring to execute our filter before the built-in 
UsernamePasswordAuthenticationFilter.
//////////////////////
You can place your filter at different points in the chain using methods like:
    addFilterBefore(): Useful for authentication or pre-processing tasks.
    addFilterAfter(): Useful for post-processing or logging.
    addFilterAt(): Used to replace an existing filter.
*/
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Check for a specific header, e.g., 'X-API-Key'
        String apiKey = request.getHeader("X-API-Key");

        if (apiKey != null && apiKey.equals("my-secret-key")) {
            // If the key is valid, continue the filter chain
            filterChain.doFilter(request, response);
        } else {
            // If the key is invalid, reject the request
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid API Key");
        }
    }
}