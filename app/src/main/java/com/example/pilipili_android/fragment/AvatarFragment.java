package com.example.pilipili_android.fragment;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.constant.UrlConstant;
import com.example.pilipili_android.util.UCropUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AvatarFragment extends Fragment {

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
    private String avatarPath;

    public AvatarFragment() {
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

        if (!avatarPath.equals("") && !avatarPath.equals(UrlConstant.GLIDE_CACHE + File.separator)) {
            Glide.with(this).load(avatarPath).diskCacheStrategy(DiskCacheStrategy.NONE).into(avatar);
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
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            openAlbum();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    private void openAlbum() {
        Matisse.from(getActivity())
                //选择视频和图片
//                .choose(MimeType.ofAll())
                //选择图片
                .choose(MimeType.ofImage())
                //选择视频
//                .choose(MimeType.ofVideo())
                //自定义选择选择的类型
//                .choose(MimeType.of(MimeType.JPEG,MimeType.AVI))
                //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                .showSingleMediaType(true)
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .capture(true)
                .captureStrategy(new CaptureStrategy(true,"com.pilipili.fuckthisshit"))
                //有序选择图片 123456...
                .countable(false)
                //最大选择数量为9
                .maxSelectable(1)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
//                .theme(R.style.Matisse_Zhihu)
                //黑色主题
                .theme(R.style.Matisse_Dracula)
                //Glide加载方式
//                .imageEngine(new GlideEngine())
                //Picasso加载方式
//                .imageEngine(new PicassoEngine())
                //请求码
                .forResult(REQUEST_CODE_CHOOSE_AVATAR);
        EventBus.getDefault().post(FragmentMsg.getInstance("AvatarFragment", "what", REQUEST_CODE_CHOOSE_AVATAR));
    }

    //获取权限的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if(grantResults.length > 0) {
                for(int result : grantResults) {
                    if(result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "啊啦~被残忍拒绝了呢~", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
                openAlbum();
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
