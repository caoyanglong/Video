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
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.LoadingPicture;
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

    }

    @Override
    public void initData() {
        loadingView.setConentContainer(contentContainer);
        loadingStart();
        getLoaderManager().restartLoader(1, null, headerAdLoader);
        getLoaderManager().restartLoader(2, null, centerAdLoader);
    }

    @Override
    public void initListener() {

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
                }
                else{
                    loadError();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };
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
                    LoadingPicture.loadPicture(context,entitiy.getData().getSrc(),centerAd);

                    centerAd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,WebDetailActivity.class);
                            intent.putExtra(WebDetailActivity.LOAD_URL,entitiy.getData().getLink());
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
                LoadingPicture.loadPicture(context,iconUrl,imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.LOAD_URL,dataSource.get(position).getLink());
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

    }

}
