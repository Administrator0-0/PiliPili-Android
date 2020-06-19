package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.PayActivity;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.databinding.FragmentMineBinding;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineFragment extends Fragment {

    @BindView(R.id.button_scancode)
    ImageView buttonScancode;
    @BindView(R.id.button_theme)
    ImageView buttonTheme;
    @BindView(R.id.linearlayout_topbar)
    LinearLayout linearlayoutTopbar;
    @BindView(R.id.button_picture)
    QMUIRadiusImageView buttonPicture;
    @BindView(R.id.image_right)
    ImageView imageRight;
    @BindView(R.id.username_tv)
    TextView textNameonaboutme;
    @BindView(R.id.text_numberofcoin)
    TextView textNumberofcoin;
    @BindView(R.id.space_btn)
    RelativeLayout relativelayoutPicture;
    @BindView(R.id.text_numberoftrends)
    TextView textNumberoftrends;
    @BindView(R.id.relativelayout_numberoftrends)
    RelativeLayout relativelayoutNumberoftrends;
    @BindView(R.id.text_numberoffans)
    TextView textNumberoffans;
    @BindView(R.id.relativelayout_numberoffans)
    RelativeLayout relativelayoutNumberoffans;
    @BindView(R.id.text_numberoffocus)
    TextView textNumberoffocus;
    @BindView(R.id.relativelayout_2)
    RelativeLayout relativelayout2;
    @BindView(R.id.button_offlinecaching)
    ImageView buttonOfflinecaching;
    @BindView(R.id.button_history)
    ImageView buttonHistory;
    @BindView(R.id.button_collection)
    ImageView buttonCollection;
    @BindView(R.id.button_later)
    ImageView buttonLater;
    @BindView(R.id.aboutme_scrollview_1)
    LinearLayout aboutmeScrollview1;
    @BindView(R.id.create_center)
    TextView createCenter;
    @BindView(R.id.button_mainpage)
    ImageView buttonMainpage;
    @BindView(R.id.button_manage)
    ImageView buttonManage;
    @BindView(R.id.button_money)
    ImageView buttonMoney;
    @BindView(R.id.button_activity)
    ImageView buttonActivity;
    @BindView(R.id.aboutme_scrollview_3)
    LinearLayout aboutmeScrollview3;
    @BindView(R.id.aboutme_scrollview_2)
    RelativeLayout aboutmeScrollview2;
    @BindView(R.id.button_publish)
    QMUIRoundButton buttonPublish;
    @BindView(R.id.more_service_tv)
    TextView moreServiceTv;
    @BindView(R.id.image_callserver)
    ImageView imageCallserver;
    @BindView(R.id.button_callserver)
    RelativeLayout buttonCallserver;
    @BindView(R.id.image_coin)
    ImageView imageCoin;
    @BindView(R.id.button_coin)
    RelativeLayout buttonCoin;
    @BindView(R.id.image_young)
    ImageView imageYoung;
    @BindView(R.id.button_young)
    RelativeLayout buttonYoung;
    @BindView(R.id.image_setting)
    ImageView imageSetting;
    @BindView(R.id.button_setting)
    RelativeLayout buttonSetting;
    @BindView(R.id.gender_img)
    ImageView genderImg;

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

    @Override
    public void onStart() {
        super.onStart();
        refreshView();
    }

    private void refreshView() {
        fragmentMineBinding.setUsername(UserBaseDetail.getUsername(fragmentMineBinding.getUserViewModel().getContext()));
        fragmentMineBinding.setCoin("P币：" + UserBaseDetail.getCoin(fragmentMineBinding.getUserViewModel().getContext()));
        fragmentMineBinding.setFollower(UserBaseDetail.getFollowerCount(fragmentMineBinding.getUserViewModel().getContext()) + "");
        fragmentMineBinding.setFollowing(UserBaseDetail.getFollowingCount(fragmentMineBinding.getUserViewModel().getContext()) + "");
        fragmentMineBinding.setGender(UserBaseDetail.getGender(fragmentMineBinding.getUserViewModel().getContext()));
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
}