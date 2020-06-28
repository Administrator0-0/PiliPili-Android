package com.example.pilipili_android.bean.netbean;

public class UploadVideoOrCoverReturn {

    /**
     * code : 200
     * data : {"file":"uid-1-2020-06-28-12-51-18-七七四十九天练成不坏阴茎.png","guest_key":"STS.NTJJm85wEmoRh7ndmyFVRRbj2","guest_secret":"8Rta7VLS7goWoP7tp4BbuQU6Ny7UoddSuQ5GYRGL45vA","security_token":"CAIS8wF1q6Ft5B2yfSjIr5f/AdeM2Khk2q25ahHfgG0sStl+vafB0Dz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/thyAcqBmJdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAEa7TkQD4/tuPfX9JxkpR9plLvbfd68Z3UqMUNrscnj5VpJRKzPA4V7BSJQ3sbiKjydCwaWCaOHTu2qTgHvutJVKfDdydrp3b41/JTOjERZAubU327AX7ElZIneqzS+ZCFO6woJdgttTFVcsgWlNFI18evAM0W2EUGdb8ZzP27VMg=="}
     * message : 上传封面成功
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
         * file : uid-1-2020-06-28-12-51-18-七七四十九天练成不坏阴茎.png
         * guest_key : STS.NTJJm85wEmoRh7ndmyFVRRbj2
         * guest_secret : 8Rta7VLS7goWoP7tp4BbuQU6Ny7UoddSuQ5GYRGL45vA
         * security_token : CAIS8wF1q6Ft5B2yfSjIr5f/AdeM2Khk2q25ahHfgG0sStl+vafB0Dz2IHtFe3lhA+oWt/k0lGFV7/YelqN3SoJVRErbZM1/thyAcqBmJdivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7tcYeLgGxD/u2NQPwiWeiZygB+CgE0DMnuPXvnZPNsUaD1w2rm9V4/dqhfsKWCOB3J4p6XtuP2+h7S7HMyiY46WIRr/Yp3P0eo2ee44rNUgQIs0mcUe7V781uKhBwYKcquAK8CFlRZpYagAEa7TkQD4/tuPfX9JxkpR9plLvbfd68Z3UqMUNrscnj5VpJRKzPA4V7BSJQ3sbiKjydCwaWCaOHTu2qTgHvutJVKfDdydrp3b41/JTOjERZAubU327AX7ElZIneqzS+ZCFO6woJdgttTFVcsgWlNFI18evAM0W2EUGdb8ZzP27VMg==
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
