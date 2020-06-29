package com.example.pilipili_android.bean.netbean;

public class CommentDetailsReturn {

    /**
     * code : 200
     * data : {"author":1,"content":"111.jpg","id":1,"is_liked":false,"likes":0,"replay_id":null,"replay_to_author":null,"replay_to_author_name":null,"time":"2020-06-29 18:41:37"}
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
         * content : 111.jpg
         * id : 1
         * is_liked : false
         * likes : 0
         * replay_id : null
         * replay_to_author : null
         * replay_to_author_name : null
         * time : 2020-06-29 18:41:37
         */

        private int author;
        private String content;
        private int id;
        private boolean is_liked;
        private int likes;
        private Integer replay_id;
        private Integer replay_to_author;
        private String replay_to_author_name;
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

        public Object getReplay_id() {
            return replay_id;
        }

        public void setReplay_id(Integer replay_id) {
            this.replay_id = replay_id;
        }

        public Integer getReplay_to_author() {
            return replay_to_author;
        }

        public void setReplay_to_author(Integer replay_to_author) {
            this.replay_to_author = replay_to_author;
        }

        public Object getReplay_to_author_name() {
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
