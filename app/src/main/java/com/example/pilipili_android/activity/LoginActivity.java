package com.example.pilipili_android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.pilipili_android.R;
import com.example.pilipili_android.bean.LoginReturn;
import com.example.pilipili_android.databinding.ActivityLoginBinding;
import com.example.pilipili_android.view_model.UserViewModel;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;
    private UserViewModel userViewModel;

    @BindView(R.id.top_login)
    RelativeLayout topLogin;
    @BindView(R.id.relativelayout_login_image)
    RelativeLayout relativelayoutLoginImage;
    @BindView(R.id.edit_account)
    EditText editAccount;
    @BindView(R.id.linearlayout_account)
    LinearLayout linearlayoutAccount;
    @BindView(R.id.clear_image_account)
    ImageView clearImageAccount;
    @BindView(R.id.relativelayout_account)
    RelativeLayout relativelayoutAccount;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.linearlayout_password)
    LinearLayout linearlayoutPassword;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.clear_image_password)
    ImageView clearImagePassword;
    @BindView(R.id.relativelayout_password)
    RelativeLayout relativelayoutPassword;
    @BindView(R.id.button_sign)
    QMUIRoundButton buttonSign;
    @BindView(R.id.button_login)
    QMUIRoundButton buttonLogin;
    @BindView(R.id.relativelayout_box)
    RelativeLayout relativelayoutBox;
    @BindView(R.id.policy_tv)
    TextView policyTv;
    @BindView(R.id.help_tv)
    TextView helpTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBind();
        initView();
    }

    private void initBind(){
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        activityLoginBinding.setViewModel(userViewModel);
        ButterKnife.bind(this);
    }

    private void initView(){
        SpannableString spannableString1 = new SpannableString("登录即代表你同意用户协议和隐私政策");
        spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.qmui_btn_blue_border)), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.qmui_btn_blue_border)), 13, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        policyTv.setText(spannableString1);

        SpannableString spannableString2 = new SpannableString("遇到问题？查看帮助");
        spannableString2.setSpan(new ForegroundColorSpan(getColor(R.color.qmui_btn_blue_border)), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helpTv.setText(spannableString2);

        clearImageAccount.setOnClickListener(view -> {
            editAccount.setText("");
        });

        clearImagePassword.setOnClickListener(view -> {
            editPassword.setText("");
        });

        buttonLogin.setOnClickListener(view -> {
            userViewModel.login(editAccount.getText().toString(), editPassword.getText().toString());
        });

        userViewModel.getIsSuccessLogin().observe(this, isSuccessLogin -> {
            if(isSuccessLogin) {
                Log.d("aaa111", "initView: ");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
