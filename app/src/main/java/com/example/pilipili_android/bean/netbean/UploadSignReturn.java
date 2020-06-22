package com.example.pilipili_android.bean.netbean;

public class UploadSignReturn {

    /**
     * code : 200
     * data : {"sign":"大屁股裂了，我变成中屁股了"}
     * message : 签名
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
         * sign : 大屁股裂了，我变成中屁股了
         */

        private String sign;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
