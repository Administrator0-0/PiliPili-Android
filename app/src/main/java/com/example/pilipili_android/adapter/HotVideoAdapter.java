package com.example.pilipili_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.bean.netbean.VideoDetailReturn;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pilipili_android.constant.UrlConstant.PILIPILI_BUCKET;
import static com.example.pilipili_android.util.AliyunOSSUtil.getOSS;

public class HotVideoAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<VideoDetailReturn.DataBean> dataBeanList;

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cover_image)
        ImageView coverImage;
        @BindView(R.id.text_number_display)
        TextView textNumberDisplay;
        @BindView(R.id.text_danmu)
        TextView textDanmu;
        @BindView(R.id.text_duration)
        TextView textDuration;
        @BindView(R.id.text_video_title)
        TextView textVideoTitle;
        @BindView(R.id.video_type)
        TextView videoType;

        VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public HotVideoAdapter(Context context) {
        this.context = context;
    }

    public void setDataBeanList(List<VideoDetailReturn.DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
        VideoDetailReturn.DataBean dataBean = dataBeanList.get(position);
        videoViewHolder.textDuration.setText(dataBean.getDuration());
        if(dataBean.getViews() == 0) {
            videoViewHolder.textNumberDisplay.setText("- 播放");
        } else {
            videoViewHolder.textNumberDisplay.setText(dataBean.getViews() + "播放");
        }
        videoViewHolder.textVideoTitle.setText(dataBean.getTitle());
        if(dataBean.getType() == 1) {
            videoViewHolder.videoType.setText("综合");
        } else if(dataBean.getType() == 2) {
            videoViewHolder.videoType.setText("颜色");
        } else if(dataBean.getType() == 3) {
            videoViewHolder.videoType.setText("番剧");
        }
        if(dataBean.getDanmuku() == 0) {
            videoViewHolder.textDanmu.setText("- 弹幕");
        } else {
            videoViewHolder.textDanmu.setText(dataBean.getDanmuku() + "弹幕");
        }
        VideoDetailReturn.DataBean.BucketCoverBean bucketCoverBean = dataBean.getBucket_cover();
        videoViewHolder.coverImage.post(() -> {
            Glide.with(context).load(getOSS(context, bucketCoverBean.getGuest_key(), bucketCoverBean.getGuest_secret(), bucketCoverBean.getSecurity_token()).presignPublicObjectURL(PILIPILI_BUCKET, bucketCoverBean.getFile())).diskCacheStrategy(DiskCacheStrategy.NONE).into(videoViewHolder.coverImage);
        });
        videoViewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra("dataBean", dataBean);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }
}
