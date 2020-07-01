package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.adapter.VideoRelatedAdapter;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.view_model.UserViewModel;
import com.example.pilipili_android.view_model.VideoViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoInfoFragment extends Fragment {

    @BindView(R.id.user_avatar)
    QMUIRadiusImageView userAvatar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.fans)
    TextView fans;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_play_time)
    TextView tvPlayTime;
    @BindView(R.id.tv_review_count)
    TextView tvReviewCount;
    @BindView(R.id.tv_upload_time)
    TextView tvUploadTime;
    @BindView(R.id.tv_pv)
    TextView tvPv;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.like_img)
    ImageView likeImg;
    @BindView(R.id.tv_likes)
    TextView tvLikes;
    @BindView(R.id.dislike_img)
    ImageView dislikeImg;
    @BindView(R.id.coin_image)
    ImageView coinImage;
    @BindView(R.id.tv_coins)
    TextView tvCoins;
    @BindView(R.id.star_img)
    ImageView starImg;
    @BindView(R.id.tv_stars)
    TextView tvStars;
    @BindView(R.id.video_list)
    RecyclerView videoListView;

    private List<String> videoList;
    private VideoRelatedAdapter videoRelatedAdapter;
    private VideoViewModel videoViewModel;
    private UserViewModel userViewModel;

    private boolean isShowSign = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoList = new ArrayList<>();
        for (int i = 0; i < 50; i++) videoList.add("sss");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_info, container, false);
        ButterKnife.bind(this, view);
        videoViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(VideoViewModel.class);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        userViewModel.getSpaceActivityBean().observe(getActivity(), spaceActivityBean -> {
            userName.setText(spaceActivityBean.getUsername());
            fans.setText(spaceActivityBean.getFollower());
        });

        userViewModel.getSpaceAvatarUrl().observe(getActivity(), url -> {
            if (url.equals("")) {
                userAvatar.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(DefaultConstant.AVATAR_IMAGE_DEFAULT));
            } else {
                Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).into(userAvatar);
            }
        });

        initView();
        return view;
    }

    private void initView() {
        videoRelatedAdapter = new VideoRelatedAdapter(videoList);
        videoListView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        videoListView.setAdapter(videoRelatedAdapter);

        userViewModel.getUserOpenDataForVideoActivity(videoViewModel.getDataBean().getAuthor());

        tvTitle.setText(videoViewModel.getDataBean().getTitle());
        tvTitle.setSingleLine(true);
        tvTitle.setEllipsize(TextUtils.TruncateAt.END);

        tvPlayTime.setText(videoViewModel.getDataBean().getViews() + "");//播放
        tvReviewCount.setText(videoViewModel.getDataBean().getDanmuku() + "");//弹幕
        tvUploadTime.setText(videoViewModel.getDataBean().getTime());//发布日期
        tvPv.setText("pv" + videoViewModel.getDataBean().getPv());//pv号

        tvDescription.setText(videoViewModel.getDataBean().getSign());//简介
        tvDescription.setVisibility(View.GONE);

        tvLikes.setText(videoViewModel.getDataBean().getLikes() + "");
        tvCoins.setText(videoViewModel.getDataBean().getCoins() + "");
        tvStars.setText(videoViewModel.getDataBean().getCollections() + "");
        if (videoViewModel.getDataBean().isIs_liked()) {
            likeImg.setImageResource(R.drawable.bss_selected);
        }
        if (videoViewModel.getDataBean().isIs_collected()) {
            starImg.setImageResource(R.drawable.bsp_selected);
        }
    }


    @OnClick(R.id.to_space_btn)
    public void onToSpaceBtnClicked() {
        Intent intent = new Intent(getContext(), SpaceActivity.class);
        intent.putExtra("UID", videoViewModel.getDataBean().getAuthor());
        startActivity(intent);
    }

    @OnClick(R.id.follow)
    public void onFollowClicked() {

    }

    @OnClick(R.id.show_sign)
    public void onShowSignClicked() {
        if(isShowSign) {
            isShowSign = false;
            tvDescription.setVisibility(View.GONE);
        } else {
            isShowSign = true;
            tvDescription.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_like)
    public void onBtnShareClicked() {
    }

    @OnClick(R.id.btn_dislike)
    public void onBtnDislikeClicked() {
    }

    @OnClick(R.id.btn_coin)
    public void onBtnCoinClicked() {
    }

    @OnClick(R.id.btn_fav)
    public void onBtnFavClicked() {
    }

    @OnClick(R.id.to_space_btn)
    public void onViewClicked() {
    }
}
