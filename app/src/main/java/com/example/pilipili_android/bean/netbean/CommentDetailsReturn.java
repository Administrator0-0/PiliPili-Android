package com.example.pilipili_android.bean.netbean;

public class CommentDetailsReturn {
    /**
     * code : 200
     * data : {"author":1,"content":"lxwtql","likes":1,"time":"2020-06-26-16-31-39"}
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
        /**
         * author : 1
         * content : lxwtql
         * likes : 1
         * time : 2020-06-26-16-31-39
         */

        private int author;
        private String content;
        private int likes;
        private String time;

        public int getAuthor() {
            return author;
        }

        public void setAuthor(int author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
