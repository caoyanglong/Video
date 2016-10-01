package com.day.l.video.video.loader;

import android.content.Context;
import android.util.Log;

import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

/**
 * Created by cyl
 * on 2016/9/19.
 * email:670654904@qq.com
 */
public class VideoDataLoader extends BaseGetLoader {
    public VideoDataLoader(Context context, AjaxParams ajaxParams, String url) {
        super(context, ajaxParams, url);
    }

    @Override
    public JSONObject loadInBackground() {
        JSONObject object = null;
        try {
            if (ajaxParams != null) {
                Log.d("<requesturl>", url + "?" + ajaxParams.getParamString());
            }
            for (String host : Constants.VideoHostList) {
                try {
                    String content = (String) new FinalHttp().getSync(host + url, ajaxParams);
                    Log.d("<videodata_----->",content.toString());
                    object = new JSONObject(AnalysJson.getData(content));
                    if (object != null) {
                        return object;
                    }
                } catch (Exception e) {
                    Log.d("<videodata_----->",e.getMessage());
                    e.printStackTrace();
                }
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
