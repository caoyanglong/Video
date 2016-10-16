package com.day.l.video.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.day.l.video.R;

/**
 * 将activity 中经常写的代码 进行格式化
 *
 * @author 曹阳龙
 *         <p>
 *         2015-9-13
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextView title, confirm, backTv;
    protected FragmentManager fragmentManager;
    //资源管理
    protected Resources resources;
    //上下环境
    protected Context context;
    protected LayoutInflater inflater;
    //设置是否显示自定义布局
    private boolean customAction = true;
    //返回按钮
    private View back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        onCreate();
    }

    /**
     * 本类 中做的初始化
     */
    private void init() {
        beforeSetContent();
        setContentView(setContent());
        context = this;
        //使用得到  支持的supportfragmentmanager
        fragmentManager = getSupportFragmentManager();
        resources = getResources();
        inflater = LayoutInflater.from(context);
        confingActionBar();
    }

    /**
     * 用于设置布局文件
     *
     * @return
     */
    public abstract int setContent();

    /**
     * oncreat 改为内部执行
     */
    private void onCreate() {
        initView();
        initData();
        initListener();
    }

    /**
     * 配置actionbar
     */
    private void confingActionBar() {
        if (!setFullScreen()) {
            if (customAction) {
                initCustomActionbar();
            } else {
                defaultActionbar();
            }
        }
    }

    /**
     * actionbar 的默认布局
     */
    private void defaultActionbar() {
        actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.i("actionbar", "系统的actionbar 为空");
            return;
        }
//		actionBar.setBackgroundDrawable(getResources().getDrawable());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
//		actionBar.setTitle(getIntent().getStringExtra(Constants.ACTIONBARDISPLAYNAME));
    }

    /**
     * 隐藏actionbar
     */
    public void hideActionbar(){
        if(actionBar != null){
            actionBar.hide();
        }
    }

    /**
     * 显示actionbar
     */
    public void showActionbar(){
        if(actionBar != null){
            actionBar.show();
        }
    }

    /**
     * 自定义actionbar
     */
    private void initCustomActionbar() {
        actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.i("actionbar", "系统的actionbar 为空");
            return;
        }
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_title_background));
        //设置不显示logo
        actionBar.setDisplayUseLogoEnabled(false);
        //设置使用自定有  布局
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_title_center_layout);
        title = (TextView) actionBar.getCustomView().findViewById(R.id.title);
        back = actionBar.getCustomView().findViewById(R.id.back);
        confirm = (TextView) actionBar.getCustomView().findViewById(R.id.confirm);
        backTv = (TextView) actionBar.getCustomView().findViewById(R.id.back_tv);
        confirm.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightButtonListener(v);
            }
        });
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 提供这个方法原因  ，  主要  有一些代码需要在  setcontent 之前调用  如 ： window窗口的设置
     */
    public void beforeSetContent() {

    }

    /**
     * 首次   必先初始化  view
     */
    public abstract void initView();

    /**
     * 初始化数据   从网络 获取数据  数据添加到  view  等逻辑 工作
     */
    public abstract void initData();

    /**
     * 初始化  view listener 的工作
     */
    public abstract void initListener();

    /**
     * 设置activity为全屏
     */
    public boolean setFullScreen() {
        return false;
    }

    /**
     * 隐藏标题的返回键
     */
    public void hideTitleBack() {
        if (back != null && backTv != null) {
            back.setVisibility(View.GONE);
            backTv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置自定义的actionbar 中间的 title
     *
     * @param actionbarTitle
     */
    public void setActionBarCenterTile(String actionbarTitle) {
        title.setText(actionbarTitle);
    }

    /**
     * 设置自定义的actionbar 中间的 title
     *
     * @param actionbarTitle
     */
    public void setActionBarCenterTile(int actionbarTitle) {
        title.setText(getResources().getString(actionbarTitle));
    }

    /**
     * 设置使用自定义布局
     * 默认为自定义的  布局
     */
    public void setCustomActionbarEnable(Boolean enable) {
        customAction = enable;
    }

    /**
     * 设置右边按钮的文字
     *
     * @param text
     */
    public void setRightButtonText(String text) {
        confirm.setText(text);
    }

    /**
     * 点击左边的事件
     * 可以重写这个方法
     *
     * @param v
     */
    public void onRightButtonListener(View v) {

    }

    /**
     * 默认左边的按钮是关闭的
     */
    public void showRightButton() {
        confirm.setVisibility(View.VISIBLE);
    }


    /**
     * 系统标题栏透明
     */
    public void setOverLayTitileBar() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

}
