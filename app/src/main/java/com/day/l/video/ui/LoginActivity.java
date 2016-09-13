package com.day.l.video.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.day.l.video.MainActivity;
import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.LoginEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.SharePrefrenceUtil;
import com.day.l.video.widgets.LoadingView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener,LoadingView.LoadingListener{
    private EditText userNameEd;
    private EditText passwordEd;
    private TextView loginTv;
    private TextView registerTv;
    private LoadingView loadingView;

    public final static int START_REGISTER_CODE = 1000;

    @Override
    public int setContent() {
        return R.layout.login_activity_layout;
    }

    @Override
    public void initView() {
        userNameEd = (EditText) findViewById(R.id.user_name);
        passwordEd = (EditText) findViewById(R.id.pass_word);

        loginTv = (TextView) findViewById(R.id.login);
        registerTv = (TextView) findViewById(R.id.register);
        loadingView = (LoadingView) findViewById(R.id.loading_view);

    }

    @Override
    public void initData() {
        setActionBarCenterTile("登陆");
        if(SharePrefrenceUtil.isExit(context, Constants.USER_PASS) && SharePrefrenceUtil.isExit(context, Constants.USER_LOGIN) ){
            UserConfig.setUserName(SharePrefrenceUtil.getProperties(context,Constants.USER_LOGIN));
            UserConfig.setPassword(SharePrefrenceUtil.getProperties(context,Constants.USER_PASS));
            userNameEd.setText(UserConfig.getUserName());
            passwordEd.setText(UserConfig.getPassword());
        }
    }

    @Override
    public void initListener() {
        loginTv.setOnClickListener(this);
        registerTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                performLogin();
                break;
            case R.id.register:
                startActivityForResult(new Intent(context,RegisterActivity.class),START_REGISTER_CODE);
                break;
        }
    }

    private void performLogin() {
        String name = userNameEd.getText().toString();
        String pwd = passwordEd.getText().toString();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd) && name.length()>=2&&pwd.length()>=6){
            Bundle bundle = new Bundle();
            bundle.putString(Constants.USER_LOGIN,name);
            bundle.putString(Constants.USER_PASS,pwd);
            loadingStart();
            getSupportLoaderManager().restartLoader(1,bundle,loginLoader);
        }
        else{
            Toast.makeText(context,"抱歉输入不合法",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == requestCode && requestCode == START_REGISTER_CODE){
            userNameEd.setText(UserConfig.getUserName());
            passwordEd.setText(UserConfig.getPassword());
        }
    }

    private LoaderManager.LoaderCallbacks<JSONObject> loginLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams  = new AjaxParams();
            ajaxParams.put(Constants.USER_LOGIN,args.getString(Constants.USER_LOGIN));
            ajaxParams.put(Constants.USER_PASS,args.getString(Constants.USER_PASS));
            return new BaseGetLoader(context,ajaxParams,Constants.LONGIN_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if(data != null){
                LoginEntity entity = AnalysJson.getEntity(data,LoginEntity.class);
                if(entity != null){
                    loadFinshed();
                    if(entity.getStatus() == 1){
                        UserConfig.setToken(entity.getToken());
                        SharePrefrenceUtil.addPropties(context,Constants.TOKEN,entity.getToken());
                        SharePrefrenceUtil.addPropties(context,Constants.EXPIRATION_TIME_KEY,entity.getExpiration_time()+"");
                        UserConfig.setPassword(passwordEd.getText().toString());
                        UserConfig.setUserName(userNameEd.getText().toString());
                        SharePrefrenceUtil.addPropties(context,Constants.USER_LOGIN,UserConfig.getUserName());
                        SharePrefrenceUtil.addPropties(context,Constants.USER_PASS,UserConfig.getPassword());
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(context,entity.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context,"登陆失败",Toast.LENGTH_SHORT).show();
                    loadFinshed();
                }
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
