package com.example.pilipili_android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.VideoActivity;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class ExpandMenuView extends RelativeLayout {

    private Context mContext;
    private AttributeSet mAttrs;

    private Paint buttonIconPaint;//按钮icon画笔
    private ExpandMenuAnim anim;

    private int defaultWidth;//默认宽度
    private int defaultHeight;//默认长度
    private int viewWidth;
    private int viewHeight;
    private float backPathWidth;//绘制子View区域宽度
    private float maxBackPathWidth;//绘制子View区域最大宽度
    private int menuLeft;//menu区域left值
    private int menuRight;//menu区域right值

    private int menuBackColor;//菜单栏背景色
    private float menuStrokeSize;//菜单栏边框线的size
    private int menuStrokeColor;//菜单栏边框线的颜色
    private float menuCornerRadius;//菜单栏圆角半径

    private float buttonIconSize;//按钮icon符号的大小

    private int buttonStyle;//按钮类型
    private int buttonRadius;//按钮矩形区域内圆半径
    private float buttonTop;//按钮矩形区域top值
    private float buttonBottom;//按钮矩形区域bottom值

    private Point rightButtonCenter;//右按钮中点
    private float rightButtonLeft;//右按钮矩形区域left值
    private float rightButtonRight;//右按钮矩形区域right值

    private Point leftButtonCenter;//左按钮中点
    private float leftButtonLeft;//左按钮矩形区域left值
    private float leftButtonRight;//左按钮矩形区域right值

    private boolean isFirstLayout;//是否第一次测量位置，主要用于初始化menuLeft和menuRight的值
    private boolean isExpand;//菜单是否展开，默认为展开
    private boolean isAnimEnd;//动画是否结束
    private float downX = -1;
    private float downY = -1;
    private int expandAnimTime;//展开收起菜单的动画时间

    private VideoActivity.OnDanmukuCloseListener onDanmukuCloseListener;

    private View childView;

    /**
     * 根按钮所在位置，默认为右边
     */
    public static final int Right = 0;
    public static final int Left = 1;

    public ExpandMenuView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ExpandMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mAttrs = attrs;
        init();
    }

    public void setOnDanmukuCloseListener(VideoActivity.OnDanmukuCloseListener onDanmukuCloseListener) {
        this.onDanmukuCloseListener = onDanmukuCloseListener;
    }

    private void init() {
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.HorizontalExpandMenu);
        defaultWidth = AutoSizeUtils.dp2px(mContext, 200);
        defaultHeight = AutoSizeUtils.dp2px(mContext, 40);

        menuBackColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_back_color, Color.WHITE);
        menuStrokeSize = typedArray.getDimension(R.styleable.HorizontalExpandMenu_stroke_size, 1);
        menuStrokeColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_stroke_color, Color.GRAY);
        menuCornerRadius = typedArray.getDimension(R.styleable.HorizontalExpandMenu_corner_radius, AutoSizeUtils.dp2px(mContext, 20));

        buttonStyle = typedArray.getInteger(R.styleable.HorizontalExpandMenu_button_style, Right);
        buttonIconSize = typedArray.getDimension(R.styleable.HorizontalExpandMenu_button_icon_size, AutoSizeUtils.dp2px(mContext, 8));

        expandAnimTime = typedArray.getInteger(R.styleable.HorizontalExpandMenu_expand_time, 400);
        typedArray.recycle();

        isFirstLayout = true;
        isExpand = true;
        isAnimEnd = false;

        buttonIconPaint = new Paint();
        buttonIconPaint.setAntiAlias(true);

        leftButtonCenter = new Point();
        rightButtonCenter = new Point();
        anim = new ExpandMenuAnim();
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimEnd = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = measureSize(defaultHeight, heightMeasureSpec);
        int width = measureSize(defaultWidth, widthMeasureSpec);
        viewHeight = height;
        viewWidth = width;
        buttonRadius = viewHeight / 2;
        layoutRootButton();
        setMeasuredDimension(viewWidth, viewHeight);

        maxBackPathWidth = viewWidth - buttonRadius * 2;
        backPathWidth = maxBackPathWidth;

        //布局代码中如果没有设置background属性则在此处添加一个背景
        if (getBackground() == null) {
            setMenuBackground();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //如果子View数量为0时，onLayout后getLeft()和getRight()才能获取相应数值，menuLeft和menuRight保存menu初始的left和right值
        if (isFirstLayout) {
            menuLeft = getLeft();
            menuRight = getRight();
            isFirstLayout = false;
        }
        if (getChildCount() > 0) {
            childView = getChildAt(0);
            if (isExpand) {
                if (buttonStyle == Right) {
                    childView.layout(leftButtonCenter.x, (int) buttonTop, (int) rightButtonLeft, (int) buttonBottom);
                } else {
                    childView.layout((int) (leftButtonRight), (int) buttonTop, rightButtonCenter.x, (int) buttonBottom);
                }

                //限制子View在菜单内，LayoutParam类型和当前ViewGroup一致
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(viewWidth, viewHeight);
                layoutParams.setMargins(0, 0, buttonRadius * 3, 0);
                childView.setLayoutParams(layoutParams);
            } else {
                childView.setVisibility(GONE);
            }
        }
        if (getChildCount() > 2) {//限制直接子View的数量
            throw new IllegalStateException("HorizontalExpandMenu can host only one direct child");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        if (isAnimEnd) {
            if (buttonStyle == Right) {
                if (!isExpand) {
                    layout((menuRight - buttonRadius * 2), getTop(), menuRight, getBottom());
                }
            } else {
                if (!isExpand) {
                    layout(menuLeft, getTop(), (menuLeft + buttonRadius * 2), getBottom());
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        layoutRootButton();
        if(buttonStyle == Right){
            drawRightIcon(canvas);
        }else {
            drawLeftIcon(canvas);
        }

        super.onDraw(canvas);//注意父方法在最后调用，以免icon被遮盖
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (backPathWidth == maxBackPathWidth || backPathWidth == 0) {//动画结束时按钮才生效
                    switch (buttonStyle) {
                        case Right:
                            if (x == downX && y == downY && y >= buttonTop && y <= buttonBottom && x >= rightButtonLeft && x <= rightButtonRight) {
                                expandMenu(expandAnimTime);
                            }
                            break;
                        case Left:
                            if (x == downX && y == downY && y >= buttonTop && y <= buttonBottom && x >= leftButtonLeft && x <= leftButtonRight) {
                                expandMenu(expandAnimTime);
                            }
                            break;
                    }
                }
                break;
        }
        return true;
    }

    private class ExpandMenuAnim extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float left = menuRight - buttonRadius * 2;
            float right = menuLeft + buttonRadius * 2;
            if (childView != null) {
                childView.setVisibility(GONE);
            }
            if (isExpand) {
                backPathWidth = maxBackPathWidth * interpolatedTime;

                if (backPathWidth == maxBackPathWidth) {
                    if (childView != null) {
                        childView.setVisibility(VISIBLE);
                    }
                }
            } else {
                backPathWidth = maxBackPathWidth - maxBackPathWidth * interpolatedTime;
            }
            if (buttonStyle == Right) {
                layout((int) (left - backPathWidth), getTop(), menuRight, getBottom());//会调用onLayout重新测量子View位置
            } else {
                layout(menuLeft, getTop(), (int) (right + backPathWidth), getBottom());
            }
            postInvalidate();
        }
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    private void setMenuBackground(){
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(menuBackColor);
        gd.setStroke((int)menuStrokeSize, menuStrokeColor);
        gd.setCornerRadius(menuCornerRadius);
        setBackground(gd);
    }

    private void layoutRootButton() {
        buttonTop = 0;
        buttonBottom = viewHeight;

        rightButtonCenter.x = viewWidth - buttonRadius;
        rightButtonCenter.y = viewHeight / 2;
        rightButtonLeft = rightButtonCenter.x - buttonRadius;
        rightButtonRight = rightButtonCenter.x + buttonRadius;

        leftButtonCenter.x = buttonRadius;
        leftButtonCenter.y = viewHeight / 2;
        leftButtonLeft = leftButtonCenter.x - buttonRadius;
        leftButtonRight = leftButtonCenter.x + buttonRadius;
    }

    private void drawLeftIcon(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ddo);
        canvas.drawBitmap(bitmap, leftButtonCenter.x - 2 * buttonIconSize, -leftButtonCenter.y, buttonIconPaint);
        canvas.save();
    }

    private void drawRightIcon(Canvas canvas) {
        Bitmap bitmap;
        if (isExpand) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.danmuku_open);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.danmuku_close);
        }
        canvas.drawBitmap(bitmap, rightButtonCenter.x - 7 * buttonIconSize / 4,  rightButtonCenter.y -  buttonIconSize * 3 / 2, buttonIconPaint);
        canvas.save();
    }

    private void expandMenu(int time){
        anim.setDuration(time);
        isExpand = !isExpand;
        this.startAnimation(anim);
        isAnimEnd = false;
        if (onDanmukuCloseListener != null)
            onDanmukuCloseListener.onClose(isExpand);
    }
}