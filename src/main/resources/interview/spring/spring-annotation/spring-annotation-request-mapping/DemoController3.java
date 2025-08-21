/*
1. Do people normally wrap ResponseEntity<ApiResponse<T>>?
Yes, and it’s quite common.
ResponseEntity is a Spring wrapper that gives you status codes + headers.
ApiResponse<T> is your application-level response wrapper (status, 
message, data).
/////////////////////////
So you’re combining:
Transport-level (HTTP stuff: 200 OK, headers, etc.)
Domain-level (your business response format: "SUCCESS", "FAILURE", etc.)
That’s a best practice in many APIs, especially when you want consistency 
across JSON/XML responses.
*/
package com.example.controller;

import com.example.dto.ApiRequest;
import com.example.dto.ApiResponse;
import com.example.entity.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users") // class-level mapping
public class DemoController {

    // GET method-level mapping 
    @GetMapping(value = "/hello", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<User>> sayHello(@RequestParam(defaultValue = "Guest") String name) {
        User user = new User();
        user.setName(name);
        user.setAge(0);

        ApiResponse<User> response = new ApiResponse<>("SUCCESS", "Hello " + name, user);
        return ResponseEntity.ok(response);
    }

    // POST method-level mapping 
    @PostMapping(value = "/create",
                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        ApiResponse<User> response = new ApiResponse<>("SUCCESS", "User created successfully", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT method-level mapping 
    @PutMapping(value = "/update",
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<User>> updateUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        ApiResponse<User> response = new ApiResponse<>("SUCCESS", "User updated successfully", user);
        return ResponseEntity.ok(response);
    }

    // DELETE method-level mapping 
    @DeleteMapping(value = "/delete/{name}",
                   produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable("name") String name) {
        ApiResponse<String> response = new ApiResponse<>("SUCCESS", "User deleted successfully", name);
        return ResponseEntity.ok(response);
    }

    // PATCH method-level mapping 
    @PatchMapping(value = "/patch",
                  consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ApiResponse<User>> patchUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        ApiResponse<User> response = new ApiResponse<>("SUCCESS", "User partially updated", user);
        return ResponseEntity.ok(response);
    }

    // Example: returning List / Map / Set
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = Arrays.asList(
            new User("Alice", 25),
            new User("Bob", 30)
        );

        ApiResponse<List<User>> response = new ApiResponse<>("SUCCESS", "Fetched all users", users);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/map", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("user", new User("Charlie", 28));
        data.put("roles", Set.of("ADMIN", "USER"));

        ApiResponse<Map<String, Object>> response = new ApiResponse<>("SUCCESS", "Map response", data);
        return ResponseEntity.ok(response);
    }
}
