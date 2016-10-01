package com.day.l.video.ui;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
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
public class GameListFragment extends BaseLazyFragment implements LoadingView.LoadingListener{
    private LoadingView loadingView;
    private ListView appLv;
    private MyAdapter adapter;
    private String type = Constants.GAME_KEY;

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
        adapter = new MyAdapter();
        appLv.setAdapter(adapter);
        getLoaderManager().restartLoader(1,null,appListLoader);
        getLoaderManager().restartLoader(2,null,cursorLoader);
    }

    @Override
    public void initListener() {

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
                    try {
                        dataSource.addAll(appListEntity.getData());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

//                    bean.setDownloadID(reference);
//                    bean.setStatus(0);
//                    bean.setPath(new File(Environment.DIRECTORY_DOWNLOADS,bean.getName()+".apk").getAbsolutePath());
//                    MyDb.getDb(context).save(bean);

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
    private LoaderManager.LoaderCallbacks<Cursor> cursorLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(context,Uri.parse("content://downloads/my_downloads"),null,null,null,null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if(data != null){
                while (data.moveToNext()){
//
                    try {
                        long downLoadID = data.getLong(data.getColumnIndex(DownloadManager.EXTRA_DOWNLOAD_ID));
                        int bytes_downloaded = data.getInt(data.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        int bytes_total = data.getInt(data.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        int progress = (int) ((bytes_downloaded * 100) / bytes_total);
                        Log.d("<progress>",progress+"-"+downLoadID);
                        Toast.makeText(context,"--"+progress,Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

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

    }
}
