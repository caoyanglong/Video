package com.day.l.video.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class BasePostLoader extends AsyncTaskLoader<JSONObject> {
    private AjaxParams ajaxParams;
    private String url;
    public BasePostLoader(Context context, AjaxParams ajaxParams, String url) {
        super(context);
        this.ajaxParams = ajaxParams;
        this.url = url;
        forceLoad();
    }

    @Override
    public JSONObject loadInBackground() {
        try {
            if(ajaxParams != null){
                Log.d("<requesturl>",url+"?"+ajaxParams.getParamString());
            }
            return new JSONObject( (String) new FinalHttp().postSync(url,ajaxParams));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
