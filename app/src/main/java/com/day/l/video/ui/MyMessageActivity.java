package com.day.l.video.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.MyMessageEntity;
import com.day.l.video.model.StatusEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.StatusCode;
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
                MyMessageEntity entity = AnalysJson.getEntity(data,MyMessageEntity.class);
                if(entity != null){
                    try {
                        dataSource.addAll(entity.getData());
                        adater.notifyDataSetChanged();
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
    private int currentPosition = -1;

    private int readPostion = -1;
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
        public View getView(final int position, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null){
                view = getLayoutInflater().inflate(R.layout.msg_content_list_item,null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            else{
                holder = (ViewHolder) view.getTag();
            }
            final MyMessageEntity.DataBean bean = dataSource.get(position);
            holder.time.setText(bean.getSend_time());
            holder.msg.setText(bean.getContent());
            if(bean.getIs_read() == 1){
                holder.dot.setVisibility(View.GONE);
            }
            else{
                holder.dot.setVisibility(View.VISIBLE);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(bean.getIs_read() == 0){
                       Bundle bundle = new Bundle();
                       bundle.putString(Constants.MESSAGE_ID_KEY,bean.getMessage_id());
                       getSupportLoaderManager().restartLoader(3,bundle,readLoader);
                       readPostion = position;
                   }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("删除该消息")
                            .setPositiveButton("取消",null)
                            .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constants.MESSAGE_ID_KEY,bean.getMessage_id());
                                    getSupportLoaderManager().restartLoader(2,bundle,deleteMsgLoader);
                                    currentPosition = position;
                                }
                            }).show();
                    return true;
                }
            });
            return view;
        }

        private class ViewHolder {
            private TextView msg;
            private TextView time;
            private ImageView dot;

            public ViewHolder(View view){
                msg = (TextView) view.findViewById(R.id.msg);
                time = (TextView) view.findViewById(R.id.msg_time);
                dot = (ImageView) view.findViewById(R.id.tips);
            }
        }
    }

    private final LoaderManager.LoaderCallbacks<JSONObject> readLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams params = new AjaxParams();
            params.put(Constants.TOKEN,UserConfig.getToken());
            params.put(Constants.MESSAGE_ID_KEY,args.getString(Constants.MESSAGE_ID_KEY)+"");
            return new BaseGetLoader(context,params,Constants.READ_MESSAGE_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if(data != null){
                StatusEntity entity  = AnalysJson.getEntity(data,StatusEntity.class);
                if(entity != null && entity.getStatus() == StatusCode.SUCCESS_CODE){
                    if(readPostion != -1){
                        MyMessageEntity.DataBean bean = dataSource.remove(readPostion);
                        bean.setIs_read(1);
                        dataSource.add(readPostion,bean);
                        adater.notifyDataSetChanged();
                        readPostion = -1;
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };
    private final LoaderManager.LoaderCallbacks<JSONObject> deleteMsgLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams params = new AjaxParams();
            params.put(Constants.TOKEN,UserConfig.getToken());
            params.put(Constants.MESSAGE_ID_KEY,args.getString(Constants.MESSAGE_ID_KEY)+"");
            return new BaseGetLoader(context,params,Constants.DELETE_MESSAGE_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if(data != null){
                StatusEntity entity  = AnalysJson.getEntity(data,StatusEntity.class);
                if(entity != null && entity.getStatus() == StatusCode.SUCCESS_CODE){
                    if(currentPosition != -1){
                        dataSource.remove(currentPosition);
                        adater.notifyDataSetChanged();
                        currentPosition = -1;
                    }
                }
                else{
                    Toast.makeText(context,"操作失败",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(context,"操作失败",Toast.LENGTH_LONG).show();
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
        loadListData();
    }
}
