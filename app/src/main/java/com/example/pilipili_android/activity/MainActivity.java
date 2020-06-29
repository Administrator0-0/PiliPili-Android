package com.example.pilipili_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.UrlConstant;
import com.example.pilipili_android.fragment.ChannelFragment;
import com.example.pilipili_android.fragment.FragmentMsg;
import com.example.pilipili_android.fragment.MainFragment;
import com.example.pilipili_android.fragment.MineFragment;
import com.example.pilipili_android.fragment.PostFragment;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_UPLOAD = 12321;

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_UPLOAD && resultCode == RESULT_OK) {
            List<String> pathList = Matisse.obtainPathResult(Objects.requireNonNull(data));
            Intent intent = new Intent(MainActivity.this, UploadVideoActivity.class);
            intent.putExtra("path", pathList.get(0));
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentChange(FragmentMsg fragmentMsg) {
        if(fragmentMsg.getWhatFragment().equals("UploadVideoActivity")) {
            if(fragmentMsg.getMsgString().equals("showMine")) {
                fragmentManager.beginTransaction().replace(R.id.box, mineFragment).commitAllowingStateLoss();
            }
        }
    }
}
