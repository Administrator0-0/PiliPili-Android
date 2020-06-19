package com.example.pilipili_android.view_model;

import android.content.Context;

import com.example.pilipili_android.constant.SPConstant;
import com.example.pilipili_android.util.SPUtil;

public class UserBaseDetail {
    public static String getUsername(Context context) {
        return (String) SPUtil.get(context, SPConstant.USERNAME, "未登录");
    }

    public static int getCoin(Context context){
        return (int) SPUtil.get(context, SPConstant.COIN, -1);
    }

    public static int getUID(Context context){
        return (int) SPUtil.get(context, SPConstant.UID, -1);
    }

    public static String getToken(Context context) {
        return (String) SPUtil.get(context, SPConstant.TOKEN, "");
    }

    public static int getFollowerCount(Context context) {
        return (int) SPUtil.get(context, SPConstant.FOLLOWER, -1);
    }

    public static int getFollowingCount(Context context) {
        return (int) SPUtil.get(context, SPConstant.FOLLOWING, -1);
    }
}
