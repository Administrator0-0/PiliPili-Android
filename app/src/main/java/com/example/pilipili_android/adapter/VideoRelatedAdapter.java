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


public class VideoRelatedAdapter extends RecyclerView.Adapter {
    private List<String> relates;

    public VideoRelatedAdapter(List<String> relates) {
        this.relates = relates;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_video_other, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.mUpName.setText("刘薪王分身");
        itemViewHolder.mVideoPic.setImageResource(R.drawable.bxx);
        itemViewHolder.mVideoPlayNum.setText("99999999");
        itemViewHolder.mVideoReviewNum.setText("88888888");
        itemViewHolder.mVideoTitle.setText("刘薪王的分身很多");
    }

    @Override
    public int getItemCount() {
        return relates.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mVideoPic;
        TextView mVideoTitle;
        TextView mVideoPlayNum;
        TextView mVideoReviewNum;
        TextView mUpName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mVideoPic = itemView.findViewById(R.id.item_img);
            mVideoTitle = itemView.findViewById(R.id.item_title);
            mVideoPlayNum = itemView.findViewById(R.id.item_play);
            mVideoReviewNum = itemView.findViewById(R.id.item_review);
            mUpName = itemView.findViewById(R.id.item_user_name);
        }
    }
}
