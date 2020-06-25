package com.example.pilipili_android.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;

import java.util.List;


public class VideoCommentAdapter extends RecyclerView.Adapter {
    private List<List<SpannableString>> replays;
    private List<String> comments;
    private Context mContext;
    private VideoActivity.OnRelayOpenListener mListener;

    public VideoCommentAdapter(List<String> comments, List<List<SpannableString>> replays) {
        this.comments = comments;
        this.replays = replays;
    }

    public void setListener(VideoActivity.OnRelayOpenListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_comment_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.mUsername.setText("刘薪王分身");
        itemViewHolder.mUserAvatar.setImageResource(R.drawable.bxx);
        itemViewHolder.mCommentTime.setText("99999999");
        itemViewHolder.mLike.setText("88888888");
        itemViewHolder.mContent.setText("刘薪王的分身很多");
        List<SpannableString> strings = replays.get(position);
        if (strings == null) {
            itemViewHolder.replays[0].setVisibility(View.GONE);
            itemViewHolder.replays[1].setVisibility(View.GONE);
            itemViewHolder.replays[2].setVisibility(View.GONE);
            itemViewHolder.replays[3].setVisibility(View.GONE);
            return;
        }
        for (int i = 0; i < strings.size() && i < 3; i++) {
            itemViewHolder.replays[i].setVisibility(View.VISIBLE);
            itemViewHolder.replays[i].setText(strings.get(i));
        }
        if (strings.size() > 3) {
            SpannableString click = new SpannableString("更多评论");
            click.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    if (mListener != null) mListener.onOpen(position);
                }
            }, 0, click.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemViewHolder.replays[3].setMovementMethod(LinkMovementMethod.getInstance());
            itemViewHolder.replays[3].setText(click);
            itemViewHolder.replays[3].setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.replays[3].setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return replays.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mUserAvatar;
        TextView mUsername;
        TextView mCommentTime;
        TextView mContent;
        TextView mLike;
        TextView[]replays = new TextView[4];

        public ItemViewHolder(View itemView) {
            super(itemView);
            mUserAvatar = itemView.findViewById(R.id.item_user_avatar);
            mUsername = itemView.findViewById(R.id.item_user_name);
            mCommentTime = itemView.findViewById(R.id.item_comment_time);
            mContent = itemView.findViewById(R.id.item_comment_content);
            mLike = itemView.findViewById(R.id.item_comment_like);
            replays[0] = itemView.findViewById(R.id.replay_one);
            replays[1] = itemView.findViewById(R.id.replay_two);
            replays[2] = itemView.findViewById(R.id.replay_three);
            replays[3] = itemView.findViewById(R.id.replay_total);
        }
    }

}

