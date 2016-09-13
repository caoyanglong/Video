package com.day.l.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.SurpriseListEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.LoadingPicture;
import com.day.l.video.web.WebDetailActivity;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class SurperiseFragment extends BaseLazyFragment {
    private List<SurpriseListEntity.DataBean> dataSource = new ArrayList<>();
    private ListView surpriseLv;
    private MyAdapter adapter;
    @Override
    public int setContentView() {
        return R.layout.surperise_fragment_layout;
    }

    @Override
    public void initView(View view) {
        surpriseLv = (ListView) findViewById(R.id.surprise_list);
    }

    @Override
    public void initData() {
        adapter = new MyAdapter();
        surpriseLv.setAdapter(adapter);
        getLoaderManager().restartLoader(1,null, surpriseLoader);
    }

    @Override
    public void initListener() {

    }
    private final LoaderManager.LoaderCallbacks<JSONObject> surpriseLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            return new BaseGetLoader(context, ajaxParams, Constants.SURPRISE_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                SurpriseListEntity entity = AnalysJson.getEntity(data,SurpriseListEntity.class);
                if(entity != null){
                    dataSource.addAll(entity.getData());
                    adapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

    private class  MyAdapter extends BaseAdapter{

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
            Viewholder viewholder;
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.red_bags_list_item,null);
                viewholder = new Viewholder(view);
                view.setTag(viewholder);
            }
            else{
                viewholder = (Viewholder) view.getTag();
            }
            final SurpriseListEntity.DataBean bean = dataSource.get(i);
            viewholder.share.setVisibility(View.GONE);
            viewholder.bagsCount.setVisibility(View.GONE);
//            Picasso.with(context).load(bean.getSrc()).into(viewholder.icon);
            LoadingPicture.loadPicture(context,bean.getSrc(),viewholder.icon);
            viewholder.tips.setText(bean.getDescribe());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,WebDetailActivity.class);
                    intent.putExtra(WebDetailActivity.LOAD_URL,bean.getLink());
                    startActivity(intent);
                }
            });
            return view;
        }

        private class Viewholder {
            private ImageView icon;
            private TextView bagsCount;
            private TextView share;
            private TextView tips;
            public Viewholder(View view) {
                icon = (ImageView) view.findViewById(R.id.ad_icon);
                bagsCount = (TextView) view.findViewById(R.id.red_bag_count);
                share = (TextView) view.findViewById(R.id.share);
                tips = (TextView) view.findViewById(R.id.tips);

            }
        }
    }


}
