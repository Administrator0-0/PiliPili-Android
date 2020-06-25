package com.example.pilipili_android.bean.netbean;

public class GetSpaceDataReturn {

    /**
     * code : 200
     * data : {"likes":0,"sign":"大屁股裂了，我变成中屁股了"}
     * message : 空间数据
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
         * likes : 0
         * sign : 大屁股裂了，我变成中屁股了
         */

        private int likes;
        private String sign;

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
    }
}
