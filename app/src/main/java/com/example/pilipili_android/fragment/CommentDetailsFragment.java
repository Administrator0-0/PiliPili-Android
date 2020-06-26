package com.example.pilipili_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pilipili_android.R;
import com.example.pilipili_android.adapter.CommentReplayAdapter;
import com.example.pilipili_android.adapter.VideoCommentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDetailsFragment extends Fragment {

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
    @BindView(R.id.comment_add)
    ImageView commentAdd;
    @BindView(R.id.replay_list)
    RecyclerView replayListView;

    private CommentReplayAdapter adapter;
    private List<String> videoList;

    public CommentDetailsFragment() {

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
        adapter = new CommentReplayAdapter(videoList);
        replayListView.setLayoutManager(new LinearLayoutManager(getContext()));
        replayListView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        replayListView.setAdapter(adapter);
        replayListView.setNestedScrollingEnabled(false);

    }

    private void initData() {
        videoList = new ArrayList<>();
        for (int i = 0; i < 50; i++) videoList.add("sss");
    }
}
