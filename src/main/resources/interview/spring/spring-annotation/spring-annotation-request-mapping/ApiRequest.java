/*
Instead of creating UserRequest and UserResponse classes just for 
user info, you’d prefer a generic request and response wrapper 
that can carry any entity (not only users). That way, you can 
reuse them across multiple APIs.
///////////////////////////////
1. Generic Request / Response Wrapper
*/
package com.example.dto;

// A generic request wrapper
public class ApiRequest<T> {
    private T data;

    public ApiRequest() {}

    public ApiRequest(T data) {
        this.data = data;
    }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
