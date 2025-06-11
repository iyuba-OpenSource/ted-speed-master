package com.sdiyuba.tedenglish;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.iyuba.dlex.bizs.DLManager;
import com.iyuba.headlinelibrary.IHeadline;
import com.iyuba.headlinelibrary.data.local.db.HLDBManager;
import com.iyuba.imooclib.IMooc;
import com.iyuba.module.dl.BasicDLDBManager;
import com.iyuba.module.favor.BasicFavor;
import com.iyuba.module.favor.data.local.BasicFavorDBManager;
import com.iyuba.module.movies.data.local.db.DBManager;
import com.iyuba.module.privacy.PrivacyInfoHelper;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.tencent.vasdolly.helper.ChannelReaderUtil;
import com.umeng.commonsdk.UMConfigure;
import com.yd.saas.ydsdk.manager.YdConfig;
import com.youdao.sdk.common.OAIDHelper;
import com.youdao.sdk.common.YouDaoAd;
import com.youdao.sdk.common.YoudaoSDK;


public class MyApplication extends Application {


    @SuppressLint("StaticFieldLeak")
    private static Context context;


    private static String channel;


    public static String getChannel() {
        return channel;
    }

    public static void setChannel(String channel) {
        MyApplication.channel = channel;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        //需上传 3~5 张。建议尺寸（请确保宽高比9:16）：
        // 450*800px；格式：PNG、 JPG、 JPEG (均在 2 MB 以内)，以及 WEBP (100 KB 以内)。 @房经晓 我这边截图需要这个尺寸的  512  216


        //需上传 3~5 张。建议尺寸（请确保宽高比9:16）：450*800px；格式：PNG、 JPG、 JPEG (均在 2 MB 以内)，以及 WEBP (100 KB 以内)。 @房经晓 我这边截图需要这个尺寸的
        channel = ChannelReaderUtil.getChannel(getApplicationContext());

        //友盟预初始化
        UMConfigure.preInit(this, "6498147887568a379b5ebdf6",channel);
        UMConfigure.setLogEnabled(false);


        boolean status = this.getSharedPreferences("file", Context.MODE_PRIVATE)
                .getBoolean("AGREE", false);



        if (status) {//如果同意了

            NetWorkManager.getInstance().init();

            NetWorkManager.getInstance().initDev();

            System.loadLibrary("msaoaidsec");
            OAIDHelper.getInstance().init(this);
//
//
            //禁止有道获取id等信息
            YouDaoAd.getYouDaoOptions().setCanObtainAndroidId(false);

            YouDaoAd.getNativeDownloadOptions().setConfirmDialogEnabled(true);
            YouDaoAd.getYouDaoOptions().setAppListEnabled(false);
            YouDaoAd.getYouDaoOptions().setPositionEnabled(false);
            YouDaoAd.getYouDaoOptions().setSdkDownloadApkEnabled(true);
            YouDaoAd.getYouDaoOptions().setDeviceParamsEnabled(false);
            YouDaoAd.getYouDaoOptions().setWifiEnabled(false);
//            //有道
            YoudaoSDK.init(this);
            YdConfig.getInstance().init(this, String.valueOf(Constant.AppId));
//
//            //集成的广告流
            PrivacyInfoHelper.init(this);
            PrivacyInfoHelper.getInstance().putApproved(true);

            //友盟
//            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, Constant.channel);
////        EventBus.getDefault().register(this);
//            //有道广告
//            //有道处理下载类广告问题    信息流
//
            //微课初始化

            IMooc.init(getApplicationContext(), String.valueOf(Constant.AppId), Constant.EvaType);
            //微课分享
            IMooc.setEnableShare(false);
            IMooc.setYoudaoId("edbd2c39ce470cd72472c402cccfb586");
            IMooc.setAdAppId("2221");

            IMooc.setYdsdkTemplateKey("0224","0229","0236","0233","");


//            //有道广告
//            //有道处理下载类广告问题    信息流


            IHeadline.setDebug(false);
            IHeadline.init(getApplicationContext(), Constant.AppId + "", Constant.NAME);
            IHeadline.setEnableShare(false);
            IHeadline.setEnableComment(false);
            IHeadline.setEnableGoStore(false);
            IHeadline.setEnableSmallVideoTalk(false);//小视频配音
            IHeadline.setEnableIyuCircle(false);//禁用爱与圈
            IHeadline.setAdAppId("2291");
            //设置信息流广告  csj - ylh - ks -bd
            IHeadline.setYdsdkTemplateKey("0694", "0700", "0704", "0709", "");


            //db
            BasicDLDBManager.init(this);
            BasicFavor.init(getApplicationContext(), Constant.AppId + "");
            BasicFavorDBManager.init(this);
            DLManager.init(this, 5);
            DBManager.init(this);
            HLDBManager.init(this);

        }
    }


    public static Context getContext() {
        return context;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        context = null;

    }
}
