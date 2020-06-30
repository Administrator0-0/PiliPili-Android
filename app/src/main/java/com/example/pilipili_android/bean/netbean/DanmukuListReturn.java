package com.example.pilipili_android.bean.netbean;

import java.util.List;

public class DanmukuListReturn {

    /**
     * code : 200
     * data : {"all_danmuku":[{"author":2,"background":0,"color":"#FFFFFFF","content":"lxwtqllxw","id":1,"size":16,"time":1,"type":1},{"author":2,"background":0,"color":"#FFFFFFF","content":"lxwtql","id":2,"size":16,"time":1,"type":1}]}
     * message : 获取弹幕成功
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
        private List<AllDanmukuBean> all_danmuku;

        public List<AllDanmukuBean> getAll_danmuku() {
            return all_danmuku;
        }

        public void setAll_danmuku(List<AllDanmukuBean> all_danmuku) {
            this.all_danmuku = all_danmuku;
        }

        public static class AllDanmukuBean {
            /**
             * author : 2
             * background : 0
             * color : #FFFFFFF
             * content : lxwtqllxw
             * id : 1
             * size : 16
             * time : 1.0
             * type : 1
             */

            private int author;
            private int background;
            private String color;
            private String content;
            private int id;
            private int size;
            private long time;
            private int type;

            public int getAuthor() {
                return author;
            }

            public void setAuthor(int author) {
                this.author = author;
            }

            public int getBackground() {
                return background;
            }

            public void setBackground(int background) {
                this.background = background;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
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

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
