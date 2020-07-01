package com.example.pilipili_android.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
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

public class VIPActivity extends AppCompatActivity {

    @BindView(R.id.button_back_vip)
    ImageView buttonBackVip;
    @BindView(R.id.number_left_vip)
    TextView numberLeftVip;
    @BindView(R.id.vip_1)
    RelativeLayout vip1;
    @BindView(R.id.vip_2)
    RelativeLayout vip2;
    @BindView(R.id.vip_3)
    RelativeLayout vip3;
    @BindView(R.id.vip_4)
    RelativeLayout vip4;
    @BindView(R.id.vip_5)
    RelativeLayout vip5;
    @BindView(R.id.vip_6)
    RelativeLayout vip6;
    @BindView(R.id.vip_pay)
    QMUIRoundButton vipPay;
    @BindView(R.id.text_vip_1_1)
    TextView textVip11;
    @BindView(R.id.text_vip_1_2)
    TextView textVip12;
    @BindView(R.id.text_vip_2_1)
    TextView textVip21;
    @BindView(R.id.text_vip_2_2)
    TextView textVip22;
    @BindView(R.id.text_vip_3_1)
    TextView textVip31;
    @BindView(R.id.text_vip_3_2)
    TextView textVip32;
    @BindView(R.id.text_vip_4_1)
    TextView textVip41;
    @BindView(R.id.text_vip_4_2)
    TextView textVip42;
    @BindView(R.id.text_vip_5_1)
    TextView textVip51;
    @BindView(R.id.text_vip_5_2)
    TextView textVip52;
    @BindView(R.id.text_vip_6_1)
    TextView textVip61;
    @BindView(R.id.text_vip_6_2)
    TextView textVip62;
    @BindView(R.id.help_vip)
    TextView helpVip;
    @BindView(R.id.policy_tv)
    TextView policyTv;

    UserViewModel userViewModel;

    private int selectWhich = -1;
    private int selectHowMuch = -1;
    private RelativeLayout selectWhichRelativeLayout = null;
    private TextView selectWhichTextView = null;
    private TextView selectWhichTextView2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        initBind();
        initView();

        userViewModel.getIsSuccessBuyCoin().observe(this, isSuccessBuyCoin -> {
            if (isSuccessBuyCoin) {
                CommonDialog commonDialog = new CommonDialog(this);
                commonDialog.setCancelable(false);
                commonDialog.setTitle("购买成功").setMessage("您已成功购买" + selectHowMuch + "天大会员")
                        .setImageResId(R.drawable.bxx).setPositive("我知道了")
                        .setOnConfirmDialogClickListener(() -> {
                            commonDialog.dismiss();
                            this.finish();
                        }).setNegative("继续购买")
                        .setOnCancelDialogClickListener(() -> {
                            commonDialog.dismiss();
                            showLeftDays();
                        }).show();
            }
        });
    }

    private void initBind() {
        ButterKnife.bind(this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void showLeftDays() {
        String text = UserBaseDetail.getVIPDeadline(userViewModel.getContext()) + "  天";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new AbsoluteSizeSpan(AutoSizeUtils.sp2px(this, 48)), 0, text.indexOf("天") - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, text.indexOf("天") - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(AutoSizeUtils.sp2px(this, 22)), text.indexOf("天") - 2, text.indexOf("天") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        numberLeftVip.setText(spannableString);
    }

    private void initView() {
        showLeftDays();

        selectWhichRelativeLayout = vip1;
        selectWhichTextView = textVip11;
        selectWhichTextView2 = textVip12;
        selectWhich = 1;
        selectHowMuch = 6;
        vipPay.setText("确认支付 15 P币");

        SpannableString spannableString1 = new SpannableString(helpVip.getText());
        spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 57, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helpVip.setText(spannableString1);
        SpannableString spannableString2 = new SpannableString(policyTv.getText());
        spannableString2.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 5, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        policyTv.setText(spannableString2);

        setBuyTextViewSpan(textVip12);
        setBuyTextViewSpan(textVip22);
        setBuyTextViewSpan(textVip32);
        setBuyTextViewSpan(textVip42);
        setBuyTextViewSpan(textVip52);
        setBuyTextViewSpan(textVip62);
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
        vipPay.setText("确认支付 " + howMuch + " P币");
        textView.setTextColor(getColor(R.color.white));
        textView2.setTextColor(getColor(R.color.white));
    }

    private void setSelectedButtonUnselected() {
        selectWhichTextView2.setTextColor(getColor(R.color.black));
        selectWhichTextView.setTextColor(getColor(R.color.black));
        selectWhichRelativeLayout.setBackground(getDrawable(R.drawable.pay_button_unselected));
    }

    @OnClick({R.id.vip_1, R.id.vip_2, R.id.vip_3, R.id.vip_4, R.id.vip_5, R.id.vip_6})
    public void onPayButtonSelected(View view) {
        setSelectedButtonUnselected();
        switch (view.getId()) {
            case R.id.vip_1:
                selectButton(vip1, 1, 15, textVip11, textVip12);
                break;
            case R.id.vip_2:
                selectButton(vip2, 2, 45, textVip21, textVip22);
                break;
            case R.id.vip_3:
                selectButton(vip3, 3, 148, textVip31, textVip32);
                break;
            case R.id.vip_4:
                selectButton(vip4, 4, 280, textVip41, textVip42);
                break;
            case R.id.vip_5:
                selectButton(vip5, 5, 680, textVip51, textVip52);
                break;
            case R.id.vip_6:
                selectButton(vip6, 6, 1200, textVip61, textVip62);
                break;
        }
    }
    @OnClick(R.id.button_back_vip)
    public void onBackClicked() {
        this.finish();
    }

    @OnClick(R.id.vip_pay)
    public void onPayClicked() {
        userViewModel.buyCoin(selectHowMuch);
    }

}