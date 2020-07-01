package com.example.pilipili_android.bean.netbean;

public class GetOSSUrlReturn {


    /**
     * code : 200
     * data : {"file":"1-background.png","guest_key":"STS.NUt1UJJQTvcHNAw6Ck1r7ry5c","guest_secret":"6ifTuLzLb5RvYYggPJqfXAraTtYmETWQVPquxwi8nAp9","security_token":"CAIS8wF1q6Ft5B2yfSjIr5bBeu/+p451waGjTGfG0kM+Pf0bnbyegTz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tkTzYpxkJdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAEHJvSyIaznPJv6VQ+vQVlYLOvAMjEP5ceATPLW9A8ci+gp7BOa4Gt8CmcdVO1ZXRnUsXw9i9huYldcFzTdoNLZj4bvHQ3OEaC9eDGXz8WxXmg6ehkQy6ve7SnF26tpZH4/HAeR5k5ZE4QvEjahJ4fEnY7gVmxoYV1v5Gp4CPTN2A=="}
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
         * file : 1-background.png
         * guest_key : STS.NUt1UJJQTvcHNAw6Ck1r7ry5c
         * guest_secret : 6ifTuLzLb5RvYYggPJqfXAraTtYmETWQVPquxwi8nAp9
         * security_token : CAIS8wF1q6Ft5B2yfSjIr5bBeu/+p451waGjTGfG0kM+Pf0bnbyegTz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/tkTzYpxkJdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAEHJvSyIaznPJv6VQ+vQVlYLOvAMjEP5ceATPLW9A8ci+gp7BOa4Gt8CmcdVO1ZXRnUsXw9i9huYldcFzTdoNLZj4bvHQ3OEaC9eDGXz8WxXmg6ehkQy6ve7SnF26tpZH4/HAeR5k5ZE4QvEjahJ4fEnY7gVmxoYV1v5Gp4CPTN2A==
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
