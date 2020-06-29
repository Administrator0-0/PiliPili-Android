package com.example.pilipili_android.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.bean.localbean.CommentItemBean;
import com.example.pilipili_android.bean.netbean.CommentDetailsReturn;
import com.example.pilipili_android.bean.netbean.GetUserBackgroundOrAvatarReturn;
import com.example.pilipili_android.bean.netbean.NetRequestResult;
import com.example.pilipili_android.bean.netbean.UserOpenDetailReturn;
import com.example.pilipili_android.inteface.OnNetRequestListener;
import com.example.pilipili_android.model.UserDataSource;
import com.example.pilipili_android.util.AliyunOSSUtil;

import java.util.HashMap;
import java.util.List;


public class VideoCommentAdapter extends RecyclerView.Adapter {
    private HashMap<Integer, List<CommentItemBean>> replays;
    private List<CommentItemBean> comments;
    private Context mContext;
    private VideoActivity.OnRelayOpenListener mListener;
    private VideoActivity.OnRelayListener relayListener;

    public VideoCommentAdapter(List<CommentItemBean> comments, HashMap<Integer, List<CommentItemBean>> replays) {
        this.comments = comments;
        this.replays = replays;
    }

    public void setListener(VideoActivity.OnRelayOpenListener mListener) {
        this.mListener = mListener;
    }

    public void setRelayListener(VideoActivity.OnRelayListener relayListener) {
        this.relayListener = relayListener;
    }

    public void setComments(List<CommentItemBean> comments) {
        this.comments = comments;
    }

    public void setReplays(HashMap<Integer, List<CommentItemBean>> replays) {
        this.replays = replays;
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
        CommentItemBean itemBean = comments.get(position);
        itemViewHolder.mAdd.setOnClickListener(v -> relayListener.onRelay(itemBean, true));
        itemViewHolder.mLike.setOnClickListener(v -> relayListener.onLike(itemBean));
        itemViewHolder.mUsername.setText(itemBean.getUser().getUsername());
        String url = AliyunOSSUtil.getImageUrl(mContext.getApplicationContext(), itemBean.getAvatar().getGuest_key(),
                itemBean.getAvatar().getGuest_secret(), itemBean.getAvatar().getSecurity_token(), itemBean.getAvatar().getFile());
        Glide.with(mContext).load(url).into(itemViewHolder.mUserAvatar);
        itemViewHolder.mCommentTime.setText(itemBean.getComment().getTime());
        itemViewHolder.mLikeNum.setText("" + itemBean.getComment().getLikes());
        itemViewHolder.mContent.setText(itemBean.getComment().getContent());
        List<CommentItemBean> list = replays.get(itemBean.getComment().getId());
        itemViewHolder.replays[0].setVisibility(View.GONE);
        itemViewHolder.replays[1].setVisibility(View.GONE);
        itemViewHolder.replays[2].setVisibility(View.GONE);
        itemViewHolder.replays[3].setVisibility(View.GONE);
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size() && i < 3; i++) {
            itemViewHolder.replays[i].setVisibility(View.VISIBLE);
            SpannableString string = new SpannableString(list.get(i).getUser().getUsername()
                    + ": " + list.get(i).getComment().getContent());
            string.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimary)),
                    0, list.get(i).getUser().getUsername().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemViewHolder.replays[i].setText(string);
        }
        if (list.size() > 3) {
            SpannableString click = new SpannableString("更多评论");
            click.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    if (mListener != null) mListener.onOpen(itemBean);
                }
            }, 0, click.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemViewHolder.replays[3].setMovementMethod(LinkMovementMethod.getInstance());
            itemViewHolder.replays[3].setText(click);
            itemViewHolder.replays[3].setVisibility(View.VISIBLE);
        } else {
            itemViewHolder.replays[3].setVisibility(View.GONE);
        }
        if (itemBean.getComment().isIs_liked()) {
            itemViewHolder.mLike.setBackground(mContext.getResources().getDrawable(R.drawable.b_z));
        } else {
            itemViewHolder.mLike.setBackground(mContext.getResources().getDrawable(R.drawable.ba0));
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mUserAvatar;
        ImageView mLike;
        TextView mUsername;
        TextView mCommentTime;
        TextView mContent;
        TextView mLikeNum;
        ImageView mAdd;
        TextView[]replays = new TextView[4];

        public ItemViewHolder(View itemView) {
            super(itemView);
            mUserAvatar = itemView.findViewById(R.id.item_user_avatar);
            mUsername = itemView.findViewById(R.id.item_user_name);
            mCommentTime = itemView.findViewById(R.id.item_comment_time);
            mContent = itemView.findViewById(R.id.item_comment_content);
            mLikeNum = itemView.findViewById(R.id.item_comment_like_num);
            mLike = itemView.findViewById(R.id.item_comment_like);
            replays[0] = itemView.findViewById(R.id.replay_one);
            replays[1] = itemView.findViewById(R.id.replay_two);
            replays[2] = itemView.findViewById(R.id.replay_three);
            replays[3] = itemView.findViewById(R.id.replay_total);
            mAdd = itemView.findViewById(R.id.item_comment_add);
        }
    }

}

