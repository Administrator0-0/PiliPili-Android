package com.example.pilipili_android.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videoplayer.player.VideoView;
import com.example.pilipili_android.R;
import com.example.pilipili_android.fragment.CommentDetailsFragment;
import com.example.pilipili_android.fragment.VideoCommentFragment;
import com.example.pilipili_android.fragment.VideoInfoFragment;
import com.example.pilipili_android.util.AppBarStateChangeListener;
import com.example.pilipili_android.util.DanmukuSelectUtil;
import com.example.pilipili_android.util.SystemBarHelper;
import com.example.pilipili_android.widget.ExpandMenuView;
import com.example.pilipili_android.widget.PiliPiliDanmakuView;
import com.example.pilipili_android.widget.PiliPiliPlayer;
import com.example.pilipili_android.widget.PiliPiliVideoController;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tv_player)
    TextView mTvPlayer;
    @BindView(R.id.tv_pv)
    TextView mAvText;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.player)
    PiliPiliPlayer player;
    @BindView(R.id.send_danmuku_vertical)
    Button send_danmuku;
    @BindView(R.id.expand)
    ExpandMenuView expandMenuView;
    @BindView(R.id.replay_bar)
    LinearLayout replayBar;
    @BindView(R.id.tab_bar)
    LinearLayout tabBar;
    @BindView(R.id.button_back)
    ImageView replayBack;
    @BindView(R.id.root_layout)
    RelativeLayout root;

    private int pv;
    private String imgUrl;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private boolean isPlay;
    private boolean isPause;
    private boolean isSamll;
    private AppBarLayout.LayoutParams mAppBarParams;
    private View mAppBarChildAt;
    private PiliPiliDanmakuView danmakuView;
    private VideoDetailsPagerAdapter mAdapter;
    private CheckBox[] checkBoxes = new CheckBox[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        initView();
        initToolBar();
        initPage();
    }

    private AppBarStateChangeListener appBarStateChangeListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            if (state == State.EXPANDED) {
                //展开状态
                mTvPlayer.setVisibility(View.GONE);
                mAvText.setVisibility(View.GONE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else if (state == State.COLLAPSED) {
                //折叠状态
                mTvPlayer.setVisibility(View.VISIBLE);
                mAvText.setVisibility(View.GONE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                mTvPlayer.setVisibility(View.GONE);
                mAvText.setVisibility(View.GONE);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    };


    private void initView() {
        mAppBarLayout.addOnOffsetChangedListener(appBarStateChangeListener);
        initPlayer();
        send_danmuku.setOnClickListener(this);
        expandMenuView.setOnDanmukuCloseListener(isExpand -> {
            if (isExpand) {
                danmakuView.show();
            } else {
                danmakuView.hide();
            }
        });

    }

    public void initToolBar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(false);
        }
        //设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        //设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
        mAvText.setText("pv" + pv);
    }

    private void initPage() {
        fragments.add(new VideoInfoFragment());
        VideoCommentFragment fragment = new VideoCommentFragment();
        fragment.setListener(position -> {
            fragments.set(1, new CommentDetailsFragment());
            mAdapter.notifyDataSetChanged();
            tabBar.setVisibility(View.GONE);
            replayBar.setVisibility(View.VISIBLE);
            replayBack.setOnClickListener(v -> {
                fragments.set(1, fragment);
                mAdapter.notifyDataSetChanged();
                tabBar.setVisibility(View.VISIBLE);
                replayBar.setVisibility(View.GONE);
            });
        });
        fragments.add(fragment);
        setPagerTitle("1000");
    }

    private void setPagerTitle(String num) {
        titles.add("简介");
        titles.add("评论" + "(" + num + ")");
        mAdapter = new VideoDetailsPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initPlayer() {
        danmakuView = new PiliPiliDanmakuView(this);
        PiliPiliVideoController controller =
                new PiliPiliVideoController(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 25, 0, 40);
        danmakuView.setLayoutParams(layoutParams);
        controller.addDefaultControlComponent("刘薪王太强了", () -> danmakuView.addDanmaku("xxxxx", true));
        controller.addControlComponent(danmakuView);
        player.setVideoController(controller);
        player.setUrl("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4");
        player.setListener(new OnVideoListener() {
            @Override
            public void onPause() {
                if (mAppBarParams != null && mAppBarChildAt != null) {
                    mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                            | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                            | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                    mAppBarChildAt.setLayoutParams(mAppBarParams);
                }
            }

            @Override
            public void onStart() {
                mAppBarChildAt = mAppBarLayout.getChildAt(0);
                mAppBarParams = (AppBarLayout.LayoutParams)mAppBarChildAt.getLayoutParams();
                mAppBarParams.setScrollFlags(0);
                mAppBarLayout.setExpanded(true);
            }
        });
        player.start();
        player.addOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_PREPARED) {
                    simulateDanmu();
                } else if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                    mHandler.removeCallbacksAndMessages(null);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        mAppBarLayout.addOnOffsetChangedListener(appBarStateChangeListener);
        super.onResume();
        if (player != null) {
            player.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        if (player != null) {
            player.release();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }


    @Override
    public void onBackPressed() {
        if (player == null || !player.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.tv_player)
    void play() {
       mAppBarLayout.setExpanded(true);
       player.resume();
    }

    public void showDanMu(View view) {
        danmakuView.show();
    }

    public void hideDanMu(View view) {
        danmakuView.hide();
    }

    public void addDanmakuWithDrawable(View view) {
        danmakuView.addDanmakuWithDrawable();
    }

    public void addDanmaku(View view) {
        danmakuView.addDanmaku("这是来自刘薪王的意念弹幕", true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_danmuku_vertical:
                showBottomDialog();
                break;
            case R.id.send:
                danmakuView.addDanmaku("ssss", true);
                break;
        }
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        View view = View.inflate(this, R.layout.danmuku_dialog_layout, null);
        checkBoxes[0] = view.findViewById(R.id.white_checkbox);
        checkBoxes[1] = view.findViewById(R.id.blue_green_checkbox);
        checkBoxes[2] = view.findViewById(R.id.brown_checkbox);
        checkBoxes[3] = view.findViewById(R.id.green_checkbox);
        checkBoxes[4] = view.findViewById(R.id.yellow_checkbox);
        checkBoxes[5] = view.findViewById(R.id.red_checkbox);
        checkBoxes[6] = view.findViewById(R.id.pink_checkbox);
        checkBoxes[7] = view.findViewById(R.id.blue_checkbox);
        checkBoxes[8] = view.findViewById(R.id.purple_checkbox);
        checkBoxes[0].setChecked(true);
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    for (CheckBox checkBoxOther : checkBoxes) {
                        if (checkBoxOther != checkBox) {
                            checkBoxOther.setChecked(false);
                        }
                    }
                    String color = DanmukuSelectUtil.getColor(checkBox.getId());
                }
            });
        }
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.main_menu_animStyle);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        ImageView send = view.findViewById(R.id.send);
        send.setOnClickListener(this);
        dialog.show();
    }


    public static class VideoDetailsPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;
        private List<String> titles;

        VideoDetailsPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
        private FragmentManager fm;
        private boolean[] flags;//标识,重新设置fragment时全设为true

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (flags != null && flags[position]) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                String fragmentTag = fragment.getTag();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
                fragment = fragments.get(position);
                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commit();
                flags[position] = false;
                return fragment;
            } else {
                return super.instantiateItem(container, position);
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

     public interface OnVideoListener {
        void onPause();
        void onStart();
     }

    public interface OnDanmukuListener {
        void onSend();
    }

    public interface OnDanmukuCloseListener {
        void onClose(boolean isExpand);
    }

    public interface OnRelayOpenListener {
        void onOpen(int position);
    }

     private Handler mHandler = new Handler();

    /**
     * 模拟弹幕
     */
    private void simulateDanmu() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                danmakuView.addDanmaku("刘薪王太强了", false);
                mHandler.postDelayed(this, 100);
            }
        });
    }
}
