package com.day.l.video.web;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.day.l.video.utils.Constants;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class ChildWebDetailActivity extends Activity{
    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webview = new WebView(getBaseContext());
        setContentView(webview);
        WebSettings webSet = webview.getSettings();
        webSet.setJavaScriptEnabled(true);
        webSet.setJavaScriptCanOpenWindowsAutomatically(true);
        webSet.setDefaultTextEncodingName("utf-8");
        webSet.setLoadWithOverviewMode(true);
        webSet.setUseWideViewPort(true);
        webSet.setDomStorageEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webSet.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        webview.setWebChromeClient(new WebChromeClient());
        webview.setInitialScale(100);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.loadUrl(getIntent().getStringExtra(Constants.LOAD_URL));


    }
}
