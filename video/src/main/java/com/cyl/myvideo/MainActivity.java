package com.cyl.myvideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyl.myvideo.widgets.MyVideoView;
import com.cyl.myvideo.widgets.VideoPlayerView;

public class MainActivity extends AppCompatActivity {
    private VideoPlayerView videoPlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoPlayerView = (VideoPlayerView) findViewById(R.id.video_player);
        videoPlayerView.setActivity(this);
        videoPlayerView.setVideoPlayerListener(new VideoPlayerView.VideoPlayerListener() {
            @Override
            public void onVideoComplete() {

            }

            @Override
            public void onVideoError() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onPrepared(MyVideoView vv) {

            }
        });
        videoPlayerView.onPrepareAsync("http://100.100.100.100/youku/676AF7AAA54181CB1E4CA5F75/030008070057BE873C15D9363C9D762EFD81E1-9B9E-6E6D-1C17-CB55F9E0DDA1.mp4");
        videoPlayerView.onStartPlay();
    }
}
