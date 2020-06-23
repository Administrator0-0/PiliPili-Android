package com.example.pilipili_android.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.databinding.ActivitySpaceBinding;
import com.example.pilipili_android.util.EncryptUtil;
import com.example.pilipili_android.util.PermissionUtil;
import com.example.pilipili_android.util.UCropUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpaceActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 2;
    ActivitySpaceBinding activitySpaceBinding;

    @BindView(R.id.background)
    ImageView background;

    @BindView(R.id.avatar)
    QMUIRadiusImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBind();
        initView();

        activitySpaceBinding.getUserViewModel().getSpaceActivityBean().observe(this, spaceActivityBean -> activitySpaceBinding.setSpaceActivityBean(spaceActivityBean));

        activitySpaceBinding.getUserViewModel().getSpaceBackgroundUrl().observe(this, spaceBackgroundUrl -> {
            if (spaceBackgroundUrl.equals("null")) {
                background.setImageResource(DefaultConstant.BACKGROUND_IMAGE_DEFAULT);
            } else {
                Glide.with(this).load(spaceBackgroundUrl).diskCacheStrategy(DiskCacheStrategy.NONE).into(background);
            }
        });
    }

    private void initBind() {
        activitySpaceBinding = DataBindingUtil.setContentView(this, R.layout.activity_space);
        activitySpaceBinding.setUserViewModel(new ViewModelProvider(this).get(UserViewModel.class));
        ButterKnife.bind(this);
    }

    private void initView() {
        CustomTarget<Drawable> avatarTarget = new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                avatar.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        };
        if (UserBaseDetail.isAvatarDefault(this)) {
            avatar.setImageDrawable(getDrawable(DefaultConstant.AVATAR_IMAGE_DEFAULT));
        } else {
            Glide.with(this).load(UserBaseDetail.getAvatarPath(this)).into(avatarTarget);
        }

//        CustomTarget<Drawable> backgroundTarget = new CustomTarget<Drawable>() {
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                background.setImageDrawable(resource);
//            }
//
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//            }
//        };
//        if (UserBaseDetail.isBackgroundDefault(this)) {
//            background.setImageDrawable(getDrawable(DefaultConstant.BACKGROUND_IMAGE_DEFAULT));
//        } else {
//            Glide.with(this).load(UserBaseDetail.getBackgroundPath(this)).into(backgroundTarget);
//        }

        activitySpaceBinding.getUserViewModel().getUserSpaceData();
    }

    @OnClick(R.id.change_bg)
    public void onChangeBackgroundClicked() {
        showBottomDialog();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        View view = View.inflate(this, R.layout.user_space_bg_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        if (window != null) {
            //设置弹出位置
            window.setGravity(Gravity.BOTTOM);
            //设置弹出动画
            window.setWindowAnimations(R.style.main_menu_animStyle);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();

        TextView defaultBackground = view.findViewById(R.id.default_bg);
        defaultBackground.setOnClickListener(view1 -> {
            background.setImageResource(DefaultConstant.BACKGROUND_IMAGE_DEFAULT);
            dialog.dismiss();
        });

        TextView cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        QMUIRoundButton selfDef = view.findViewById(R.id.self_def);
        selfDef.setOnClickListener(view1 -> {
            dialog.dismiss();
            String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            int REQUEST_EXTERNAL_STORAGE = 1;
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                openAlbum();
            }
        });
    }

    //获取权限的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this,"啊啦~访问相册被拒绝了呢~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //启动相册
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE) {
            String path = null;
            Uri uri;
            if (data == null) {
                return;
            }
            uri = data.getData();
            //根据不同的uri进行不同的解析
            if (DocumentsContract.isDocumentUri(this, uri)){
                String docId = DocumentsContract.getDocumentId(uri);
                if (uri != null) {
                    if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                        String id = docId.split(":")[1];
                        String selection = MediaStore.Images.Media._ID + "=" + id;
                        path = UCropUtil.convertUri(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                    }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                        path = UCropUtil.convertUri(this, contentUri,null);
                    }
                }
            }else if ("content".equalsIgnoreCase(uri.getScheme())){
                path = UCropUtil.convertUri(this, uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())){
                path = uri.getPath();
            }
            if(path != null) {
                int height = background.getHeight();
                int width = background.getWidth();
                UCropUtil.startUCropAvatar(this, path, UCrop.REQUEST_CROP, width, height);
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            final Uri croppedUri;
            if (data != null) {
                croppedUri = UCrop.getOutput(data);
                try {
                    if(croppedUri != null) {
                        activitySpaceBinding.getUserViewModel().uploadUserBackground(croppedUri.getPath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                openAlbum();
                overridePendingTransition(0, 0);
            }
        }
    }
}