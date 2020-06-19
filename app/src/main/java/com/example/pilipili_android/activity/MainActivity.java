package com.example.pilipili_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pilipili_android.R;
import com.example.pilipili_android.fragment.ChannelFragment;
import com.example.pilipili_android.fragment.MainFragment;
import com.example.pilipili_android.fragment.MineFragment;
import com.example.pilipili_android.fragment.PostFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    MainFragment mainFragment;//首页
    PostFragment postFragment;//动态
    ChannelFragment channelFragment;//频道
    MineFragment mineFragment;//我的

    @BindView(R.id.mainpage_btn)
    LinearLayout mainpageBtn;
    @BindView(R.id.channel_btn)
    LinearLayout channelBtn;
    @BindView(R.id.post_btn)
    LinearLayout postBtn;
    @BindView(R.id.mine_btn)
    LinearLayout mineBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();

    }

    private void initFragment(){
        mainFragment = new MainFragment();
        postFragment = new PostFragment();
        channelFragment = new ChannelFragment();
        mineFragment = new MineFragment();
        fragmentManager.beginTransaction().add(R.id.box, mainFragment).commit();
    }


    @OnClick(R.id.mainpage_btn)
    public void onMainpageBtnClicked() {
        fragmentManager.beginTransaction().replace(R.id.box, mainFragment).commit();
    }

    @OnClick(R.id.channel_btn)
    public void onChannelBtnClicked() {
        fragmentManager.beginTransaction().replace(R.id.box, channelFragment).commit();
    }

    @OnClick(R.id.post_btn)
    public void onPostBtnClicked() {
        fragmentManager.beginTransaction().replace(R.id.box, postFragment).commit();
    }

    @OnClick(R.id.mine_btn)
    public void onMineBtnClicked() {
        fragmentManager.beginTransaction().replace(R.id.box, mineFragment).commit();
    }
}
