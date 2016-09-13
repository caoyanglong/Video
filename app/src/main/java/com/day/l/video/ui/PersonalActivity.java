package com.day.l.video.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BasePostLoader;
import com.day.l.video.model.ProfileEntity;
import com.day.l.video.model.StatusEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.widgets.LoadingView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

/**
 * Created by cyl
 * on 2016/9/10.
 * email:670654904@qq.com
 */
public class PersonalActivity extends BaseFragmentActivity implements LoadingView.LoadingListener{
    private EditText nickName,aliPay,phoneNumber;
    private TextView redBags,userLogin;
    private ProfileEntity entity;
    private LoadingView loadingView;
    @Override
    public int setContent() {
        return R.layout.personal_activity_layout;
    }

    @Override
    public void initView() {
        userLogin = (TextView) findViewById(R.id.user_login);
        nickName = (EditText) findViewById(R.id.nick_name);
        aliPay = (EditText) findViewById(R.id.ali_pay);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        redBags = (TextView) findViewById(R.id.red_bag_count);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
    }

    @Override
    public void initData() {
        setActionBarCenterTile("个人资料");
        setRightButtonText("保存");
        this.entity = AnalysJson.getEntity(getIntent().getStringExtra(MyFragment.USER_INFO_KEY),ProfileEntity.class);
        userLogin.setText(entity.getUser_login());
        nickName.setText(entity.getFull_name());
        aliPay.setText(entity.getAlipay());
        phoneNumber.setText(entity.getMobile());
        redBags.setText(entity.getRed_bag());
        showRightButton();
    }

    @Override
    public void onRightButtonListener(View v) {
        loadingStart();
        getSupportLoaderManager().restartLoader(1,null,editUerInfoLoader);
    }

    @Override
    public void initListener() {

    }

    private final LoaderManager.LoaderCallbacks<JSONObject> editUerInfoLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            ajaxParams.put(Constants.FULL_NAME, nickName.getText().toString());
            ajaxParams.put(Constants.ALIPAY_KEY, aliPay.getText().toString());
            ajaxParams.put(Constants.MOBILE_KEY, phoneNumber.getText().toString());
            return new BasePostLoader(context, ajaxParams, Constants.SET_USER_INFO_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                Log.d("<json_object>",data.toString());
                StatusEntity entity = AnalysJson.getEntity(data,StatusEntity.class);
                if(entity != null && entity.getStatus() == 1){
                    Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,entity.getMsg(),Toast.LENGTH_SHORT).show();
                }
                loadFinshed();
            }
            else{
                loadError();
                Toast.makeText(context,"提交失败",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };
    @Override
    public void loadingStart() {
        loadingView.setLoadingType(LoadingView.LoadingType.LOADING);
    }

    @Override
    public void loadError() {
        loadingView.setLoadingType(LoadingView.LoadingType.LOADFINISHED);
    }

    @Override
    public void loadFinshed() {
        loadingView.setLoadingType(LoadingView.LoadingType.LOADFINISHED);
    }

    @Override
    public void retryLoading() {

    }
}
