package com.day.l.video.utils;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by cyl
 * on 2016/10/15.
 * email:670654904@qq.com
 */
public class AdManager {
    public static void showBannerAd(Context context, LinearLayout bannerLayout){
        new BannerAd(bannerLayout,context).showBanner();
    }

}
