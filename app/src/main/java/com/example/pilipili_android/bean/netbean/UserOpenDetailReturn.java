package com.example.pilipili_android.bean.netbean;

public class UserOpenDetailReturn {

    /**
     * code : 200
     * data : {"fans_count":0,"followings_count":0,"gender":false,"id":5,"likes":0,"sign":"","username":"你妈飞了","vip":null}
     * message : 获取用户信息成功
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
         * fans_count : 0
         * followings_count : 0
         * gender : false
         * id : 5
         * likes : 0
         * sign :
         * username : 你妈飞了
         * vip : null
         */

        private int fans_count;
        private int followings_count;
        private boolean gender;
        private int id;
        private int likes;
        private String sign;
        private String username;
        private Object vip;

        public int getFans_count() {
            return fans_count;
        }

        public void setFans_count(int fans_count) {
            this.fans_count = fans_count;
        }

        public int getFollowings_count() {
            return followings_count;
        }

        public void setFollowings_count(int followings_count) {
            this.followings_count = followings_count;
        }

        public boolean isGender() {
            return gender;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
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

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getVip() {
            return vip;
        }

        public void setVip(Object vip) {
            this.vip = vip;
        }
    }
}
