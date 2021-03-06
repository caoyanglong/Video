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
public class BaseGetLoader extends AsyncTaskLoader<JSONObject> {
    protected AjaxParams ajaxParams;
    protected String url;
    public BaseGetLoader(Context context, AjaxParams ajaxParams, String url) {
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
            String content = (String) new FinalHttp().getSync(url,ajaxParams);
            JSONObject object = new JSONObject(content);
            Log.d("<coentent>",object.toString());
            return object;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
