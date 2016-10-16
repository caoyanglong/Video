package com.day.l.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ImageView;

import com.day.l.video.MainActivity;
import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.SplashEntity;
import com.day.l.video.utils.AdKey;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.LoadingPicture;

import net.tsz.afinal.http.AjaxParams;
import net.youmi.android.AdManager;

import org.json.JSONObject;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 * 启动页面
 */
public class SplashActivity extends BaseFragmentActivity {
    /**
     * 设置启动时间
     */
    private final  static  long startTime = 3000;
    private CountDownTimer timer = new CountDownTimer(startTime,startTime) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            if(UserConfig.getToken() != null){
                startActivity(new Intent(context, MainActivity.class));
            }
            else{
                startActivity(new Intent(context, LoginActivity.class));
            }
            finish();
        }
    };
    @Override
    public int setContent() {
        return R.layout.splash_activity_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        getSupportLoaderManager().restartLoader(1,null,splashLoader);
        timer.start();
        AdManager.getInstance(context).init(AdKey.YOUMI_ID, AdKey.YOUMI_KEY, true, true);
    }

    @Override
    public void onBackPressed() {
        //返回按钮屏蔽
    }

    @Override
    public void initListener() {

    }
    private final LoaderManager.LoaderCallbacks<JSONObject> splashLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            return new BaseGetLoader(context, ajaxParams, Constants.SPLASH_ICON_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                SplashEntity entity = AnalysJson.getEntity(data,SplashEntity.class);
                if(entity != null){
                    try{
                        String splashUrl = entity.getData().get(0).getSrc();
                        LoadingPicture.loadPicture(context,splashUrl,(ImageView) findViewById(R.id.splash_icon));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

}
