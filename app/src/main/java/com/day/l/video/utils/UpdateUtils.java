package com.day.l.video.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.UpdateEntity;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

/**
 * Created by cyl
 * on 2016/9/11.
 * email:670654904@qq.com
 */
public class UpdateUtils {
    private Context context;
    private LoaderManager loaderManager;

    /**
     * 检查更新
     * @param context
     * @param loaderManager
     */
    public static void updateApp(Context context, LoaderManager loaderManager){
        new UpdateUtils(context,loaderManager).checkUpdate();
    }

    public UpdateUtils(Context context, LoaderManager loaderManager) {
        this.context = context;
        this.loaderManager = loaderManager;
    }

    /**
     * 更新
     */
    private void checkUpdate(){
        loaderManager.restartLoader(10,null,updateLoader);
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
                final UpdateEntity entity = AnalysJson.getEntity(data,UpdateEntity.class);
                if(entity.getStatus() == StatusCode.UPDATE_CODE){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle("提示")
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
                }else{
//                    Toast.makeText(context,"已经最新版本",Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };
}
