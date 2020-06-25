package com.example.pilipili_android.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Patterns;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.signature.EmptySignature;
import com.bumptech.glide.util.Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class EncryptUtil {

    public static String getVerificationToken(String token) {
        return "JWT " + token;
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

    /**
     * Glide缓存存储路径：/data/data/com.example.pilipili_android/cache/image_manager_disk_cache
     * Glide文件名生成规则函数 : 4.0+ 版本
     * @param url 传入图片url
     */
    public static String getGlide4_SafeKey(String url) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            EmptySignature signature = EmptySignature.obtain();
            signature.updateDiskCacheKey(messageDigest);
            new GlideUrl(url).updateDiskCacheKey(messageDigest);
            String safeKey = Util.sha256BytesToHex(messageDigest.digest());
            return safeKey + ".0";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
