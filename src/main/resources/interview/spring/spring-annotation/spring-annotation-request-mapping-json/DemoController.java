/*
You want every response wrapped in ApiResponse and serialized 
strictly as JSON, not XML. That means:
Add Jackson dependency in pom.xml (for JSON serialization).
Configure Spring MVC to use only JSON (disable XML HttpMessageConverters).
Provide ApiResponse and ApiRequest DTOs.
Make sure ResponseEntity<ApiResponse<T>> always returns JSON.
*/
package com.example.controller;

import com.example.dto.ApiRequest;
import com.example.dto.ApiResponse;
import com.example.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE) // final path = /api/users/**
public class DemoController {

    // GET /api/users/hello?name=Guest
    @GetMapping("/hello")
    public ResponseEntity<ApiResponse<User>> sayHello(@RequestParam(defaultValue = "Guest") String name) {
        User user = new User(name, 0);
        ApiResponse<User> response = new ApiResponse<>("SUCCESS", "Hello " + name, user);
        return ResponseEntity.ok(response);
    }

    // POST /api/users/create
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        ApiResponse<User> response = new ApiResponse<>("SUCCESS", "User created successfully", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /api/users/update
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<User>> updateUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        ApiResponse<User> response = new ApiResponse<>("SUCCESS", "User updated successfully", user);
        return ResponseEntity.ok(response);
    }

    // DELETE /api/users/delete/{name}
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable("name") String name) {
        ApiResponse<String> response = new ApiResponse<>("SUCCESS", "User deleted successfully", name);
        return ResponseEntity.ok(response);
    }

    // PATCH /api/users/patch
    @PatchMapping(value = "/patch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<User>> patchUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        ApiResponse<User> response = new ApiResponse<>("SUCCESS", "User partially updated", user);
        return ResponseEntity.ok(response);
    }

    // GET /api/users/all
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = Arrays.asList(
            new User("Alice", 25),
            new User("Bob", 30)
        );
        ApiResponse<List<User>> response = new ApiResponse<>("SUCCESS", "Fetched all users", users);
        return ResponseEntity.ok(response);
    }

    // GET /api/users/map
    @GetMapping("/map")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("user", new User("Charlie", 28));
        data.put("roles", Set.of("ADMIN", "USER"));

        ApiResponse<Map<String, Object>> response = new ApiResponse<>("SUCCESS", "Map response", data);
        return ResponseEntity.ok(response);
    }

    // Optional: ensure any unhandled error still returns JSON ApiResponse
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAnyException(Exception ex) {
        ApiResponse<Void> body = new ApiResponse<>("ERROR", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
