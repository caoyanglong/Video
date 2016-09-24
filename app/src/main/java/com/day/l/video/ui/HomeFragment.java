package com.day.l.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.CenterAdEntitiy;
import com.day.l.video.model.HeaderAdIconEntitiy;
import com.day.l.video.model.VideoEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.DES;
import com.day.l.video.utils.LoadingPicture;
import com.day.l.video.utils.TimeUtils;
import com.day.l.video.video.ui.MoreVideoDetailActivity;
import com.day.l.video.video.loader.VideoDataLoader;
import com.day.l.video.video.ui.VideoDetailActivity;
import com.day.l.video.web.WebDetailActivity;
import com.day.l.video.widgets.LoadingView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class HomeFragment extends BaseLazyFragment implements LoadingView.LoadingListener,
        View.OnClickListener {
    private ViewPager viewPager;
    private List<HeaderAdIconEntitiy.DataBean> dataSource = new ArrayList<>();
    private MyAdapter adapter = new MyAdapter();
    private ImageView centerAd;
    private View contentContainer;
    private LoadingView loadingView;
    private ImageView video1, video2, video3,video4, video5, video6;
    private View moreVideo;

    @Override
    public int setContentView() {
        return R.layout.home_fragment_layout;
    }

    @Override
    public void initView(View view) {
        viewPager = (ViewPager) findViewById(R.id.header_ad_vp);
        centerAd = (ImageView) findViewById(R.id.center_ad);
        viewPager.setAdapter(adapter);
        contentContainer = findViewById(R.id.content_container);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
        video1 = (ImageView) findViewById(R.id.video1);
        video2 = (ImageView) findViewById(R.id.video2);
        video3 = (ImageView) findViewById(R.id.video3);
        video4 = (ImageView) findViewById(R.id.video4);
        video5 = (ImageView) findViewById(R.id.video5);
        video6 = (ImageView) findViewById(R.id.video6);
        moreVideo = findViewById(R.id.more_video);
    }

    @Override
    public void initData() {
        loadingView.setConentContainer(contentContainer);
        loadingStart();
        getLoaderManager().restartLoader(1, null, headerAdLoader);
        getLoaderManager().restartLoader(2, null, centerAdLoader);
        getLoaderManager().restartLoader(3, null, videoEntityLoader);
    }

    @Override
    public void initListener() {
        moreVideo.setOnClickListener(this);
    }

    private final LoaderManager.LoaderCallbacks<JSONObject> headerAdLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            return new BaseGetLoader(context, ajaxParams, Constants.HEADER_AD_ICON_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                HeaderAdIconEntitiy entitiy = AnalysJson.getEntity(data, HeaderAdIconEntitiy.class);
                if (entitiy != null) {
                    dataSource.addAll(entitiy.getData());
                    adapter.notifyDataSetChanged();
                    loadFinshed();
                } else {
                    loadError();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };
    private final LoaderManager.LoaderCallbacks<JSONObject> videoEntityLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams videoParams = new AjaxParams();
            try {
                videoParams.put("uID", DES.encrypt(""));
                videoParams.put("TimeStamp", TimeUtils.getTimeStap());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new VideoDataLoader(context, videoParams, Constants.GET_VIDEO_DATA_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                try {
                    VideoEntity entity = AnalysJson.getEntity(data,VideoEntity.class);
                    initVideoListener(entity);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

    private void initVideoListener(final VideoEntity entity) {
        LoadingPicture.loadPicture(context, entity.getVideo().get(0).getImageUrl(), video1);
        LoadingPicture.loadPicture(context, entity.getVideo().get(1).getImageUrl(), video2);
        LoadingPicture.loadPicture(context, entity.getVideo().get(2).getImageUrl(), video3);
        LoadingPicture.loadPicture(context, entity.getVideo().get(3).getImageUrl(), video4);
        LoadingPicture.loadPicture(context, entity.getVideo().get(4).getImageUrl(), video5);
        LoadingPicture.loadPicture(context, entity.getVideo().get(5).getImageUrl(), video6);
        video1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra(Constants.ID,entity.getVideo().get(0).getID());
                intent.putExtra(Constants.VIDEO_NAME,entity.getVideo().get(0).getName());
                startActivity(intent);
            }
        });
        video2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra(Constants.ID,entity.getVideo().get(1).getID());
                intent.putExtra(Constants.VIDEO_NAME,entity.getVideo().get(1).getName());
                startActivity(intent);
            }
        });
        video3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra(Constants.ID,entity.getVideo().get(2).getID());
                intent.putExtra(Constants.VIDEO_NAME,entity.getVideo().get(2).getName());
                startActivity(intent);
            }
        });
        video4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra(Constants.ID,entity.getVideo().get(3).getID());
                intent.putExtra(Constants.VIDEO_NAME,entity.getVideo().get(3).getName());
                startActivity(intent);
            }
        });
        video5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra(Constants.ID,entity.getVideo().get(4).getID());
                intent.putExtra(Constants.VIDEO_NAME,entity.getVideo().get(4).getName());
                startActivity(intent);
            }
        });
        video6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra(Constants.ID,entity.getVideo().get(5).getID());
                intent.putExtra(Constants.VIDEO_NAME,entity.getVideo().get(5).getName());
                startActivity(intent);
            }
        });

    }

    private final LoaderManager.LoaderCallbacks<JSONObject> centerAdLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            return new BaseGetLoader(context, ajaxParams, Constants.CENTER_AD_ICON_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                final CenterAdEntitiy entitiy = AnalysJson.getEntity(data, CenterAdEntitiy.class);
                if (entitiy != null) {
//                    Picasso.with(context).load(entitiy.getData().getSrc()).into(centerAd);
                    LoadingPicture.loadPicture(context, entitiy.getData().getSrc(), centerAd);

                    centerAd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, WebDetailActivity.class);
                            intent.putExtra(WebDetailActivity.LOAD_URL, entitiy.getData().getLink());
                            startActivity(intent);
                        }
                    });
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return dataSource.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String iconUrl = dataSource.get(position).getSrc();
            if (iconUrl != null) {
//                Picasso.with(context).load(iconUrl).into(imageView);
                LoadingPicture.loadPicture(context, iconUrl, imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.LOAD_URL, dataSource.get(position).getLink());
                        intent.setClass(context, WebDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_video:
                Intent intent = new Intent(context, MoreVideoDetailActivity.class);
                startActivity(intent);
                break;

        }
    }

}
