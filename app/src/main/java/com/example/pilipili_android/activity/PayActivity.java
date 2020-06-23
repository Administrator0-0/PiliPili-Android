package com.example.pilipili_android.activity;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pilipili_android.R;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.example.pilipili_android.view_model.UserViewModel;
import com.example.pilipili_android.widget.CommonDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

public class PayActivity extends AppCompatActivity {

    @BindView(R.id.button_back)
    ImageView buttonBack;
    @BindView(R.id.usage)
    TextView usage;
    @BindView(R.id.relativelayout_top)
    RelativeLayout relativelayoutTop;
    @BindView(R.id.keyongyue_tv)
    TextView keyongyueTv;
    @BindView(R.id.p_coin_count)
    TextView pCoinCount;
    @BindView(R.id.relativelayout_mid)
    RelativeLayout relativelayoutMid;
    @BindView(R.id.buy1)
    RelativeLayout buy1;
    @BindView(R.id.buy2)
    RelativeLayout buy2;
    @BindView(R.id.buy3)
    RelativeLayout buy3;
    @BindView(R.id.linear_top)
    LinearLayout linearTop;
    @BindView(R.id.buy4)
    RelativeLayout buy4;
    @BindView(R.id.buy5)
    RelativeLayout buy5;
    @BindView(R.id.buy6)
    RelativeLayout buy6;
    @BindView(R.id.linear_top2)
    LinearLayout linearTop2;
    @BindView(R.id.other)
    QMUIRoundButton other;
    @BindView(R.id.help_tv)
    TextView helpTv;
    @BindView(R.id.policy_tv)
    TextView policyTv;
    @BindView(R.id.pay)
    QMUIRoundButton pay;
    @BindView(R.id.buy1_tv)
    TextView buy1Tv;
    @BindView(R.id.buy2_tv)
    TextView buy2Tv;
    @BindView(R.id.buy3_tv)
    TextView buy3Tv;
    @BindView(R.id.buy4_tv)
    TextView buy4Tv;
    @BindView(R.id.buy5_tv)
    TextView buy5Tv;
    @BindView(R.id.buy6_tv)
    TextView buy6Tv;
    @BindView(R.id.buy1_tv2)
    TextView buy1Tv2;
    @BindView(R.id.buy2_tv2)
    TextView buy2Tv2;
    @BindView(R.id.buy3_tv2)
    TextView buy3Tv2;
    @BindView(R.id.buy4_tv2)
    TextView buy4Tv2;
    @BindView(R.id.buy5_tv2)
    TextView buy5Tv2;
    @BindView(R.id.buy6_tv2)
    TextView buy6Tv2;

    UserViewModel userViewModel;

    private int selectWhich = -1;
    private int selectHowMuch = -1;
    private RelativeLayout selectWhichRelativeLayout = null;
    private TextView selectWhichTextView = null;
    private TextView selectWhichTextView2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initBind();
        initView();

