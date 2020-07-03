package com.example.pilipili_android.bean.netbean;

import java.io.Serializable;

public class VideoDetailReturn {

    /**
     * code : 200
     * data : {"author":3,"author_name":"user","bucket_cover":{"file":"uid-3-2020-06-30-15-20-18-1593501615410.png","guest_key":"STS.NT3zr1No6JEKmRLgpfyFnxhW3","guest_secret":"LfTwRLySe8HxC9WtN7sk1FN6HJ9grzrrp6rm2LTa8dT","security_token":"CAIS8wF1q6Ft5B2yfSjIr5eGMciFo7AX/Yegb3T9g3AzdclCl6380Tz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tn65X5N5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAFDCP69PCuBgyGQPj8aurKUiIDav9QIySt0/m4fkbiUBwcCZP9REVldT2NwXgq7y0BtS+b2YUEEkhS84oQa5oxdzhaZWS/eayFqVaWQIuUij5JpArk7tsBZRAyGlLveqwv0oJaMCLKuOabHIpQA5Q6+jqb8ab8A1tJBjOJQepsazA=="},"coins":1,"collections":0,"comments":0,"danmuku":0,"duration":"4:55","is_collected":false,"is_followed":false,"is_liked":true,"likes":1,"pv":22,"sign":"本视频提供B站众人津津乐道的各种梗的出处，希望能搏你一笑","time":"2020-06-30 15:21:40","title":"万恶之源\u2014B站中梗的出处","type":1,"views":1}
     * message : 详情
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

    public static class DataBean implements Serializable{
        /**
         * author : 3
         * author_name : user
         * bucket_cover : {"file":"uid-3-2020-06-30-15-20-18-1593501615410.png","guest_key":"STS.NT3zr1No6JEKmRLgpfyFnxhW3","guest_secret":"LfTwRLySe8HxC9WtN7sk1FN6HJ9grzrrp6rm2LTa8dT","security_token":"CAIS8wF1q6Ft5B2yfSjIr5eGMciFo7AX/Yegb3T9g3AzdclCl6380Tz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tn65X5N5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAFDCP69PCuBgyGQPj8aurKUiIDav9QIySt0/m4fkbiUBwcCZP9REVldT2NwXgq7y0BtS+b2YUEEkhS84oQa5oxdzhaZWS/eayFqVaWQIuUij5JpArk7tsBZRAyGlLveqwv0oJaMCLKuOabHIpQA5Q6+jqb8ab8A1tJBjOJQepsazA=="}
         * coins : 1
         * collections : 0
         * comments : 0
         * danmuku : 0
         * duration : 4:55
         * is_collected : false
         * is_followed : false
         * is_liked : true
         * likes : 1
         * pv : 22
         * sign : 本视频提供B站众人津津乐道的各种梗的出处，希望能搏你一笑
         * time : 2020-06-30 15:21:40
         * title : 万恶之源—B站中梗的出处
         * type : 1
         * views : 1
         */

        private int author;
        private String author_name;
        private BucketCoverBean bucket_cover;
        private int coins;
        private int collections;
        private int comments;
        private int danmuku;
        private String duration;
        private boolean is_collected;
        private boolean is_followed;
        private boolean is_liked;
        private int likes;
        private int pv;
        private String sign;
        private String time;
        private String title;
        private int type;
        private int views;

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

        public BucketCoverBean getBucket_cover() {
            return bucket_cover;
        }

        public void setBucket_cover(BucketCoverBean bucket_cover) {
            this.bucket_cover = bucket_cover;
        }

        public int getCoins() {
            return coins;
        }

        public void setCoins(int coins) {
            this.coins = coins;
        }

        public int getCollections() {
            return collections;
        }

        public void setCollections(int collections) {
            this.collections = collections;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getDanmuku() {
            return danmuku;
        }

        public void setDanmuku(int danmuku) {
            this.danmuku = danmuku;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public boolean isIs_collected() {
            return is_collected;
        }

        public void setIs_collected(boolean is_collected) {
            this.is_collected = is_collected;
        }

        public boolean isIs_followed() {
            return is_followed;
        }

        public void setIs_followed(boolean is_followed) {
            this.is_followed = is_followed;
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

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public static class BucketCoverBean implements Serializable {
            /**
             * file : uid-3-2020-06-30-15-20-18-1593501615410.png
             * guest_key : STS.NT3zr1No6JEKmRLgpfyFnxhW3
             * guest_secret : LfTwRLySe8HxC9WtN7sk1FN6HJ9grzrrp6rm2LTa8dT
             * security_token : CAIS8wF1q6Ft5B2yfSjIr5eGMciFo7AX/Yegb3T9g3AzdclCl6380Tz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tn65X5N5Jdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAFDCP69PCuBgyGQPj8aurKUiIDav9QIySt0/m4fkbiUBwcCZP9REVldT2NwXgq7y0BtS+b2YUEEkhS84oQa5oxdzhaZWS/eayFqVaWQIuUij5JpArk7tsBZRAyGlLveqwv0oJaMCLKuOabHIpQA5Q6+jqb8ab8A1tJBjOJQepsazA==
             */

            private String file;
            private String guest_key;
            private String guest_secret;
            private String security_token;

            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }

            public String getGuest_key() {
                return guest_key;
            }

            public void setGuest_key(String guest_key) {
                this.guest_key = guest_key;
            }

            public String getGuest_secret() {
                return guest_secret;
            }

            public void setGuest_secret(String guest_secret) {
                this.guest_secret = guest_secret;
            }

            public String getSecurity_token() {
                return security_token;
            }

            public void setSecurity_token(String security_token) {
                this.security_token = security_token;
            }
        }
    }
}
