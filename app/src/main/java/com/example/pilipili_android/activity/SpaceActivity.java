package com.example.pilipili_android.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pilipili_android.R;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpaceActivity extends AppCompatActivity {

    @BindView(R.id.button_return_inspace)
    ImageButton buttonReturnInspace;
    @BindView(R.id.button_search_inspace)
    ImageButton buttonSearchInspace;
    @BindView(R.id.button_share_inspace)
    ImageButton buttonShareInspace;
    @BindView(R.id.text_numberoffans_inspace)
    TextView textNumberoffansInspace;
    @BindView(R.id.text_numberoffocus_inspace)
    TextView textNumberoffocusInspace;
    @BindView(R.id.text_numberofpraise_inspace)
    TextView textNumberofpraiseInspace;
    @BindView(R.id.button_editmessage_inspace)
    QMUIRoundButton buttonEditmessageInspace;
    @BindView(R.id.text_name_inspace)
    TextView textNameInspace;
    @BindView(R.id.button_tomedal)
    QMUIRoundButton buttonTomedal;
    @BindView(R.id.button_details_inspace)
    QMUIRoundButton buttonDetailsInspace;
    @BindView(R.id.text_sign_inspace)
    TextView textSignInspace;
    @BindView(R.id.text_mainpage_inspace)
    TextView textMainpageInspace;
    @BindView(R.id.text_trends_inspace)
    TextView textTrendsInspace;
    @BindView(R.id.text_publish_inspace)
    TextView textPublishInspace;
    @BindView(R.id.text_collection_inspace)
    TextView textCollectionInspace;
    @BindView(R.id.viewpager_inspace)
    QMUIViewPager viewpagerInspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);
        ButterKnife.bind(this);
    }
}