        userViewModel.getIsSuccessBuyCoin().observe(this, isSuccessBuyCoin -> {
            if (isSuccessBuyCoin) {
                CommonDialog commonDialog = new CommonDialog(this);
                commonDialog.setTitle("充值成功").setMessage("您已成功充值" + selectHowMuch + "元")
                        .setImageResId(R.drawable.bxx).setPositive("我知道了")
                        .setOnConfirmDialogClickListener(() -> {
                            commonDialog.dismiss();
                            this.finish();
                        }).setNegative("继续充值")
                        .setOnCancelDialogClickListener(() -> {
                            commonDialog.dismiss();
                            showCoin();
                        }).show();
            }
        });
    }

    private void initBind() {
        ButterKnife.bind(this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void showCoin() {
        String text = UserBaseDetail.getCoin(userViewModel.getContext()) + "  P币";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new AbsoluteSizeSpan(AutoSizeUtils.sp2px(this, 48)), 0, text.indexOf("P") - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, text.indexOf("P") - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(AutoSizeUtils.sp2px(this, 22)), text.indexOf("P") - 2, text.indexOf("P") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        pCoinCount.setText(spannableString);
    }

    private void initView() {
        showCoin();

        selectWhichRelativeLayout = buy1;
        selectWhichTextView = buy1Tv;
        selectWhichTextView2 = buy1Tv2;
        selectWhich = 1;
        selectHowMuch = 6;
        pay.setText("确认支付 ¥6.00");

        SpannableString spannableString1 = new SpannableString(helpTv.getText());
        spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 68, 72, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helpTv.setText(spannableString1);
        SpannableString spannableString2 = new SpannableString(policyTv.getText());
        spannableString2.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 5, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        policyTv.setText(spannableString2);

        setBuyTextViewSpan(buy1Tv);
        setBuyTextViewSpan(buy2Tv);
        setBuyTextViewSpan(buy3Tv);
        setBuyTextViewSpan(buy4Tv);
        setBuyTextViewSpan(buy5Tv);
        setBuyTextViewSpan(buy6Tv);
    }

    private void setBuyTextViewSpan(TextView textView) {
        SpannableString spannableString = new SpannableString(textView.getText());
        int end = textView.getText().toString().indexOf("P");
        spannableString.setSpan(new AbsoluteSizeSpan(AutoSizeUtils.sp2px(this, 22)), 0, end - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(AutoSizeUtils.sp2px(this, 17)), end - 1, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void selectButton(RelativeLayout relativeLayout, int which, int howMuch, TextView textView, TextView textView2) {
        selectWhichTextView = textView;
        selectWhichRelativeLayout = relativeLayout;
        selectWhichTextView2 = textView2;
        relativeLayout.setBackground(getDrawable(R.drawable.pay_button_selected));
        selectWhich = which;
        selectHowMuch = howMuch;
        pay.setText("确认支付 ¥" + howMuch + ".00");
        textView.setTextColor(getColor(R.color.white));
        textView2.setTextColor(getColor(R.color.white));
    }

    private void setSelectedButtonUnselected() {
        selectWhichTextView2.setTextColor(getColor(R.color.black));
        selectWhichTextView.setTextColor(getColor(R.color.black));
        selectWhichRelativeLayout.setBackground(getDrawable(R.drawable.pay_button_unselected));
        if (selectWhich < 7) other.setText("其他金额");
    }

    @OnClick({R.id.buy1, R.id.buy2, R.id.buy3, R.id.buy4, R.id.buy5, R.id.buy6})
    public void onPayButtonSelected(View view) {
        setSelectedButtonUnselected();
        switch (view.getId()) {
            case R.id.buy1:
                selectButton(buy1, 1, 6, buy1Tv, buy1Tv2);
                break;
            case R.id.buy2:
                selectButton(buy2, 2, 18, buy2Tv, buy2Tv2);
                break;
            case R.id.buy3:
                selectButton(buy3, 3, 30, buy3Tv, buy3Tv2);
                break;
            case R.id.buy4:
                selectButton(buy4, 4, 68, buy4Tv, buy4Tv2);
                break;
            case R.id.buy5:
                selectButton(buy5, 5, 108, buy5Tv, buy5Tv2);
                break;
            case R.id.buy6:
                selectButton(buy6, 6, 158, buy6Tv, buy6Tv2);
                break;
        }
        other.setText("其他金额");
    }

    @OnClick(R.id.button_back)
    public void onBackClicked() {
        this.finish();
    }

    @OnClick(R.id.other)
    public void onOtherClicked() {
        showBottomDialog();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        View view = View.inflate(this, R.layout.other_pay_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        if (window != null) {
            //设置弹出位置
            window.setGravity(Gravity.BOTTOM);
            //设置弹出动画
            window.setWindowAnimations(R.style.main_menu_animStyle);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCancelable(false);
        dialog.show();

        TextView buy7Tv = view.findViewById(R.id.buy7_tv);
        TextView buy8Tv = view.findViewById(R.id.buy8_tv);
        TextView buy9Tv = view.findViewById(R.id.buy9_tv);
        TextView buy10Tv = view.findViewById(R.id.buy10_tv);
        TextView buy11Tv = view.findViewById(R.id.buy11_tv);
        TextView buy12Tv = view.findViewById(R.id.buy12_tv);
        RelativeLayout buy7 = view.findViewById(R.id.buy7);
        RelativeLayout buy8 = view.findViewById(R.id.buy8);
        RelativeLayout buy9 = view.findViewById(R.id.buy9);
        RelativeLayout buy10 = view.findViewById(R.id.buy10);
        RelativeLayout buy11 = view.findViewById(R.id.buy11);
        RelativeLayout buy12 = view.findViewById(R.id.buy12);
        TextView buy7Tv2 = view.findViewById(R.id.buy7_tv2);
        TextView buy8Tv2 = view.findViewById(R.id.buy8_tv2);
        TextView buy9Tv2 = view.findViewById(R.id.buy9_tv2);
        TextView buy10Tv2 = view.findViewById(R.id.buy10_tv2);
        TextView buy11Tv2 = view.findViewById(R.id.buy11_tv2);
        TextView buy12Tv2 = view.findViewById(R.id.buy12_tv2);

        switch (selectWhich) {
            case 7:
                buy7Tv2.setTextColor(getColor(R.color.white));
                buy7Tv.setTextColor(getColor(R.color.white));
                buy7.setBackground(getDrawable(R.drawable.pay_button_selected));
                break;
            case 8:
                buy8Tv2.setTextColor(getColor(R.color.white));
                buy8Tv.setTextColor(getColor(R.color.white));
                buy8.setBackground(getDrawable(R.drawable.pay_button_selected));
                break;
            case 9:
                buy9Tv2.setTextColor(getColor(R.color.white));
                buy9Tv.setTextColor(getColor(R.color.white));
                buy9.setBackground(getDrawable(R.drawable.pay_button_selected));
                break;
            case 10:
                buy10Tv2.setTextColor(getColor(R.color.white));
                buy10Tv.setTextColor(getColor(R.color.white));
                buy10.setBackground(getDrawable(R.drawable.pay_button_selected));
                break;
            case 11:
                buy11Tv2.setTextColor(getColor(R.color.white));
                buy11Tv.setTextColor(getColor(R.color.white));
                buy11.setBackground(getDrawable(R.drawable.pay_button_selected));
                break;
            case 12:
                buy12Tv2.setTextColor(getColor(R.color.white));
                buy12Tv.setTextColor(getColor(R.color.white));
                buy12.setBackground(getDrawable(R.drawable.pay_button_selected));
                break;
            default:
                break;
        }

        setBuyTextViewSpan(view.findViewById(R.id.buy7_tv));
        setBuyTextViewSpan(view.findViewById(R.id.buy8_tv));
        setBuyTextViewSpan(view.findViewById(R.id.buy9_tv));
        setBuyTextViewSpan(view.findViewById(R.id.buy10_tv));
        setBuyTextViewSpan(view.findViewById(R.id.buy11_tv));
        setBuyTextViewSpan(view.findViewById(R.id.buy12_tv));

        buy7.setOnClickListener(view1 -> {
            setSelectedButtonUnselected();
            selectWhich = 7;
            pay.setText("确认支付 ¥198.00");
            other.setText("其他金额 ¥198.00");
            selectHowMuch = 198;
            dialog.dismiss();
        });
        buy8.setOnClickListener(view1 -> {
            setSelectedButtonUnselected();
            selectWhich = 8;
            pay.setText("确认支付 ¥233.00");
            selectHowMuch = 233;
            other.setText("其他金额 ¥233.00");
            dialog.dismiss();
        });
        buy9.setOnClickListener(view1 -> {
            setSelectedButtonUnselected();
            selectWhich = 9;
            pay.setText("确认支付 ¥388.00");
            other.setText("其他金额 ¥388.00");
            selectHowMuch = 388;
            dialog.dismiss();
        });
        buy10.setOnClickListener(view1 -> {
            setSelectedButtonUnselected();
            selectWhich = 10;
            pay.setText("确认支付 ¥648.00");
            other.setText("其他金额 ¥648.00");
            selectHowMuch = 648;
            dialog.dismiss();
        });
        buy11.setOnClickListener(view1 -> {
            setSelectedButtonUnselected();
            selectWhich = 11;
            pay.setText("确认支付 ¥998.00");
            other.setText("其他金额 ¥998.00");
            selectHowMuch = 998;
            dialog.dismiss();
        });
        buy12.setOnClickListener(view1 -> {
            setSelectedButtonUnselected();
            selectWhich = 12;
            pay.setText("确认支付 ¥1298.00");
            other.setText("其他金额 ¥1298.00");
            selectHowMuch = 1298;
            dialog.dismiss();
        });
        (view.findViewById(R.id.cancel_pay_other)).setOnClickListener(view1 -> dialog.dismiss());
    }

    @OnClick(R.id.pay)
    public void onPayClicked() {
        userViewModel.buyCoin(selectHowMuch);
    }

    @OnClick(R.id.usage)
    public void onUsageClicked() {
        CommonDialog commonDialog = new CommonDialog(this);
        commonDialog.setTitle("使用说明").setPositive("我知道了").setSingle(true).setImageResId(R.drawable.bh3)
                .setOnConfirmDialogClickListener(commonDialog::dismiss)
                .setMessage("P币可以用于打赏P站视频哦~");
        commonDialog.show();
    }
}
