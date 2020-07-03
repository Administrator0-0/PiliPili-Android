package com.example.pilipili_android.bean.netbean;

import java.util.List;

public class ReplayListReturn {

    /**
     * code : 200
     * data : {"replay_all":[{"author":1,"author_name":"123456","content":"111.jpg","id":2,"is_liked":false,"likes":0,"replay_id":1,"replay_to_author":1,"replay_to_author_name":"123456","time":"2020-06-29 18:41:55"},{"author":1,"author_name":"123456","content":"111.jpg","id":4,"is_liked":false,"likes":0,"replay_id":2,"replay_to_author":1,"replay_to_author_name":"123456","time":"2020-06-29 18:42:05"},{"author":1,"author_name":"123456","content":"111.jpg","id":5,"is_liked":false,"likes":0,"replay_id":4,"replay_to_author":1,"replay_to_author_name":"123456","time":"2020-06-29 18:42:09"},{"author":1,"author_name":"123456","content":"111.jpg","id":6,"is_liked":false,"likes":0,"replay_id":5,"replay_to_author":1,"replay_to_author_name":"123456","time":"2020-06-29 18:42:15"},{"author":1,"author_name":"123456","content":"111.jpg","id":7,"is_liked":false,"likes":0,"replay_id":6,"replay_to_author":1,"replay_to_author_name":"123456","time":"2020-06-29 18:42:18"},{"author":1,"author_name":"123456","content":"111.jpg","id":3,"is_liked":false,"likes":0,"replay_id":1,"replay_to_author":1,"replay_to_author_name":"123456","time":"2020-06-29 18:41:58"},{"author":1,"author_name":"123456","content":"111.jpg","id":8,"is_liked":false,"likes":0,"replay_id":3,"replay_to_author":1,"replay_to_author_name":"123456","time":"2020-06-29 18:42:21"}]}
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
        private List<ReplayAllBean> replay_all;

        public List<ReplayAllBean> getReplay_all() {
            return replay_all;
        }

        public void setReplay_all(List<ReplayAllBean> replay_all) {
            this.replay_all = replay_all;
        }

        public static class ReplayAllBean {
            /**
             * author : 1
             * author_name : 123456
             * content : 111.jpg
             * id : 2
             * is_liked : false
             * likes : 0
             * replay_id : 1
             * replay_to_author : 1
             * replay_to_author_name : 123456
             * time : 2020-06-29 18:41:55
             */

            private int author;
            private String author_name;
            private String content;
            private int id;
            private boolean is_liked;
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

            public boolean isIs_liked() {
                return is_liked;
            }

            public void setIs_liked(boolean is_liked) {
                this.is_liked = is_liked;
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
}
