package com.example.pilipili_android.activity;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.pilipili_android.R;
import com.example.pilipili_android.view_model.UserViewModel;

public class SplashActivity extends BaseActivity {

    private boolean isLoginActivity = true;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Handler handler = new Handler();
        handler.postDelayed(runnable, 2000);
        if(!userViewModel.verifyLocalToken()) {
            isLoginActivity = true;
        } else {
            userViewModel.getIsValid().observe(this, isNetValid -> {
                isLoginActivity = !isNetValid;
            });
        }

        userViewModel.getIsSuccessLogin().observe(this, isSuccessLogin -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private Runnable runnable = () -> {
        if(isLoginActivity) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            userViewModel.getUserDetailInfo();

        }

    };
}
