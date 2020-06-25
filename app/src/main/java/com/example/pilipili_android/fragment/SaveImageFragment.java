package com.example.pilipili_android.fragment;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.util.UCropUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SaveImageFragment extends Fragment {

    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.change_avatar)
    TextView changeAvatar;
    @BindView(R.id.save_avatar)
    TextView saveAvatar;

    private static final int REQUEST_CODE_CHOOSE_AVATAR = 10010;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private Unbinder unbinder;
    private UserViewModel userViewModel;
    private String avatarPath;

    public SaveImageFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_image, container, false);
        unbinder = ButterKnife.bind(this, view);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        if (!avatarPath.equals("")) {
            Glide.with(this).load(avatarPath).into(avatar);
        } else {
            avatar.setImageResource(DefaultConstant.AVATAR_IMAGE_DEFAULT);
        }
        return view;
    }

    @OnClick(R.id.cancel)
    void onCancelClicked() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @OnClick(R.id.change_avatar)
    void onChangeAvatarClicked() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            openAlbum();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    //启动相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        Objects.requireNonNull(getActivity()).startActivityForResult(intent, REQUEST_CODE_CHOOSE_AVATAR);
    }

    //获取权限的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
            } else {
                Toast.makeText(getContext(), "啊啦~访问相册被拒绝了呢~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.save_avatar)
    void onSaveAvatarClicked() {
        File file = new File(avatarPath);
        try {
            MediaStore.Images.Media.insertImage(Objects.requireNonNull(getContext()).getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
