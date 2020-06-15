package com.example.pilipili_android.bean;

/**
 * 一般情况的返回数据格式
 */
public class CommonReturn {

    /**
     * code : 200
     * message : 成功
     */

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
