package com.example.pilipili_android.bean;

/**
 * 一般情况的返回数据格式
 */
public class CommonInfoBean {

    /**
     * code : 200
     * msg : 成功
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
