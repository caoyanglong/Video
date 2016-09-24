package com.day.l.video.utils;

/**
 * Created by cyl
 * on 2016/9/18.
 * email:670654904@qq.com
 */
public class TimeUtils {
    /**
     * 得到10 位的时间戳
     * @return
     */
    public static String getTimeStap(){
        String timeStap = System.currentTimeMillis()+"";
        return timeStap.substring(0, 10);
    }
}
