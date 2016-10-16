package com.day.l.video.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.cyl.myvideo.utils.MyLog;
import com.day.l.video.service.InstalledService;

/**
 * Created by cyl
 * on 2016/10/16.
 * email:670654904@qq.com
 */
public class AppObserver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Uri uri = intent.getData();
        String packageName = uri.toString().replace("package:","");
        MyLog.d("<receiver_action> %s",action);
        if(TextUtils.equals(action, InstalledService.ADDAPP_ACTION)){
            InstalledService.addApp(packageName);
        }
        else if(TextUtils.equals(action,InstalledService.REPLACEAPP_ACTION)){
            InstalledService.replaceApp(packageName);
        }
        else if(TextUtils.equals(action,InstalledService.DELETEAPP_ACTION)){
            InstalledService.deleteApp(packageName);

        }
    }
}
