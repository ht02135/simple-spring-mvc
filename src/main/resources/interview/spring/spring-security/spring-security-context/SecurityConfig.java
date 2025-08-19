/*
What is Security Context?
Security Context is a core concept in Spring Security that holds 
security information about the current execution thread. It contains 
authentication details of the currently authenticated user and is 
accessible throughout the application during request processing.
Key Components
1. SecurityContext
Container for security information
Holds the Authentication object
Managed by SecurityContextHolder
////////////////////
2. SecurityContextHolder
Static utility class that provides access to SecurityContext
Uses ThreadLocal by default to store context per thread
Three storage strategies:

MODE_THREADLOCAL (default)
MODE_INHERITABLETHREADLOCAL
MODE_GLOBAL
////////////////////
3. Authentication Object
Represents the authenticated user
Contains:
Principal (usually UserDetails)
Credentials (password, tokens)
Authorities (roles/permissions)
Authentication status
//////////////////////
Best Practices
Never store SecurityContext in static variables - Use SecurityContextHolder properly
Clear context after processing - Especially in custom filters
Use method-level security for fine-grained control
Validate authentication state before accessing user details
Handle authentication failures gracefully
Use @Transactional appropriately with security methods
Test security configurations thoroughly
//////////////////////
Security Context Lifecycle
Authentication Filter captures credentials
Authentication Manager validates credentials
Authentication Provider performs actual authentication
SecurityContext is populated with Authentication object
Request processing continues with security context available
SecurityContextHolder clears context after request completion
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}