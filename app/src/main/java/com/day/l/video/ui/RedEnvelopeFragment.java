package com.day.l.video.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.day.l.video.model.RedBagsListEntity;
import com.day.l.video.share.TencentManager;
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
public class RedEnvelopeFragment extends BaseLazyFragment {
    private List<RedBagsListEntity.DataBean> dataSource = new ArrayList<>();
    private ListView redBagsLv;
    private MyAdapter adapter;
    @Override
    public int setContentView() {
        return R.layout.red_envelope_fragment_layout;
    }

    @Override
    public void initView(View view) {
        redBagsLv = (ListView) findViewById(R.id.red_bag_list);
    }

    @Override
    public void initData() {
        adapter = new MyAdapter();
        redBagsLv.setAdapter(adapter);
        getLoaderManager().restartLoader(1,null,redBagsLoader);
    }

    @Override
    public void initListener() {

    }
    private final LoaderManager.LoaderCallbacks<JSONObject> redBagsLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            return new BaseGetLoader(context, ajaxParams, Constants.RED_BAG_LIST_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                RedBagsListEntity entity = AnalysJson.getEntity(data,RedBagsListEntity.class);
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
            final RedBagsListEntity.DataBean bean = dataSource.get(i);
            viewholder.bagsCount.setText(bean.getNumber());
//            Picasso.with(context).load(bean.getSrc()).into(viewholder.icon);
            LoadingPicture.loadPicture(context,bean.getSrc(),viewholder.icon);
            viewholder.tips.setText(bean.getDescribe());
            viewholder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    //    指定下拉列表的显示数据
                    final String[] cities = {"朋友圈","取消"};
                    //    设置一个下拉的列表选择项
                    builder.setItems(cities, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            switch (which) {
                                case 0: {
                                    TencentManager manager = new TencentManager(getActivity());
                                    manager.shareWeChatWebPage(TencentManager.SCENE_WECHAT_MOMENT,bean.getDescribe(),bean.getDescribe(),
                                            bean.getLink(),bean.getRedbag_id());
                                    break;
                                }
                                case 1:
//                                    TencentManager manager = new TencentManager(getActivity());
//                                    manager.shareWeChatWebPage(TencentManager.SCENE_WECHAT,bean.getDescribe(),bean.getDescribe(),
//                                            bean.getLink(),bean.getRedbag_id());
                                    dialog.cancel();
                                    break;
                                case 2:
                                    dialog.cancel();
                                    break;
                            }
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            });
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
