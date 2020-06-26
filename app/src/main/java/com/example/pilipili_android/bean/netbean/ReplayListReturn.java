package com.example.pilipili_android.bean.netbean;

import java.util.List;

public class ReplayListReturn {
    /**
     * code : 200
     * data : {"all_replays":[4,5]}
     * message : 获取回复成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<Integer> all_replays;

        public List<Integer> getAll_replays() {
            return all_replays;
        }

        public void setAll_replays(List<Integer> all_replays) {
            this.all_replays = all_replays;
        }
    }
}
