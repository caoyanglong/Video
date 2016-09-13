package com.day.l.video.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 
 * @author cyl
 * email:670654904@qq.com
 * 2016年2月18日
 */
public abstract class BaseLazyFragment extends Fragment {
    
	protected ActionBarActivity barActivity;
    private static final String TAG = BaseLazyFragment.class.getSimpleName();
    private boolean isPrepared;
    private View view;
    protected Context context;
    protected FragmentManager fragmentManager,childFragmentManager;
    protected Resources resources;
    //是否显示dialog
    protected boolean showDialog = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }


    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    public synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	view = inflater.inflate(setContentView(), container, false);
    	context = view.getContext();
		init();
    	return view;
    }
    /**
	 * fragment  中获取view 自定义方法
	 * @param id 组件id
	 * @return
	 */
	public View getView(View view,int id){
		return view.findViewById(id);
	}

    private void init() {
    	setHasOptionsMenu(true);
		fragmentManager = getFragmentManager();
		childFragmentManager = getFragmentManager();
		resources = getResources();
		Activity activity = getActivity();
		if(activity instanceof ActionBarActivity){
			barActivity = (ActionBarActivity) activity;
		}
	}

	/**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstUserVisible() {
    	initView(view);
    	initData();
    	initListener();
    }
    /**
     * 设置布局文件
     * @return
     */
	public abstract int setContentView();
	
	/**
	 * 首次   必先初始化  view 
	 */
	public abstract void initView(View view);
	/**
	 * 初始化数据   从网络 获取数据  数据添加到  view  等逻辑 工作
	 */
	public abstract void initData();
	/**
	 * 初始化  view listener 的工作
	 */
	public abstract void initListener();

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onUserVisible() {

    }

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstUserInvisible() {

    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onUserInvisible() {

    }

    public View findViewById(int id){
        return view.findViewById(id);
    }

}
