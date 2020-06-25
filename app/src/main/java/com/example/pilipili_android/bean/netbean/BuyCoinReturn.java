package com.example.pilipili_android.bean.netbean;

/**
 * 买币的返回数据
 */
public class BuyCoinReturn {

    /**
     * code : 200
     * data : {"coins":100}
     * message : 购买硬币成功
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
         * coins : 100
         */

        private int coins;

        public int getCoins() {
            return coins;
        }

        public void setCoins(int coins) {
            this.coins = coins;
        }
    }
}
