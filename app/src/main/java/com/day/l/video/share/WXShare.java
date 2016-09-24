package com.day.l.video.share;

import android.content.Context;
import android.os.Bundle;

import com.day.l.video.BuildConfig;
import com.tencent.mm.sdk.openapi.GetMessageFromWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

/**
 * Created by cyl
 * on 2016/9/12.
 * email:670654904@qq.com
 */
public class WXShare {
    private IWXAPI api;
    private Context context;
    private Bundle bundle;
    private String text;
    public static final String WX_KEY = BuildConfig.DEBUG?"wx2b5387025852c4b1":"wxb3bd26f0ded297c8";

    public WXShare(Context context, Bundle bundle, String text) {
        this.context = context;
        this.bundle = bundle;
        this.text = text;
        regToWx();
    }

    public void shareToWx(){
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;
        WXMediaMessage msg = new WXMediaMessage(textObject);
        msg.description = text;
        GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
        resp.transaction = new GetMessageFromWX.Req(bundle).transaction;
        resp.message = msg;

        api.sendResp(resp);
    }

    private void regToWx(){
        api = WXAPIFactory.createWXAPI(context, WX_KEY,true);
        api.registerApp(WX_KEY);
    }
    public void shareText(){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com";

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "WebPage Title";
        msg.description = "WebPage Description";

        GetMessageFromWX.Resp resp = new GetMessageFromWX.Resp();
        resp.transaction = getTransaction();
        resp.message = msg;

        api.sendResp(resp);
    }

    private String getTransaction() {
        final GetMessageFromWX.Req req = new GetMessageFromWX.Req(bundle);
        return req.transaction;
    }



}
