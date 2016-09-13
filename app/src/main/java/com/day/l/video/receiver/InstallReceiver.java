package com.day.l.video.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by cyl
 * on 2016/9/13.
 * email:670654904@qq.com
 */
public class InstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            installFile(context,id);
        }
    }
    private void installFile(Context context,long id) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        Uri downloadFileUri = ((DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE)).getUriForDownloadedFile(id);
//        Log.d("<downloaduri>",downloadFileUri.toString());
//        install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//
//        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(install);
    }
}
