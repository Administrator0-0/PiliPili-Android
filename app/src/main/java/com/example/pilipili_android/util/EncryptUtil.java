package com.example.pilipili_android.util;

import android.util.Patterns;

public class EncryptUtil {

    public static String getVerificationToken(String token) {
        return "JWT " + token;
    }

    public static String getBucketEncryption() {

    }



    // 检查邮箱合法
    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // 检查密码合法 (6~20位，字母数字下划线)
    public static boolean isPasswordValid(String password) {
        if(password != null){
            String reg = "^([A-Z]|[a-z]|[0-9]|_){6,20}$";
            return password.matches(reg);
        } else {
            return false;
        }
    }

    // 检查用户名合法 (2~10位，汉字字母数字下划线)
    public static boolean isUsernameValid(String username) {
        if(username != null && username.length() <= 10 && username.length() >= 2){
            String reg = "[\\u4e00-\\u9fa5]*|\\w*|\\d*|_*";
            return username.matches(reg);
        } else {
            return false;
        }
    }

}
