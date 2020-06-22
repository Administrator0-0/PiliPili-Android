package com.example.pilipili_android.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.pilipili_android.R;
import com.example.pilipili_android.databinding.ActivitySpaceBinding;
import com.example.pilipili_android.view_model.UserViewModel;

import butterknife.ButterKnife;

public class SpaceActivity extends AppCompatActivity {

    ActivitySpaceBinding activitySpaceBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBind();
        initView();

        activitySpaceBinding.getUserViewModel().getSpaceActivityBean().observe(this, spaceActivityBean -> {
            activitySpaceBinding.setSpaceActivityBean(spaceActivityBean);
        });
    }

    private void initBind() {
        activitySpaceBinding = DataBindingUtil.setContentView(this, R.layout.activity_space);
        activitySpaceBinding.setUserViewModel(new ViewModelProvider(this).get(UserViewModel.class));
        ButterKnife.bind(this);
    }

    private void initView(){
        activitySpaceBinding.getUserViewModel().getUserSpaceData();
    }
}