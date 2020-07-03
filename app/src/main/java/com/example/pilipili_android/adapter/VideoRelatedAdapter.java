package com.example.pilipili_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.bean.netbean.GetRelatedVideoReturn;
import com.example.pilipili_android.inteface.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pilipili_android.constant.UrlConstant.PILIPILI_BUCKET;
import static com.example.pilipili_android.util.AliyunOSSUtil.getOSS;


public class VideoRelatedAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<GetRelatedVideoReturn.DataBean.VideoListBean> relateVideoList;
    private OnItemClickListener onItemClickListener;

    public VideoRelatedAdapter(Context context, List<GetRelatedVideoReturn.DataBean.VideoListBean> relateVideoList, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.relateVideoList = relateVideoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_video_other, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        GetRelatedVideoReturn.DataBean.VideoListBean videoRelatedBean = relateVideoList.get(position);
        itemViewHolder.textNameUp.setText(videoRelatedBean.getAuthor_name());
        itemViewHolder.textDanmu.setText(videoRelatedBean.getDanmuku() + "");
        itemViewHolder.textDuration.setText(videoRelatedBean.getDuration());
        itemViewHolder.textNumberDisplay.setText(videoRelatedBean.getViews() + "");
        itemViewHolder.textVideoTitle.setText(videoRelatedBean.getTitle());
        itemViewHolder.coverImage.post(() -> Glide.with(context).load(getOSS(context, videoRelatedBean.getBucket_cover().getGuest_key(), videoRelatedBean.getBucket_cover().getGuest_secret(), videoRelatedBean.getBucket_cover().getSecurity_token()).presignPublicObjectURL(PILIPILI_BUCKET, videoRelatedBean.getBucket_cover().getFile())).diskCacheStrategy(DiskCacheStrategy.NONE).into(itemViewHolder.coverImage));
        itemViewHolder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(videoRelatedBean.getId());
        });
    }

    @Override
    public int getItemCount() {
        return relateVideoList.size();
    }

    public void setRelateVideoList(List<GetRelatedVideoReturn.DataBean.VideoListBean> relateVideoList) {
        this.relateVideoList = relateVideoList;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_image)
        ImageView coverImage;
        @BindView(R.id.text_duration)
        TextView textDuration;
        @BindView(R.id.text_video_title)
        TextView textVideoTitle;
        @BindView(R.id.text_name_up)
        TextView textNameUp;
        @BindView(R.id.text_number_display)
        TextView textNumberDisplay;
        @BindView(R.id.text_danmu)
        TextView textDanmu;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
