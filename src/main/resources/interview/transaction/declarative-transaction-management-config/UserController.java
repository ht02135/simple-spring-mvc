
/*
Let’s extend our annotation-driven Spring transaction example by 
adding a simple web layer with UserController and CourseController.

We’ll use Spring MVC style controllers that call the UserService 
and CourseService
*/
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

    // POST /users
    @PostMapping
    public Long createUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}

