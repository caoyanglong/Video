package com.day.l.video.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.SharePrefrenceUtil;
import com.day.l.video.video.entity.SelectorVideoEntity;
import com.day.l.video.video.entity.VideoDetailEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyl
 * on 2016/9/20.
 * email:670654904@qq.com
 */
public class SelectVideoDetailFragment extends BaseLazyFragment {
    private GridView gridView;
    private List<SelectorVideoEntity> dataSource = new ArrayList<>();
    private VideoAdapter videoAdapter;
    private String jsonObject;
    private VideoDetailEntity entity;
    @Override
    public int setContentView() {
        return R.layout.selector_videos_framgent_layout;
    }

    @Override
    public void initView(View view) {
        gridView = (GridView) findViewById(R.id.selector_video);
        videoAdapter = new VideoAdapter();
        gridView.setAdapter(videoAdapter);
    }

    @Override
    public void initData() {
        jsonObject = getArguments().getString(Constants.JSON_KEY);
        entity = AnalysJson.getEntity(jsonObject,VideoDetailEntity.class);
        if(entity.getAppType().contains("2")){
            try {
                int index = 0;
                try {
                    index = Integer.parseInt(SharePrefrenceUtil.getProperties(context, entity.getID()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Log.d("<current_index> %s",entity.getCurIndex());
                int current = Integer.parseInt(entity.getCurIndex());
                for (int i = 0;i<current;i++){
                    if(index == i){
                        dataSource.add(new SelectorVideoEntity((i+1)+"",true));
                    }else {
                        dataSource.add(new SelectorVideoEntity((i+1)+"",false));
                    }

                }
                videoAdapter.notifyDataSetChanged();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initListener() {

    }
    private final class  VideoAdapter extends BaseAdapter{
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.selector_item,null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            else{
                holder = (ViewHolder) view.getTag();
            }
            SelectorVideoEntity videoEntity = dataSource.get(i);
            holder.textView.setText(videoEntity.getIndex());
            holder.textBack.setText(videoEntity.getIndex());
            if(videoEntity.isSelected()){
                holder.textView.setVisibility(View.GONE);
                holder.textBack.setVisibility(View.VISIBLE);
            }
            else{
                holder.textView.setVisibility(View.VISIBLE);
                holder.textBack.setVisibility(View.GONE);
            }

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharePrefrenceUtil.addPropties(context,entity.getID(),i+"");
                    Intent intent = new Intent(context,VideoPlayerActivity.class);
                    intent.putExtra(Constants.INDEX_KEY,(i+1)+"");
                    intent.putExtra(Constants.JSON_KEY,jsonObject);
                    startActivity(intent);
                }
            });
            return view;
        }
        private class ViewHolder{
            private TextView textView;
            private TextView textBack;
            private ViewHolder(View view){
                textView = (TextView) view.findViewById(R.id.item);
                textBack = (TextView) view.findViewById(R.id.item1);
            }
        }
    }

    public static Fragment getInstance(Bundle bundle){
        Fragment fragment = new SelectVideoDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            if(dataSource.size()>0){
                dataSource.clear();
            }
            initData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
