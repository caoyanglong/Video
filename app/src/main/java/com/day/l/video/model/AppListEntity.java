package com.day.l.video.model;

import java.util.List;

/**
 * Created by cyl
 * on 2016/9/11.
 * email:670654904@qq.com
 */
public class AppListEntity {

    /**
     * id : 15
     * name : 应用宝
     * src : http://115.28.9.92/eliu/data/upload/20160908/57d126d5bfa6b.png
     * describe : 应用宝
     * link : http://www.wandoujia.com/apps/com.tencent.android.qqdownloader
     */

    private List<AppBeanEntitiy> data;

    public List<AppBeanEntitiy> getData() {
        return data;
    }

    public void setData(List<AppBeanEntitiy> data) {
        this.data = data;
    }

}
