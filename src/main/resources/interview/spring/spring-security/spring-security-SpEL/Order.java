package com.example.securityaopdemo.service;

public class Order {
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

    @Override
    public String toString() {
        return "Order{id='" + orderId + "', owner='" + owner + "'}";
    }
}