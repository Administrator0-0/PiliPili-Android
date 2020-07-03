package com.example.pilipili_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pilipili_android.R;
import com.example.pilipili_android.view_model.UserBaseDetail;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class RewardVideoFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.p_coin_left)
    TextView pCoinLeft;

    public RewardVideoFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward_video, container, false);
        unbinder = ButterKnife.bind(this, view);

        pCoinLeft.setText("P币余额：" + UserBaseDetail.getCoin(getContext()));
        return view;
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @OnClick(R.id.blue1)
    public void onBlue1Clicked() {
        if(UserBaseDetail.getCoin(getContext()) < 1) {
            Toast.makeText(getContext(), "投币失败，余额不足~", Toast.LENGTH_SHORT).show();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else {
            EventBus.getDefault().post(FragmentMsg.getInstance("RewardVideoFragment", "1"));
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
    }

    @OnClick(R.id.blue2)
    public void onBlue2Clicked() {
        if(UserBaseDetail.getCoin(getContext()) < 2) {
            Toast.makeText(getContext(), "投币失败，余额不足~", Toast.LENGTH_SHORT).show();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else {
            EventBus.getDefault().post(FragmentMsg.getInstance("RewardVideoFragment", "2"));
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
    }
}
