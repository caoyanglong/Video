package com.day.l.video.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.day.l.video.MyApplication;
import com.day.l.video.share.WXShare;
import com.day.l.video.utils.Constants;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WXShare.WX_KEY, false);
        api.registerApp(WXShare.WX_KEY);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp instanceof SendAuth.Resp) {
            SendAuth.Resp sar = ((SendAuth.Resp) baseResp);
            Intent intent = new Intent();
//            intent.putExtra(Constants.ERRCODE, sar.errCode);
//            intent.setAction(TencentContents.WE_CHAT_RESULT);
            switch (sar.errCode) {
                case BaseResp.ErrCode.ERR_OK:
//                    intent.putExtra(TencentContents.WE_CHAT_CODE, sar.code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                    GGToast.showToast(getString(R.string.str_wechat_refuse), true, Toast.LENGTH_SHORT);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    GGToast.showToast(getString(R.string.str_wechat_cancel), true, Toast.LENGTH_SHORT);
                    break;
                default:
//                    GGToast.showToast(getString(R.string.str_wechat_retry), true, Toast.LENGTH_SHORT);
                    break;
            }
            sendBroadcast(intent);
        } else if(baseResp instanceof SendMessageToWX.Resp) {//分享
            switch (baseResp.errCode) {
                /**
                 * 分享成功
                 */
                case BaseResp.ErrCode.ERR_OK:
                    Intent intent = new Intent(MyApplication.GET_RED_BAGS_ACTION);
                    intent.putExtra(Constants.RED_BAGS_ID,baseResp.transaction);
                    sendBroadcast(intent);
                    break;
                /**
                 * 分享取消
                 */
                case BaseResp.ErrCode.ERR_USER_CANCEL:
//                    EventManager.logEvent(this,EventManager.EVENT_INVITE_CANCEL);
                    Toast.makeText(getApplicationContext(),"分享取消",Toast.LENGTH_SHORT).show();
                    break;
                /**
                 * 分享拒绝
                 */
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                    EventManager.logEvent(this,EventManager.EVENT_INVITE_FAILED);
                    Toast.makeText(getApplicationContext(),"分享拒绝",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
        finish();
    }
}
