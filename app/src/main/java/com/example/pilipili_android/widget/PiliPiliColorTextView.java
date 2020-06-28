package com.example.pilipili_android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PiliPiliColorTextView extends androidx.appcompat.widget.AppCompatTextView {

    private int mLastMotionX, mLastMotionY;
    private boolean isMoved;
    private boolean isReleased;
    private int mCounter;
    private Runnable mLongPressRunnable;
    private Runnable mClickPressRunnable;
    private static final int TOUCH_SLOP_X = 250;
    private static final int TOUCH_SLOP_Y = 0;

    public PiliPiliColorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mLongPressRunnable = () -> {
            mCounter--;
            Log.d("aaa", mCounter + "   counter");
            Log.d("aaa", isMoved + "   isMove");
            Log.d("aaa", isReleased + "    isRelease");
            // 计数器大于0，说明当前执行的Runnable不是最后一次down产生的。
            if (mCounter > 0 || isReleased || !isMoved)
                return;
            performLongClick();// 回调长按事件
        };
        mClickPressRunnable = () -> {
            if(isReleased)
                performClick();
        };
    }

    public PiliPiliColorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PiliPiliColorTextView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                mCounter++;
                isReleased = false;
                isMoved = false;
                postDelayed(mClickPressRunnable, 300);
                postDelayed(mLongPressRunnable, 3000);//按下3秒后调用线程
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoved)
                    break;
                if (Math.abs(mLastMotionX - x) > TOUCH_SLOP_X
                        || Math.abs(mLastMotionY - y) > TOUCH_SLOP_Y) {
                    //移动超过阈值，则表示移动了
                    isMoved = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //释放了
                isReleased = true;
                break;
        }
        return true;
    }
}

