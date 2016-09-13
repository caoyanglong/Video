package com.day.l.video.utils;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class AnalysJson {
    public static <T> T getEntity(String json,Class<T> tClass){
        try {
            return new Gson().fromJson(json,tClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T getEntity(JSONObject json, Class<T> tClass){
        try {
            return new Gson().fromJson(json.toString(),tClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
