package com.example.pilipili_android.view_model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.constant.SPConstant;
import com.example.pilipili_android.constant.UrlConstant;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.SPUtil;

import java.io.File;
import java.util.Objects;

public class UserBaseDetail {

    private MutableLiveData<Drawable> avatar = new MutableLiveData<>();

    /***********************************************************************************************
     * Basic
     */
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

    public static String getAvatarPath(Context context) {
        return UrlConstant.GLIDE_CACHE + File.separator + SPUtil.get(context, SPConstant.AVATAR, "");
    }

    public static boolean isAvatarDefault(Context context) {
        return Objects.requireNonNull(SPUtil.get(context, SPConstant.AVATAR, "")).equals("");
    }

    public static Boolean getGenderBoolean(Context context) {
        return (Boolean) SPUtil.get(context, SPConstant.GENDER, false);
    }

    public static Drawable getGender (Context context) {
        if((boolean) SPUtil.get(context, SPConstant.GENDER, false)) {
            return context.getDrawable(R.drawable.cyx);
        } else {
            return context.getDrawable(R.drawable.cyz);
        }
    }

    public static String getVIPDeadline (Context context) {
        return (String) SPUtil.get(context, SPConstant.VIP_DDL, "");
    }

    /***********************************************************************************************
     * Special
     */

    public static String getFollowerInSpace(Context context) {
        return (int) SPUtil.get(context, SPConstant.FOLLOWER, -1) + "\n粉丝";
    }

    public static String getFollowingInSpace(Context context) {
        return (int) SPUtil.get(context, SPConstant.FOLLOWING, -1) + "\n关注";
    }

    public static String getCoinInMineFragment(Context context){
        return "P币：" + (int) SPUtil.get(context, SPConstant.COIN, -1);
    }

    public static String getFollowerInMineFragment(Context context) {
        return (int) SPUtil.get(context, SPConstant.FOLLOWER, -1) + "";
    }

    public static String getFollowingInMineFragment(Context context) {
        return (int) SPUtil.get(context, SPConstant.FOLLOWING, -1) + "";
    }
}
