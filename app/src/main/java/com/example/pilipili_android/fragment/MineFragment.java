package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.PayActivity;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.databinding.FragmentMineBinding;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineFragment extends Fragment {

    @BindView(R.id.avatar)
    QMUIRadiusImageView avatar;
    private Unbinder unbinder;
    private FragmentMineBinding fragmentMineBinding;

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
        fragmentMineBinding.setUserViewModel(new ViewModelProvider(MineFragment.this).get(UserViewModel.class));
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    private void initView() {
        if(UserBaseDetail.isAvatarDefault(getContext())) {
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
        intent.putExtra("UID", UserBaseDetail.getUID(getContext()));
        startActivity(intent);
    }

    @OnClick(R.id.button_coin)
    void onBuyCoinClicked() {
        Intent intent = new Intent(getActivity(), PayActivity.class);
        startActivity(intent);
    }
}