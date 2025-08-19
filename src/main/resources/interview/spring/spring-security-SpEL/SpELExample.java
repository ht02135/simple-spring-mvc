/*
Explain which security annotations are allowed to use SpEL. and what is SpEL.
and java example 
////////////////
SpEL, short for Spring Expression Language, is a powerful expression 
language used in the Spring Framework. It allows you to query and 
manipulate an object graph at runtime. It's similar to other expression 
languages like OGNL and MVEL, but it's specifically designed for Spring.
////////////////
Security Annotations and SpEL
The following security annotations in Spring Security are allowed to use SpEL:
1>@PreAuthorize: This annotation is used to decide whether a method can be 
invoked. The SpEL expression is evaluated before the method is called. For 
example, you can check if the currently authenticated user has a specific role.

2>@PostAuthorize: This annotation is used to decide whether the result of a 
method invocation can be returned. The SpEL expression is evaluated after 
the method has executed but before the result is returned to the caller. 
This is useful for making decisions based on the returned object.

3>@PreFilter: This annotation is used to filter collections before a method 
is executed. The SpEL expression is applied to each element of the collection. 
It's often used with method parameters that are collections or arrays.

4>@PostFilter: This annotation is used to filter collections after a method has 
executed. The SpEL expression is applied to each element of the returned 
collection, effectively removing elements that don't meet the criteria.
//////////////////////////
Here's a Java example demonstrating the use of SpEL with 
@PreAuthorize and @PostAuthorize annotations.
*/
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    // @PreAuthorize example: checks if the user has the 'ADMIN' role before the method runs.
    @PreAuthorize("hasRole('ADMIN')")
    public String createOrder(String orderId) {
        // Method logic to create an order
        return "Order " + orderId + " created successfully.";
    }

    // @PostAuthorize example: checks if the returned order's owner matches the current user's username.
    @PostAuthorize("returnObject.owner == authentication.name")
    public Order getOrderById(String orderId) {
        // This is a placeholder. In a real application, this would fetch an order from a database.
        Order order = new Order(orderId, "user123");
        return order;
    }
}

class Order {
    private String orderId;
    private String owner;

    public Order(String orderId, String owner) {
        this.orderId = orderId;
        this.owner = owner;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOwner() {
        return owner;
    }
}