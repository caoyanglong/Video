package com.day.l.video.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.day.l.video.R;

/**
 * Created by cyl
 * on 2016/9/11.
 * email:670654904@qq.com
 */
public class LoadingView extends FrameLayout {
    private TextView loadingText;
    private TextView loadErrorText;
    private TextView retryTv;
    private ViewGroup loadingContainer;
    private ViewGroup loadErrorContainer;
    private View contentContainer;

    private LoadingListener listener;

    private LoadingType type;
    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.loading_view_layout,this);
        loadingText = (TextView) findViewById(R.id.loading_text);
        loadErrorText = (TextView) findViewById(R.id.loading_error);
        retryTv = (TextView) findViewById(R.id.retry);
        retryTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.retryLoading();
                }
            }
        });

        loadErrorContainer = (ViewGroup) findViewById(R.id.retry_view_container);
        loadingContainer = (ViewGroup) findViewById(R.id.loading_container);
        setLoadingType(type);
    }

    public enum LoadingType{
        /**
         * 加载中...
         */
        LOADING,
        /**
         * 加载出现错误  网络失败 等等
         */
        LOADERROR,
        /**
         * 加载完成的时候
         */
        LOADFINISHED
    }

    /**
     * 设置加载的loading 类型
     * @param type
     */
    public void setLoadingType(LoadingType type){
        if(type == LoadingType.LOADING){
            showContainer(loadingContainer);
            if(contentContainer != null){
                contentContainer.setVisibility(GONE);
            }
        }
        else if(type == LoadingType.LOADERROR){
            showContainer(loadErrorContainer);
            if(contentContainer != null){
                contentContainer.setVisibility(GONE);
            }
        }
        else if(type == LoadingType.LOADFINISHED){
            setVisibility(GONE);
            if(contentContainer != null){
                contentContainer.setVisibility(VISIBLE);
            }
        }
    }

    private void showContainer(View view){
        loadingContainer.setVisibility(GONE);
        loadErrorContainer.setVisibility(GONE);
        view.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
    }

    /**
     * 提供一个监听的接口
     */
    public interface LoadingListener{
        void loadingStart();
        void loadError();
        void loadFinshed();
        void retryLoading();
    }

    /**
     * 重试监听接口
     */
    public interface RetryListener{
        void retryLoading();
    }
    public void setRetryListener(LoadingListener listener){
        this.listener = listener;
    }
    /**
     * 设置内容容器
     * @param view
     */
    public void setConentContainer(View view){
        this.contentContainer = view;
    }


}
