package com.example.pilipili_android.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pilipili_android.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadVideoActivity extends AppCompatActivity {

    @BindView(R.id.button_return_publish)
    ImageView buttonReturnPublish;
    @BindView(R.id.button_publish)
    QMUIRoundButton buttonPublish;
    @BindView(R.id.button_setcover)
    TextView buttonSetcover;
    @BindView(R.id.select_part)
    TextView selectPart;
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.bumber_of_title)
    TextView bumberOfTitle;
    @BindView(R.id.select_tag)
    TextView selectTag;
    @BindView(R.id.edit_intro)
    EditText editIntro;
    @BindView(R.id.number_of_intro)
    TextView numberOfIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        ButterKnife.bind(this);

    }
}