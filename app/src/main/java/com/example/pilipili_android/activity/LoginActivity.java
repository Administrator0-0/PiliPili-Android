package com.example.pilipili_android.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.pilipili_android.R;
import com.example.pilipili_android.fragment.FragmentMsg;
import com.example.pilipili_android.fragment.LoginFragment;
import com.example.pilipili_android.fragment.RegisterFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.right_img)
    ImageView rightImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initFragment();
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentChange(FragmentMsg fragmentMsg) {
        if ("register".equals(fragmentMsg.getWhatFragment())) {
            title.setText("账号注册");
            SpannableString spannableString1 = new SpannableString("注册即代表你同意用户协议和隐私政策");
            spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 13, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            policyTv.setText(spannableString1);
            leftImg.setImageResource(R.drawable.left);
            rightImg.setImageResource(R.drawable.right);
        } else if ("login".equals(fragmentMsg.getWhatFragment())) {
            if (fragmentMsg.getMsgString().equals("show")) {
                title.setText("密码登录");
                SpannableString spannableString1 = new SpannableString("登录即代表你同意用户协议和隐私政策");
                spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString1.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 13, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                policyTv.setText(spannableString1);
            } else if (fragmentMsg.getMsgString().equals("open_eyes")) {
                leftImg.setImageResource(R.drawable.left);
                rightImg.setImageResource(R.drawable.right);
            } else if (fragmentMsg.getMsgString().equals("close_eyes")) {
                leftImg.setImageResource(R.drawable.left_hide);
                rightImg.setImageResource(R.drawable.right_hide);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        SpannableString spannableString2 = new SpannableString("遇到问题？查看帮助");
        spannableString2.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helpTv.setText(spannableString2);
    }

}
