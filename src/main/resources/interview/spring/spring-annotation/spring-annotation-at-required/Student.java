/*
Student.java
Example Bean with @Required
///////////////////
Usage: The annotation is placed on the setter method of a bean's property.
*/
package com.example;

import org.springframework.beans.factory.annotation.Required;

public class Student {

    private String name;

    @Required
    public void setName(String name) {
        this.name = name;
    }

    public void printInfo() {
        System.out.println("Student name: " + name);
    }
}
