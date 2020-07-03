package com.example.pilipili_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pilipili_android.R;
import com.example.pilipili_android.constant.DefaultConstant;
import com.example.pilipili_android.view_model.UserBaseDetail;
import com.flyco.tablayout.SlidingTabLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    @BindView(R.id.avatar)
    QMUIRadiusImageView avatar;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.tablayout_main)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewpager_main)
    ViewPager viewPager;

    private Unbinder unbinder;

    public MainFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initFragment();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(UserBaseDetail.isAvatarDefault(getContext())) {
            avatar.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(DefaultConstant.AVATAR_IMAGE_DEFAULT));
        } else {
            Glide.with(this).load(UserBaseDetail.getAvatarPath(getContext())).diskCacheStrategy(DiskCacheStrategy.NONE).into(avatar);
        }
    }

    private void initFragment() {
        RecommendFragment recommendFragment = new RecommendFragment();
        AnimeFragment animeFragment = new AnimeFragment();
        HotFragment hotFragment = new HotFragment();
        ColorFragment colorFragment = new ColorFragment();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(recommendFragment);
        fragmentList.add(hotFragment);
        fragmentList.add(animeFragment);
        fragmentList.add(colorFragment);
        String[] fragmentTitle = {"推荐", "热门", "追番", "资源"};
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
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
    }


    @OnClick(R.id.avatar)
    void onViewClicked() {
        EventBus.getDefault().post(FragmentMsg.getInstance("MainFragment", "showMineFragment"));
    }
}