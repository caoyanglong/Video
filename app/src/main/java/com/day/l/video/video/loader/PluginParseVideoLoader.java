package com.day.l.video.video.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.day.l.video.utils.Constants;

import net.tsz.afinal.FinalHttp;

/**
 * Created by cyl
 * on 2016/10/19.
 * email:670654904@qq.com
 */
public class PluginParseVideoLoader extends AsyncTaskLoader<String> {
    /**
     * 网页url
     */
    private String sourceUrl;
    public PluginParseVideoLoader(Context context,String sourceUrl) {
        super(context);
        this.sourceUrl = sourceUrl;
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String content = (String) new FinalHttp().getSync(Constants.PLUGIN_API+sourceUrl);
        return content;
    }
}
