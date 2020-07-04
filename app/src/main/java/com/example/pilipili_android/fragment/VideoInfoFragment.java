package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.OtherSpaceActivity;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.adapter.VideoRelatedAdapter;
import com.example.pilipili_android.bean.netbean.GetRelatedVideoReturn;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.inteface.OnItemClickListener;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.example.pilipili_android.view_model.VideoViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.follow)
    TextView follow;
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
    @BindView(R.id.up_down_img)
    ImageView upDownImg;

    private VideoRelatedAdapter videoRelatedAdapter;
    private VideoViewModel videoViewModel;
    private UserViewModel userViewModel;

    private boolean isShowSign = false;
    private boolean isFollow = false;
    private boolean isLike = false;
    private boolean isDisLike = false;
    private boolean isStar = false;

    private int coin;
    private int star;
    private int like;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_info, container, false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);

        videoViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(VideoViewModel.class);
        videoRelatedAdapter = new VideoRelatedAdapter(getContext(), videoViewModel.getVideoRelatedBeans().getValue(), pv -> {
            videoViewModel.getVideoDetail(pv);
        });
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        videoViewModel.getRelatedVideos(videoViewModel.getDataBean().getPv());

        coin = videoViewModel.getDataBean().getCoins();
        like = videoViewModel.getDataBean().getLikes();
        star = videoViewModel.getDataBean().getCollections();

        initView();

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

        videoViewModel.getIsLikeSuccess().observe(getActivity(), isSuccess -> {
            isLike = true;
            like += 1;
            tvLikes.setText(like + "");
            likeImg.setImageResource(R.drawable.bss_selected);
        });
        videoViewModel.getIsCancelLikeSuccess().observe(getActivity(), isSuccess -> {
            isLike = false;
            like -= 1;
            tvLikes.setText(like + "");
            likeImg.setImageResource(R.drawable.bss);
        });
        videoViewModel.getIsStarSuccess().observe(getActivity(), isSuccess -> {
            isStar = true;
            star += 1;
            tvStars.setText(star + "");
            starImg.setImageResource(R.drawable.bsp_selected);
        });
        videoViewModel.getIsCancelStarSuccess().observe(getActivity(), isSuccess -> {
            isStar = false;
            star -= 1;
            tvStars.setText(star + "");
            starImg.setImageResource(R.drawable.bsp);
        });
        videoViewModel.getIsRewardSuccess().observe(getActivity(), coins -> {
            coin += coins;
            tvCoins.setText(coin + "");
            coinImage.setImageResource(R.drawable.bsl_selected);
        });

        userViewModel.getIsFollowed().observe(getActivity(), isFollow -> {
            if(isFollow) {
                this.isFollow = true;
                follow.setTextColor(getContext().getColor(R.color.gray));
                follow.setText("已关注");
                follow.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.btn_pin_bg_already_follow));
            } else {
                this.isFollow = false;
                follow.setTextColor(getContext().getColor(R.color.white));
                follow.setText("关注TA");
                follow.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.btn_pin_bg));
            }
        });

        videoViewModel.getVideoRelatedBeans().observe(getActivity(), videoListBeans -> {
            videoRelatedAdapter.setRelateVideoList(videoViewModel.getVideoRelatedBeans().getValue());
            videoRelatedAdapter.notifyDataSetChanged();
        });

        videoViewModel.getVideoDetailBean().observe(getActivity(), videoDetailBean -> {
            Intent intent = new Intent(videoViewModel.getContext(), VideoActivity.class);
            intent.putExtra("dataBean", videoDetailBean);
            videoViewModel.getContext().startActivity(intent);
        });

        return view;
    }

    private void initView() {
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
        tvPv.setText("PV" + videoViewModel.getDataBean().getPv());//pv号

        tvDescription.setText(videoViewModel.getDataBean().getSign());//简介
        tvDescription.setVisibility(View.GONE);

        tvLikes.setText(videoViewModel.getDataBean().getLikes() + "");
        tvCoins.setText(videoViewModel.getDataBean().getCoins() + "");
        tvStars.setText(videoViewModel.getDataBean().getCollections() + "");
        if (videoViewModel.getDataBean().isIs_liked()) {
            isLike = true;
            likeImg.setImageResource(R.drawable.bss_selected);
        }
        if (videoViewModel.getDataBean().isIs_collected()) {
            isStar = true;
            starImg.setImageResource(R.drawable.bsp_selected);
        }
        if(UserBaseDetail.getUID(getContext()) == videoViewModel.getDataBean().getAuthor()) {
            follow.setVisibility(View.GONE);
            this.isFollow = false;
        }
        if(videoViewModel.getDataBean().isIs_followed()) {
            isFollow = true;
            follow.setText("已关注");
            follow.setTextColor(getContext().getColor(R.color.gray));
            follow.setBackground(getContext().getDrawable(R.drawable.btn_pin_bg_already_follow));
        }
    }

    @OnClick(R.id.to_space_btn)
    void onToSpaceBtnClicked() {
        if(videoViewModel.getDataBean().getAuthor() == UserBaseDetail.getUID(getContext())) {
            Intent intent = new Intent(getContext(), SpaceActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), OtherSpaceActivity.class);
            intent.putExtra("UID", videoViewModel.getDataBean().getAuthor());
            startActivity(intent);
        }
    }

    @OnClick(R.id.follow)
    void onFollowClicked() {
        if(isFollow) {
            userViewModel.unFollow(videoViewModel.getDataBean().getAuthor());
        } else {
            userViewModel.follow(videoViewModel.getDataBean().getAuthor());
        }
    }

    @OnClick(R.id.show_sign)
    void onShowSignClicked() {
        if (isShowSign) {
            isShowSign = false;
            upDownImg.setImageResource(R.drawable.bez);
            tvDescription.setVisibility(View.GONE);
        } else {
            isShowSign = true;
            upDownImg.setImageResource(R.drawable.bf0);
            if(!videoViewModel.getDataBean().getSign().equals(""))
                tvDescription.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_like)
    void onBtnLikeClicked() {
        if(isLike) {
            videoViewModel.cancelLikeVideo();
        } else {
            videoViewModel.likeVideo();
        }
    }

    @OnClick(R.id.btn_dislike)
    void onBtnDislikeClicked() {
        if(isDisLike) {
            Toast.makeText(getContext(), "取消不喜欢", Toast.LENGTH_SHORT).show();
            isDisLike = false;
            dislikeImg.setImageResource(R.drawable.bsn);
        } else {
            Toast.makeText(getContext(), "感谢反馈", Toast.LENGTH_SHORT).show();
            isDisLike = true;
            dislikeImg.setImageResource(R.drawable.bsn_selected);
        }
    }

    @OnClick(R.id.btn_coin)
    void onBtnCoinClicked() {
        EventBus.getDefault().post(FragmentMsg.getInstance("VideoInfoFragment", "show"));
    }

    @OnClick(R.id.btn_star)
    void onBtnFavClicked() {
        if(isStar) {
            videoViewModel.cancelStarVideo();
        } else {
            videoViewModel.starVideo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentMessage(FragmentMsg fragmentMsg) {
        if(fragmentMsg.getWhatFragment().equals("RewardVideoFragment")) {
            if(fragmentMsg.getMsgString().equals("1")) {
                videoViewModel.rewardVideo(1);
            } else if(fragmentMsg.getMsgString().equals("2")) {
                videoViewModel.rewardVideo(2);
            }
        }
    }

}
