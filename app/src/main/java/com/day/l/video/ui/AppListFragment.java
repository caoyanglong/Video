package com.day.l.video.ui;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.AppBeanEntitiy;
import com.day.l.video.model.AppListEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.LoadingPicture;
import com.day.l.video.widgets.LoadingView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class AppListFragment extends BaseLazyFragment implements LoadingView.LoadingListener{
    private LoadingView loadingView;
    private ListView appLv;
    private MyAdapter adapter;
    private String type = Constants.APPLICATION_KEY;


    private List<AppBeanEntitiy> dataSource = new ArrayList<>();
    @Override
    public int setContentView() {
        return R.layout.applist_fragment_layout;
    }

    @Override
    public void initView(View view) {
        appLv = (ListView) findViewById(R.id.app_list);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
    }

    @Override
    public void initData() {
        this.type = getArguments().getString(Constants.JSON_KEY);
        adapter = new MyAdapter();
        appLv.setAdapter(adapter);
        loadingView.setRetryListener(this);
        loadData();
//        getLoaderManager().restartLoader(2,null,cursorLoader);
    }

    private void loadData() {
        loadingStart();
        getLoaderManager().restartLoader(1,null,appListLoader);
    }

    @Override
    public void initListener() {

    }

    /**
     * 获取对应fragment 的实例
     * @param bundle
     * @return
     */
    public static AppListFragment getInstance(Bundle bundle){
        AppListFragment appListFragment = new AppListFragment();
        appListFragment.setArguments(bundle);
        return appListFragment;
    }

    private final LoaderManager.LoaderCallbacks<JSONObject> appListLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            ajaxParams.put(Constants.SYSTEM_KEY, "android");
            ajaxParams.put(Constants.TYPE_KEY, type);
            return new BaseGetLoader(context, ajaxParams, Constants.APP_LIST_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                Log.d("<json_object>",data.toString());
                AppListEntity appListEntity = AnalysJson.getEntity(data,AppListEntity.class);
                if(appListEntity != null){
                    dataSource.addAll(appListEntity.getData());
                    adapter.notifyDataSetChanged();
                }
                loadFinshed();
            }
            else{
                loadError();
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };
    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return dataSource.size();
        }

        @Override
        public Object getItem(int i) {
            return dataSource.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.applist_item,null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            else {
                holder = (ViewHolder) view.getTag();
            }
            final AppBeanEntitiy bean = dataSource.get(i);
            LoadingPicture.loadPicture(context,bean.getSrc(),holder.icon);
            holder.appName.setText(bean.getName());
            holder.bar.setProgress(bean.getProgres());
            holder.down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(bean.getLink()));
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE| DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalFilesDir(context,
                                Environment.DIRECTORY_DOWNLOADS,bean.getName()+".apk");
                        request.setTitle(bean.getName());
                        long reference = downloadManager.enqueue(request);
                        Toast.makeText(context,bean.getName()+"已开始下载",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return view;
        }
        private final class ViewHolder{
            private ImageView icon;
            private TextView appName;
            private ProgressBar bar;
            private TextView down;

            public ViewHolder(View view){
                icon = (ImageView) view.findViewById(R.id.icon);
                appName = (TextView) view.findViewById(R.id.app_name);
                bar = (ProgressBar) view.findViewById(R.id.down_progress);
                down = (TextView) view.findViewById(R.id.down);
            }
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
        loadData();
    }
}
