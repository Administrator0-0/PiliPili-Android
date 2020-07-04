package com.example.pilipili_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.OtherSpaceActivity;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.bean.netbean.ListFollowReturn;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.fragment.FanListFragment;
import com.example.pilipili_android.view_model.UserBaseDetail;

import java.util.List;

public class UserFollowAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ListFollowReturn.DataBean.ListBean> userList;
    private FanListFragment.OnFollowOrUnFollow listener;

    public UserFollowAdapter(List<ListFollowReturn.DataBean.ListBean> userList, FanListFragment.OnFollowOrUnFollow listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public void setUserList(List<ListFollowReturn.DataBean.ListBean> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        ListFollowReturn.DataBean.ListBean bean = userList.get(position);
        itemViewHolder.username.setText(bean.getUsername());
        itemViewHolder.sign.setText(bean.getSign());
        if (bean.getId() == UserBaseDetail.getUID(mContext)) {
            itemViewHolder.follow.setVisibility(View.GONE);
        } else {
            if (bean.isIs_followed()) {
                itemViewHolder.follow.setText("取消关注");
                itemViewHolder.follow.setOnClickListener(v -> listener.onUnFollow(bean));
            } else {
                itemViewHolder.follow.setText("+ 关注");
                itemViewHolder.follow.setOnClickListener(v -> listener.onFollow(bean));
            }
        }
        itemViewHolder.userLayout.setOnClickListener(v -> {
            if (bean.getId() == UserBaseDetail.getUID(mContext)) {
                Intent intent = new Intent(mContext, SpaceActivity.class);
                intent.putExtra("UID", UserBaseDetail.getUID(mContext));
                mContext.startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, OtherSpaceActivity.class);
                intent.putExtra("UID", bean.getId());
                mContext.startActivity(intent);
            }
        });
        itemViewHolder.userAvatar.post(() -> {
            if(bean.getAvatar() != null) {
                Glide.with(mContext).load(bean.getAvatar()).diskCacheStrategy(DiskCacheStrategy.NONE).into(itemViewHolder.userAvatar);
            } else {
                itemViewHolder.userAvatar.setImageResource(DefaultConstant.AVATAR_IMAGE_DEFAULT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView userAvatar;
        TextView username;
        TextView sign;
        Button follow;
        RelativeLayout userLayout;

        ItemViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            username = itemView.findViewById(R.id.user_name);
            sign = itemView.findViewById(R.id.sign);
            follow = itemView.findViewById(R.id.follow);
            userLayout = itemView.findViewById(R.id.user_layout);
        }
    }
}
