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
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dueeeke.videoplayer.player.VideoView;
import com.example.pilipili_android.R;
import com.example.pilipili_android.bean.localbean.CommentItemBean;
import com.example.pilipili_android.fragment.CommentDetailsFragment;
import com.example.pilipili_android.fragment.VideoCommentFragment;
import com.example.pilipili_android.fragment.VideoInfoFragment;
import com.example.pilipili_android.util.AppBarStateChangeListener;
import com.example.pilipili_android.util.DanmukuSelectUtil;
import com.example.pilipili_android.util.SystemBarHelper;
import com.example.pilipili_android.view_model.CommentViewModel;
import com.example.pilipili_android.widget.ExpandMenuView;
import com.example.pilipili_android.widget.PiliPiliDanmakuView;
import com.example.pilipili_android.widget.PiliPiliPlayer;
import com.example.pilipili_android.widget.PiliPiliVideoController;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @BindView(R.id.comment_bar)
    LinearLayout commentBar;
    @BindView(R.id.edit_comment)
    EditText editComment;
    @BindView(R.id.send_comment)
    Button sendComment;

    private int pv;
    private String imgUrl;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private AppBarLayout.LayoutParams mAppBarParams;
    private View mAppBarChildAt;
    private PiliPiliDanmakuView danmakuView;
    private VideoDetailsPagerAdapter mAdapter;
    private CheckBox[] checkBoxes = new CheckBox[9];
    private CommentViewModel commentViewModel;
    private CommentItemBean parent;
    private CommentItemBean temp1;
    private CommentItemBean temp2;
    private CommentItemBean target;
    private boolean targetIsReplay;
    private boolean isReplay1;
    private boolean isReplay2;
    private boolean isDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        pv = intent.getIntExtra("pv", -1);
        pv = 1;
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
        sendComment.setOnClickListener(this);
        editComment.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                isReplay1 = false;
                isReplay2 = false;
                editComment.setText("");
            }
        });
        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isDetails) {
                    target = isReplay2 ? temp2 : parent;
                    targetIsReplay = isReplay2;
                } else {
                    target = temp1;
                    targetIsReplay = isReplay1;
                }
                sendComment.setEnabled((s != null && !s.toString().isEmpty()));
            }
        });
        commentViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(CommentViewModel.class);
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
        VideoCommentFragment fragment = new VideoCommentFragment(pv);
        fragment.setListener(itemBean -> {
            CommentDetailsFragment detailsFragment = new CommentDetailsFragment(itemBean);
            detailsFragment.setRelayListener((new OnRelayListener() {
                @Override
                public void onRelay(CommentItemBean itemBean, boolean isReplay) {
                    if (isReplay) {
                        temp2 = itemBean;
                    }
                    editComment.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    Objects.requireNonNull(imm).showSoftInput(editComment, InputMethodManager.SHOW_IMPLICIT);
                    isReplay2 = isReplay;
                    isDetails = true;
                }

                @Override
                public void onLike(CommentItemBean itemBean) {
                    if (itemBean.getComment().isIs_liked()) {
                        commentViewModel.unlikeComment(itemBean, parent.getComment().getId());
                    } else {
                        commentViewModel.likeComment(itemBean, parent.getComment().getId());
                    }
                }
            }));
            fragments.set(1, detailsFragment);
            isDetails = true;
            parent = itemBean;
            mAdapter.notifyDataSetChanged();
            tabBar.setVisibility(View.GONE);
            replayBar.setVisibility(View.VISIBLE);
            replayBack.setOnClickListener(v -> {
                fragments.set(1, fragment);
                mAdapter.notifyDataSetChanged();
                tabBar.setVisibility(View.VISIBLE);
                replayBar.setVisibility(View.GONE);
                isDetails = false;
            });
        });
        fragment.setRelayListener(new OnRelayListener() {
            @Override
            public void onRelay(CommentItemBean itemBean, boolean isReplay) {
                temp1 = itemBean;
                editComment.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).showSoftInput(editComment, InputMethodManager.SHOW_IMPLICIT);
                isReplay1 = isReplay;
                isDetails = false;
            }

            @Override
            public void onLike(CommentItemBean itemBean) {
                if (itemBean.getComment().isIs_liked()) {
                    commentViewModel.unlikeComment(itemBean, -1);
                } else {
                    commentViewModel.likeComment(itemBean, -1);
                }
            }
        });
        fragments.add(fragment);
        setPagerTitle("1000");
    }

    private void setPagerTitle(String num) {
        titles.add("简介");
        titles.add("评论" + "(" + num + ")");
        mAdapter = new VideoDetailsPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    commentBar.setVisibility(View.GONE);
                } else {
                    commentBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initPlayer() {
        danmakuView = new PiliPiliDanmakuView(this);
        PiliPiliVideoController controller =
                new PiliPiliVideoController(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 30, 0, 40);
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
            case R.id.send_comment:
                if (isDetails) {
                    commentViewModel.detailsReplay(parent.getComment().getId(), target.getComment().getId(), editComment.getText().toString());
                } else {
                    if (targetIsReplay) {
                        commentViewModel.replay(target.getComment().getId(), editComment.getText().toString());
                    } else {
                        commentViewModel.comment(pv, editComment.getText().toString());
                    }
                }
                editComment.setText("");
                editComment.clearFocus();
                sendComment.setEnabled(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
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
        void onOpen(CommentItemBean itemBean);
    }

    public interface OnRelayListener {
        void onRelay(CommentItemBean itemBean, boolean isReplay);
        void onLike(CommentItemBean itemBean);
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
