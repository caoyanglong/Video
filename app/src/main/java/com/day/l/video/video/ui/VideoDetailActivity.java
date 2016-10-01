package com.day.l.video.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.DES;
import com.day.l.video.utils.LoadingPicture;
import com.day.l.video.utils.TimeUtils;
import com.day.l.video.video.entity.VideoDetailEntity;
import com.day.l.video.video.loader.VideoDataLoader;
import com.day.l.video.widgets.LoadingView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

/**
 * Created by cyl
 * on 2016/9/20.
 * email:670654904@qq.com
 * 视频详情页面
 */
public class VideoDetailActivity extends BaseFragmentActivity implements LoadingView.LoadingListener {
    private ImageView videoIcon;
    private TextView nameTv, areaTv, directorTv, typeTv, scoreTv;
    private LoadingView loadingView;
    private View contentContainer;
    private String videoId;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private VideoAdapter adapter;
    private JSONObject jsonObject;

    @Override
    public int setContent() {
        return R.layout.videos_detail_activity_layout;
    }

    @Override
    public void initView() {
        videoIcon = (ImageView) findViewById(R.id.video_icon);
        nameTv = (TextView) findViewById(R.id.video_name);
        areaTv = (TextView) findViewById(R.id.area);
        directorTv = (TextView) findViewById(R.id.actor);
        typeTv = (TextView) findViewById(R.id.video_type);
        scoreTv = (TextView) findViewById(R.id.video_score);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
        contentContainer = findViewById(R.id.top_container);
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,VideoPlayerActivity.class);
                intent.putExtra(Constants.INDEX_KEY,"1");
                intent.putExtra(Constants.JSON_KEY,jsonObject.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        videoId = getIntent().getStringExtra(Constants.ID);
        setActionBarCenterTile(getIntent().getStringExtra(Constants.VIDEO_NAME));
        loadingView.setConentContainer(contentContainer);

        getVideoInfos();
    }

    private void getVideoInfos() {
        loadingStart();
        getSupportLoaderManager().restartLoader(1, null, videoInfosLoader);
    }

    @Override
    public void initListener() {
        loadingView.setRetryListener(this);
    }

    private final LoaderManager.LoaderCallbacks<JSONObject> videoInfosLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams params = new AjaxParams();
            try {
                params.put(Constants.ID, DES.encrypt(videoId));
                params.put(Constants.TimeStamp, TimeUtils.getTimeStap());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new VideoDataLoader(context, params, Constants.GET_VIDEO_DETAIL_INFO_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                jsonObject = data;
                VideoDetailEntity videoDetailEntity = AnalysJson.getEntity(data, VideoDetailEntity.class);
                if (videoDetailEntity != null) {
                    addDataToView(videoDetailEntity);
                }
                loadFinshed();
            } else {
                loadError();
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

    private void addDataToView(VideoDetailEntity videoDetailEntity) {
        LoadingPicture.loadPicture(context,videoDetailEntity.getImageUrl(),videoIcon);
        nameTv.setText(videoDetailEntity.getName());
        areaTv.setText(getResources().getString(R.string.area,videoDetailEntity.getArea()));
        directorTv.setText(getResources().getString(R.string.actor,videoDetailEntity.getDirector()));
        typeTv.setText(getResources().getString(R.string.video_type,videoDetailEntity.getType()));
        scoreTv.setText(getResources().getString(R.string.video_score,videoDetailEntity.getStar()));
        try {
            tabLayout = (TabLayout) findViewById(R.id.indicator);
            viewPager = (ViewPager) findViewById(R.id.pager);
            int type = Integer.parseInt(videoDetailEntity.getAppType());
            adapter = new VideoAdapter(getSupportFragmentManager(),type);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    private final class VideoAdapter extends FragmentPagerAdapter {
        public String[] title = new String[]{"选集","详情"};
        private int type;
        public VideoAdapter(FragmentManager fm, int type) {
            super(fm);
            if(type == 1){
                title = new String[]{"详情"};
            }else if(type == 2){
                title = new String[]{"选集","详情"};
            }
            this.type = type;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.JSON_KEY,jsonObject.toString());
            switch (position){

                case 0:
                    return type==1?VideoDetailFragment.getInstance(bundle):SelectVideoDetailFragment.getInstance(bundle);
                case 1:
                    return VideoDetailFragment.getInstance(bundle);
            }
            return null;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

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
        getVideoInfos();
    }
}
