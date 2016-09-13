package com.day.l.video.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment 代码进行格式化
 *
 * @author 曹阳龙
 *         <p>
 *         2015-9-13
 *  修改getview ----->findViewById保持和activity一致 的访问方法
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(setContentView(),container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        context = view.getContext();
        onViewCreated(view);
    }

    public Context getContext() {
        return context;
    }

    public void onViewCreated(View view) {
        initView(view);
        initData();
        initListener();
    }

    /**
     * 设置fragment 的布局文件防止 遗忘
     *
     * @return
     */
    public abstract int setContentView();

    /**
     * 首次 必先初始化 view
     */
    public abstract void initView(View view);

    /**
     * 初始化数据 从网络 获取数据 数据添加到 view 等逻辑 工作
     */
    public abstract void initData();

    /**
     * 初始化 view listener 的工作
     */
    public abstract void initListener();


    /**
     * 和activity 的一致的访问方法
     * @param viewid
     * @return
     */
    public View findViewById(int viewid){
        return view.findViewById(viewid);
    }
}