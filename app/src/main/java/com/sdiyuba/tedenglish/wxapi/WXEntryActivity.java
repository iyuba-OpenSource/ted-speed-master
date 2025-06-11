package com.sdiyuba.tedenglish.wxapi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.wxapi.util.WeiXinConstants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.annotations.Nullable;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI wxapi;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxapi.handleIntent(intent, this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wxentry);

        wxapi = WXAPIFactory.createWXAPI(this, WeiXinConstants.APP_ID);
        wxapi.handleIntent(getIntent(), this);
    }

    /**
     * 微信发送请求到第三方应用时，会回调到该方法
     */
    @Override
    public void onReq(BaseReq baseReq) {
        // 这里不作深究


    }

    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     * app发送消息给微信，处理返回消息的回调
     */
    @Override
    public void onResp(BaseResp baseResp) {


        if (baseResp.getType()==ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM){

            WXLaunchMiniProgram.Resp launch = (WXLaunchMiniProgram.Resp) baseResp;
            String extraData = launch.extMsg;
        }


        switch (baseResp.errCode) {
            // 正确返回
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    // ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX是微信分享，api自带
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        Constant.WX_SHARE=true;
                        // 只是做了简单的finish操作
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            default:
                // 错误返回
                switch (baseResp.getType()) {
                    // 微信分享
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:

                        finish();
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}
