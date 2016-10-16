package com.day.l.video.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.cyl.myvideo.utils.MyLog;
import com.day.l.video.config.UserConfig;
import com.day.l.video.ui.MathUtils;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.SharePrefrenceUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cyl
 * on 2016/9/11.
 * email:670654904@qq.com
 */
public class InstalledService extends Service {
    public final static String ADDAPP_ACTION = "android.intent.action.PACKAGE_ADDED";
    public final static String REPLACEAPP_ACTION = "android.intent.action.PACKAGE_REPLACED";
    public final static String DELETEAPP_ACTION = "android.intent.action.PACKAGE_REMOVED";
    private static PackageManager mPackageManager;

    /**
     * 记录已安装的应用
     */
    private static Map<String,PackageInfo> appList = new LinkedHashMap<>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(installedReceiver, filter);
        checkToken();
        //添加一个新的应用
        IntentFilter installedFilter = new IntentFilter(ADDAPP_ACTION);
        IntentFilter replaceFilter = new IntentFilter(REPLACEAPP_ACTION);
        IntentFilter removeFilter = new IntentFilter(REPLACEAPP_ACTION);

        registerReceiver(serviceReceiver,installedFilter);
        registerReceiver(serviceReceiver,replaceFilter);
        registerReceiver(serviceReceiver,removeFilter);

        mPackageManager = getPackageManager();
    }

    /**
     * 检查token
     */
    private void checkToken() {
        if (UserConfig.getToken() != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        MathUtils.isExpaireDate(getApplicationContext());
                        if (SharePrefrenceUtil.isExit(getApplicationContext(), Constants.EXPIRATION_TIME_KEY)) {
                            int time = Integer.valueOf(SharePrefrenceUtil.getProperties(getApplicationContext(), Constants.EXPIRATION_TIME_KEY));
                            if (time - (System.currentTimeMillis() / 1000) < 60 * 60 * 24 * 2) {
                                Log.d("<token------->", "token 即将过期 刷新token" + (System.currentTimeMillis() / (1000 / 24 * 60 * 60)));
                                AjaxParams ajaxParams = new AjaxParams();
                                ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
                                String content = (String) new FinalHttp().getSync(Constants.REFRESH_TOKEN_API, ajaxParams);

                            } else {
                                Log.d("<token------->", "token 剩余" + time + "-" + (System.currentTimeMillis() / (1000 / 24 * 60 * 60)) + "days");
                                AjaxParams ajaxParams = new AjaxParams();
                                ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
                                String content = (String) new FinalHttp().getSync(Constants.REFRESH_TOKEN_API, ajaxParams);
                                Log.d("<token_log>", content);
                            }
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("<token------->", "token 剩余=====");
                    }
                }
            }).start();
        }
    }

    private BroadcastReceiver installedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                Log.d("<download_content_msg>", intent.toString());
                installFile(id);
            }
        }
    };

    private void installFile(long id) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // 查询
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor cursor = downloadManager.query(query);
        String apkPath = null;

//        install.setDataAndType(downloadFileUri, "application/vnd.Android.package-archive");
//        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            while (cursor.moveToNext()) {
                apkPath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                Uri uri = Uri.parse(apkPath);
                Intent intent = new Intent();
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri,
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }
//            startActivity(install);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initAppList();
        return START_FLAG_RETRY;
    }

    private void initAppList() {
        synchronized (appList){
            PackageManager pm = getPackageManager();
            // Return a List of all packages that are installed on the device.
            List<PackageInfo> packages = pm.getInstalledPackages(0);
            for (PackageInfo info:packages){
                appList.put(info.packageName,info);
            }
        }
    }

    private BroadcastReceiver serviceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            MyLog.d("<receiver_action> %s",action);
            if(TextUtils.equals(action,ADDAPP_ACTION)){

            }
            else if(TextUtils.equals(action,REPLACEAPP_ACTION)){

            }
            else if(TextUtils.equals(action,DELETEAPP_ACTION)){

            }
        }
    };

    @Override
    public void onDestroy() {
        if(serviceReceiver != null){
            unregisterReceiver(serviceReceiver);
        }
        super.onDestroy();
    }

    /**
     * 安装一个新的应用
     * 更新列表
     */
    public static void addApp(String packageName){
        synchronized (appList){
            try {
                if(null != mPackageManager){
                    PackageInfo info = mPackageManager.getPackageInfo(packageName,0);
                    appList.put(info.packageName,info);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 替换一个应用版本
     * @param packageName
     */
    public static void replaceApp(String packageName){
        synchronized (appList){
            try {
                if(null != mPackageManager){
                    PackageInfo info = mPackageManager.getPackageInfo(packageName,0);
                    appList.put(info.packageName,info);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 卸载应用 删除内存中的应用列表
     * @param packageName
     */
    public static void deleteApp(String packageName){
        synchronized (appList){
            try {
                if(null != mPackageManager){
                    PackageInfo info = mPackageManager.getPackageInfo(packageName,0);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                //
                if(appList.containsKey(packageName)){
                    appList.remove(packageName);
                }
            }
        }
    }

    /**
     * 获取对应应用的信息
     * @param packName
     * @return
     */
    public static PackageInfo getPackageInfo(String packName){
        if(appList.containsKey(packName)){
            return appList.get(packName);
        }
        return null;
    }
}
