package com.example.pilipili_android.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.adapter.VideoCommentAdapter;
import com.example.pilipili_android.bean.localbean.CommentItemBean;
import com.example.pilipili_android.bean.netbean.CommentDetailsReturn;
import com.example.pilipili_android.view_model.CommentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoCommentFragment extends Fragment {

    @BindView(R.id.comment_list)
    RecyclerView videoListView;

    private int pv;
    private int up;
    private VideoCommentAdapter adapter;
    private CommentViewModel commentViewModel;
    private VideoActivity.OnRelayOpenListener mListener;
    private VideoActivity.OnRelayListener relayListener;

    public VideoCommentFragment(int pv, int up) {
        this.pv = pv;
        this.up = up;
    }

    public void setListener(VideoActivity.OnRelayOpenListener mListener) {
        this.mListener = mListener;
    }

    public void setRelayListener(VideoActivity.OnRelayListener relayListener) {
        this.relayListener = relayListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_comment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initData();
        adapter = new VideoCommentAdapter(commentViewModel.getCommentList().getValue(), commentViewModel.getReplayList().getValue(), up);
        adapter.setListener(mListener);
        adapter.setRelayListener(relayListener);
        adapter.setOnDeleteListener(itemBean -> {
            commentViewModel.delete(itemBean.getComment().getId(), -1, false, itemBean);
        });
        videoListView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        videoListView.setAdapter(adapter);
        videoListView.requestDisallowInterceptTouchEvent(true);

    }

    private void initData() {
        commentViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CommentViewModel.class);
        commentViewModel.getCommentList().observe(getViewLifecycleOwner(), dataBeans -> {
            adapter.setComments(dataBeans);
            adapter.notifyDataSetChanged();
        });
        commentViewModel.getReplayList().observe(getViewLifecycleOwner(), dataBeans -> {
            adapter.setReplays(dataBeans);
            adapter.notifyDataSetChanged();
        });
        commentViewModel.getCommentList(pv, 1);
    }

    public interface OnDeleteListener {
        void onDelete(CommentItemBean itemBean);
    }

}
