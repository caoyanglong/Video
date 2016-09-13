package com.day.l.video.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.MyMessageEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.widgets.LoadingView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyl
 * on 2016/9/11.
 * email:670654904@qq.com
 */
public class MyMessageActivity extends BaseFragmentActivity implements LoadingView.LoadingListener{
    private ListView messageLv;
    private LoadingView loadingView;
    private MyAdater adater;
    private List<MyMessageEntity.DataBean> dataSource = new ArrayList<>();
    @Override
    public int setContent() {
        return R.layout.my_message_activity_layout;
    }

    @Override
    public void initView() {
        loadingView = (LoadingView) findViewById(R.id.loading_view);
        messageLv = (ListView) findViewById(R.id.msg_list);

    }

    private final LoaderManager.LoaderCallbacks<JSONObject> listDataLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            return new BaseGetLoader(context, ajaxParams, Constants.MESSAGE_LIST_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                Log.d("<json_object>",data.toString());
                MyMessageEntity entity = AnalysJson.getEntity(data,MyMessageEntity.class);
                if(entity != null){
                    dataSource.addAll(entity.getData());
                    adater.notifyDataSetChanged();
                }
                loadFinshed();
            }
            else{
                loadError();
                Toast.makeText(context,"提交失败",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

    @Override
    public void initData() {
        setActionBarCenterTile("我的消息");
        adater = new MyAdater();
        messageLv.setAdapter(adater);

        loadListData();
    }

    /**
     * 加载列表数据
     */
    private void loadListData() {
        loadingStart();
        getSupportLoaderManager().restartLoader(1,null, listDataLoader);
    }

    @Override
    public void initListener() {
        loadingView.setRetryListener(this);
    }
    private class MyAdater  extends BaseAdapter{
        @Override
        public int getCount() {
            return dataSource.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null){
                view = getLayoutInflater().inflate(R.layout.msg_content_list_item,null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            else{
                holder = (ViewHolder) view.getTag();
            }
            MyMessageEntity.DataBean bean = dataSource.get(i);
            holder.time.setText(bean.getSend_time());
            holder.msg.setText(bean.getContent());

            return view;
        }

        private class ViewHolder {
            private TextView msg;
            private TextView time;

            public ViewHolder(View view){
                msg = (TextView) view.findViewById(R.id.msg);
                time = (TextView) view.findViewById(R.id.msg_time);
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
        loadListData();
    }
}
