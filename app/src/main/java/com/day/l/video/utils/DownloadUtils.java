package com.day.l.video.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by cyl
 * on 2016/9/17.
 * email:670654904@qq.com
 */
public class DownloadUtils {
    /**
     * 添加下载任务
     * @param context
     * @param src     下载对应的目标地址
     * @param name    下载的名字
     * @return
     */
    public static long addDownloadTask(Context context,String src,String name){
        if(!src.contains("http")){
            return -1;
        }
        DownloadManager downloadManager;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(src);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setTitle(name);
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, name+".apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE|DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        long reference = downloadManager.enqueue(request);
        return reference;
    }
}
