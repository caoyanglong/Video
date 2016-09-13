package com.day.l.video.ui;

import android.content.Context;

import com.day.l.video.utils.Constants;
import com.day.l.video.utils.SharePrefrenceUtil;

/**
 * Created by cyl
 * on 2016/9/13.
 * email:670654904@qq.com
 */
public class MathUtils {
    /**
     * 判断是否过期
     * @param context
     * @return
     */
    public static boolean isExpaireDate(Context context){
        try {
            return Long.valueOf(SharePrefrenceUtil.getProperties(context, Constants.EXPIRATION_TIME_KEY)) <= System.currentTimeMillis()/1000;
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断剩余是否小于两天
     * @return
     */
    public static boolean leftTwoDays(Context context){
        try {
            return Long.valueOf(SharePrefrenceUtil.getProperties(context, Constants.EXPIRATION_TIME_KEY)) - System.currentTimeMillis()/1000 > 2*24*60*60;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
