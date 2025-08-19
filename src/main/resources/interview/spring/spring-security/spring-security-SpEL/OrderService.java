/*
Expanded to include @PreFilter and @PostFilter.
////////////////////
you want a full runnable Spring Boot example that demonstrates 
@PreAuthorize, @PostAuthorize, @PreFilter, and @PostFilter, 
including Java classes, XML configuration (for AOP/security), 
and the pom.xml.
////////////////////
Now you have:
@PreAuthorize → method entry restriction
@PostAuthorize → result validation after execution
@PreFilter → filters input collection before execution
@PostFilter → filters returned collection after execution
Both Java config and XML config
pom.xml ready to run
*/
package com.example.securityaopdemo.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    // @PreAuthorize example: checks if user has ADMIN role
    @PreAuthorize("hasRole('ADMIN')")
    public String createOrder(String orderId) {
        return "Order " + orderId + " created successfully.";
    }

    // @PostAuthorize example: checks returned order’s owner
    /*
    That string inside @PostAuthorize("...") is a Spring Expression 
    Language (SpEL) expression.
    //////////////////////
    What is SpEL?
	SpEL = Spring Expression Language
	It’s a little expression language that Spring uses inside annotations 
	(and XML) to dynamically evaluate conditions at runtime.
	It can access method arguments, return values, beans, authentication info, etc.
	It returns true/false for security checks in @PreAuthorize / @PostAuthorize.
	////////////////////
	1>returnObject → refers to the method’s return value (in this case, an Order object).
	2>.owner → calls the getOwner() getter on that object.
	3>authentication → refers to the current Authentication object from Spring 
	Security (the logged-in user).
	4>.name → gets the username (e.g., "user123").
	//////////////////////
	Quick SpEL Cheat Sheet for Security
	Expression	Meaning
	---------------------------
	#paramName	Access method parameter (by name)
	authentication	Current authentication object
	authentication.name	Logged-in username
	hasRole('ADMIN')	Check if user has role ADMIN
	principal	Current user’s principal object
	returnObject	Value returned by method (used in @PostAuthorize / @PostFilter)
	filterObject	Current element in a collection (used in @PreFilter / @PostFilter)
	//////////////////////
	So this expression means:
	“Allow the method only if the returned order’s owner matches the logged-in user’s username.”
    */
    @PostAuthorize("returnObject.owner == authentication.name")
    public Order getOrderById(String orderId) {
        return new Order(orderId, "user123");
    }

    // @PreFilter example: filters input before execution
    /*
    What does @PreFilter do?
	It filters a method argument (usually a collection or array) before the method runs.
	Spring goes through each element and applies the SpEL expression (value attribute).
	Only the elements that match the condition stay in the collection passed to the method.
	//////////////////////////
	Breaking down the annotation
	1>filterTarget = "orderIds"
	The parameter to filter (must match the method parameter name).
	Here, it says: filter the orderIds list.
	2>value = "filterObject.startsWith('A')"
	This is the SpEL condition applied to each element of that collection.
	filterObject represents the current element being checked.
	Here: keep the element only if it starts with "A".
	//////////////////////////
	bulkProcessOrders(List.of("A001", "B002", "A003", "C004"));
	Spring applies the filter before the method executes.
	filterObject.startsWith("A") → only "A001" and "A003" survive.
	So inside the method, orderIds becomes:
	["A001", "A003"]
    */
    @PreFilter(filterTarget = "orderIds", value = "filterObject.startsWith('A')")
    public List<String> bulkProcessOrders(List<String> orderIds) {
        return orderIds; // only orders starting with 'A' are processed
    }

    // @PostFilter example: filters returned list after execution
    @PostFilter("filterObject.owner == authentication.name")
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("A001", "admin"));
        orders.add(new Order("A002", "user123"));
        orders.add(new Order("B001", "user456"));
        return orders;
    }
}
