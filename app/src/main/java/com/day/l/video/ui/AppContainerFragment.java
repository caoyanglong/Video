package com.day.l.video.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.utils.Constants;

/**
 * Created by cyl
 * on 2016/9/18.
 * email:670654904@qq.com
 */
public class AppContainerFragment extends BaseLazyFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public int setContentView() {
        return R.layout.app_container_fragment_layout;
    }

    @Override
    public void initView(View view) {
        tabLayout = (TabLayout) findViewById(R.id.tab_indicator);
        viewPager = (ViewPager) findViewById(R.id.content_container);
        viewPager.setAdapter(new MainFragmentAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
    public class MainFragmentAdapter extends FragmentPagerAdapter {
        public final String[] title = new String[]{"应用","游戏"};
        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position){

                case 0:
                    return new AppListFragment();
                case 1:
                    bundle.putString("type",Constants.GAME_KEY);
                    return new GameListFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
