package com.example.pilipili_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pilipili_android.R;
import com.example.pilipili_android.adapter.UserFollowAdapter;
import com.example.pilipili_android.bean.netbean.ListFollowReturn;
import com.example.pilipili_android.view_model.UserViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FanListFragment extends Fragment {

    @BindView(R.id.fan_list)
    RecyclerView fan;
    private boolean isFollow;
    private int id;
    private UserFollowAdapter adapter;
    private UserViewModel userViewModel;
    private boolean isLoaded;

    public FanListFragment(int id, boolean isFollow) {
        this.id = id;
        this.isFollow = isFollow;
    }

    @Override
    public void onResume() {
        if (!isLoaded)
            loadData();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fan_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initData() {
        userViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(UserViewModel.class);
        if (isFollow) {
            userViewModel.getFanList().observe(getViewLifecycleOwner(), listBeans -> {
                adapter.setUserList(listBeans);
                adapter.notifyDataSetChanged();
            });
        } else {
            userViewModel.getFollowedList().observe(getViewLifecycleOwner(), listBeans -> {
                adapter.setUserList(listBeans);
                adapter.notifyDataSetChanged();
            });
        }
    }

    private void initView() {
        initData();
        if (isFollow) {
            adapter = new UserFollowAdapter(userViewModel.getFanList().getValue(), onFollowOrUnFollow);
        } else {
            adapter = new UserFollowAdapter(userViewModel.getFollowedList().getValue(), onFollowOrUnFollow);
        }
        fan.setLayoutManager(new LinearLayoutManager(getContext()));
        fan.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        fan.setAdapter(adapter);
    }

    private void loadData() {
        if (isFollow) {
            userViewModel.listFan(id);
        } else {
            userViewModel.listFollowings(id);
        }
        isLoaded = true;
    }

    private OnFollowOrUnFollow onFollowOrUnFollow = new OnFollowOrUnFollow() {
        @Override
        public void onFollow(ListFollowReturn.DataBean.ListBean bean) {
            userViewModel.follow(bean);
        }

        @Override
        public void onUnFollow(ListFollowReturn.DataBean.ListBean bean) {
            userViewModel.unFollow(bean);
        }
    };

    public interface OnFollowOrUnFollow {
        void onFollow(ListFollowReturn.DataBean.ListBean bean);
        void onUnFollow(ListFollowReturn.DataBean.ListBean bean);
    }
}
