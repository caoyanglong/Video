package com.day.l.video.utils;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

/**
 * Created by cyl
 * on 2016/10/15.
 * email:670654904@qq.com
 */
public class BannerAd {
    private LinearLayout bannerLayout;
    private Context context;

    public BannerAd(LinearLayout bannerLayout, Context context) {
        this.bannerLayout = bannerLayout;
        this.context = context;
    }
    public void showBanner(){
        // 获取广告条
        View bannerView = BannerManager.getInstance(context)
                .getBannerView(new BannerViewListener() {
                    @Override
                    public void onRequestSuccess() {
                        bannerLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSwitchBanner() {

                    }

                    @Override
                    public void onRequestFailed() {
                        bannerLayout.setVisibility(View.GONE);
                    }
                });


// 将广告条加入到布局中
        bannerLayout.addView(bannerView);
    }
}
