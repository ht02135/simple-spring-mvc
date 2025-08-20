package com.example.filters;

import jakarta.servlet.*;
import java.io.IOException;

public class HeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("HeaderFilter: Adding custom header.");
        chain.doFilter(request, response);
    }
}