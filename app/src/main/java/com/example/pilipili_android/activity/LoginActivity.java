package com.example.pilipili_android.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pilipili_android.R;
import com.example.pilipili_android.fragment.LoginFragment;
import com.example.pilipili_android.fragment.RegisterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    LoginFragment loginFragment;
    RegisterFragment registerFragment;


    @BindView(R.id.top_login)
    RelativeLayout topLogin;
    @BindView(R.id.relativelayout_login_image)
    RelativeLayout relativelayoutLoginImage;
    @BindView(R.id.relativelayout_box)
    FrameLayout relativelayoutBox;
    @BindView(R.id.policy_tv)
    TextView policyTv;
    @BindView(R.id.help_tv)
    TextView helpTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    private void initFragment() {
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        loginFragment.setRegisterFragment(registerFragment);
        registerFragment.setLoginFragment(loginFragment);
        fragmentManager.beginTransaction().add(R.id.relativelayout_box, registerFragment)
                .add(R.id.relativelayout_box, loginFragment)
                .hide(registerFragment).commit();
    }

    private void initView() {
        SpannableString spannableString1 = new SpannableString("登录即代表你同意用户协议和隐私政策");
        spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.qmui_btn_blue_border)), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.qmui_btn_blue_border)), 13, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        policyTv.setText(spannableString1);

        SpannableString spannableString2 = new SpannableString("遇到问题？查看帮助");
        spannableString2.setSpan(new ForegroundColorSpan(getColor(R.color.qmui_btn_blue_border)), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helpTv.setText(spannableString2);
    }

}
