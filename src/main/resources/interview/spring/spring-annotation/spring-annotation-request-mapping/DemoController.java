/*
With this setup:
GET /api/hello → uses class-level + method-level @RequestMapping
POST /api/submit → demonstrates consumes and produces
GET /api/greet → shortcut @GetMapping
POST /api/create, PUT /api/update, DELETE /api/delete, PATCH /api/patch → shortcut mappings
////////////////////////
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
*/
package com.example.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")  // Class-level mapping
public class DemoController {

	// method-level mapping 
    // 1. Method with @RequestMapping - value/path and GET method
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String sayHello() {
        return "Hello from @RequestMapping with GET!";
    }

	// method-level mapping 
    // 2. Method with @RequestMapping - POST, consumes JSON, produces XML
    @RequestMapping(
        value = "/submit",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_XML_VALUE
    )
    @ResponseBody
    public String submitData(@RequestBody String json) {
        return "<response>Received JSON: " + json + "</response>";
    }

    // 3. Using shortcut annotations --------------------

	// method-level mapping 
    @GetMapping("/greet")
    @ResponseBody
    public String greet() {
        return "Hello from @GetMapping!";
    }

	// method-level mapping 
    @PostMapping("/create")
    @ResponseBody
    public String create() {
        return "Data created using @PostMapping!";
    }

	// method-level mapping 
    @PutMapping("/update")
    @ResponseBody
    public String update() {
        return "Data updated using @PutMapping!";
    }

	// method-level mapping 
    @DeleteMapping("/delete")
    @ResponseBody
    public String delete() {
        return "Data deleted using @DeleteMapping!";
    }

	// method-level mapping 
    @PatchMapping("/patch")
    @ResponseBody
    public String patch() {
        return "Data patched using @PatchMapping!";
    }
}
