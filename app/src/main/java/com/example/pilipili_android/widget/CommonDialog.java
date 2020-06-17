package com.example.pilipili_android.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pilipili_android.R;
import com.example.pilipili_android.inteface.OnDialogClickListener;

/**
 * description:自定义dialog
 */

public class CommonDialog extends Dialog {
    /**
     * 显示的图片
     */
    private ImageView imageIv ;

    /**
     * 显示的标题
     */
    private TextView titleTv ;

    /**
     * 显示的消息
     */
    private TextView messageTv ;

    /**
     * 确认和取消按钮
     */
    private Button confirmBtn;
    private Button cancelBtn;


    public CommonDialog(Context context) {
        super(context, R.style.CommonDialogTheme);
    }

    private String message;
    private String title;
    private String positive;
    private String negative;
    private int imageResId = -1;
    private boolean single = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conmmon_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        confirmBtn.setOnClickListener(v -> {
            if ( onConfirmDialogClickListener != null) {
                onConfirmDialogClickListener.onClick();
            }
        });
        cancelBtn.setOnClickListener(v -> {
            if ( onCancelDialogClickListener != null) {
                onCancelDialogClickListener.onClick();
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        }else {
            titleTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            confirmBtn.setText(positive);
        }else {
            confirmBtn.setText("确定");
        }
        if (!TextUtils.isEmpty(negative)) {
            cancelBtn.setText(negative);
        }else {
            cancelBtn.setText("确定");
        }

        if (imageResId!=-1){
            imageIv.setImageResource(imageResId);
            imageIv.setVisibility(View.VISIBLE);
        }else {
            imageIv.setVisibility(View.GONE);
        }

        if(single) {
            cancelBtn.setVisibility(View.GONE);
        } else {
            cancelBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        confirmBtn = findViewById(R.id.confirm);
        cancelBtn = findViewById(R.id.cancel);
        titleTv = findViewById(R.id.title);
        messageTv = findViewById(R.id.message);
        imageIv = findViewById(R.id.image);
    }

    /**
     * 设置确定取消按钮的回调
     */
    private OnDialogClickListener onCancelDialogClickListener;
    private OnDialogClickListener onConfirmDialogClickListener;

    public CommonDialog setOnConfirmDialogClickListener(OnDialogClickListener onConfirmDialogClickListener) {
        this.onConfirmDialogClickListener = onConfirmDialogClickListener;
        return this;
    }

    public CommonDialog setOnCancelDialogClickListener(OnDialogClickListener onCancelDialogClickListener) {
        this.onCancelDialogClickListener = onCancelDialogClickListener;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this ;
    }

    public String getPositive() {
        return positive;
    }

    public CommonDialog setPositive(String positive) {
        this.positive = positive;
        return this ;
    }

    public String getNegative() {
        return negative;
    }

    public CommonDialog setNegative(String negative) {
        this.negative = negative;
        return this ;
    }

    public int getImageResId() {
        return imageResId;
    }

    public CommonDialog setImageResId(int imageResId) {
        this.imageResId = imageResId;
        return this ;
    }

    public boolean isSingle() {
        return single;
    }

    public CommonDialog setSingle(boolean single) {
        this.single = single;
        return this;
    }
}

