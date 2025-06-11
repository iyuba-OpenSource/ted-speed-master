package com.sdiyuba.tedenglish;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iyuba.dlex.bizs.DLManager;
import com.iyuba.headlinelibrary.IHeadline;
import com.iyuba.headlinelibrary.data.local.db.HLDBManager;
import com.iyuba.imooclib.IMooc;
import com.iyuba.module.dl.BasicDLDBManager;
import com.iyuba.module.favor.BasicFavor;
import com.iyuba.module.favor.data.local.BasicFavorDBManager;
import com.iyuba.module.movies.data.local.db.DBManager;
import com.sdiyuba.tedenglish.presenter.home.RequestAdPresenter;
import com.bumptech.glide.Glide;
import com.iyuba.module.privacy.PrivacyInfoHelper;
import com.sdiyuba.tedenglish.R;
import com.johnwa.spannabletext.ITextListener;
import com.johnwa.spannabletext.SpannableText;
import com.mob.MobSDK;
import com.sdiyuba.tedenglish.databinding.ActivityInitBinding;

import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.AdEntryBean;
import com.sdiyuba.tedenglish.view.home.RequestAdContract;
import com.umeng.commonsdk.UMConfigure;
import com.yd.saas.base.interfaces.AdViewSpreadListener;
import com.yd.saas.config.exception.YdError;
import com.yd.saas.ydsdk.YdSpread;
import com.yd.saas.ydsdk.manager.YdConfig;
import com.youdao.sdk.common.OAIDHelper;
import com.youdao.sdk.common.YouDaoAd;
import com.youdao.sdk.common.YoudaoSDK;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/*
启动页
 */
public class InitActivity extends AppCompatActivity implements ITextListener, RequestAdContract.RequestAdView, AdViewSpreadListener {


    private String url1 = Constant.tiaokuan; //用户条款
    private String url2 = Constant.yinsi; //隐私政策
    private String content = "    感谢您对本公司的支持!本公司非常重视您的个人信息和隐私保护。" +
            " 为了更好地保障您的个人权益，在您使用我们的产品前，请务必审慎阅读《隐私政策》和《用户协议》内的所有条款，" + "尤其是:" + "\n" +
            "       1.我们对您的个人信息的收集/保存/使用/对外提供/保护等规则条款，以及您的用户权利等条款; " + "\n" +
            "       2.约定我们的限制责任、免责条款;" + "\n" +
            "       3.其他以颜色或加粗进行标识的重要条款。" + "\n" +
            "      您点击“同意并继续”的行为即表示您已阅读完毕并同意以上协议的全部内容。" +
            " 如您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务!";

    Dialog dialog;

    private long time5s = 0;

    private boolean isAdCLick = false;

    private RequestAdPresenter requestAdPresenter;


    ImageView imageView;


    private ActivityInitBinding binding;


    private AdEntryBean.DataDTO dataDTO;


    LinearLayout ad_view;


    private String adkey = "0087";

    private boolean isAdFirst = true;

    private String ad_title = "ads4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInitBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //设置状态栏文字颜色及图标为深色
        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        imageView = findViewById(R.id.init_iv);

        //广告
        ad_view = binding.initLl.findViewById(R.id.csj_initad);

//        channel =Constant.channel;

        requestAdPresenter = new RequestAdPresenter();
        requestAdPresenter.attchView(this);


