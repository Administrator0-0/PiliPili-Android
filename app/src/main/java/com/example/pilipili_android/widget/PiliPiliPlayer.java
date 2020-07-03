package com.example.pilipili_android.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.dueeeke.videoplayer.player.VideoView;
import com.example.pilipili_android.activity.VideoActivity;

public class PiliPiliPlayer extends VideoView {

    private VideoActivity.OnVideoListener mListener;

    public PiliPiliPlayer(Context context) {
        super(context);
    }

    public PiliPiliPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setListener(VideoActivity.OnVideoListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void start() {
        super.start();
        if (mListener != null)
            mListener.onStart();
    }

    @Override
    public void pause() {
        super.pause();
        if (mListener != null)
            mListener.onPause();
    }

    @Override
    public void resume() {
        super.resume();
        if (mListener != null)
            mListener.onStart();
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        if (mListener != null)
            mListener.onStart();
    }

    @Override
    protected boolean showNetWarning() {
        return false;
    }





}
