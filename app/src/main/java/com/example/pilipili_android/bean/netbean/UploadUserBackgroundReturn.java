package com.example.pilipili_android.bean.netbean;

public class UploadUserBackgroundReturn {

    /**
     * code : 200
     * data : {"background":"3-background.jpg"}
     * message : 上传背景成功
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
         * background : 3-background.jpg
         */

        private String background;

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }
    }
}
