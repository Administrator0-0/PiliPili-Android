package com.example.pilipili_android.bean.netbean;

public class RenameReturn {

    /**
     * code : 200
     * message : wdnmd
     * data : {"username":"中屁股裂了","coins":200}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * username : 中屁股裂了
         * coins : 200
         */

        private String username;
        private int coins;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getCoins() {
            return coins;
        }

        public void setCoins(int coins) {
            this.coins = coins;
        }
    }
}
