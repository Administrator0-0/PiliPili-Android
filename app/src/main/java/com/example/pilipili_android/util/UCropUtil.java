package com.example.pilipili_android.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;

import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.UrlConstant;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

public class UCropUtil {
    /**
     * 启动裁剪
     * @param sourceFilePath 需要裁剪图片的绝对路径
     * @param requestCode 比如：UCrop.REQUEST_CROP
     * @param aspectRatioX 裁剪图片宽高比
     * @param aspectRatioY 裁剪图片宽高比
     * @return
     */
    public static void startUCropAvatar(Activity activity, String sourceFilePath,
                              int requestCode, float aspectRatioX, float aspectRatioY) {
        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));
        File outDir = new File(UrlConstant.CROP_CACHE);
        if (!outDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            outDir.mkdirs();
        }
        File outFile = new File(outDir, System.currentTimeMillis() + ".png");
        //裁剪后图片的绝对路径
        Uri destinationUri = Uri.fromFile(outFile);
        //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.NONE);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.black));
        options.setToolbarTitle("");
        options.setToolbarWidgetColor(ActivityCompat.getColor(activity, R.color.white));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.white));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(false);
        //设置是否展示矩形裁剪框
        options.setShowCropFrame(false);
        //设置是否显示网格
        options.setShowCropGrid(false);
        //设置最大缩放比例
        options.setMaxScaleMultiplier(9);
        //设置图片格式
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        //UCrop配置
        uCrop.withOptions(options);
        //设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(aspectRatioX, aspectRatioY);
        //跳转裁剪页面
        uCrop.start(activity, requestCode);
    }

    /**
     * 将content类型的Uri转化为文件类型的Uri
     * @param uri
     * @return
     */
    public static String convertUri(Context context, Uri uri, String selection){
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
