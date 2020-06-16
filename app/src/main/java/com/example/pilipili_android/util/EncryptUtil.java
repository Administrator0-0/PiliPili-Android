package com.example.pilipili_android.util;

public class EncryptUtil {

    public static String getVerificationToken(String token) {
        return "JWT " + token;
    }
}
