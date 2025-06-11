package com.sdiyuba.tedenglish.activity.myFragmentDetail;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;
import com.iyuba.module.user.IyuUserManager;
import com.iyuba.module.user.User;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityBuyIyubiBinding;
import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.model.bean.VipBean;
import com.sdiyuba.tedenglish.model.bean.VipParseBean;
import com.sdiyuba.tedenglish.model.bean.WxOrderBean;
import com.sdiyuba.tedenglish.presenter.home.UidLoginPresenter;
import com.sdiyuba.tedenglish.presenter.home.WxOrderPresenter;
import com.sdiyuba.tedenglish.presenter.home.vipBuyPresenter;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.view.home.WxOrderContract;
import com.sdiyuba.tedenglish.view.home.uidLoginContract;
import com.sdiyuba.tedenglish.view.home.vipBuyContract;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

public class BuyIyubiActivity extends AppCompatActivity implements vipBuyContract.vipBuyView, uidLoginContract.uidLoginView, WxOrderContract.WxOrderView{

    private ActivityBuyIyubiBinding binding;

    private UidLoginPresenter uidLoginpresenter;

    private vipBuyPresenter vipBuypresenter;

    private PopupWindow popupWindowWord;

    private String code = null;

    private String WIDtatal_fee = null;

    private int amount = 0;

    private final String wxKey = Constant.wxKey;
    private IWXAPI iwxapi;

    private int product_id = -1;

    private String WIDbody = null;

    private String WIDsubject = null;

    LinearLayout chooseWxLinear,chooseZfbLinear,liubai;

    ImageView checkWxImage,checkZfbImage;

    TextView buy;

    private boolean isWxPay = true;

    private WxOrderPresenter wxOrderPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBuyIyubiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        vipBuypresenter = new vipBuyPresenter();
        vipBuypresenter.attchView(this);

        uidLoginpresenter=new UidLoginPresenter();
        uidLoginpresenter.attchView(this);

        wxOrderPresenter=new WxOrderPresenter();
        wxOrderPresenter.attchView(this);

        iwxapi = WXAPIFactory.createWXAPI(this, wxKey, true);
        iwxapi.registerApp(wxKey);

        binding.tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.buySmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 19.9 + "";
                amount = 210;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" +amount+ WIDsubject;

