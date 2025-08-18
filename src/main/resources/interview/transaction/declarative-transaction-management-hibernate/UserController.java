package x.y.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import x.y.model.User;
import x.y.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User create(@RequestParam String name) {
        return userService.registerUser(name);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/{id}/invoice")
    public String invoice(@PathVariable Long id) {
        userService.invoice(id);
        return "Invoice created for user " + id;
    }
}