/*
he FilterChainProxy is the main class that manages the security filter chain. 
It contains a list of SecurityFilterChain objects, each with a specific 
request matcher and a list of filters. When a request comes in, FilterChainProxy 
checks which SecurityFilterChain matches the request's URL. Once a match is 
found, the filters in that specific chain are executed in order.
/////////////////
A typical security filter chain includes filters like:
1>UsernamePasswordAuthenticationFilter: Handles form-based login requests.
2>BasicAuthenticationFilter: Processes HTTP Basic authentication headers.
3>AuthorizationFilter: Checks if the authenticated user has the necessary 
permissions to access a resource
4>ExceptionTranslationFilter: Catches security-related exceptions and 
translates them into appropriate HTTP responses, like redirecting to a 
login page or sending a 403 Forbidden error.
/////////////////
Here's a basic Java example using Spring Security's configuration to 
define a security filter chain. This configuration sets up form-based 
authentication and authorizes all requests to /public/** while requiring 
authentication for all other requests.
/////////////////
In this example, the securityFilterChain bean configures the order and 
behavior of the filters. It uses the authorizeHttpRequests method to 
define authorization rules. It also enables form-based login using 
formLogin. Spring Security automatically adds the relevant filters to 
the chain based on this configuration, abstracting the complex process 
of manually defining and ordering each filter.
////////////////////////////////////
Filter Chain Order
The order of these filters is crucial for the security mechanism to work 
correctly. A simplified flow is as follows:

1>A request arrives and is handled by the SecurityContextPersistenceFilter, 
which loads the SecurityContext from the session.
2>The request then passes through various authentication filters, such as 
UsernamePasswordAuthenticationFilter, which handle login attempts.
3>After successful authentication, the request proceeds to filters that 
manage authorization and other security features.
4>Finally, the request is passed to the FilterSecurityInterceptor for 
authorization checks.
5>If any filter in the chain throws an exception, the ExceptionTranslationFilter 
catches it and handles the response.
*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/public/**").permitAll() // Allow public access
                .anyRequest().authenticated() // Require authentication for all other requests
            )
            .formLogin(formLogin -> formLogin.permitAll()) // Enable form login for all
            .csrf(csrf -> csrf.disable()); // Disable CSRF for this example

        return http.build();
    }
}