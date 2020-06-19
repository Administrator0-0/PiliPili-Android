package com.example.pilipili_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.pilipili_android.R;
import com.example.pilipili_android.fragment.MineFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mineFragment = new MineFragment();
        fragmentManager.beginTransaction().add(R.id.box, mineFragment).commit();

    }
}
