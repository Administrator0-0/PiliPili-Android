package com.example.pilipili_android.bean.netbean;

public class BuyVIPReturn {

    /**
     * code : 200
     * data : {"coins":2133331,"vip":"2021-01-18"}
     * message : 购买大会员成功
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
         * coins : 2133331
         * vip : 2021-01-18
         */

        private int coins;
        private String vip;

        public int getCoins() {
            return coins;
        }

        public void setCoins(int coins) {
            this.coins = coins;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }
    }
}
