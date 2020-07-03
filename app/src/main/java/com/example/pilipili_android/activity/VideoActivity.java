package com.example.pilipili_android.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoView;
import com.example.pilipili_android.R;
import com.example.pilipili_android.bean.localbean.CommentItemBean;
import com.example.pilipili_android.bean.netbean.DanmukuListReturn;
import com.example.pilipili_android.bean.netbean.DanmukuSend;
import com.example.pilipili_android.bean.netbean.VideoDetailReturn;
import com.example.pilipili_android.fragment.CommentDetailsFragment;
import com.example.pilipili_android.fragment.FragmentMsg;
import com.example.pilipili_android.fragment.RewardVideoFragment;
import com.example.pilipili_android.fragment.VideoCommentFragment;
import com.example.pilipili_android.fragment.VideoInfoFragment;
import com.example.pilipili_android.util.AppBarStateChangeListener;
import com.example.pilipili_android.util.DanmukuSelectUtil;
import com.example.pilipili_android.util.SystemBarHelper;
import com.example.pilipili_android.view_model.CommentViewModel;
import com.example.pilipili_android.view_model.DanmukuViewModel;
import com.example.pilipili_android.view_model.VideoViewModel;
import com.example.pilipili_android.widget.ExpandMenuView;
import com.example.pilipili_android.widget.PiliPiliDanmakuView;
import com.example.pilipili_android.widget.PiliPiliPlayer;
import com.example.pilipili_android.widget.PiliPiliVideoController;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private FragmentManager fragmentManager;
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
    private String color;
    private String content;
    private int type;
    private int size;
    private DanmukuSend temp;
    private DanmukuViewModel danmukuViewModel;
    private VideoViewModel videoViewModel;
    private Dialog danmukuDialog;
    private boolean isLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        videoViewModel.setDataBean((VideoDetailReturn.DataBean)intent.getSerializableExtra("dataBean"));
        pv = videoViewModel.getDataBean().getPv();
        videoViewModel.getVideo(pv);
        type = 1;
        color = DanmukuSelectUtil.getColor(Color.WHITE);
        size = 24;

        videoViewModel.getVideoUrl().observe(this, url -> {
            player.setUrl(url);
            player.setPlayerFactory(IjkPlayerFactory.create());
            player.start();
        });

        danmukuViewModel = new ViewModelProvider(this).get(DanmukuViewModel.class);
        initView();
        initToolBar();
        initPage();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentMessage(FragmentMsg fragmentMsg) {
        if(fragmentMsg.getWhatFragment().equals("VideoInfoFragment")) {
            if(fragmentMsg.getMsgString().equals("show")) {
                fragmentManager.beginTransaction().add(R.id.box, new RewardVideoFragment()).commit();
            }
        }
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
        mToolbar.setNavigationOnClickListener(view -> {
            VideoActivity.this.finish();
        });
        //设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        //设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
        mAvText.setText("pv" + pv);
    }

    private void initPage() {
        VideoInfoFragment videoInfoFragment = new VideoInfoFragment();
        fragments.add(videoInfoFragment);
        int up = videoViewModel.getDataBean().getAuthor();
        VideoCommentFragment videoCommentFragment = new VideoCommentFragment(pv, up);
        videoCommentFragment.setListener(itemBean -> {
            CommentDetailsFragment detailsFragment = new CommentDetailsFragment(itemBean, up);
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
                fragments.set(1, videoCommentFragment);
                mAdapter.notifyDataSetChanged();
                tabBar.setVisibility(View.VISIBLE);
                replayBar.setVisibility(View.GONE);
                isDetails = false;
            });
        });
        videoCommentFragment.setRelayListener(new OnRelayListener() {
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
        fragments.add(videoCommentFragment);
        setPagerTitle(videoViewModel.getDataBean().getComments());
    }

    private void setPagerTitle(int num) {
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
        controller.addDefaultControlComponent(videoViewModel.getDataBean().getTitle(), danmuku -> {
            danmuku.setTime(danmakuView.getCurrentTime() + 1200);
            temp = danmuku;
            danmukuViewModel.postDanmuku(pv, danmuku);
        });
        controller.addControlComponent(danmakuView);
        player.setVideoController(controller);
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
                if (!isLoaded) {
                    danmukuViewModel.getDanmuku(pv);
                    isLoaded = true;
                }
                mAppBarChildAt = mAppBarLayout.getChildAt(0);
                mAppBarParams = (AppBarLayout.LayoutParams)mAppBarChildAt.getLayoutParams();
                mAppBarParams.setScrollFlags(0);
                mAppBarLayout.setExpanded(true);
            }
        });
        danmukuViewModel.getDanmukuList().observe(this, dataBean -> {
            if (temp != null) {
                danmakuView.addDanmaku(temp, true);
                temp = null;
            } else {
                if (dataBean.getAll_danmuku() != null) {
                    for (DanmukuListReturn.DataBean.AllDanmukuBean bean : dataBean.getAll_danmuku()) {
                        DanmukuSend send = new DanmukuSend();
                        send.setTime(bean.getTime());
                        send.setSize(bean.getSize());
                        send.setContent(bean.getContent());
                        send.setType(bean.getType());
                        send.setColor(bean.getColor());
                        send.setBackground(bean.getBackground());
                        danmakuView.addDanmaku(send, false);
                    }
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
        EventBus.getDefault().unregister(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_danmuku_vertical:
                showBottomDialog();
                break;
            case R.id.send:
                DanmukuSend danmuku = new DanmukuSend();
                danmuku.setType(type);
                danmuku.setContent(content);
                danmuku.setColor(color);
                danmuku.setSize(size);
                danmuku.setTime(danmakuView.getCurrentTime() + 1200);
                temp = danmuku;
                danmukuViewModel.postDanmuku(pv, danmuku);
                danmukuDialog.dismiss();
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
        danmukuDialog = new Dialog(this, R.style.DialogTheme);
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
                    color = DanmukuSelectUtil.getColor(checkBox.getId());
                }
            });
        }
        EditText edit = view.findViewById(R.id.input_danmuku);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString();
            }
        });
        CheckBox smallSize;
        CheckBox largeSize;
        CheckBox top;
        CheckBox scroll;
        CheckBox bottom;
        smallSize = view.findViewById(R.id.small_size);
        largeSize = view.findViewById(R.id.large_size);
        smallSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                size = DanmukuSelectUtil.getSize(R.id.small_size);
                largeSize.setChecked(false);
            }
        });
        largeSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                size = DanmukuSelectUtil.getSize(R.id.large_size);
                smallSize.setChecked(false);
            }
        });
        top = view.findViewById(R.id.top_type);
        bottom = view.findViewById(R.id.bottom_type);
        scroll = view.findViewById(R.id.scroll_type);
        top.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                type = 5;
                bottom.setChecked(false);
                scroll.setChecked(false);
            }
        });
        bottom.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                type = 4;
                top.setChecked(false);
                scroll.setChecked(false);
            }
        });
        scroll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                type = 1;
                top.setChecked(false);
                bottom.setChecked(false);
            }
        });
        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener((v) -> danmukuDialog.dismiss());
        danmukuDialog.setContentView(view);
        Window window = danmukuDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.main_menu_animStyle);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        ImageView send = view.findViewById(R.id.send);
        send.setOnClickListener(this);
        danmukuDialog.show();
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
        void onSend(DanmukuSend danmuku);
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

}
