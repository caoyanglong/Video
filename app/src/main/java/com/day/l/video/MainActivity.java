package com.day.l.video;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.ui.AppContainerFragment;
import com.day.l.video.ui.HomeFragment;
import com.day.l.video.ui.MyFragment;
import com.day.l.video.ui.RedEnvelopeFragment;
import com.day.l.video.ui.SurperiseFragment;
import com.day.l.video.utils.UpdateUtils;

public class MainActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener{
    private ViewPager mainVP;
    private RadioGroup radioGroup;
    private  MainFragmentAdapter adapter;
    private long currentTime;
    @Override
    public int setContent() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mainVP = (ViewPager) findViewById(R.id.container_vp);
        radioGroup = (RadioGroup) findViewById(R.id.control_button_container);
        adapter = new MainFragmentAdapter(getSupportFragmentManager());
        mainVP.setAdapter(adapter);
        mainVP.setCurrentItem(0);
    }

    @Override
    public void initData() {
        setActionBarCenterTile("首页");
        mainVP.setOffscreenPageLimit(5);
        hideTitleBack();
        hideActionbar();
    }

    @Override
    public void initListener() {
        mainVP.setOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }
    public class MainFragmentAdapter extends FragmentPagerAdapter{
        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new HomeFragment();
                case 1:
                    return new AppContainerFragment();
                case 2:
                    return new SurperiseFragment();
                case 3:
                    return new RedEnvelopeFragment();
                case 4:
                    return new MyFragment();


            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                setActionBarCenterTile("首页");
                radioGroup.check(R.id.home);

                break;
            case 1:
                setActionBarCenterTile("应用");
                radioGroup.check(R.id.app_list);
                break;
            case 2:
                setActionBarCenterTile("惊喜");
                radioGroup.check(R.id.surperise);
                break;
            case 3:
                setActionBarCenterTile("红包");
                radioGroup.check(R.id.red_envelope);
                break;
            case 4:
                setActionBarCenterTile("我");
                radioGroup.check(R.id.me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.home:
                    mainVP.setCurrentItem(0);
                    break;
                case R.id.app_list:
                    mainVP.setCurrentItem(1);
                    break;
                case R.id.surperise:
                    mainVP.setCurrentItem(2);
                    break;
                case R.id.red_envelope:
                    mainVP.setCurrentItem(3);
                    break;
                case R.id.me:
                    mainVP.setCurrentItem(4);
                    break;
            }
    }
    private boolean canCheck = true;
    @Override
    protected void onResume() {
        super.onResume();
        if(canCheck){
            UpdateUtils.updateApp(context,getSupportLoaderManager());
            canCheck = false;
        }
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if(now - currentTime < 1000){
            super.onBackPressed();
        }
        else{
            Toast.makeText(context,"再按一次，退出应用",Toast.LENGTH_LONG).show();
            currentTime = now;

        }
    }

    /**
     * 监听activity 的返回结果
     */
    public interface ActivityResultListener{
        void onFragmentResult(int requestCode, int resultCode, Intent intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(listener != null){
            listener.onFragmentResult(requestCode,resultCode,data);
        }
    }
    private ActivityResultListener listener;
    public void setOnActivityResultListener(ActivityResultListener listener){
        this.listener = listener;
    }
}
