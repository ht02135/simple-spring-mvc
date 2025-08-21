package com.example;

public class ResourceBean {

    public void init() {
        System.out.println("ResourceBean initialized: Opening resource...");
    }

    public void doWork() {
        System.out.println("ResourceBean is working...");
    }

    public void cleanup() {
        System.out.println("ResourceBean cleanup: Closing resource...");
    }
}
