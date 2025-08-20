package com.example.filters;

import jakarta.servlet.*;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("AuthenticationFilter: Authenticating user.");
        // In a real application, you'd check for a token or session.
        chain.doFilter(request, response);
    }
}