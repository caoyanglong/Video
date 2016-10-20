package com.day.l.video.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.JavascriptInterface;
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
    public PareseVideoByWebview(Context context,String url){
        this.context = context;
        this.webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(){
            @Override
            public void showSource(String html) {
                super.showSource(html);
            }
        }, "local_obj");
        webView.setWebViewClient(new MyWebViewClient());
        String pluginUrl = Constants.PLUGIN_API+url+"x";
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
        public void onPageFinished(WebView view, String url) {
            MyLog.d("WebView %s","onPageFinished ");
            view.loadUrl("javascript:window.local_obj.showSource(document.getElementsByTagName(video)[0]).src)");
            super.onPageFinished(view, url);
        }
    }

    class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            MyLog.d("HTML  %s", html);
        }
    }
}
