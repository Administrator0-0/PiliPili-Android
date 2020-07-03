package com.example.pilipili_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.adapter.CommentReplayAdapter;
import com.example.pilipili_android.adapter.VideoCommentAdapter;
import com.example.pilipili_android.bean.localbean.CommentItemBean;
import com.example.pilipili_android.util.AliyunOSSUtil;
import com.example.pilipili_android.view_model.CommentViewModel;
import com.example.pilipili_android.view_model.UserBaseDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDetailsFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.user_avatar)
    ImageView user;
    @BindView(R.id.user_name)
    TextView username;
    @BindView(R.id.comment_time)
    TextView commentTime;
    @BindView(R.id.comment_content)
    TextView commentContent;
    @BindView(R.id.comment_like)
    ImageView commentLike;
    @BindView(R.id.comment_like_num)
    TextView commentLikeNum;
    @BindView(R.id.replay_list)
    RecyclerView replayListView;

    private CommentReplayAdapter adapter;
    private CommentViewModel commentViewModel;
    private CommentItemBean main;
    private VideoActivity.OnRelayListener relayListener;
    private int up;

    public CommentDetailsFragment(CommentItemBean main, int up) {
        this.main = main;
        this.up = up;
    }

    public void setRelayListener(VideoActivity.OnRelayListener relayListener) {
        this.relayListener = relayListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_details, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initData();
        adapter = new CommentReplayAdapter(main, Objects.requireNonNull(commentViewModel.getReplayList().getValue()), up);
        adapter.setRelayListener(relayListener);
        adapter.setOnDeleteListener(itemBean -> commentViewModel.delete(itemBean.getComment().getId(), main.getComment().getId(), true, itemBean));
        replayListView.setLayoutManager(new LinearLayoutManager(getContext()));
        replayListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        replayListView.setAdapter(adapter);
        replayListView.setNestedScrollingEnabled(false);
        commentLike.setOnClickListener(this);
    }

    private void initData() {
        commentViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CommentViewModel.class);
        commentViewModel.getReplayList().observe(getViewLifecycleOwner(), dataBeans -> {
            adapter.setReplays(dataBeans);
            adapter.notifyDataSetChanged();
            if (main.getComment().isIs_liked()) {
                commentLike.setImageResource(R.drawable.mall_ic_liked);
            } else {
                commentLike.setImageResource(R.drawable.mall_ic_like);
            }
        });
        commentViewModel.getCommentList().observe(getViewLifecycleOwner(), dataBeans -> {
            if (main.getComment().isIs_liked()) {
                commentLike.setImageResource(R.drawable.mall_ic_liked);
            } else {
                commentLike.setImageResource(R.drawable.mall_ic_like);
            }
        });
        commentViewModel.getReplayListDFS(main.getComment().getId());
        String url = AliyunOSSUtil.getImageUrl(getActivity().getApplicationContext(), main.getAvatar().getGuest_key(),
                main.getAvatar().getGuest_secret(), main.getAvatar().getSecurity_token(), main.getAvatar().getFile());
        Glide.with(getActivity()).load(url).into(user);
        username.setText(main.getComment().getAuthor_name());
        commentTime.setText(main.getComment().getAuthor_name());
        commentContent.setText(main.getComment().getContent());
        commentLikeNum.setText("" + main.getComment().getLikes());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.comment_like) {
            if (main.getComment().isIs_liked()) {
                commentViewModel.unlikeComment(main, -1);
            } else {
                commentViewModel.likeComment(main, -1);
            }
        }
    }

    public interface OnDeleteListener {
        void onDelete(CommentItemBean itemBean);
    }
}
