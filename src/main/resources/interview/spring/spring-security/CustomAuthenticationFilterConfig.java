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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class CustomAuthenticationFilterConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;

    public SecurityConfig(CustomAuthenticationFilter customAuthenticationFilter) {
        this.customAuthenticationFilter = customAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/public").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for this example

        return http.build();
    }
}