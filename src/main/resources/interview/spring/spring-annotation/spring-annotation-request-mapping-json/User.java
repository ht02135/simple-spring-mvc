/*
User class does not need to implement java.io.Serializable.
Here’s why:
1>Serializable is for Java’s binary object serialization (e.g., writing 
to disk or sending over RMI).
2>Jackson does text-based JSON serialization by inspecting getters/setters/fields 
via reflection.
3>As long as your class follows bean conventions (public no-arg 
constructor + getters/setters, or public fields), Jackson can handle it.
///////////////////
in context of restful api, under what circumstance user need serialization
----------------------------------------
In a REST API, objects are never passed around as Java objects across the network.
They are always serialized to text (JSON, XML, etc.) by HttpMessageConverter 
(e.g. Jackson).
Jackson uses reflection, getters/setters, annotations — it does not rely on 
Serializable.
-------------------------------------
The only exceptions inside a REST API context might be:
1>If you are mixing REST with HTTP session storage

1a>Example: you log in via a REST endpoint and you decide to keep the User 
	object in the HttpSession (stateful REST, which is rare and generally discouraged).

1b>Then the servlet container may need to serialize the session object 
	(for failover or persistence), requiring User implements Serializable.

If you put your User object into the HttpSession, the servlet container (like
Example:
request.getSession().setAttribute("user", user);
Here User should implement Serializable, otherwise you’ll get runtime errors 
if the container tries to persist the session.
////////////////////
For stateless RESTful APIs, your DTOs/entities (User, ApiRequest, ApiResponse) 
do not need Serializable.
All you need is:
A default constructor
Public getters/setters (or public fields)
Optionally Jackson annotations (@JsonProperty, etc.)
///////////////////
Rule of thumb
1>Web API (JSON/XML) → Serializable not needed.
2>Session / Cache / Remote / Disk persistence → Serializable often needed.
*/
package com.example.entity;

public class User {
    private String name;
    private int age;

    public User() {}
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
