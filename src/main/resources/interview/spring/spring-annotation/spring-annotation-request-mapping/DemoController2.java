/*
With this setup, you can use the same ApiRequest<T> and 
ApiResponse<T> for any entity (not just User).
/////////////////////
@RequestMapping has several attributes to define the mapping more precisely:
1>value or path: Specifies the URL or URL pattern to which the method is mapped.
2>method: Specifies the HTTP request method(s) (e.g., RequestMethod.GET, RequestMethod.POST).
3>consumes: Specifies the media types the controller can consume (e.g., application/json).
4>produces: Specifies the media types the controller can produce (e.g., application/xml).
///////////////////
Spring introduced more specific and easier-to-read annotations
1>@GetMapping: A shortcut for @RequestMapping(method = RequestMethod.GET).
2>@PostMapping: A shortcut for @RequestMapping(method = RequestMethod.POST).
3>@PutMapping: A shortcut for @RequestMapping(method = RequestMethod.PUT).
4>@DeleteMapping: A shortcut for @RequestMapping(method = RequestMethod.DELETE).
5>@PatchMapping: A shortcut for @RequestMapping(method = RequestMethod.PATCH).
////////////////////////////////
*/
package com.example.controller;

import com.example.dto.ApiRequest;
import com.example.dto.ApiResponse;
import com.example.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users") // class-level mapping
public class DemoController {

    // GET method-level mapping 
    @GetMapping(value = "/hello", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<User> sayHello(@RequestParam(defaultValue = "Guest") String name) {
        User user = new User();
        user.setName(name);
        user.setAge(0);

        return new ApiResponse<>("SUCCESS", "Hello " + name, user);
    }

    // POST method-level mapping 
    @PostMapping(value = "/create",
                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<User> createUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        return new ApiResponse<>("SUCCESS", "User created successfully", user);
    }

    // PUT method-level mapping 
    @PutMapping(value = "/update",
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<User> updateUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        return new ApiResponse<>("SUCCESS", "User updated successfully", user);
    }

    // DELETE method-level mapping 
    @DeleteMapping(value = "/delete/{name}",
                   produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<String> deleteUser(@PathVariable("name") String name) {
        return new ApiResponse<>("SUCCESS", "User deleted successfully", name);
    }

    // PATCH method-level mapping 
    @PatchMapping(value = "/patch",
                  consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<User> patchUser(@RequestBody ApiRequest<User> request) {
        User user = request.getData();
        return new ApiResponse<>("SUCCESS", "User partially updated", user);
    }
}
