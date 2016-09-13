package com.day.l.video.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * 此类  对于android 系统中，一个临时存储的  小型数据库，其实是以xml 形式存储在  手机本地 
 * 软件卸载后，这个xml 也会被卸载掉
 * @author 曹阳龙
 *
 * 2015-9-12
 */
public class SharePrefrenceUtil {
    public final static String filename = "lefeng";
	private static String defValue = "error";
	/**
	 * 玩这张表中添加  属性
	 * @param context
	 * @param name
	 * @param value
	 */
	public static void addPropties(Context context,String name ,String value){
		try {
			SharedPreferences sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE); //私有数据
			Editor editor = sharedPreferences.edit();//获取编辑器
			editor.putString(name, value);
			editor.commit();//提交修改
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到表中的属性
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getProperties(Context context,String name){
		SharedPreferences sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		return sharedPreferences.getString(name, defValue);
	}
	/**
	 * 可以 查看表中某个属性是否存在
	 * @param context
	 * @param name
	 * @return
	 */
	public static boolean isExit(Context context,String name){
		return !getProperties(context, name).equals(defValue);
	}
	/**
	 * 去除某个值
	 * @param context
	 * @param name
	 */
	public static void remove(Context context,String name){
		SharedPreferences sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE); //私有数据
		Editor editor = sharedPreferences.edit();//获取编辑器
		editor.remove(name);
		editor.commit();//提交修改
	}
}
