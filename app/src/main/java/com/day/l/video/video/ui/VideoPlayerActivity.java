package com.day.l.video.video.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.Toast;
import android.widget.VideoView;

import com.cyl.myvideo.widgets.MyVideoView;
import com.cyl.myvideo.widgets.VideoPlayerView;
import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.DES;
import com.day.l.video.utils.PareseVideoByWebview;
import com.day.l.video.utils.TimeUtils;
import com.day.l.video.video.entity.PlayVideoEntitiy;
import com.day.l.video.video.entity.VideoDetailEntity;
import com.day.l.video.video.loader.VideoDataLoader;
import com.day.l.video.widgets.LoadingView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

/**
 * Created by cyl
 * on 2016/9/24.
 * email:670654904@qq.com
 * 播放视频的界面
 */
public class VideoPlayerActivity extends BaseFragmentActivity implements LoadingView.LoadingListener,
        VideoPlayerView.VideoControlListener {
    private VideoPlayerView playerView;
    private LoadingView loadingView;
    private VideoDetailEntity entity;
    @Override
    public int setContent() {
        return R.layout.video_player_activity_layout;
    }

    @Override
    public void initView() {
        playerView = (VideoPlayerView) findViewById(R.id.player);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
    }

    @Override
    public void initData() {
        loadingView.setLoadingText("地址解析中...");
        loadingView.setConentContainer(playerView);
        loadingView.setRetryListener(this);
        playerView.setActivity(this);
        entity = AnalysJson.getEntity(getIntent().getStringExtra(Constants.JSON_KEY), VideoDetailEntity.class);
        loadingUrl();

    }

    private void loadingUrl() {
        loadingStart();

        PareseVideoByWebview pareseVideoByWebview = new PareseVideoByWebview(context,entity.getSiteUrl());
//        getSupportLoaderManager().restartLoader(1, null, videoLoader);
    }


    @Override
    public void initListener() {
        playerView.setOnVideoControlListener(this);
        playerView.setVideoPlayerListener(new VideoPlayerView.VideoPlayerListener() {
            @Override
            public void onVideoComplete() {
                finish();
            }

            @Override
            public void onVideoError() {
                Toast.makeText(context, "播放出错了", Toast.LENGTH_LONG).show();
                finish();
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
    }

    private final LoaderManager.LoaderCallbacks<JSONObject> videoLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            VideoDetailEntity entity = AnalysJson.getEntity(getIntent().getStringExtra(Constants.JSON_KEY), VideoDetailEntity.class);
            String index = getIntent().getStringExtra(Constants.INDEX_KEY);
            playerView.setVideoTitle(entity.getName());
            AjaxParams params = new AjaxParams();
            try {
                params.put(Constants.ID, DES.encrypt(entity.getID()));
                params.put("uType", "url");
                params.put("vType", "2");
                params.put(Constants.INDEX_KEY, index);
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put(Constants.TimeStamp, TimeUtils.getTimeStap());
            return new VideoDataLoader(context, params, Constants.GET_VIDEOS_URL_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                try {
                    PlayVideoEntitiy entitiy = AnalysJson.getEntity(data, PlayVideoEntitiy.class);
                    if (entitiy != null) {
                        String url = entitiy.getData().get(0).getList().get(0);
                        playerView.onPrepareAsync(url);
                        playerView.onStartPlay();
                    }
                    loadFinshed();
                } catch (Exception e) {
                    loadError();
                    e.printStackTrace();
                }
            } else {
                loadError();
            }

        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

    @Override
    public void loadingStart() {
        loadingView.setLoadingType(LoadingView.LoadingType.LOADING);
    }

    @Override
    public void loadError() {
        loadingView.setLoadingType(LoadingView.LoadingType.LOADERROR);
    }

    @Override
    public void loadFinshed() {
        loadingView.setLoadingType(LoadingView.LoadingType.LOADFINISHED);
    }

    @Override
    public void retryLoading() {
        loadingUrl();
    }

    @Override
    public boolean setFullScreen() {
        return true;
    }

    @Override
    public void onClickedFullScreenButton(VideoView vv) {

    }

    @Override
    public void onClickedTitleBackButton(VideoView vv) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerView != null) {
            playerView.onPausePlay();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playerView != null) {
            playerView.onResumePlay();
        }
    }
}
