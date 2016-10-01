package com.cyl.myvideo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;
/**
 * Created by cyl
 * on 2016/9/6.
 * email:670654904@qq.com
 */
public class MyVideoView extends VideoView {

    private VideoPlayerView.VideoPlayerListener videoListener;

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void pause() {
        super.pause();
        if (videoListener != null) {
            videoListener.onPause();
        }
    }

    @Override
    public void start() {
        super.start();

        if (videoListener != null) {
            videoListener.onStart();
        }
    }

    public void setVideoListener(VideoPlayerView.VideoPlayerListener videoListener) {
        this.videoListener = videoListener;
    }

}
