/*
UserRestfulController.java
//////////////////////////////
move away from the classic <form:form> post-back and instead do an 
AJAX form submission using jQuery, calling your Spring MVC controller’s 
REST API, and then dynamically updating the view
-------------------
1. Controller (API style)
We’ll keep the same UserController, but add an API endpoint that 
returns JSON instead of redirecting to JSP.
-------------------
2. JSP Form Page (userFormAjax.jsp)
Here we use jQuery to intercept the form submission and call the 
controller API asynchronously.
-------------------
3. What Changed?
1>Controller
	1a>Instead of returning a JSP in /save, it now returns JSON 
	(@ResponseBody + produces="application/json").
	1b>User object is automatically serialized to JSON (thanks to 
	Jackson, included by default in Spring Web MVC since 4.x+ if on classpath).
2>JSP
	2a>Regular HTML form + jQuery intercepts submit event.
	2b>Sends serialized form fields as AJAX POST request to /user/save.
	2c>On success, dynamically updates a <div> with the returned JSON data.
//////////////////////////////
4. How does AJAX pass data to @ModelAttribute?
When you do in jQuery:
$.ajax({
    url: "/user/save",
    type: "POST",
    data: $("#userForm").serialize()
});
-------------------------
The serialized form looks like:
name=Alice&email=alice@example.com
--------------------------
Spring’s binding flow is:
1>Check if "user" exists in model (from populateUser()).
2>Use that User object as the binding target.
3>Map request params (name, email) to fields of the User.
Equivalent to calling user.setName("Alice"); user.setEmail("alice@example.com");
That’s why @ModelAttribute("user") User user works seamlessly — 
whether the request comes from a JSP form post or an AJAX request.
////////////////////////////
1. The @ModelAttribute method
@ModelAttribute("commonMessage")
public String populateCommonMessage() {
    return "Welcome to Spring MVC @ModelAttribute Demo!";
}
This method is defined in your @Controller.
Key points:
1a>@ModelAttribute("commonMessage") tells Spring: "Before running any 
handler in this controller, put the returned value into the Model under 
the key 'commonMessage'."
1b>The return type is String, so the value itself is "Welcome to Spring 
MVC @ModelAttribute Demo!"
--------------------------
2. JSP Expression
<h2>${commonMessage}</h2>
2a>${commonMessage} is Expression Language (EL) syntax.
2b>EL looks up the attribute named commonMessage in the following scopes (in order):
Page
Request
Session
Application
3>In Spring MVC, the Model attributes are copied into the request scope, so EL can 
find them there.
////////////////////////
3. Spring MVC Request Flow (binding logic)
Let’s trace a GET request to /user/form:
1>DispatcherServlet receives request
URL: /user/form
Finds the appropriate controller (UserController) and handler method (showForm()).
2>HandlerAdapter prepares the handler
Before invoking showForm(), Spring detects all @ModelAttribute methods in the controller excluding those with request params binding.
Calls:
populateCommonMessage();
Result: "Welcome to Spring MVC @ModelAttribute Demo!"
Stores in the Model as:
model.addAttribute("commonMessage", "Welcome to Spring MVC @ModelAttribute Demo!");
3>Handler method executes
showForm() returns the view name: "userForm".
4>ViewResolver resolves JSP
InternalResourceViewResolver maps "userForm" → /WEB-INF/views/userForm.jsp.
5>Request is forwarded to JSP
The JSP receives a request with all model attributes in request scope.
commonMessage is available in request attributes.
6>SP EL evaluates ${commonMessage}
EL looks in the request attributes → finds commonMessage.
Value: "Welcome to Spring MVC @ModelAttribute Demo!"
Renders it inside <h2>.

hung: make sense, because starting point is api call /user/form..
but everything breaks apart if you hit jsp directly...
///////////////////////
*/
package com.example.controller;

import com.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserAjaxController {

    @ModelAttribute("commonMessage")
    public String populateCommonMessage() {
        return "Welcome to Spring MVC @ModelAttribute Demo!";
    }

    /*
    This method runs before every request handler in that controller.
	Whatever it returns is placed in the Model with key "user".
	That’s why you can reference ${user} in your JSP, or bind your form 
	with modelAttribute="user".
	When you submit the form (or AJAX), Spring reuses this object as the 
	target for data binding.
    */
    // Provide default User object
    @ModelAttribute("user")
    public User populateUser() {
        User u = new User();
        u.setName("Default Name");
        u.setEmail("default@example.com");
        return u;
    }

    // Return form page
    @GetMapping("/form")
    public String showForm() {
        return "userFormAjax";  // new JSP with jQuery form
    }

    /*
    Here, Spring takes request parameters (name=Alice&email=alice@example.com)
	It creates or retrieves a User object from the model
	Then binds form fields (or AJAX request params) to it automatically.
	That’s why @ModelAttribute is so powerful for form submissions — 
	you don’t manually parse request params.
    */
    // API endpoint called by jQuery (returns JSON)
    @PostMapping(value = "/save", produces = "application/json")
    @ResponseBody
    public User saveUser(@ModelAttribute("user") User user) {
        // Pretend saving to DB here...
        // Returning the updated user object as JSON
        return user;
    }

    // Another API for details (sharing the same @ModelAttribute "user")
    @GetMapping(value = "/details", produces = "application/json")
    @ResponseBody
    public User userDetails(@ModelAttribute("user") User user) {
        return user;
    }
}
