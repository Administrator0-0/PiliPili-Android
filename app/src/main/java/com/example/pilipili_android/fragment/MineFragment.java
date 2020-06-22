package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.PayActivity;
import com.example.pilipili_android.activity.SpaceActivity;
import com.example.pilipili_android.databinding.FragmentMineBinding;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineFragment extends Fragment {

    private Unbinder unbinder;
    private FragmentMineBinding fragmentMineBinding;

    public MineFragment() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        View view = fragmentMineBinding.getRoot();
        fragmentMineBinding.setUserViewModel(new ViewModelProvider(MineFragment.this).get(UserViewModel.class));
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String ddl = UserBaseDetail.getVIPDeadline(getContext());
        if(ddl == null) {
            fragmentMineBinding.setVIPDeadline("");
            fragmentMineBinding.setVIPShow("成为大会员");
        } else {
            fragmentMineBinding.setVIPDeadline(ddl + "到期");
            fragmentMineBinding.setVIPShow("PiliPili大会员");
        }
    }

    @OnClick(R.id.space_btn)
    void onMySpaceClicked() {
        Intent intent = new Intent(getActivity(), SpaceActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_coin)
    void onBuyCoinClicked() {
        Intent intent = new Intent(getActivity(), PayActivity.class);
        startActivity(intent);
    }
}