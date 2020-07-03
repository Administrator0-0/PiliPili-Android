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
import com.example.pilipili_android.bean.netbean.UserVideoReturn;
import com.example.pilipili_android.inteface.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pilipili_android.constant.UrlConstant.PILIPILI_BUCKET;
import static com.example.pilipili_android.util.AliyunOSSUtil.getOSS;

public class UserVideoAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<UserVideoReturn.DataBean.VideoListBean> userVideoList;
    private OnItemClickListener onItemClickListener;

    public UserVideoAdapter(Context context, List<UserVideoReturn.DataBean.VideoListBean> userVideoList, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.userVideoList = userVideoList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserVideoAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_user_video_in_space, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserVideoAdapter.ItemViewHolder itemViewHolder = (UserVideoAdapter.ItemViewHolder) holder;
        UserVideoReturn.DataBean.VideoListBean userVideoBean = userVideoList.get(position);
        itemViewHolder.textTime.setText(userVideoBean.getTime());
        itemViewHolder.textDanmu.setText(userVideoBean.getDanmuku() + "");
        itemViewHolder.textDuration.setText(userVideoBean.getDuration());
        itemViewHolder.textNumberDisplay.setText(userVideoBean.getViews() + "");
        itemViewHolder.textVideoTitle.setText(userVideoBean.getTitle());
        itemViewHolder.coverImage.post(() -> Glide.with(context).load(getOSS(context, userVideoBean.getBucket_cover().getGuest_key(), userVideoBean.getBucket_cover().getGuest_secret(), userVideoBean.getBucket_cover().getSecurity_token()).presignPublicObjectURL(PILIPILI_BUCKET, userVideoBean.getBucket_cover().getFile())).diskCacheStrategy(DiskCacheStrategy.NONE).into(itemViewHolder.coverImage));
        itemViewHolder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(userVideoBean.getPv()));
    }

    @Override
    public int getItemCount() {
        return userVideoList.size();
    }

    public void setUserVideoList(List<UserVideoReturn.DataBean.VideoListBean> relateVideoList) {
        this.userVideoList = relateVideoList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_image)
        ImageView coverImage;
        @BindView(R.id.text_duration)
        TextView textDuration;
        @BindView(R.id.text_video_title)
        TextView textVideoTitle;
        @BindView(R.id.text_time)
        TextView textTime;
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
