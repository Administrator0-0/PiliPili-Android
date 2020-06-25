package com.example.pilipili_android.bean.netbean;

/**
 * 登陆的返回数据
 */
public class LoginReturn {

    /**
     * code : 200
     * data : {"token":"eyJhbGciOiJIUzUxMiIsImlhdCI6MTU5MjA1OTc2MywiZXhwIjoxNTkyMzE4OTYzfQ.eyJ1aWQiOjJ9.pw_mxp3maiD1Ukol9DwuDAVVZ6ovaFYvaGQMw0Tr5tiyAKtHbOQynreKcxD4XTa-AyJ-9dBbYhy073JfHVb5sA","username":"HouTaiQiang"}
     * message : 登录成功
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
         * token : eyJhbGciOiJIUzUxMiIsImlhdCI6MTU5MjA1OTc2MywiZXhwIjoxNTkyMzE4OTYzfQ.eyJ1aWQiOjJ9.pw_mxp3maiD1Ukol9DwuDAVVZ6ovaFYvaGQMw0Tr5tiyAKtHbOQynreKcxD4XTa-AyJ-9dBbYhy073JfHVb5sA
         * username : HouTaiQiang
         */

        private String token;
        private String username;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
