package com.example.pilipili_android.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoCommentFragment extends Fragment {

    @BindView(R.id.comment_list)
    RecyclerView videoListView;

    private List<String> videoList;
    private VideoCommentAdapter adapter;
    private List<List<SpannableString>> replays;
    private VideoActivity.OnRelayOpenListener mListener;

    public VideoCommentFragment() {

    }

    public void setListener(VideoActivity.OnRelayOpenListener mListener) {
        this.mListener = mListener;
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
        adapter = new VideoCommentAdapter(videoList, replays);
        adapter.setListener(mListener);
        videoListView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        videoListView.setAdapter(adapter);
        videoListView.requestDisallowInterceptTouchEvent(true);

    }

    private void initData() {
        videoList = new ArrayList<>();
        for (int i = 0; i < 50; i++) videoList.add("sss");
        replays = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            List<SpannableString>list = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                SpannableString string = new SpannableString("刘薪王分身: 没错没错没错没错没错没错没错没错");
                string.setSpan(new ForegroundColorSpan(getActivity().getColor(R.color.colorPrimary)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                list.add(string);
            }
            replays.add(list);
        }
    }
}
