package com.example.pilipili_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pilipili_android.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.buy_coin)).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PayActivity.class);
            startActivity(intent);
        });
    }
}
