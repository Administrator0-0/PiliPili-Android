package com.example.pilipili_android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pilipili_android.R;
import com.example.pilipili_android.adapter.RecommendVideoAdapter;
import com.example.pilipili_android.view_model.VideoViewModel;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class RecommendFragment extends Fragment {

    private RecyclerView recyclerView;
    private VideoViewModel videoViewModel;
    private RecommendVideoAdapter recommendVideoAdapter;
    private RefreshLayout refreshLayout;
    private StoreHouseHeader refreshHeader;
    private boolean isThis = true;

    public RecommendFragment() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        videoViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(VideoViewModel.class);
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recommendVideoAdapter = new RecommendVideoAdapter(getContext());
        recommendVideoAdapter.setDataBeanList(videoViewModel.getRecommendVideoBeans().getValue());
        recyclerView.setAdapter(recommendVideoAdapter);
        if(videoViewModel.getRecommendVideoBeans().getValue().size() == 0){
            videoViewModel.getRecommendVideoDetail();
        }

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshHeader = new StoreHouseHeader(Objects.requireNonNull(getContext()));
        refreshHeader.initWithString("PiliPili");
        refreshHeader.setLineWidth(3);
        refreshHeader.setTextColor(getContext().getColor(R.color.white));
        refreshLayout.setRefreshHeader(refreshHeader);

        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            videoViewModel.getRecommendVideoDetail();
            refreshLayout.autoRefresh();
        });

        videoViewModel.getRecommendVideoBeans().observe(getActivity(), isFinish -> {
            recommendVideoAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        isThis = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isThis = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isThis = false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentChange(FragmentMsg fragmentMsg) {
        if(fragmentMsg.getWhatFragment().equals("MainActivity")) {
            if(fragmentMsg.getMsgString().equals("refresh")) {
                if(isThis) {
                    videoViewModel.getRecommendVideoDetail();
                    refreshLayout.autoRefresh();
                }
            }
        }
    }
}