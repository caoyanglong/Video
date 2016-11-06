package com.day.l.video;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioGroup;

import com.cyl.myvideo.utils.MyLog;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.UpdateEntity;
import com.day.l.video.ui.AppContainerFragment;
import com.day.l.video.ui.HomeFragment;
import com.day.l.video.ui.MyFragment;
import com.day.l.video.ui.RedEnvelopeFragment;
import com.day.l.video.ui.SurperiseFragment;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.DownloadUtils;
import com.day.l.video.utils.PhoneVersion;
import com.day.l.video.utils.StatusCode;

import net.tsz.afinal.http.AjaxParams;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

import org.json.JSONObject;

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
            getSupportLoaderManager().restartLoader(100,null,updateLoader);
            canCheck = false;
        }
    }

    private Dialog updateDialog;
    /**
     * 更新版本
     */
    private final LoaderManager.LoaderCallbacks<JSONObject> updateLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.SYSTEM_KEY, "android");
            ajaxParams.put(Constants.VERIOSN_KEY, PhoneVersion.getVersion(context));
            return new BaseGetLoader(context, ajaxParams, Constants.UPDATE_VERSION_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                Log.d("json_key",data.toString());
                try {
                    final UpdateEntity entity = AnalysJson.getEntity(data,UpdateEntity.class);
                    if(entity.getStatus() == StatusCode.SUCCESS_CODE){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                .setTitle("发现新版本")
                                .setMessage(entity.getMsg())
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        updateDialog.cancel();
                                    }
                                })
                                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DownloadUtils.addDownloadTask(context,entity.getData().getLink(),entity.getData().getVersion()+".apk");
                                    }
                                });
                        updateDialog = builder.create();
                        updateDialog.show();
                    }
                    else{
//                        Toast.makeText(context,"已经最新版本",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };


    @Override
    public void onBackPressed() {
//        long now = System.currentTimeMillis();
//        if(now - currentTime < 1000){
//            super.onBackPressed();
//        }
//        else{
//            Toast.makeText(context,"再按一次，退出应用",Toast.LENGTH_LONG).show();
//            currentTime = now;
//
//        }
        SpotManager.getInstance(context).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);
        SpotManager.getInstance(context).setAnimationType( SpotManager.ANIMATION_TYPE_NONE);
        SpotManager.getInstance(context).showSpot(context,
                new SpotListener() {
                    @Override
                    public void onShowSuccess() {
                        MyLog.d("<show_success> %s","show_success");
                    }

                    @Override
                    public void onShowFailed(int i) {
                        MyLog.d("<show_success> %s","onShowFailed"+i);
                    }

                    @Override
                    public void onSpotClosed() {
                        MyLog.d("<show_success> %s","onSpotClosed");
                    }

                    @Override
                    public void onSpotClicked(boolean b) {
                        MyLog.d("<show_success> %s","onSpotClicked");
                    }
                });
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
