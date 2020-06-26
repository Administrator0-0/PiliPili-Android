package com.example.pilipili_android.bean.netbean;

import java.util.List;

public class CommentListReturn {
    /**
     * code : 200
     * data : {"all_comments":[1,2,3]}
     * message : 获取评论成功
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
        private List<Integer> all_comments;

        public List<Integer> getAll_comments() {
            return all_comments;
        }

        public void setAll_comments(List<Integer> all_comments) {
            this.all_comments = all_comments;
        }
    }
}
