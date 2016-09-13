package com.day.l.video.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.day.l.video.R;
import com.day.l.video.base.BaseFragmentActivity;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.model.RegisterEntity;
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
public class RegisterActivity extends BaseFragmentActivity implements View.OnClickListener
,LoadingView.LoadingListener{
    private EditText userNameEd;
    private EditText passwordEd;
    private EditText passwordAgainEd;
    private EditText invitationCodeEd;
    private TextView registerTv;
    private LoadingView loadingView;
    @Override
    public int setContent() {
        return R.layout.register_activity_layout;
    }

    @Override
    public void initView() {
        userNameEd = (EditText) findViewById(R.id.user_name);
        passwordEd = (EditText) findViewById(R.id.pass_word);
        invitationCodeEd = (EditText) findViewById(R.id.invitation_code);

        passwordAgainEd = (EditText) findViewById(R.id.pass_word_again);
        registerTv = (TextView) findViewById(R.id.register);

        loadingView = (LoadingView) findViewById(R.id.loading_view);
    }

    @Override
    public void initData() {
        setActionBarCenterTile("注册");
    }

    @Override
    public void initListener() {
        registerTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String user = userNameEd.getText().toString();
        String pwd = passwordEd.getText().toString();
        String pwda = passwordAgainEd.getText().toString();
        String superiorCode = invitationCodeEd.getText().toString();

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pwd) && user.length() >=6 && pwd.length() >= 6 && TextUtils.equals(pwd,pwda)){
            Bundle bundle = new Bundle();
            bundle.putString(Constants.USER_LOGIN,user);
            bundle.putString(Constants.USER_PASS,pwd);
            bundle.putString(Constants.SUPERIO,superiorCode);
            loadingStart();
            getSupportLoaderManager().restartLoader(1,bundle,regsterLoader);
        }
        else{
            Toast.makeText(context,"输入不合法",Toast.LENGTH_SHORT).show();
        }

    }
    private final LoaderManager.LoaderCallbacks<JSONObject> regsterLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams  = new AjaxParams();
            ajaxParams.put(Constants.USER_LOGIN,args.getString(Constants.USER_LOGIN));
            ajaxParams.put(Constants.USER_PASS,args.getString(Constants.USER_PASS));
            ajaxParams.put(Constants.SUPERIO,args.getString(Constants.SUPERIO));
            return new BaseGetLoader(context,ajaxParams,Constants.REGISTER_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            loadFinshed();
            if(data != null){
                RegisterEntity entity = AnalysJson.getEntity(data.toString(),RegisterEntity.class);
                if(entity != null){
                    Toast.makeText(context,entity.getMsg(),Toast.LENGTH_SHORT).show();
                    if(entity.getStatus() == 1){
                        UserConfig.setPassword(passwordAgainEd.getText().toString());
                        UserConfig.setUserName(userNameEd.getText().toString());
                        SharePrefrenceUtil.addPropties(context,Constants.USER_LOGIN,userNameEd.getText().toString());
                        SharePrefrenceUtil.addPropties(context,Constants.USER_PASS,passwordAgainEd.getText().toString());
                        setResult(LoginActivity.START_REGISTER_CODE);
                        finish();
                    }
                }else {
                    Toast.makeText(context,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(context,"注册失败",Toast.LENGTH_SHORT).show();
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
