package com.example.pilipili_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilipili_android.R;

import java.util.List;


public class CommentReplayAdapter extends RecyclerView.Adapter {
    private List<String> relates;

    public CommentReplayAdapter(List<String> relates) {
        this.relates = relates;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_replay, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.username.setText("刘薪王分身");
        itemViewHolder.userAvatar.setImageResource(R.drawable.bxx);
        itemViewHolder.commentTime.setText("99999999");
        itemViewHolder.commentLikeNum.setText("88888888");
        itemViewHolder.commentContent.setText("刘薪王的分身很多");
    }

    @Override
    public int getItemCount() {
        return relates.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView userAvatar;
        TextView username;
        TextView commentTime;
        TextView commentContent;
        TextView commentLikeNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            username = itemView.findViewById(R.id.user_name);
            commentTime = itemView.findViewById(R.id.comment_time);
            commentContent = itemView.findViewById(R.id.comment_content);
            commentLikeNum = itemView.findViewById(R.id.comment_like_num);
        }
    }
}

