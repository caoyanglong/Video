package com.day.l.video.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cyl.myvideo.utils.MyLog;

/**
 * Created by cyl
 * on 2016/10/20.
 * email:670654904@qq.com
 */
public class PareseVideoByWebview {
    private Context context;
    private WebView webView;
    private OnParseVideoListener listener;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (listener != null) {
                try {
                    listener.onPareseVideoSuccess(msg.obj.toString().contains("null")?null:msg.obj.toString());
                } catch (Exception e) {
                    listener.onPareseVideoSuccess(null);
                    e.printStackTrace();
                }
            }
        }
    };

    public PareseVideoByWebview(Context context) {
        this.context = context;
        this.webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.addJavascriptInterface(new ParseVideoJavascriptInterface() {
            @Override
            public void showSource(String html) {
                MyLog.d("<my_url %s>", html);
            }
        }, "parseVideo");


    }

    public void startParseVideo(String url) {
        String pluginUrl = Constants.PLUGIN_API2 + url;
        webView.loadUrl(pluginUrl);
    }

    final class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            MyLog.d("WebView %s", "onPageFinished ");
            view.evaluateJavascript("document.getElementsByTagName(\"video\")[0].src", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
//                    s = s.substring(1,s.length()-1);
                    MyLog.d("<video_url> %s",s);
                    if(s.substring(0,1).equals("\"")){
                        s = s.substring(1,s.length());
                    }
                    if(s.substring(s.length()-1,s.length()).equals("\"")){
                        s = s.substring(0,s.length()-1);
                    }
                    handler.obtainMessage(200, s).sendToTarget();
                }
            });
            webView.loadUrl("javascript:document.getElementsByTagName(\"video\")[0].src");

        }
    }

    public interface ParseVideoJavascriptInterface {
        @JavascriptInterface
        void showSource(String url);
    }

    /**
     * 解析 地址
     */
    public interface OnParseVideoListener {
        void onPareseVideoSuccess(String url);
    }

    public void setListener(OnParseVideoListener listener) {
        this.listener = listener;
    }
}