        PravicyCheck();

    }


    public void onClickAgree(View v) {

        initview(); //初始化


        dialog.dismiss(); //关闭


        isAdCLick = true;


        //下面将已阅读标志写入文件，再次启动的时候判断是否显示。
        this.getSharedPreferences("file", Context.MODE_PRIVATE).edit()
                .putBoolean("AGREE", true).apply();

        this.getSharedPreferences("init", Context.MODE_PRIVATE).edit()
                .putBoolean("isFirst", false).apply();


        onResume();
    }

    public void onClickDisagree(View v) {
        System.exit(0);//退出软件
    }


    public void showPrivacy(String privacyFileName) {
        String str = initAssets(privacyFileName);
        final View inflate = LayoutInflater.from(InitActivity.this).inflate(R.layout.init_pop, null);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        tv_title.setText("隐私政策授权提示");
        TextView tv_content = (TextView) inflate.findViewById(R.id.tv_content);
        tv_content.setText(str);
        SpannableText spannableText = new SpannableText(this, (ITextListener) this);
        //设置参数
        spannableText.setParam(content, "《用户协议》", "《隐私政策》", url1, url2);
        //设置目标字体样式
        spannableText.setTargetStyle(R.color.lianjiese, false);
        //设置控件
        spannableText.setTextView(tv_content);
        dialog = new AlertDialog
                .Builder(InitActivity.this)
                .setView(inflate)
                .show();
        // 通过WindowManager获取    窗口获取
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = dm.widthPixels * 4 / 5;  //窗口宽高
        params.height = dm.heightPixels * 1 / 2;
        dialog.setCancelable(false);//屏蔽返回键
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    /**
     * 从assets下的txt文件中读取数据
     */
    public String initAssets(String fileName) {
        String str = content;

        try {
            InputStream inputStream = getAssets().open(fileName);
            str = getString(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return str;
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void PravicyCheck() {

        SharedPreferences userInfo = getSharedPreferences("useruid", MODE_PRIVATE); // 获得SharedPreferences对象userInfo
// 方法的第一个String类参数 name 指存储文件名，第二个int型参数 mode 指文件打开方式
        Constant.uid = userInfo.getString("useruid", Constant.uid); // 获得SharedPreferences对象的指定变量的值


        SharedPreferences vipInfo = getSharedPreferences("vipStates", MODE_PRIVATE); // 获得SharedPreferences对象userInfo
// 方法的第一个String类参数 name 指存储文件名，第二个int型参数 mode 指文件打开方式
        Constant.vipStatus = vipInfo.getInt("vipStates", Constant.vipStatus); // 获得SharedPreferences对象的指定变量的值


        Boolean status = this.getSharedPreferences("file", Context.MODE_PRIVATE)
                .getBoolean("AGREE", false);

        Boolean isFirst = this.getSharedPreferences("init", Context.MODE_PRIVATE)
                .getBoolean("isFirst", true);


        if (status) {//如果同意了

            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, MyApplication.getChannel());
//
//            Handler mHandler = new Handler();
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    startActivity(new Intent(InitActivity.this, MainActivity.class));
//                    finish();
//                }
//            }, 2000); //延迟时间

            if (Constant.uid != null) {

                requestAdPresenter.getAdEntryAll(Constant.AdAppID + "", 1, Constant.uid);
            } else {
                requestAdPresenter.getAdEntryAll(Constant.AdAppID + "", 1, "0");
            }


//
//
//            Log.d("fang00123",Constant.vipStatus+"*"+Constant.uid);
//            if (Constant.vipStatus ==0 ||Constant.vipStatus ==120){
//                if(Constant.uid!=null){
//                    initPresenter.getAdEntryAll(291+"", 1, Constant.uid);
//                }else {
//                    initPresenter.getAdEntryAll(291+"", 1, "0");
//                }
//            }else {

//            }


        } else {
            showPrivacy("privacy.txt");//放在assets目录下的隐私政策文本文件
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (initPresenter == null) {
//
//            initPresenter.detachView();
//        }
//        if (countDownTimer != null) {
//
//            countDownTimer.cancel();
//        }
    }

    @Override
    public void onClickText(String url) {
        //点击隐私协议跳转网页
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);

    }


    @Override
    protected void onResume() {
        super.onResume();


        if (isAdCLick) {//点击了就直接跳转mainactivity

            startActivity(new Intent(InitActivity.this, MainActivity.class));
            finish();
        }


    }


    public void initview() {

        //友盟
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, MyApplication.getChannel());


        MobSDK.submitPolicyGrantResult(true); //验证码   只执行一次

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

        //微课初始化

        IMooc.init(getApplicationContext(), String.valueOf(Constant.AppId), Constant.EvaType);
        //微课分享
        IMooc.setEnableShare(false);
        IMooc.setYoudaoId("edbd2c39ce470cd72472c402cccfb586");
        IMooc.setAdAppId("2291");

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


    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toast(String msg) {

    }

    @Override
    public void getAdEntryAllComplete(AdEntryBean adEntryBean) {

        dataDTO = adEntryBean.getData();
        String type = dataDTO.getType();

        if (!dataDTO.getTitle().equals("")){

            ad_title = dataDTO.getTitle();
        }


        if (type.equals("web")) {

            Glide.with(InitActivity.this).load("http://static3.iyuba.cn/dev" + dataDTO.getStartuppic()).into(binding.initIv);
        } else if (type.equals(Constant.AD_ADS1) || type.equals(Constant.AD_ADS2) || type.equals(Constant.AD_ADS3)
                || type.equals(Constant.AD_ADS4) || type.equals(Constant.AD_ADS5)|| type.equals(Constant.AD_ADS6)) {

            if(type.equals(Constant.AD_ADS1)){
                adkey = "0087";
            } else if (type.equals(Constant.AD_ADS2)) {
                adkey = "0087";
            }else if (type.equals(Constant.AD_ADS3) ){
                adkey = "0231";
            }else if (type.equals(Constant.AD_ADS4)) {
                adkey = "0226";
            }else if (type.equals(Constant.AD_ADS5) ){
                adkey = "0234";
            }else if (type.equals(Constant.AD_ADS6)) {
                adkey = "0593";
            }


            YdSpread mSplashAd = new YdSpread.Builder(InitActivity.this)
                    .setKey(adkey)
                    .setContainer(ad_view)
                    .setSpreadListener(this)
                    .setCountdownSeconds(4)
                    .setSkipViewVisibility(true)
                    .build();
            mSplashAd.requestSpread();
        } else {

            startActivity(new Intent(InitActivity.this, MainActivity.class));
            finish();
        }

    }

    @Override
    public void onAdDisplay() {

    }

    @Override
    public void onAdClose() {
        if (!isAdCLick) {

            startActivity(new Intent(InitActivity.this, MainActivity.class));
            isAdCLick = true;
            finish();
        }
    }

    @Override
    public void onAdClick(String s) {
        isAdCLick = true;
    }

    @Override
    public void onAdFailed(YdError ydError) {


        Log.d("fang8989898",ydError.getMsg());
        if (isAdFirst){

            isAdFirst = false;


            if(ad_title.equals(Constant.AD_ADS1)){
                adkey = "0087";
            } else if (ad_title.equals(Constant.AD_ADS2)) {
                adkey = "0087";
            }else if (ad_title.equals(Constant.AD_ADS3) ){
                adkey = "0231";
            }else if (ad_title.equals(Constant.AD_ADS4)) {
                adkey = "0226";
            }else if (ad_title.equals(Constant.AD_ADS5) ){
                adkey = "0234";
            }else if (ad_title.equals(Constant.AD_ADS6)) {
                adkey = "0593";
            }

            YdSpread mSplashAd = new YdSpread.Builder(InitActivity.this)
                    .setKey(adkey)
                    .setContainer(ad_view)
                    .setSpreadListener(this)
                    .setCountdownSeconds(4)
                    .setSkipViewVisibility(true)
                    .build();
            mSplashAd.requestSpread();


        }else {
            startActivity(new Intent(InitActivity.this, MainActivity.class));
            finish();
        }
    }
}