                showPop();
            }
        });

        binding.buyMoreSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 59.9 + "";
                amount = 650;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" +amount+ WIDsubject;

                showPop();
            }
        });

        binding.buyNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 99.9 + "";
                amount = 1100;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" +amount+ WIDsubject;

                showPop();
            }
        });

        binding.buyMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 599 + "";
                amount = 6600;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" +amount+ WIDsubject;

                showPop();
            }
        });

        binding.buyMost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 999 + "";
                amount = 12000;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" +amount+ WIDsubject;

                showPop();
            }
        });




    }

    @SuppressLint("MissingInflatedId")
    public void showPop(){

        //暂时直接支付宝支付
        vipBuypresenter.getVip(Constant.AppId, Integer.parseInt(Constant.uid), code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject,0);


        //取词成功，生成屏蔽层    popupWindowWord == null
            if (false) {
                View popView = getLayoutInflater().inflate(R.layout.pop_buy_iyubi, null);
                popupWindowWord = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                popupWindowWord.showAtLocation(binding.buyIyubiLine, Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;

                getWindow().setAttributes(lp);

                //获取屏蔽层控件
                chooseWxLinear = popView.findViewById(R.id.choose_wx_linear);

                chooseZfbLinear = popView.findViewById(R.id.choose_zfb_linear);

                checkWxImage = popView.findViewById(R.id.check_wx_image);
                checkZfbImage = popView.findViewById(R.id.check_zfb_image);


                buy = popView.findViewById(R.id.now_buy);

                liubai = popView.findViewById(R.id.liubai);

                //支付方式
                chooseWxLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkWxImage.setImageResource(R.drawable.check_true);
                        checkZfbImage.setImageResource(R.drawable.check_false);
                        isWxPay = true;
                    }
                });

                chooseZfbLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkWxImage.setImageResource(R.drawable.check_false);
                        checkZfbImage.setImageResource(R.drawable.check_true);
                        isWxPay= false;
                    }
                });

                liubai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindowWord.dismiss();
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1.0f;
                        getWindow().setAttributes(lp);
                        popupWindowWord = null;
                    }
                });

                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //isWxPay
                        if (isWxPay) {
                            //wx支付


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");// HH:mm:ss
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            simpleDateFormat.format(date);

                            //wx product_id传13 是ted演讲
//                            product_id = 13;
                            String sign = MD5.md5(Constant.AppId + Constant.uid + WIDtatal_fee + amount + simpleDateFormat.format(date));

                            wxOrderPresenter.getWxOrder(wxKey, wxKey, Constant.AppId, Constant.uid, WIDtatal_fee, amount, product_id, "json", sign, 0);


                            //微信
                        } else {
                            //支付宝


                            vipBuypresenter.getVip(Constant.AppId, Integer.parseInt(Constant.uid), code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject,0);


                        }
                    }
                });

            }



    }

    @Override
    public void getVip(VipParseBean vipParseBean) {



        if(vipParseBean.getResult().equals("200")){


//            Handler mHandler = new Handler();

            Runnable payRunnable = () -> {
                PayTask alipay = new PayTask(BuyIyubiActivity.this);
                Map<String,String> result = alipay.payV2(vipParseBean.getAlipayTradeStr(),true);
                //Log.d("chen",result.toString());
                if(result.get("resultStatus").equals("9000")){
                    vipBuypresenter.callbackVip(result.toString());
                }else{
                    toast(result.get("memo"));
                    hideLoading();
                }
              /*  Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);*/
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();

        }
    }

    @Override
    public void callbackVip(VipBean vipBean) {


        String sign = MD5.md5("20001" + Constant.uid + "iyubaV2");
        uidLoginpresenter.uidLogin("android", "json", "20001", Constant.uid, Constant.uid, Constant.AppId, sign);

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
    public void uidLogin(UidBean uidBean) {
        Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());

        SharedPreferences vipStatusInfo = getSharedPreferences("vipStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = vipStatusInfo.edit();

        Constant.money = uidBean.getMoney();//钱包

        Constant.aiyubi = uidBean.getAmount();//爱语币

        Constant.jifen = Integer.parseInt(uidBean.getCredits());//积分

        Constant.phone = uidBean.getMobile();//手机号


        //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
        //要转成后的时间的格式
        android.icu.text.SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
        // 时间戳转换成时间
        String vipDate = null;
        vipDate = simpleDateFormat.format(new Date(timeStamp));

        Constant.vipDate = vipDate;//vip时间


        User user = new User();
        user.vipStatus = String.valueOf(Constant.vipStatus);
        if (Constant.vipStatus != 0) {
            user.vipExpireTime = uidBean.getExpireTime();
        }
        user.uid = Integer.parseInt(Constant.uid);
        user.credit = Integer.parseInt(uidBean.getCredits());
        user.name = Constant.username;
        user.imgUrl = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.uid + "&size=big";

        user.mobile = Constant.mobile;
        user.iyubiAmount = (int) Constant.aiyubi;
        IyuUserManager.getInstance().setCurrentUser(user);

        Toast.makeText(BuyIyubiActivity.this, "购买成功!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void getWxOrder(WxOrderBean wxOrderBean) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                //判断有没有安装微信，没有就做相应提示
                if(iwxapi.isWXAppInstalled()){
                    PayReq req = new PayReq();
                    req.appId = wxOrderBean.getAppid();
                    req.partnerId = wxOrderBean.getMchId();///
                    req.prepayId = wxOrderBean.getPrepayid();
                    req.packageValue = "Sign=WXPay";
                    req.nonceStr = wxOrderBean.getNoncestr();
                    req.timeStamp = wxOrderBean.getTimestamp();

                    req.sign = buildWeixinSign(req, wxOrderBean.getMchKey());

                    iwxapi.sendReq(req);
                }else{
                    Toast.makeText(BuyIyubiActivity.this,"未安装微信，请安装微信支付",
                            Toast.LENGTH_SHORT).show();
                }


            }
        }); //延迟时间

    }

    private String buildWeixinSign(PayReq payReq, String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(buildWeixinStringA(payReq));
        sb.append("&key=").append(key);

        return MD5.md5(sb.toString()).toUpperCase();
    }

    private String buildWeixinStringA(PayReq payReq) {
        StringBuilder sb = new StringBuilder();
        sb.append("appid=").append(payReq.appId);
        sb.append("&noncestr=").append(payReq.nonceStr);
        sb.append("&package=").append(payReq.packageValue);
        sb.append("&partnerid=").append(payReq.partnerId);
        sb.append("&prepayid=").append(payReq.prepayId);
        sb.append("&timestamp=").append(payReq.timeStamp);
        return sb.toString();
    }

}