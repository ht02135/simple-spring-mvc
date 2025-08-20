package com.example.filters;

import jakarta.servlet.*;
import java.io.IOException;

public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("LoggingFilter: Logging request path: " + ((jakarta.servlet.http.HttpServletRequest) request).getRequestURI());
        chain.doFilter(request, response);
    }
}