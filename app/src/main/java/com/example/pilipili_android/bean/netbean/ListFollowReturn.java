package com.example.pilipili_android.bean.netbean;

import java.util.List;

public class ListFollowReturn {
    /**
     * code : 200
     * data : {"list":[{"id":2,"is_followed":false,"sign":"","username":"123"}]}
     * message : 获取成功
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2
             * is_followed : false
             * sign :
             * username : 123
             */

            private int id;
            private boolean is_followed;
            private String sign;
            private String username;
            private String avatar;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isIs_followed() {
                return is_followed;
            }

            public void setIs_followed(boolean is_followed) {
                this.is_followed = is_followed;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getAvatar() {
                return avatar;
            }
        }
    }
}
