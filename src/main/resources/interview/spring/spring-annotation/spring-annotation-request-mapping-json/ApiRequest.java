package com.example.dto;

public class ApiRequest<T> {
    private T data;

    public ApiRequest() {}
    public ApiRequest(T data) { this.data = data; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
