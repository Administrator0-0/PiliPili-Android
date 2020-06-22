package com.example.pilipili_android.bean.netbean;

public class UserDetailReturn {

    /**
     * code : 200
     * data : {"coins":1,"email":"lxw@lxw.com","fans_count":0,"followings_count":0,"gender":false,"id":1,"username":"lxw","vip":"2020-07-22"}
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
         * coins : 1
         * email : lxw@lxw.com
         * fans_count : 0
         * followings_count : 0
         * gender : false
         * id : 1
         * username : lxw
         * vip : 2020-07-22
         */

        private int coins;
        private String email;
        private int fans_count;
        private int followings_count;
        private boolean gender;
        private int id;
        private String username;
        private String vip;

        public int getCoins() {
            return coins;
        }

        public void setCoins(int coins) {
            this.coins = coins;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }
    }
}
