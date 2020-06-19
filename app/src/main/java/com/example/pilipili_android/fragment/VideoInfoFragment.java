package com.example.pilipili_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pilipili_android.R;
import com.example.pilipili_android.adapter.VideoRelatedAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoInfoFragment extends Fragment {

    @BindView(R.id.video_list)
    RecyclerView videoListView;

    private List<String> videoList;
    private VideoRelatedAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoList = new ArrayList<>();
        for (int i = 0; i < 50; i++) videoList.add("sss");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_info, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        adapter = new VideoRelatedAdapter(videoList);
        videoListView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        videoListView.setAdapter(adapter);

    }
}
