package com.example.pilipili_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
import com.example.pilipili_android.activity.OtherSpaceActivity;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.bean.localbean.CommentItemBean;
import com.example.pilipili_android.fragment.VideoCommentFragment;
import com.example.pilipili_android.inteface.OnDialogClickListener;
import com.example.pilipili_android.util.AliyunOSSUtil;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.widget.CommonDialog;

import java.util.HashMap;
import java.util.List;

import static com.example.pilipili_android.constant.DefaultConstant.AVATAR_IMAGE_DEFAULT;


public class VideoCommentAdapter extends RecyclerView.Adapter {
    private HashMap<Integer, List<CommentItemBean>> replays;
    private List<CommentItemBean> comments;
    private Context mContext;
    private VideoActivity.OnRelayOpenListener mListener;
    private VideoActivity.OnRelayListener relayListener;
    private VideoCommentFragment.OnDeleteListener onDeleteListener;
    private int up;

    public VideoCommentAdapter(List<CommentItemBean> comments, HashMap<Integer, List<CommentItemBean>> replays, int up) {
        this.comments = comments;
        this.replays = replays;
        this.up = up;
    }

    public void setListener(VideoActivity.OnRelayOpenListener mListener) {
        this.mListener = mListener;
    }

    public void setRelayListener(VideoActivity.OnRelayListener relayListener) {
        this.relayListener = relayListener;
    }

    public void setOnDeleteListener(VideoCommentFragment.OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
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
        itemViewHolder.more.setOnClickListener(v -> {
            CommonDialog commonDialog = new CommonDialog(mContext);
            commonDialog.setTitle("删除评论").setPositive("删除").setNegative("取消").setImageResId(R.drawable.bh3)
                    .setOnCancelDialogClickListener(commonDialog::dismiss)
                    .setOnConfirmDialogClickListener(() -> {
                        onDeleteListener.onDelete(itemBean);
                        commonDialog.dismiss();
                    })
                    .setMessage("是否删除该评论？");
            commonDialog.show();
        });
        itemViewHolder.mAdd.setOnClickListener(v -> relayListener.onRelay(itemBean, true));
        itemViewHolder.mLike.setOnClickListener(v -> relayListener.onLike(itemBean));
        itemViewHolder.mUserAvatar.setOnClickListener(v -> {
            if (itemBean.getComment().getAuthor() == UserBaseDetail.getUID(mContext)) {
                Intent intent = new Intent(mContext, SpaceActivity.class);
                intent.putExtra("UID", UserBaseDetail.getUID(mContext));
                mContext.startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, OtherSpaceActivity.class);
                intent.putExtra("UID", itemBean.getComment().getAuthor());
                mContext.startActivity(intent);
            }
        });
        itemViewHolder.mUsername.setOnClickListener(v -> {
            if (itemBean.getComment().getAuthor() == UserBaseDetail.getUID(mContext)) {
                Intent intent = new Intent(mContext, SpaceActivity.class);
                intent.putExtra("UID", UserBaseDetail.getUID(mContext));
                mContext.startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, OtherSpaceActivity.class);
                intent.putExtra("UID", itemBean.getComment().getAuthor());
                mContext.startActivity(intent);
            }
        });
        if (itemBean.getComment().getAuthor() != UserBaseDetail.getUID(mContext)
                && UserBaseDetail.getUID(mContext) != up) {
            itemViewHolder.more.setVisibility(View.GONE);
        }
        itemViewHolder.mUsername.setText(itemBean.getComment().getAuthor_name());
        if (itemBean.getAvatar() != null && itemBean.getAvatar().getFile() != null) {
            String url = AliyunOSSUtil.getImageUrl(mContext.getApplicationContext(), itemBean.getAvatar().getGuest_key(),
                    itemBean.getAvatar().getGuest_secret(), itemBean.getAvatar().getSecurity_token(), itemBean.getAvatar().getFile());
            Glide.with(mContext).load(url).into(itemViewHolder.mUserAvatar);
        } else {
            itemViewHolder.mUserAvatar.setImageDrawable(mContext.getDrawable(AVATAR_IMAGE_DEFAULT));
        }
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
            SpannableString string = new SpannableString(list.get(i).getComment().getAuthor_name()
                    + ": " + list.get(i).getComment().getContent());
            string.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorPrimary)),
                    0, list.get(i).getComment().getAuthor_name().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        ImageView more;
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
            more = itemView.findViewById(R.id.item_comment_more);
        }
    }

}

