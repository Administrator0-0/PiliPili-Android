package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.adapter.AnimeVideoAdapter;
import com.example.pilipili_android.adapter.HotVideoAdapter;
import com.example.pilipili_android.view_model.VideoViewModel;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class AnimeFragment extends Fragment {

    private RecyclerView recyclerView;
    private VideoViewModel videoViewModel;
    private AnimeVideoAdapter animeVideoAdapter;
    private RefreshLayout refreshLayout;
    private StoreHouseHeader refreshHeader;
    private boolean isThis = true;

    public AnimeFragment() {

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
        View view = inflater.inflate(R.layout.fragment_anime, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        animeVideoAdapter = new AnimeVideoAdapter(getContext(), pv -> {
            videoViewModel.getVideoDetail(pv, 3);
        });
        animeVideoAdapter.setDataBeanList(videoViewModel.getAnimeVideoBeans().getValue());
        recyclerView.setAdapter(animeVideoAdapter);
        if(videoViewModel.getAnimeVideoBeans().getValue().size() == 0){
            videoViewModel.getAnimeVideoDetail();
        }

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshHeader = new StoreHouseHeader(Objects.requireNonNull(getContext()));
        refreshHeader.initWithString("PiliPili");
        refreshHeader.setLineWidth(3);
        refreshHeader.setTextColor(getContext().getColor(R.color.white));
        refreshLayout.setRefreshHeader(refreshHeader);

        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            videoViewModel.getAnimeVideoDetail();
            refreshLayout.autoRefresh();
        });

        videoViewModel.getAnimeVideoBeans().observe(getActivity(), isFinish -> {
            animeVideoAdapter.setDataBeanList(videoViewModel.getAnimeVideoBeans().getValue());
            animeVideoAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
        });

        videoViewModel.getVideoDetailBeanFromAnime().observe(getActivity(), dataBean -> {
            Intent intent = new Intent(getActivity(), VideoActivity.class);
            intent.putExtra("dataBean", dataBean);
            getContext().startActivity(intent);
        });

        return view;
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

    @Override
    public void onStart() {
        super.onStart();
        isThis = true;
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