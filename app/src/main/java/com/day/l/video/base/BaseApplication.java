package com.day.l.video.base;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * 完全退出 自己 应用程序
 *
 * @author cyl
 *         <p>
 *         2015-12-13
 *
 *  修改2016-7-20
 *  改为软引用方式  来管理程序中的所有activity
 */
public class BaseApplication extends Application {
    /**
     * fragmentactivity
     */
    private List<WeakReference<FragmentActivity>> activityList = new LinkedList<WeakReference<FragmentActivity>>();
    private List<WeakReference<Activity>> activities = new LinkedList<WeakReference<Activity>>();
    private static BaseApplication instance;

    // 单例模式中获取唯一的MyApplication实例
    public static BaseApplication getInstance() {
        if (null == instance) {
            instance = new BaseApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(FragmentActivity activity) {
        activityList.add(new WeakReference<FragmentActivity>(activity));
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activities.add(new WeakReference<Activity>(activity));
    }

    /**
     * 移除 activity
     *
     * @param activity
     */
    public void removeAtivity(FragmentActivity activity) {
        activityList.remove(new WeakReference<FragmentActivity>(activity));
    }

    /**
     * 移除 activity
     *
     * @param activity
     */
    public void removeAtivity(Activity activity) {
        activities.remove(new WeakReference<Activity>(activity));
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (WeakReference<FragmentActivity> activity : activityList) {
            if(activity != null){
                activity.get().finish();
            }
        }
        for (WeakReference<Activity> activity : activities) {
            activity.get().finish();
        }
        System.exit(0);
    }
} 