package com.example.pilipili_android.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.bean.localbean.CommentItemBean;
import com.example.pilipili_android.util.AliyunOSSUtil;

import java.util.HashMap;
import java.util.List;


public class CommentReplayAdapter extends RecyclerView.Adapter {
    private List<CommentItemBean> replays;
    private CommentItemBean main;
    private Context mContext;
    private VideoActivity.OnRelayListener relayListener;

    public CommentReplayAdapter(CommentItemBean main, HashMap<Integer, List<CommentItemBean>> allReplays) {
        this.main = main;
        this.replays = allReplays.get(main.getComment().getId());
    }

    public void setReplays(HashMap<Integer, List<CommentItemBean>> allReplays) {
        this.replays = allReplays.get(main.getComment().getId());
    }

    public void setRelayListener(VideoActivity.OnRelayListener relayListener) {
        this.relayListener = relayListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_replay, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        CommentItemBean itemBean = replays.get(position);
        itemViewHolder.mAdd.setOnClickListener(v -> relayListener.onRelay(itemBean, true));
        itemViewHolder.mLike.setOnClickListener(v -> relayListener.onLike(itemBean));
        itemViewHolder.username.setText(itemBean.getUser().getUsername());
        String url = AliyunOSSUtil.getImageUrl(mContext.getApplicationContext(), itemBean.getAvatar().getGuest_key(),
                itemBean.getAvatar().getGuest_secret(), itemBean.getAvatar().getSecurity_token(), itemBean.getAvatar().getFile());
        Glide.with(mContext).load(url).into(itemViewHolder.userAvatar);
        itemViewHolder.commentTime.setText(itemBean.getComment().getTime());
        itemViewHolder.commentLikeNum.setText("" + itemBean.getComment().getLikes());
        if (itemBean.getComment().getReplay_to_author_name() != null) {
            SpannableString string = new SpannableString("@回复" + itemBean.getComment().getReplay_to_author_name() + " " + itemBean.getComment().getContent());
            string.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimary)),
                    0, ((String)(itemBean.getComment().getReplay_to_author_name())).length() + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemViewHolder.commentContent.setText(string);
        } else {
            itemViewHolder.commentContent.setText(itemBean.getComment().getContent());
        }
        if (itemBean.getComment().isIs_liked()) {
            itemViewHolder.mLike.setBackground(mContext.getResources().getDrawable(R.drawable.b_z));
        } else {
            itemViewHolder.mLike.setBackground(mContext.getResources().getDrawable(R.drawable.ba0));
        }
    }

    @Override
    public int getItemCount() {
        return replays.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView userAvatar;
        TextView username;
        TextView commentTime;
        TextView commentContent;
        TextView commentLikeNum;
        ImageView mAdd;
        ImageView mLike;

        public ItemViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            username = itemView.findViewById(R.id.user_name);
            commentTime = itemView.findViewById(R.id.comment_time);
            commentContent = itemView.findViewById(R.id.comment_content);
            commentLikeNum = itemView.findViewById(R.id.comment_like_num);
            mAdd = itemView.findViewById(R.id.comment_add);
            mLike = itemView.findViewById(R.id.comment_like);
        }
    }
}

