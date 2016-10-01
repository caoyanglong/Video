package com.day.l.video.video.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.utils.Constants;

/**
 * Created by cyl
 * on 2016/9/18.
 * email:670654904@qq.com
 * 更多视频
 */
public class MoreVideoDetailActivity extends BaseFragmentActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public int setContent() {
        return R.layout.video_detail_activity_layout;
    }

    @Override
    public void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab_indicator);
        viewPager = (ViewPager) findViewById(R.id.content_container);
        viewPager.setAdapter(new VideoAdapter(fragmentManager));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initData() {
        setActionBarCenterTile("影视");
    }

    @Override
    public void initListener() {

    }
    public class VideoAdapter extends FragmentPagerAdapter {
        public final String[] title = new String[]{"电影","电视剧"};
        public VideoAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position){
                case 0:
                    bundle.putString(Constants.TYPE_KEY,"1");
                    return VideoFragment.getInstance(bundle);
                case 1:
                    bundle.putString(Constants.TYPE_KEY,"2");
                    return VideoFragment.getInstance(bundle);
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
