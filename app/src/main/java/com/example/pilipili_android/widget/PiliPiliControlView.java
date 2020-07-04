package com.example.pilipili_android.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.dueeeke.videoplayer.controller.ControlWrapper;
import com.dueeeke.videoplayer.controller.IControlComponent;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;
import com.example.pilipili_android.bean.netbean.DanmukuSend;
import com.example.pilipili_android.util.DanmukuSelectUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import static com.dueeeke.videoplayer.util.PlayerUtils.stringForTime;

public class PiliPiliControlView extends FrameLayout implements IControlComponent, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    protected ControlWrapper mControlWrapper;

    private TextView mTotalTime, mCurrTime;
    private ImageView mFullScreen;
    private LinearLayout mBottomContainer;
    private SeekBar mVideoProgress;
    private ProgressBar mBottomProgress;
    private ImageView mPlayButton;
    private QMUIRoundButton mOpenDanmuku;
    private RelativeLayout mDanmukuBar;
    private EditText mEdit;
    private ImageView mBack;
    private ImageView mSend;
    private CheckBox[] checkBoxes;
    private CheckBox smallSize;
    private CheckBox largeSize;
    private CheckBox top;
    private CheckBox scroll;
    private CheckBox bottom;

    private boolean mIsDragging;

    private boolean mIsShowBottomProgress = true;

    private boolean isFull;

    private String color;
    private int type;
    private int size;

    private VideoActivity.OnDanmukuListener mDanmukuListener;


    public PiliPiliControlView(@NonNull Context context) {
        super(context);
    }

    public PiliPiliControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PiliPiliControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
        mFullScreen = findViewById(R.id.fullscreen);
        mFullScreen.setOnClickListener(this);
        mBottomContainer = findViewById(R.id.bottom_container);
        mVideoProgress = findViewById(R.id.seekBar);
        mVideoProgress.setOnSeekBarChangeListener(this);
        mTotalTime = findViewById(R.id.total_time);
        mCurrTime = findViewById(R.id.curr_time);
        mPlayButton = findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(this);
        mBottomProgress = findViewById(R.id.bottom_progress);
        checkBoxes = new CheckBox[9];
        color = DanmukuSelectUtil.getColor(Color.WHITE);
        type = 1;
        size = 24;
    }

    protected int getLayoutId() {
        return R.layout.layout_pilipili_control_view_small;
    }

    /**
     * 是否显示底部进度条，默认显示
     */
    public void showBottomProgress(boolean isShow) {
        mIsShowBottomProgress = isShow;
    }

    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {
        if (isVisible) {
            mBottomContainer.setVisibility(VISIBLE);
            if (anim != null) {
                mBottomContainer.startAnimation(anim);
            }
            if (mIsShowBottomProgress) {
                mBottomProgress.setVisibility(GONE);
            }
        } else {
            mBottomContainer.setVisibility(GONE);
            if (anim != null) {
                mBottomContainer.startAnimation(anim);
            }
            if (mIsShowBottomProgress) {
                mBottomProgress.setVisibility(VISIBLE);
                AlphaAnimation animation = new AlphaAnimation(0f, 1f);
                animation.setDuration(300);
                mBottomProgress.startAnimation(animation);
            }
        }
    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoView.STATE_IDLE:
            case VideoView.STATE_PLAYBACK_COMPLETED:
                setVisibility(GONE);
                mBottomProgress.setProgress(0);
                mBottomProgress.setSecondaryProgress(0);
                mVideoProgress.setProgress(0);
                mVideoProgress.setSecondaryProgress(0);
                break;
            case VideoView.STATE_START_ABORT:
            case VideoView.STATE_PREPARING:
            case VideoView.STATE_PREPARED:
            case VideoView.STATE_ERROR:
                setVisibility(GONE);
                break;
            case VideoView.STATE_PLAYING:
                mPlayButton.setSelected(true);
                if (mIsShowBottomProgress) {
                    if (mControlWrapper.isShowing()) {
                        mBottomProgress.setVisibility(GONE);
                        mBottomContainer.setVisibility(VISIBLE);
                    } else {
                        mBottomContainer.setVisibility(GONE);
                        mBottomProgress.setVisibility(VISIBLE);
                    }
                } else {
                    mBottomContainer.setVisibility(GONE);
                }
                setVisibility(VISIBLE);
                //开始刷新进度
                mControlWrapper.startProgress();
                break;
            case VideoView.STATE_PAUSED:
                mPlayButton.setSelected(false);
                break;
            case VideoView.STATE_BUFFERING:
            case VideoView.STATE_BUFFERED:
                mPlayButton.setSelected(mControlWrapper.isPlaying());
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                mFullScreen.setSelected(false);
                isFull = false;
                changeControlView();
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                mFullScreen.setSelected(true);
                isFull = true;
                changeControlView();
                break;
        }

        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity != null && mControlWrapper.hasCutout()) {
            int orientation = activity.getRequestedOrientation();
            int cutoutHeight = mControlWrapper.getCutoutHeight();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                mBottomContainer.setPadding(0, 0, 0, 0);
                mBottomProgress.setPadding(0, 0, 0, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                mBottomContainer.setPadding(cutoutHeight, 0, 0, 0);
                mBottomProgress.setPadding(cutoutHeight, 0, 0, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                mBottomContainer.setPadding(0, 0, cutoutHeight, 0);
                mBottomProgress.setPadding(0, 0, cutoutHeight, 0);
            }
        }
    }

    @Override
    public void setProgress(int duration, int position) {
        if (mIsDragging) {
            return;
        }

        if (mVideoProgress != null) {
            if (duration > 0) {
                mVideoProgress.setEnabled(true);
                int pos = (int) (position * 1.0 / duration * mVideoProgress.getMax());
                mVideoProgress.setProgress(pos);
                mBottomProgress.setProgress(pos);
            } else {
                mVideoProgress.setEnabled(false);
            }
            int percent = mControlWrapper.getBufferedPercentage();
            if (percent >= 95) { //解决缓冲进度不能100%问题
                mVideoProgress.setSecondaryProgress(mVideoProgress.getMax());
                mBottomProgress.setSecondaryProgress(mBottomProgress.getMax());
            } else {
                mVideoProgress.setSecondaryProgress(percent * 10);
                mBottomProgress.setSecondaryProgress(percent * 10);
            }
        }

        if (mTotalTime != null)
            mTotalTime.setText(stringForTime(duration));
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime(position));
    }

    @Override
    public void onLockStateChanged(boolean isLocked) {
        onVisibilityChanged(!isLocked, null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fullscreen:
                toggleFullScreen();
                break;
            case R.id.iv_play:
                mControlWrapper.togglePlay();
                break;
            case R.id.send_danmuku:
                if (mDanmukuBar != null) {
                    mDanmukuBar.setVisibility(VISIBLE);
                    AlphaAnimation animation = new AlphaAnimation(0f, 1f);
                    animation.setDuration(300);
                    mDanmukuBar.startAnimation(animation);
                }
                break;
            case R.id.back:
                if (mDanmukuBar != null) {
                    mDanmukuBar.setVisibility(GONE);
                }
                break;
            case R.id.send:
                DanmukuSend danmuku = new DanmukuSend();
                danmuku.setType(type);
                danmuku.setContent(mEdit.getText().toString());
                danmuku.setColor(color);
                danmuku.setSize(size);
                mDanmukuListener.onSend(danmuku);
                mDanmukuBar.setVisibility(GONE);
                break;
        }
    }

    /**
     * 横竖屏切换
     */
    private void toggleFullScreen() {
        Activity activity = PlayerUtils.scanForActivity(getContext());
        mControlWrapper.toggleFullScreen(activity);
    }

    public void changeControlView() {
        int progress = mVideoProgress.getProgress();
        String curTime = mCurrTime.getText().toString();
        String totalTime = mTotalTime.getText().toString();
        removeAllViews();
        if (isFull) {
            LayoutInflater.from(getContext()).inflate(R.layout.layout_pilipili_control_view, this, true);
            mOpenDanmuku = findViewById(R.id.send_danmuku);
            mDanmukuBar = findViewById(R.id.danmuku_bar);
            mEdit = findViewById(R.id.input_danmuku);
            mBack = findViewById(R.id.back);
            mSend = findViewById(R.id.send);
            checkBoxes[0] = findViewById(R.id.white_checkbox);
            checkBoxes[1] = findViewById(R.id.blue_green_checkbox);
            checkBoxes[2] = findViewById(R.id.brown_checkbox);
            checkBoxes[3] = findViewById(R.id.green_checkbox);
            checkBoxes[4] = findViewById(R.id.yellow_checkbox);
            checkBoxes[5] = findViewById(R.id.red_checkbox);
            checkBoxes[6] = findViewById(R.id.pink_checkbox);
            checkBoxes[7] = findViewById(R.id.blue_checkbox);
            checkBoxes[8] = findViewById(R.id.purple_checkbox);
            checkBoxes[0].setChecked(true);
            mBack.setOnClickListener(this);
            mSend.setOnClickListener(this);
            mOpenDanmuku.setOnClickListener(this);
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
            smallSize = findViewById(R.id.small_size);
            largeSize = findViewById(R.id.large_size);
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
            top = findViewById(R.id.top_type);
            bottom = findViewById(R.id.bottom_type);
            scroll = findViewById(R.id.scroll_type);
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
            smallSize.setChecked(true);
            scroll.setChecked(true);
            mFullScreen = findViewById(R.id.fullscreen);
            mFullScreen.setOnClickListener(this);
            mBottomContainer = findViewById(R.id.bottom_container);
            mVideoProgress = findViewById(R.id.seekBar);
            mVideoProgress.setOnSeekBarChangeListener(this);
            mTotalTime = findViewById(R.id.total_time);
            mCurrTime = findViewById(R.id.curr_time);
            mPlayButton = findViewById(R.id.iv_play);
            mPlayButton.setOnClickListener(this);
            mPlayButton.setSelected(mControlWrapper.isPlaying());
            mBottomProgress = findViewById(R.id.bottom_progress);
            mBottomProgress.setProgress(progress);
            mCurrTime.setText(curTime);
            mTotalTime.setText(totalTime);
            mDanmukuBar.setVisibility(GONE);
        } else {
            LayoutInflater.from(getContext()).inflate(R.layout.layout_pilipili_control_view_small, this, true);
            mFullScreen = findViewById(R.id.fullscreen);
            mFullScreen.setOnClickListener(this);
            mBottomContainer = findViewById(R.id.bottom_container);
            mVideoProgress = findViewById(R.id.seekBar);
            mVideoProgress.setOnSeekBarChangeListener(this);
            mTotalTime = findViewById(R.id.total_time);
            mCurrTime = findViewById(R.id.curr_time);
            mPlayButton = findViewById(R.id.iv_play);
            mPlayButton.setOnClickListener(this);
            mPlayButton.setSelected(mControlWrapper.isPlaying());
            mBottomProgress = findViewById(R.id.bottom_progress);
            mBottomProgress.setProgress(progress);
            mCurrTime.setText(curTime);
            mTotalTime.setText(totalTime);

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mIsDragging = true;
        mControlWrapper.stopProgress();
        mControlWrapper.stopFadeOut();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        long duration = mControlWrapper.getDuration();
        long newPosition = (duration * seekBar.getProgress()) / mVideoProgress.getMax();
        mControlWrapper.seekTo((int) newPosition);
        mIsDragging = false;
        mControlWrapper.startProgress();
        mControlWrapper.startFadeOut();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }

        long duration = mControlWrapper.getDuration();
        long newPosition = (duration * progress) / mVideoProgress.getMax();
        if (mCurrTime != null)
            mCurrTime.setText(stringForTime((int) newPosition));
    }

    public void setDanmukuListener(VideoActivity.OnDanmukuListener mDanmukuListener) {
        this.mDanmukuListener = mDanmukuListener;
    }

    public interface OnFullChangeListener {
        void onFullChange();
    }
}