package com.example.pilipili_android.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.LoginActivity;
import com.example.pilipili_android.activity.MainActivity;
import com.example.pilipili_android.activity.PayActivity;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.activity.VIPActivity;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.constant.UrlConstant;
import com.example.pilipili_android.databinding.FragmentMineBinding;
import com.example.pilipili_android.util.SPUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.pilipili_android.constant.SPConstant.AVATAR;
import static com.example.pilipili_android.constant.UrlConstant.COMPRESS_CACHE;
import static com.example.pilipili_android.constant.UrlConstant.CROP_CACHE;

public class MineFragment extends Fragment {

    @BindView(R.id.avatar)
    QMUIRadiusImageView avatar;
    @BindView(R.id.big_vip)
    RelativeLayout bigVip;
    private Unbinder unbinder;
    private FragmentMineBinding fragmentMineBinding;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_UPLOAD = 12321;
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public MineFragment() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        View view = fragmentMineBinding.getRoot();
        fragmentMineBinding.setUserViewModel(new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserViewModel.class));
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        if (UserBaseDetail.isAvatarDefault(getContext())) {
            avatar.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(DefaultConstant.AVATAR_IMAGE_DEFAULT));
        } else {
            Glide.with(this).load(UserBaseDetail.getAvatarPath(getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(avatar);
        }
        String ddl = UserBaseDetail.getVIPDeadline(getContext());
        if (ddl.equals("")) {
            fragmentMineBinding.setVIPDeadline("");
            fragmentMineBinding.setVIPShow("成为大会员");
        } else {
            fragmentMineBinding.setVIPDeadline(ddl + "到期");
            fragmentMineBinding.setVIPShow("PiliPili大会员");
        }
        fragmentMineBinding.setUsername(UserBaseDetail.getUsername(getContext()));
        fragmentMineBinding.setGender(UserBaseDetail.getGender(getContext()));
        fragmentMineBinding.setCoins(UserBaseDetail.getCoinInMineFragment(getContext()));
        fragmentMineBinding.setFollower(UserBaseDetail.getFollowerInMineFragment(getContext()));
        fragmentMineBinding.setFollowing(UserBaseDetail.getFollowingInMineFragment(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    @OnClick(R.id.space_btn)
    void onMySpaceClicked() {
        Intent intent = new Intent(getActivity(), SpaceActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_coin)
    void onBuyCoinClicked() {
        Intent intent = new Intent(getActivity(), PayActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.big_vip)
    void onVIPClicked() {
        Intent intent = new Intent(getActivity(), VIPActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_publish)
    void onPublishClicked() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
            openAlbum();
        }
    }

    private void openAlbum() {
        Matisse.from(getActivity()).choose(MimeType.ofVideo()).showSingleMediaType(true)
                .capture(true).captureStrategy(new CaptureStrategy(true, "com.pilipili.fuckthisshit"))
                .countable(false).maxSelectable(1).restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.8f).theme(R.style.Matisse_Dracula).forResult(REQUEST_UPLOAD);
    }

    //获取权限的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getContext(), "啊啦~被残忍拒绝了呢~", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
                openAlbum();
            }
        }
    }

    @OnClick(R.id.button_setting)
    void onSettingClicked() {
        showBottomDialog();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.DialogTheme);
        View view = View.inflate(getContext(), R.layout.two_and_cancel_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        if (window != null) {
            //设置弹出位置
            window.setGravity(Gravity.BOTTOM);
            //设置弹出动画
            window.setWindowAnimations(R.style.main_menu_animStyle);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCancelable(true);
        dialog.show();

        TextView cancelTv = view.findViewById(R.id.cancel);
        cancelTv.setTextColor(getContext().getColor(R.color.qmui_config_color_gray_3));
        cancelTv.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        TextView cleanCache = view.findViewById(R.id.tv1);
        TextView logOff = view.findViewById(R.id.tv2);
        cleanCache.setText("清除缓存");
        logOff.setText("退出登录");
        logOff.setTextColor(getContext().getColor(R.color.firebrick));
        cleanCache.setOnClickListener(view1 -> {
            dialog.dismiss();
            new Thread(() -> Glide.get(Objects.requireNonNull(getActivity())).clearDiskCache()).start();
            Glide.get(Objects.requireNonNull(getActivity())).clearMemory();
//            File file1 = new File(CROP_CACHE);
//            File file2 = new File(COMPRESS_CACHE);
//            if(file1.delete() && file2.delete()) {
//                Toast.makeText(getContext(), "清除缓存成功", Toast.LENGTH_SHORT).show();
//            }
        });

        logOff.setOnClickListener(view1 -> {
            dialog.dismiss();
            SPUtil.clear(getContext());
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        });
    }

}