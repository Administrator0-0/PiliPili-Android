package com.example.pilipili_android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import java.io.File;

public class PiliPiliPlayer extends StandardGSYVideoPlayer {

    private boolean isLinkScroll = false;
    private File mDumakuFile;
    private boolean isPaused;
    private VideoActivity.OnVideoListener mListener;

    public PiliPiliPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public PiliPiliPlayer(Context context) {
        super(context);
    }

    public PiliPiliPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void init(Context context) {
        super.init(context);

        post(new Runnable() {
            @Override
            public void run() {
                gestureDetector = new GestureDetector(getContext().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        touchDoubleUp();
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (!mChangePosition && !mChangeVolume && !mBrightness) {
                            onClickUiToggle();
                        }
                        return super.onSingleTapConfirmed(e);
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                    }
                });
            }
        });
    }

    public void setListener(VideoActivity.OnVideoListener mListener) {
        this.mListener = mListener;
    }

    //这个必须配置最上面的构造才能生效
    @Override
    public int getLayoutId() {
        return R.layout.player_pilipili;
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        isPaused = false;
        mListener.onStart();
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        isPaused = true;
        mListener.onPause();
    }

    @Override
    public void onVideoResume(boolean isResume) {
        super.onVideoResume(isResume);
        isPaused = false;
        mListener.onStart();
    }

    @Override
    protected void clickStartIcon() {
        super.clickStartIcon();
        if (mCurrentState == CURRENT_STATE_PLAYING) {
            isPaused = false;
            mListener.onStart();
        } else if (mCurrentState == CURRENT_STATE_PAUSE) {
            isPaused = true;
            mListener.onPause();
        }
    }


    @Override
    public void onSeekComplete() {
        super.onSeekComplete();
        int time = mProgressBar.getProgress() * getDuration() / 100;
    }

    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        ((PiliPiliPlayer) to).mDumakuFile = ((PiliPiliPlayer) from).mDumakuFile;
        ((PiliPiliPlayer) to).isPaused = ((PiliPiliPlayer) from).isPaused;
        super.cloneParams(from, to);
    }


    @Override
    protected void updateStartImage() {
        if (mIfCurrentIsFullscreen) {
            if(mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setImageResource(R.drawable.video_click_pause_selector);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setImageResource(R.drawable.video_click_play_selector);
                } else {
                    imageView.setImageResource(R.drawable.video_click_play_selector);
                }
            }
        } else {
            super.updateStartImage();
        }
    }

    @Override
    public int getEnlargeImageRes() {
        return R.drawable.ic_live_palyer_zoom_in;
    }

    @Override
    public int getShrinkImageRes() {
        return R.drawable.ic_live_player_zoom_out;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isLinkScroll && !isIfCurrentIsFullscreen()) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        PiliPiliPlayer piliPiliPlayer = (PiliPiliPlayer)gsyVideoPlayer;
        piliPiliPlayer.dismissProgressDialog();
        piliPiliPlayer.dismissVolumeDialog();
        piliPiliPlayer.dismissBrightnessDialog();
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
    }

    public void setLinkScroll(boolean linkScroll) {
        isLinkScroll = linkScroll;
    }


    @Override
    protected void changeUiToCompleteClear() {
        super.changeUiToCompleteClear();
        setTextAndProgress(0, true);
    }

    @Override
    protected void changeUiToCompleteShow() {
        super.changeUiToCompleteShow();
        setTextAndProgress(0, true);
    }


}
