package com.example;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

@Component
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
