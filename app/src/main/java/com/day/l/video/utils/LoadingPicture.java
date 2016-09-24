package com.day.l.video.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.day.l.video.R;

/**
 * Created by cyl
 * on 2016/9/11.
 * email:670654904@qq.com
 */
public class LoadingPicture {
    public static void loadPicture(Context context, String url, ImageView imageView){
//        Picasso.with(context).load(url).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(imageView);
        Glide.with(context).load(Constants.getImageUrl(url)).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

    /**
     * 圆形加载无缓存
     */
    public static void loadCircleImageNoCache(final Context context, String url, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop().
                into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


}
