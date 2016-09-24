package com.day.l.video.video.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.video.entity.VideoDetailEntity;

/**
 * Created by cyl
 * on 2016/9/20.
 * email:670654904@qq.com
 */
public class VideoDetailFragment extends BaseLazyFragment{
    private TextView nameTv,actorTv,typeTv,areaTv,dircotrTv,scoreTv,yearTv,contentTv;
    @Override
    public int setContentView() {
        return R.layout.video_detail_fragment_layout;
    }

    @Override
    public void initView(View view) {
        nameTv = (TextView) findViewById(R.id.name);
        actorTv = (TextView) findViewById(R.id.actor);
        dircotrTv = (TextView) findViewById(R.id.dirctor);
        typeTv = (TextView) findViewById(R.id.type);
        scoreTv = (TextView) findViewById(R.id.score);
        areaTv = (TextView) findViewById(R.id.area);
        yearTv = (TextView) findViewById(R.id.year);
        contentTv = (TextView) findViewById(R.id.content_desc);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        VideoDetailEntity videoDetailEntity = AnalysJson.getEntity(bundle.getString(Constants.JSON_KEY), VideoDetailEntity.class);
        nameTv.setText(videoDetailEntity.getName());
        actorTv.setText(getResources().getString(R.string.actor,videoDetailEntity.getName()));
        dircotrTv.setText(getResources().getString(R.string.dirctor,videoDetailEntity.getDirector()));
        typeTv.setText(getResources().getString(R.string.video_type,videoDetailEntity.getType()));
        scoreTv.setText(getResources().getString(R.string.video_score,videoDetailEntity.getStar()));
        areaTv.setText(getResources().getString(R.string.area,videoDetailEntity.getArea()));
        yearTv.setText(getResources().getString(R.string.year,videoDetailEntity.getAddDate()));
        contentTv.setText("剧情：\n"+videoDetailEntity.getContent());
    }

    @Override
    public void initListener() {

    }
    public static Fragment getInstance(Bundle bundle){
        Fragment fragment = new VideoDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
