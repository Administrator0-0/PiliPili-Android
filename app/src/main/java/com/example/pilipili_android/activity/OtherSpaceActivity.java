package com.example.pilipili_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pilipili_android.R;
import com.example.pilipili_android.adapter.UserVideoAdapter;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.databinding.ActivityOtherSpaceBinding;
import com.example.pilipili_android.util.AppBarStateChangeListener;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherSpaceActivity extends AppCompatActivity {

    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.gender_img)
    ImageView genderImg;
    @BindView(R.id.big_vip)
    ImageView bigVip;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.sign)
    TextView signTv;
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.avatar)
    QMUIRadiusImageView avatar;
    @BindView(R.id.uid)
    TextView uid;
    @BindView(R.id.fan)
    TextView fan;
    @BindView(R.id.recyclerview_space)
    RecyclerView recyclerViewSpace;
    @BindView(R.id.txt_main_title)
    TextView txtMainTitle;
    @BindView(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    private int UID;
    private boolean isDetail = false;
    private String signSingle = "";//指sign单行显示
    private boolean hasSign = false;
    private String signOrigin = "";//指sign展开显示
    ActivityOtherSpaceBinding activitySpaceBinding;

    private UserVideoAdapter userVideoAdapter;

    private AppBarStateChangeListener appBarStateChangeListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            if (state == State.EXPANDED) {
                //展开状态

                toolbarLayout.setVisibility(View.GONE);
            } else if (state == State.COLLAPSED) {
                //折叠状态
                toolbarLayout.setVisibility(View.VISIBLE);
            } else {
                toolbarLayout.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initBind();
        initView();

        activitySpaceBinding.getUserViewModel().getSpaceActivityBean().observe(this, spaceActivityBean -> {
            activitySpaceBinding.setSpaceActivityBean(spaceActivityBean);
            txtMainTitle.setText(activitySpaceBinding.getSpaceActivityBean().getUsername());
            if (spaceActivityBean.isGender()) {
                genderImg.setImageResource(R.drawable.cyx);
            } else {
                genderImg.setImageResource(R.drawable.cyz);
            }

            if (spaceActivityBean.isVip()) {
                bigVip.setImageResource(R.drawable.bxrb);
                usernameTv.setTextColor(getColor(R.color.colorPrimary));
            } else {
                bigVip.setVisibility(View.GONE);
            }
            signOrigin = spaceActivityBean.getSign();
            if(signOrigin.equals("")) {
                signOrigin = "这个人很神秘，什么都没有留下";
                signSingle = "这个人很神秘，什么都没有留下";
                hasSign = false;
            } else {
                signTv.setText(signOrigin);
                signSingle = signTv.getText().toString().substring(0, signTv.getLayout().getLineEnd(0));
                hasSign = true;
            }
            signTv.setText(signSingle);
            detail.setText("详情");
            uid.setVisibility(View.GONE);
        });

        activitySpaceBinding.getUserViewModel().getSpaceBackgroundUrl().observe(this, spaceBackgroundUrl -> {
            if (spaceBackgroundUrl.equals("")) {
                background.setImageResource(DefaultConstant.BACKGROUND_IMAGE_DEFAULT);
            } else {
                Glide.with(this).load(spaceBackgroundUrl).into(background);
            }
        });

        activitySpaceBinding.getUserViewModel().getSpaceAvatarUrl().observe(this, spaceAvatarUrl -> {
            if (spaceAvatarUrl.equals("")) {
                avatar.setImageDrawable(getDrawable(DefaultConstant.AVATAR_IMAGE_DEFAULT));
            } else {
                Glide.with(this).load(spaceAvatarUrl).into(avatar);
            }
        });

        activitySpaceBinding.getUserViewModel().getIsFollowed().observe(this, aBoolean -> {
            fan.setText(aBoolean ? "取消关注" : "关注TA");
        });

        activitySpaceBinding.getUserViewModel().getUserVideoBeans().observe(this, dataBeans -> {
            userVideoAdapter.setUserVideoList(activitySpaceBinding.getUserViewModel().getUserVideoBeans().getValue());
            userVideoAdapter.notifyDataSetChanged();
        });

        activitySpaceBinding.getUserViewModel().getVideoDetailBean().observe(this, videoDetailBean -> {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra("dataBean", videoDetailBean);
            startActivity(intent);
        });

    }

    private void initData() {
        Intent intent = getIntent();
        UID = intent.getIntExtra("UID", -1);
    }

    private void initBind() {
        activitySpaceBinding = DataBindingUtil.setContentView(this, R.layout.activity_other_space);
        activitySpaceBinding.setUserViewModel(new ViewModelProvider(this).get(UserViewModel.class));
        ButterKnife.bind(this);
    }

    private void initView() {
        appBarLayout.addOnOffsetChangedListener(appBarStateChangeListener);

        userVideoAdapter = new UserVideoAdapter(this, activitySpaceBinding.getUserViewModel().getUserVideoBeans().getValue(), pv -> {
            activitySpaceBinding.getUserViewModel().getVideoDetail(pv);
        });
        recyclerViewSpace.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSpace.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewSpace.setAdapter(userVideoAdapter);

        activitySpaceBinding.getUserViewModel().getUserVideo(UID);

        //部分机型的statusbar会有半透明的黑色背景
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
  //      getWindow().setStatusBarColor(Color.TRANSPARENT);
        activitySpaceBinding.getUserViewModel().getUserSpaceData(UID);
        activitySpaceBinding.getUserViewModel().isFollowed(UID);
    }

    @OnClick(R.id.button_return_inspace)
    public void onBackClicked() {
        this.finish();
    }

    @OnClick(R.id.fan)
    public void onFanClicked() {
        if (activitySpaceBinding.getUserViewModel().getIsFollowed().getValue() == null) return;
        if (activitySpaceBinding.getUserViewModel().getIsFollowed().getValue()) {
            activitySpaceBinding.getUserViewModel().unFollow(UID);
        } else {
            activitySpaceBinding.getUserViewModel().follow(UID);
        }
    }

    @OnClick(R.id.relativelayout_6)
    public void onDetailClicked() {
        if (!isDetail) {
            signTv.setText(signOrigin);
            uid.setVisibility(View.VISIBLE);
            detail.setText("收起");
        } else {
            signTv.setText(signSingle);
            uid.setVisibility(View.GONE);
            detail.setText("详情");
        }
        isDetail = !isDetail;
    }

    @OnClick(R.id.follower_tv)
    public void onFanClick() {
        Intent intent = new Intent(this, FanListActivity.class);
        intent.putExtra("id", UID);
        intent.putExtra("isFollow", true);
        startActivity(intent);
    }

    @OnClick(R.id.following_tv)
    public void onFollowingClick() {
        Intent intent = new Intent(this, FanListActivity.class);
        intent.putExtra("id", UID);
        intent.putExtra("isFollow", false);
        startActivity(intent);
    }

    @OnClick(R.id.uid)
    public void onUidClicked() {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", UID + "");
        // 将ClipData内容放到系统剪贴板里。
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }
        Toast.makeText(this, "UID已复制到剪切板", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.icon_left)
    public void onIconLeftClicked() {
        this.finish();
    }

}
