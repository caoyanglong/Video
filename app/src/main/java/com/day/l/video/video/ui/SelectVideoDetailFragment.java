package com.day.l.video.video.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
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
    private List<String> dataSource = new ArrayList<>();
    private VideoAdapter videoAdapter;
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
        VideoDetailEntity entity = AnalysJson.getEntity(getArguments().getString(Constants.JSON_KEY),VideoDetailEntity.class);
        if(entity.getType().contains("2")){
            try {
                int current = Integer.parseInt(entity.getCurIndex());
                for (int i = 0;i<current;i++){
                    dataSource.add((i+1)+"");
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.selector_item,null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            else{
                holder = (ViewHolder) view.getTag();
            }
            holder.textView.setText(dataSource.get(i));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"-------",Toast.LENGTH_LONG).show();
                }
            });
            return view;
        }
        private class ViewHolder{
            private TextView textView;
            private ViewHolder(View view){
                textView = (TextView) view.findViewById(R.id.item);
            }
        }
    }

    public static Fragment getInstance(Bundle bundle){
        Fragment fragment = new SelectVideoDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
