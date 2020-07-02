package com.example.pilipili_android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.pilipili_android.R;
import com.example.pilipili_android.fragment.FanListFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FanListActivity extends AppCompatActivity {

    @BindView(R.id.tablayout_fan)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewpager_fan)
    ViewPager viewPager;

    private int id;
    private boolean isFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        isFollow = intent.getBooleanExtra("isFollow", true);
        initView();
    }

    private void initView() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FanListFragment(id, true));
        fragmentList.add(new FanListFragment(id, false));
        String[] fragmentTitle = {"粉丝", "关注"};
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentTitle.length;
            }
        });
        slidingTabLayout.setViewPager(viewPager, fragmentTitle);
        if (isFollow) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }
    }
}
