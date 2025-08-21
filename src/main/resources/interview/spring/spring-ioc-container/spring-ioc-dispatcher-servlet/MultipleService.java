// Service Classes
package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public UserService() {
        // Sample data
        saveUser(new User(counter.getAndIncrement(), "John Doe", "john@example.com"));
        saveUser(new User(counter.getAndIncrement(), "Jane Smith", "jane@example.com"));
        saveUser(new User(counter.getAndIncrement(), "Bob Johnson", "bob@example.com"));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(counter.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public void deleteUser(Long id) {
        users.remove(id);
    }

    public long getTotalUserCount() {
        return users.size();
    }

    public Map<String, Object> getUserStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", users.size());
        stats.put("activeUsers", users.size()); // Mock data
        return stats;
    }
}

package com.example.service;

import com.example.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    
    private final Map<Long, Product> products = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public ProductService() {
        // Sample data
        saveProduct(new Product(counter.getAndIncrement(), "Laptop", "Gaming Laptop", new BigDecimal("1299.99")));
        saveProduct(new Product(counter.getAndIncrement(), "Mouse", "Wireless Mouse", new BigDecimal("29.99")));
        saveProduct(new Product(counter.getAndIncrement(), "Keyboard", "Mechanical Keyboard", new BigDecimal("89.99")));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProductById(Long id) {
        return products.get(id);
    }

    public Product saveProduct(Product product) {
        if (product.getId() == null) {
            product.setId(counter.getAndIncrement());
        }
        products.put(product.getId(), product);
        return product;
    }

    public void deleteProduct(Long id) {
        products.remove(id);
    }

    public long getTotalProductCount() {
        return products.size();
    }
}

// ===================================================================
// Model Classes
package com.example.model;

public class User {
    private Long id;
    private String name;
    private String email;

    public User() {}

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

package com.example.model;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;

    public Product() {}

    public Product(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}

// ===================================================================
// Interceptor Classes
package com.example.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebSecurityInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Web security logic here
        System.out.println("Web Security Interceptor - Request: " + request.getRequestURI());
        return true;
    }
}

package com.example.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiAuthenticationInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // API authentication logic here
        String authHeader = request.getHeader("Authorization");
        System.out.println("API Auth Interceptor - Auth Header: " + authHeader);
        
        // For demo purposes, allow all requests
        // In real implementation, validate JWT token here
        return true;
    }
}

package com.example.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminSecurityInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request,