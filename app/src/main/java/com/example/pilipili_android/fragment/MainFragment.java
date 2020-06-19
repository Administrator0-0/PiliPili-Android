package com.example.pilipili_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.pilipili_android.R;
import com.example.pilipili_android.activity.MainActivity;
import com.example.pilipili_android.activity.VideoActivity;

public class MainFragment extends Fragment {

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button button = view.findViewById(R.id.video);
        button.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), VideoActivity.class);
            startActivity(intent);
        });
        return view;
    }
}