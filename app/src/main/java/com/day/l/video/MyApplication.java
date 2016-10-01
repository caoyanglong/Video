package com.day.l.video;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.day.l.video.base.BaseApplication;
import com.day.l.video.config.UserConfig;
import com.day.l.video.model.StatusEntity;
import com.day.l.video.service.InstalledService;
import com.day.l.video.share.WXShare;
import com.day.l.video.ui.MathUtils;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.SharePrefrenceUtil;
import com.day.l.video.utils.StatusCode;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class MyApplication extends BaseApplication {
    /**
     * 分享获取红包的action
     */
    public static final String GET_RED_BAGS_ACTION = "com.day.l.video.GET_RED_BAGS_ACTION";
    public static IWXAPI api;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if(SharePrefrenceUtil.isExit(this, Constants.TOKEN)){
            if(!MathUtils.isExpaireDate(this)){
                UserConfig.setToken(SharePrefrenceUtil.getProperties(this,Constants.TOKEN));
            }

        }
        startService(new Intent(this, InstalledService.class));

//        MobclickAgent.UMAnalyticsConfig(this, ShareConstants.UMENT_KEY, "c1");
        api = WXAPIFactory.createWXAPI(this, WXShare.WX_KEY, false);
        api.registerApp(WXShare.WX_KEY);
        IntentFilter filter = new IntentFilter(GET_RED_BAGS_ACTION);
        registerReceiver(redBagsReceiver,filter);
    }
    public static Context getMyContext(){
        return context;
    }
    private BroadcastReceiver redBagsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            if(TextUtils.equals(intent.getAction(),GET_RED_BAGS_ACTION)){
                AjaxParams ajaxParams = new AjaxParams();
                ajaxParams.put(Constants.TOKEN,UserConfig.getToken());
                ajaxParams.put(Constants.RED_BAGS_ID, intent.getStringExtra(Constants.RED_BAGS_ID));
                Log.d("<share_success>","ssssssssssssssssssss"+intent.getStringExtra(Constants.RED_BAGS_ID));
                new FinalHttp().get(Constants.GET_SHARE_RED_BAGS_API, ajaxParams, new AjaxCallBack<String>() {
                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        Toast.makeText(getApplicationContext(),"分享失败",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        super.onSuccess(s);
                        StatusEntity code = AnalysJson.getEntity(s,StatusEntity.class);
                        if(code != null){
                            if(code.getStatus() == 1){
                                Toast.makeText(getApplicationContext(),"恭喜你，获得一个红包",Toast.LENGTH_SHORT).show();
                            }
                            else if(code.getStatus() == StatusCode.SHARE_REPEAT){
                                Toast.makeText(getApplicationContext(),"您以领取该红包",Toast.LENGTH_SHORT).show();
                            }
                        }


                    }

                });
            }
        }
    };

}











