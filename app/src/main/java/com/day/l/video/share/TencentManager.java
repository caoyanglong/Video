package com.day.l.video.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.day.l.video.MyApplication;
import com.day.l.video.R;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import java.io.ByteArrayOutputStream;


public class TencentManager {

    /**
     * 微信
     */
    public static final int SCENE_WECHAT = SendMessageToWX.Req.WXSceneSession;
    /**
     * 微信朋友圈
     */
    public static final int SCENE_WECHAT_MOMENT = SendMessageToWX.Req.WXSceneTimeline;

    private Context context;

    public TencentManager(Activity context) {
        this.context = context;
    }

    /**
     * 微信分享图片
     *
     * @param scene
     * @param bitmap
     */
    public void shareWeChatImage(int scene, Bitmap bitmap,String shareid) {
        WXImageObject wxImageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage(wxImageObject);
        msg.mediaObject = wxImageObject;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = shareid;
        req.message = msg;
        if (MyApplication.api.isWXAppInstalled() && MyApplication.api.isWXAppSupportAPI()) {
            req.scene = scene;
            MyApplication.api.sendReq(req);
        } else {

        }
    }

    /**
     * 微信分享网页
     *
     * @param title       标题
     * @param description 描述
     * @param webpageUrl  链接
     */
    public void shareWeChatWebPage(int scene, String title, String description, String webpageUrl,String shareid) {
        WXWebpageObject webpage = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = shareid;
        req.message = msg;
        msg.title = title;
        msg.description = description;
        webpage.webpageUrl = webpageUrl;
        if (MyApplication.api.isWXAppInstalled() && MyApplication.api.isWXAppSupportAPI()) {
            req.scene = scene;
            MyApplication.api.sendReq(req);
        } else {
//            GGToast.showToast("您未安装微信，请选择其他方式分享", true);
        }
    }


    /**
     * 更多分享渠道
     */
    public void shareMore(String url, String desc) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, desc + ":" + url);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "GG分享");
        context.startActivity(Intent.createChooser(sendIntent, "发送到.."));
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String buildTransaction(final String type) {
        return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 是否安装QQ客户端.
     *
     * @param context
     * @return
     */
    public static boolean isInstalledQQ(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.tencent.mobileqq", 0);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
