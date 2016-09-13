package com.day.l.video.utils;

import android.os.Environment;

import java.io.File;

/**
 * ��sd���ϼ򵥴���  ����Ŀ¼ ��������Ҫ�ĳ���Ŀ¼
 * @author ������
 *
 * 2015-9-9
 */
public class SDCard {
	/**
	 * ��ȡ�ֻ�SD���ĸ�Ŀ¼�ļ�
	 * @return
	 */
	public static File getSDCardFile(){
		return Environment.getExternalStorageDirectory();
	}
	/**
	 * ��ȡ�ֻ�SD���ĸ�Ŀ¼
	 * @return
	 */
	public static String getSDCardPath(){
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	public static File getPicturePath(){
		return createPath("/picture");
	}
	public static void createLefengPath(){
		String path = Environment.getExternalStorageDirectory()+"/root";
		File file = new File(path);
		if(!file.exists()){
			file.mkdir();
		}
	}
	public static File createPath(String pathName){
		createLefengPath();
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/root"+pathName;
		File file = new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		return file;
	}
	
}
