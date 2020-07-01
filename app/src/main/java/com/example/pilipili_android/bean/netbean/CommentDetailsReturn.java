package com.example.pilipili_android.bean.netbean;

public class CommentDetailsReturn {


    /**
     * code : 200
     * data : {"author":1,"author_name":"123456","content":"111.jpg","id":4,"likes":0,"replay_id":2,"replay_to_author":1,"replay_to_author_name":"123456","time":"2020-06-29 18:42:05"}
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
         * author_name : 123456
         * content : 111.jpg
         * id : 4
         * likes : 0
         * replay_id : 2
         * replay_to_author : 1
         * replay_to_author_name : 123456
         * time : 2020-06-29 18:42:05
         */

        private int author;
        private String author_name;
        private String content;
        private int id;
        private int likes;
        private int replay_id;
        private int replay_to_author;
        private String replay_to_author_name;
        private String time;

        public int getAuthor() {
            return author;
        }

        public void setAuthor(int author) {
            this.author = author;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getReplay_id() {
            return replay_id;
        }

        public void setReplay_id(int replay_id) {
            this.replay_id = replay_id;
        }

        public int getReplay_to_author() {
            return replay_to_author;
        }

        public void setReplay_to_author(int replay_to_author) {
            this.replay_to_author = replay_to_author;
        }

        public String getReplay_to_author_name() {
            return replay_to_author_name;
        }

        public void setReplay_to_author_name(String replay_to_author_name) {
            this.replay_to_author_name = replay_to_author_name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
