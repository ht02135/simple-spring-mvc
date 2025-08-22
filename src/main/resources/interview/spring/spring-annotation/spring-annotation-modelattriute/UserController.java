/*
Controller with @ModelAttribute usage
///////////////////////
how @ModelAttribute works in a Spring MVC project — including Maven 
setup, applicationContext.xml, web.xml, controller, and especially 
how data is shared between multiple handler methods within the same controller.
//////////////////////
Key Points in This Example
1>Data Binding
In saveUser(@ModelAttribute("user") User user) → Spring automatically binds 
form input fields to the User object.
2>Shared Data Between Handlers
	2a>@ModelAttribute("user") at the method level ensures that the same 
	User object is available across multiple request mappings (/form, /save, /details).
	2b>@ModelAttribute("commonMessage") is also shared across all handlers → 
	all JSPs can display it without explicitly adding it to Model.
*/
package com.example.controller;

import com.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    // Shared model data across all handlers in this controller
    @ModelAttribute("commonMessage")
    public String populateCommonMessage() {
        return "Welcome to Spring MVC @ModelAttribute Demo!";
    }

    // Shared object initialization
    @ModelAttribute("user")
    public User populateUser() {
        User u = new User();
        u.setName("Default Name");
        u.setEmail("default@example.com");
        return u;
    }

    // Handler 1: Show form
    @GetMapping("/form")
    public String showForm() {
        return "userForm";  // JSP name
    }

    // Handler 2: Process form submission
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("successMessage", "User saved successfully!");
        return "userDetails"; // JSP name
    }

    // Handler 3: Display user details (reuses @ModelAttribute data)
    @GetMapping("/details")
    public String userDetails(@ModelAttribute("user") User user) {
        return "userDetails"; // Will display the same shared User object
    }
}
