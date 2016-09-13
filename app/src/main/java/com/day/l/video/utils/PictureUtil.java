package com.day.l.video.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

/**
 * @Description 调用系统拍照或进入图库中选择照片,再进行裁�?,压缩.
 * @author 疯尘�?
 */
public class PictureUtil {
	/** 用来请求照相功能的常�? */
	public static final int CAMERA_WITH_DATA = 168;
	/** 用来请求图片选择器的常量 */
	public static final int PHOTO_PICKED_WITH_DATA = CAMERA_WITH_DATA + 1;
	/** 用来请求图片裁剪�? */
	public static final int PHOTO_CROP = PHOTO_PICKED_WITH_DATA + 1;
	/**	拍照的照片存储位�? */
	public static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/");
	public static final int REQUEST_CAMERA = 20000;


	public File file;		// 截图后得到的图片
	public static Uri imageUri;	// 拍照后的图片路径
	public static Uri imageCaiUri;	// 裁剪后的图片路径
	
	/**
	 * �?始启动照片�?�择�?
	 * @param context
	 * 是否裁剪
	 */
	public static void doPickPhotoAction(final Activity context) {
		doPicture(context);
	}

	private static void doPicture(final Activity context) {
		Dialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		//    指定下拉列表的显示数据
		final String[] cities = {"   拍照", "   从相册中选择","   取消"};
		//    设置一个下拉的列表选择项
		builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which) {
                case 0: {
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD�?
                        Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SDCard.getPicturePath(), "header.png")));
                        context.startActivityForResult(getImageByCamera, REQUEST_CAMERA);
                    } else {
                        Toast.makeText(context, "没有找到SD卡?", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case 1:
                    doPickPhotoFromGallery(context);// 从相册中去获�?
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

	/**
	 * 调用相册程序
	 * @param context
	 */
	public static void doPickPhotoFromGallery(Context context) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,	"image/*");
			((Activity) context).startActivityForResult(intent,	PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(context, "没有找到相册", Toast.LENGTH_SHORT).show();
		}
	}
	
}