package com.example.pilipili_android.activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pilipili_android.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    //登录
    @BindView(R.id.button_login)
    QMUIRoundButton btnLogIn;
    //注册
    @BindView(R.id.button_sign)
    QMUIRoundButton btnSign;
    //忘记密码
    @BindView(R.id.button_forgetpassword)
    QMUIRoundButton btnForgetPassword;
    //游客登陆
    @BindView(R.id.button_login_asvisitor)
    QMUIRoundButton btnLoginAsVisitor;
    //账号
    @BindView(R.id.edit_account)
    EditText editAccount;
    //密码
    @BindView(R.id.edit_password)
    EditText editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.button_login_asvisitor)
    public void loginAsVisitor(QMUIRoundButton button){

    }
    @OnClick(R.id.button_forgetpassword)
    public void forgetPassword(QMUIRoundButton button){

    }
    @OnClick(R.id.button_login)
    public void logIn(QMUIRoundButton button){

    }
    @OnClick(R.id.button_sign)
    public void sign(QMUIRoundButton button){

    }
}
