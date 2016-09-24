package com.day.l.video.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.DES;
import com.day.l.video.utils.LoadingPicture;
import com.day.l.video.utils.TimeUtils;
import com.day.l.video.video.entity.SortVideoAdapterDao;
import com.day.l.video.video.entity.VideoListDataEntity;
import com.day.l.video.video.loader.VideoDataLoader;
import com.day.l.video.widgets.EndlessRecyclerOnScrollListener;
import com.day.l.video.widgets.HeaderViewRecyclerAdapter;
import com.day.l.video.widgets.LoadMoreView;
import com.day.l.video.widgets.LoadRecyclerView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyl
 * on 2016/9/18.
 * email:670654904@qq.com
 */
public class VideoFragment extends BaseLazyFragment implements EndlessRecyclerOnScrollListener.RvLoadMoreListener {
    private LoadRecyclerView loadRecyclerView;
    private LoadMoreView mLoadMoreView;
    private HeaderViewRecyclerAdapter mAdapt;
    private VideoAdapter adapter;
    private List<SortVideoAdapterDao> dataSource = new ArrayList<>();
    private String type;
    private int currentPage = 1;

    @Override
    public int setContentView() {
        return R.layout.video_fragment_layout;
    }

    @Override
    public void initView(View view) {
        loadRecyclerView = (LoadRecyclerView) findViewById(R.id.video_recyleview);
        loadRecyclerView.setItemAnimator(new DefaultItemAnimator());
        loadRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void initData() {

        this.type = getArguments().getString(Constants.TYPE_KEY);
        loadRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        loadRecyclerView.setRvLoadMoreListener(this);
        adapter = new VideoAdapter();
        mAdapt = new HeaderViewRecyclerAdapter(adapter);
        mLoadMoreView = new LoadMoreView(context, loadRecyclerView);
        mAdapt.addFooterView(mLoadMoreView.getFooterView());
        loadRecyclerView.setAdapter(mAdapt);

        mLoadMoreView.resetMore();

        loadVideosData();
    }

    private void loadVideosData() {
        getLoaderManager().restartLoader(1, null, videoLoader);
    }

    private final LoaderManager.LoaderCallbacks<JSONObject> videoLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams params = new AjaxParams();
            try {
                params.put("AppType", type);
                params.put("PageIndex", currentPage+"");
                params.put("PageSize", "27");
                params.put("K", DES.encrypt(""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put("TimeStamp", TimeUtils.getTimeStap());
            return new VideoDataLoader(context, params, Constants.GET_VIDEO_LIST_DATA_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                VideoListDataEntity entity = AnalysJson.getEntity(data, VideoListDataEntity.class);
                if (entity != null) {
                    SortVideoAdapterDao sortVideoAdapterDao = null;
                    for (int i = 0; i < entity.getList().size(); i++) {
                        if((i+1)%3==1){
                            sortVideoAdapterDao = new SortVideoAdapterDao();
                            sortVideoAdapterDao.setFirstAppInfoDao(entity.getList().get(i));
                        }
                        else if((i+1)%3==2){
                            sortVideoAdapterDao.setSecondAppInfoDao(entity.getList().get(i));
                        }
                        else if((i+1)%3==0){
                            sortVideoAdapterDao.setThirdAppInfoDao(entity.getList().get(i));
                            dataSource.add(sortVideoAdapterDao);
                        }
                    }
                    if(entity.getList().size() == 27){
                        currentPage++;
                        mLoadMoreView.resetMore();
                    }else{
                        mLoadMoreView.setNoMore();
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    mLoadMoreView.resetMore();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

    private final class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyHolder> {


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.sort_video_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(MyHolder viewHolder, int position) {
            SortVideoAdapterDao dao = dataSource.get(position);
            VideoListDataEntity.ListBean dao1 = dao.getFirstAppInfoDao();
            VideoListDataEntity.ListBean dao2 = dao.getSecondAppInfoDao();
            VideoListDataEntity.ListBean dao3 = dao.getThirdAppInfoDao();
            if (dao1 != null) {
                LoadingPicture.loadPicture(context, Constants.getImageUrl(dao1.getImageUrl()), viewHolder.icon);
                viewHolder.videoTitle.setText(dao1.getAppName());
                viewHolder.descripe.setVisibility(View.GONE);
                if (dao1.getScore().equals("0")) {
                    viewHolder.vip1.setVisibility(View.GONE);
                } else {
                    viewHolder.vip1.setVisibility(View.VISIBLE);
                }
                setIconListener(viewHolder.icon,dao1);
            }
            if (dao2 != null) {
                LoadingPicture.loadPicture(context, Constants.getImageUrl(dao2.getImageUrl()), viewHolder.icon2);
                viewHolder.videoTitle2.setText(dao2.getAppName());
                viewHolder.descripe2.setVisibility(View.GONE);
                if (dao2.getScore().equals("0")) {
                    viewHolder.vip2.setVisibility(View.GONE);
                } else {
                    viewHolder.vip2.setVisibility(View.VISIBLE);
                }
                setIconListener(viewHolder.icon2,dao2);
            }
            if (dao3 != null) {
                setIconListener(viewHolder.icon3,dao3);
                LoadingPicture.loadPicture(context, Constants.getImageUrl(dao3.getImageUrl()), viewHolder.icon3);
                viewHolder.videoTitle3.setText(dao3.getAppName());
                viewHolder.descripe3.setVisibility(View.GONE);
                if (dao3.getScore().equals("0")) {
                    viewHolder.vip3.setVisibility(View.GONE);
                } else {
                    viewHolder.vip3.setVisibility(View.VISIBLE);
                }
            }
        }

        private void setIconListener(ImageView icon, final VideoListDataEntity.ListBean dao) {
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoDetailActivity.class);
                    intent.putExtra(Constants.ID,dao.getID());
                    intent.putExtra(Constants.VIDEO_NAME,dao.getAppName());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSource.size();
        }
        public class MyHolder extends RecyclerView.ViewHolder {
            public ImageView icon;
            //����
            public TextView descripe;

            public TextView vip1;
            //��Ƶ����
            public TextView videoTitle;
            public ImageView icon2;
            public TextView vip2;
            //����
            public TextView descripe2;
            //��Ƶ����
            public TextView videoTitle2;
            public ImageView icon3;
            //����
            public TextView descripe3;
            public TextView vip3;
            //��Ƶ����
            public TextView videoTitle3;

            public MyHolder(View view) {
                super(view);
                icon = (ImageView) view.findViewById(R.id.icon);
                descripe = (TextView) view.findViewById(R.id.tips);
                videoTitle = (TextView) view.findViewById(R.id.title);
                vip1 = (TextView) view.findViewById(R.id.vip_tips1);

                icon2 = (ImageView) view.findViewById(R.id.icon2);
                descripe2 = (TextView) view.findViewById(R.id.tips2);
                videoTitle2 = (TextView) view.findViewById(R.id.title2);
                vip2 = (TextView) view.findViewById(R.id.vip_tips2);

                icon3 = (ImageView) view.findViewById(R.id.icon3);
                descripe3 = (TextView) view.findViewById(R.id.tips3);
                videoTitle3 = (TextView) view.findViewById(R.id.title3);
                vip3 = (TextView) view.findViewById(R.id.vip_tips3);
            }
        }

    }
    public static Fragment getInstance(Bundle bundle){
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onLoadMore(int currentPage) {
        loadVideosData();
    }

}
