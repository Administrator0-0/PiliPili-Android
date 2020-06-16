package com.example.pilipili_android.bean;

public final class NetRequestResult<T> {

    private T data;

    public T getData() {
        return data;
    }

    public NetRequestResult(T data) {
        this.data = data;
    }
}
