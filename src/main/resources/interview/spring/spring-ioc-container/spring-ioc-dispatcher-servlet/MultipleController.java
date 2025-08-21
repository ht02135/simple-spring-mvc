/*
MultipleController.java
*/
// Web Frontend Controllers
package com.example.controller.web;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebHomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Web Frontend");
        return "home";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user-detail";
    }
}

@Controller
@RequestMapping("/products")
public class WebProductController {

    @Autowired
    private com.example.service.ProductService productService;

    @GetMapping("/")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-detail";
    }
}

// ===================================================================
// REST API Controllers
package com.example.controller.api;

import com.example.service.UserService;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class ApiUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

@RestController
@RequestMapping("/products")
public class ApiProductController {

    @Autowired
    private com.example.service.ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<com.example.model.Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.model.Product> getProductById(@PathVariable Long id) {
        com.example.model.Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<com.example.model.Product> createProduct(@RequestBody com.example.model.Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }
}

// ===================================================================
// Admin Controllers
package com.example.controller.admin;

import com.example.service.UserService;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class AdminDashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.getTotalUserCount());
        model.addAttribute("totalProducts", productService.getTotalProductCount());
        model.addAttribute("message", "Admin Dashboard");
        return "dashboard";
    }

    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-management";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully");
        return "redirect:/admin/users";
    }

    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-management";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        return "settings";
    }
}

@Controller
@RequestMapping("/reports")
public class AdminReportController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String reports(Model model) {
        return "reports";
    }

    @GetMapping("/users")
    public String userReport(Model model) {
        model.addAttribute("userStats", userService.getUserStatistics());
        return "user-report";
    }

    @GetMapping("/export/users")
    @ResponseBody
    public String exportUsers() {
        // Export logic here
        return "User export completed";
    }
}