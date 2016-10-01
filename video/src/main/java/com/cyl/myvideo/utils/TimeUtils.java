package com.cyl.myvideo.utils;

/**
 * Created by CYL on 16-9-6.
 * email:670654904@qq.com
 */
public class TimeUtils {
    /**
     * 得到视频格式化以后的 字符串
     * @param total
     * @param mProgress
     * @return
     */
    public static String getVideoTime(int total, int mProgress){
        int t1 = mProgress / 1000;
        int s1 = t1 % 60;
        int m1 = (t1 / 60) % 60;
        int h1 = t1 / 3600;

        int t2 = total / 1000;
        int s2 = t2 % 60;
        int m2 = (t2 / 60) % 60;
        int h2 = t2 / 3600;

        String left, right;
        if (h1 > 0) {
            left = String.format("%d:%02d:%02d", h1, m1, s1);
        } else {
            left = String.format("%02d:%02d",  m1, s1);
        }

        if (h2 > 0) {
            right = String.format("%d:%02d:%02d", h2, m2, s2);
        } else {
            right = String.format("%02d:%02d",  m2, s2);
        }
        return left + "/" + right;
    }
}
