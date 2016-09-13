package com.day.l.video.db;

import android.content.Context;

import net.tsz.afinal.FinalDb;

/**
 * Created by cyl
 * on 2016/9/12.
 * email:670654904@qq.com
 */
public class MyDb {
    private static FinalDb db;
    private final static String DB_NAME = "download_info_tab.db";
    public static FinalDb getDb(Context context){
        if(db == null){
            db = FinalDb.create(context,DB_NAME);
        }
        return db;
    }
}
