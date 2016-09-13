package com.day.l.video.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.day.l.video.MainActivity;
import com.day.l.video.R;
import com.day.l.video.base.BaseLazyFragment;
import com.day.l.video.config.UserConfig;
import com.day.l.video.loader.BaseGetLoader;
import com.day.l.video.loader.BasePostLoader;
import com.day.l.video.model.ProfileEntity;
import com.day.l.video.model.StatusEntity;
import com.day.l.video.model.UpdateEntity;
import com.day.l.video.utils.AnalysJson;
import com.day.l.video.utils.Constants;
import com.day.l.video.utils.ImageUtil;
import com.day.l.video.utils.LoadingPicture;
import com.day.l.video.utils.PhoneVersion;
import com.day.l.video.utils.PictureUtil;
import com.day.l.video.utils.SDCard;
import com.day.l.video.utils.SharePrefrenceUtil;
import com.day.l.video.widgets.LoadingView;

import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class MyFragment extends BaseLazyFragment implements View.OnClickListener
,MainActivity.ActivityResultListener,LoadingView.LoadingListener{
    public final static String USER_INFO_KEY = "user_info_key";
    private ImageView headerIcon;
    private TextView username;
    private View profile, myMessage, exchangeRecords, myRedBags, update,exit;

    private String profileJson;
    private LoadingView loadingView;

    @Override
    public int setContentView() {
        return R.layout.my_fragment_layout;
    }

    @Override
    public void initView(View view) {
        headerIcon = (ImageView) findViewById(R.id.header_icon);
        username = (TextView) findViewById(R.id.user_name);

        profile = findViewById(R.id.my_profile);
        myMessage = findViewById(R.id.my_message);
        exchangeRecords = findViewById(R.id.exchage_records);
        myRedBags = findViewById(R.id.my_red_bag);
        update = findViewById(R.id.update);

        loadingView = (LoadingView) findViewById(R.id.loading_view);
        exit = findViewById(R.id.exit);


    }

    @Override
    public void initData() {
        getLoaderManager().restartLoader(1, null, profileLoader);
    }

    @Override
    public void initListener() {
        headerIcon.setOnClickListener(this);
        profile.setOnClickListener(this);
        myMessage.setOnClickListener(this);
        exchangeRecords.setOnClickListener(this);
        myRedBags.setOnClickListener(this);
        update.setOnClickListener(this);
        exit.setOnClickListener(this);
        ((MainActivity)getActivity()).setOnActivityResultListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_icon:
                PictureUtil.doPickPhotoAction(getActivity());
                break;
            case R.id.my_profile:
                if(profileJson != null){
                    Intent intent = new Intent(context,PersonalActivity.class);
                    intent.putExtra(USER_INFO_KEY,profileJson);
                    startActivity(intent);
                }
                break;
            case R.id.my_message:
                if(profileJson != null){
                    Intent intent = new Intent(context,MyMessageActivity.class);
                    intent.putExtra(USER_INFO_KEY,profileJson);
                    startActivity(intent);
                }
                break;
            case R.id.exchage_records:
                if(profileJson != null){
                    Intent intent = new Intent(context,ExchangeRecordsActivity.class);
                    intent.putExtra(USER_INFO_KEY,profileJson);
                    startActivity(intent);
                }
                break;
            case R.id.my_red_bag:
                if(profileJson != null){
                    Intent intent = new Intent(context,MyRedBagsActivity.class);
                    intent.putExtra(USER_INFO_KEY,profileJson);
                    startActivity(intent);
                }
                break;
            case R.id.update:
                loadingStart();
                getLoaderManager().restartLoader(5,null,updateLoader);
                break;
            case R.id.exit:
                SharePrefrenceUtil.remove(context,Constants.TOKEN);
                UserConfig.setToken(null);
                startActivity(new Intent(context,LoginActivity.class));
                getActivity().finish();
                break;

        }
    }

    private final LoaderManager.LoaderCallbacks<JSONObject> profileLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            return new BaseGetLoader(context, ajaxParams, Constants.USER_INFO_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                ProfileEntity entity = AnalysJson.getEntity(data, ProfileEntity.class);
                if (entity != null) {
                    profileJson = data.toString();
                    username.setText(entity.getUser_login());
                    if(entity.getAvatar() != null){
                        LoadingPicture.loadCircleImageNoCache(context,entity.getAvatar(),headerIcon);
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };
    private Dialog updateDialog;
    /**
     * 更新版本
     */
    private final LoaderManager.LoaderCallbacks<JSONObject> updateLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.SYSTEM_KEY, "android");
            ajaxParams.put(Constants.VERIOSN_KEY, PhoneVersion.getVersion(context));
            return new BaseGetLoader(context, ajaxParams, Constants.UPDATE_VERSION_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            loadFinshed();
            if (data != null) {
               Log.d("json_key",data.toString());
                UpdateEntity entity = AnalysJson.getEntity(data,UpdateEntity.class);
                if(entity.getStatus() == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle("发现新版本")
                            .setMessage(entity.getMsg())
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    updateDialog.cancel();
                                }
                            })
                            .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse("http://www.jb51.net");
                                    intent.setData(content_url);
                                    startActivity(intent);

                                }
                            });
                    updateDialog = builder.create();
                    updateDialog.show();
                }else{
                    Toast.makeText(context,"已经最新版本",Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<JSONObject> loader) {

        }
    };

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureUtil.PHOTO_PICKED_WITH_DATA:
                    Uri data2 = intent.getData();
                    ImageUtil.cropImage(data2,getActivity());
                    Log.d("<PHOTO_PICKE>","PHOTO_PICKED_WITH_DATA");
                    break;
                case PictureUtil.REQUEST_CAMERA:
                    ImageUtil.cropImage(Uri.fromFile(new File(SDCard.getPicturePath(), "header.png")),getActivity());
                    Log.d("<PHOTO_PICKE>","REQUEST_CAMERA");
                    break;
                case PictureUtil.PHOTO_CROP:
                    Bundle bundle = intent.getExtras();
                    Bitmap myBitmap = (Bitmap) bundle.get("data");
                    Log.d("<photo_crop>","-------------------->PHOTO_CROP");
                    updateHeader = ImageUtil.comp(myBitmap);
                    ImageUtil.saveBitmap2file(updateHeader, new File(SDCard.getPicturePath(), "header.png"));
                    uploadIconToServer(ImageUtil.Bitmap2Bytes(updateHeader));
                default:
                    break;
            }
        }
    }
    private Bitmap updateHeader;
    public void uploadIconToServer(byte[] data){
        try {
            String iconStr = ImageUtil.encodeBase64File(data);
            if(iconStr != null){
                Bundle bundle = new Bundle();
                bundle.putString(Constants.AVATAR_KEY,"data:/image/png:base64,"+iconStr);
                loadingStart();
                getLoaderManager().restartLoader(3,bundle,uploadIconLoader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final LoaderManager.LoaderCallbacks<JSONObject> uploadIconLoader = new LoaderManager.LoaderCallbacks<JSONObject>() {
        @Override
        public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(Constants.TOKEN, UserConfig.getToken());
            ajaxParams.put(Constants.AVATAR_KEY, args.getString(Constants.AVATAR_KEY));
            return new BasePostLoader(context, ajaxParams, Constants.UPLOAD_HEAD_ICON_API);
        }

        @Override
        public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
            if (data != null) {
                headerIcon.setImageBitmap(ImageUtil.getCroppedRoundBitmap(updateHeader,90));
                Log.d("<json_object>",data.toString());
                StatusEntity entity = AnalysJson.getEntity(data,StatusEntity.class);
                if(entity != null){
                    if(entity.getStatus() == 1){
                        Toast.makeText(context,"上传成功",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context,"上传失败",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(context,"上传失败",Toast.LENGTH_SHORT).show();
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
