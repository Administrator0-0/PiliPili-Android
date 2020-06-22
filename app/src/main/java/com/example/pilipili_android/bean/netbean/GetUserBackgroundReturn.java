package com.example.pilipili_android.bean.netbean;

public class GetUserBackgroundReturn {

    /**
     * code : 200
     * data : {"background":null,"guest_Key":"LTAI4G2sAv9YZPY7npjXzb3W","guest_Secret":"RrgZoFblhs7IK7swSYJ3owviqHk3mt","security_token":"CAIS8wF1q6Ft5B2yfSjIr5bgAOyGqapUgLSgN0H9lW0iVPlWrqGflDz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tnWLUINkJdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAFJ9HVI9N66dLr+ETHz1A3WhYoN8f/A7kL53UGXes2RWyBpXBUhGpqRS6qQYI70+haMzkK6adfDyFVlXBKkQMo/r9pD/ib0SZlME6fZoczla5ejxYtsmxkRlVoAddxvrWavLKk7nW+TJR9og2YPxXAQELxpLOhkjA/TKPJxfCG6Qg=="}
     * message : 背景
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
         * background : null
         * guest_Key : LTAI4G2sAv9YZPY7npjXzb3W
         * guest_Secret : RrgZoFblhs7IK7swSYJ3owviqHk3mt
         * security_token : CAIS8wF1q6Ft5B2yfSjIr5bgAOyGqapUgLSgN0H9lW0iVPlWrqGflDz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tnWLUINkJdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAFJ9HVI9N66dLr+ETHz1A3WhYoN8f/A7kL53UGXes2RWyBpXBUhGpqRS6qQYI70+haMzkK6adfDyFVlXBKkQMo/r9pD/ib0SZlME6fZoczla5ejxYtsmxkRlVoAddxvrWavLKk7nW+TJR9og2YPxXAQELxpLOhkjA/TKPJxfCG6Qg==
         */

        private String background;
        private String guest_Key;
        private String guest_Secret;
        private String security_token;

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getGuest_Key() {
            return guest_Key;
        }

        public void setGuest_Key(String guest_Key) {
            this.guest_Key = guest_Key;
        }

        public String getGuest_Secret() {
            return guest_Secret;
        }

        public void setGuest_Secret(String guest_Secret) {
            this.guest_Secret = guest_Secret;
        }

        public String getSecurity_token() {
            return security_token;
        }

        public void setSecurity_token(String security_token) {
            this.security_token = security_token;
        }
    }
}
