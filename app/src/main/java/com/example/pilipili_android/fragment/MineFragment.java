package com.example.pilipili_android.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import com.example.pilipili_android.activity.PayActivity;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.activity.UploadVideoActivity;
import com.example.pilipili_android.activity.VIPActivity;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.databinding.FragmentMineBinding;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    public void onVIPClicked() {
        Intent intent = new Intent(getActivity(), VIPActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_publish)
    public void onPublishClicked() {
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
                .capture(true).captureStrategy(new CaptureStrategy(true,"com.pilipili.fuckthisshit"))
                .countable(false).maxSelectable(1).restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.8f).theme(R.style.Matisse_Dracula).forResult(REQUEST_UPLOAD);
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
}