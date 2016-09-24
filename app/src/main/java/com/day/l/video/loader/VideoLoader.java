package com.day.l.video.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.day.l.video.model.VideoEntity;
import com.day.l.video.model.VideoStateEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.DES;
import com.day.l.video.utils.TimeUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by cyl
 * on 2016/9/18.
 * email:670654904@qq.com
 */
public class VideoLoader extends AsyncTaskLoader<VideoEntity> {
    public VideoLoader(Context context) {
        super(context);
        forceLoad();
    }

    @Override
    public VideoEntity loadInBackground() {
        FinalHttp finalHttp = null;
        try {
            AjaxParams videoParams = new AjaxParams();
            videoParams.put("uID",DES.encrypt(""));
            videoParams.put("TimeStamp",TimeUtils.getTimeStap());
            String videoData = (String) finalHttp.getSync(Constants.GET_VIDEO_DATA_API,videoParams);
            VideoEntity videoEntity = AnalysJson.getEntity(DES.decryptMsg(AnalysJson.getEntity(videoData, VideoStateEntity.class).getData()),VideoEntity.class);
            return videoEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
