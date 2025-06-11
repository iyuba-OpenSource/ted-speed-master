package com.sdiyuba.tedenglish.activity.myFragmentDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.bumptech.glide.Glide;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.MyApplication;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityVippersonBinding;
import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.model.bean.VipBean;
import com.sdiyuba.tedenglish.model.bean.VipParseBean;
import com.sdiyuba.tedenglish.model.bean.WxOrderBean;
import com.sdiyuba.tedenglish.presenter.home.UidLoginPresenter;
import com.sdiyuba.tedenglish.presenter.home.WxOrderPresenter;
import com.sdiyuba.tedenglish.presenter.home.vipBuyPresenter;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.util.WheelView;
import com.sdiyuba.tedenglish.view.home.WxOrderContract;
import com.sdiyuba.tedenglish.view.home.uidLoginContract;
import com.sdiyuba.tedenglish.view.home.vipBuyContract;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyActivity extends AppCompatActivity implements vipBuyContract.vipBuyView, uidLoginContract.uidLoginView, WxOrderContract.WxOrderView {


    private TextView vipDate;
    private TextView money;

    private ImageView vipmyphoto;

    private IWXAPI iwxapi;

    private String cate;
    private String price;
    private int amount;
    private String productId;

    int FirstSum = 1;
    private ActivityVippersonBinding binding;

    private vipBuyPresenter vipBuypresenter;

    private static final int SDK_PAY_FLAG = 1;

    private UidLoginPresenter uidLoginpresenter;

    private PopupWindow popupWindow;

    private LinearLayout linearLayout_pay, wx_linear, zfb_linear;

    private String code = null;

    private ImageView wx_imageview, zfb_imageview;
    private String WIDtatal_fee = null;

    private final String wxKey = Constant.wxKey;

    private int product_id = -1;

    private boolean isCheck = false; //是否选中红包抵扣

    private String WIDbody = null;

    private String WIDsubject = null;

    public static final int PAY_FOR_ZFB = 2;

    public static final int NET_ERROR = -1;
    public static final int REQUEST_WX_INFO_FINISH = 3;

    private WxOrderPresenter wxOrderPresenter;

    private String chooseMoney = "1";//抵扣的金额

    private int Endmoney;

    List<String> list; //钱包的list

    private int maxCharge;//最多抵扣的金额

    @Override
    public void getWxOrder(WxOrderBean wxOrderBean) {


        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                //判断有没有安装微信，没有就做相应提示
                if (iwxapi.isWXAppInstalled()) {
                    PayReq req = new PayReq();
                    req.appId = wxOrderBean.getAppid();
                    req.partnerId = wxOrderBean.getMchId();///
                    req.prepayId = wxOrderBean.getPrepayid();
                    req.packageValue = "Sign=WXPay";
                    req.nonceStr = wxOrderBean.getNoncestr();
                    req.timeStamp = wxOrderBean.getTimestamp();

                    req.sign = buildWeixinSign(req, wxOrderBean.getMchKey());

                    iwxapi.sendReq(req);
                } else {
                    Toast.makeText(BuyActivity.this, "未安装微信，请安装微信支付",
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVippersonBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        iwxapi = WXAPIFactory.createWXAPI(this, wxKey, true);
        iwxapi.registerApp(wxKey);

        vipBuypresenter = new vipBuyPresenter();
        vipBuypresenter.attchView(this);

        wxOrderPresenter = new WxOrderPresenter();
        wxOrderPresenter.attchView(this);


        uidLoginpresenter = new UidLoginPresenter();
        uidLoginpresenter.attchView(this);


        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                if (Constant.abc==3){
                    binding.vipHuang.callOnClick();
                    Constant.abc=0;
                }else {
                    binding.vipBen.callOnClick();
                }

            }
        }); //延迟时间


        binding.tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.isPayTrue = false;
                finish();
            }
        });


        // 初始化控件


        vipDate = findViewById(R.id.vipDate);
        vipmyphoto = findViewById(R.id.vipmyPhoto);
        money = findViewById(R.id.vipQb);


        if (Constant.vipStatus ==0) {

            vipDate.setText(Constant.username);
            Glide.with(this).load(Constant.userimgUrl).into(vipmyphoto); //头像
            binding.vipmyPhoto.setVisibility(View.VISIBLE);

        } else {

            vipDate.setText("到期时间: " + Constant.vipDate);
            Glide.with(this).load(Constant.userimgUrl).into(vipmyphoto); //头像
            binding.vipmyPhoto.setVisibility(View.VISIBLE);
        }

        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        money.setText(df.format(Constant.money) + " 元");
        //会员协议
        binding.vipRuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://iuserspeech.iyuba.cn:9001/api/vipServiceProtocol.jsp?company=3&type=app");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        //钱包记录
        binding.myMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BuyActivity.this, MyMoneyDetailActivity.class));
            }
        });

        //钱包抵扣
        binding.deduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCheck) {
                    DissChoose();

                } else {
                    java.text.DecimalFormat df = new java.text.DecimalFormat("0");
//                    Endmoney = Integer.parseInt((df.format(Constant.money)));
                    Endmoney = (int) Math.floor(Constant.money);
                    maxCharge = (int) Math.floor(Integer.parseInt(WIDtatal_fee) / 2);


                    list = new ArrayList<>();

                    if (Endmoney < 1) {

                        DissChoose();
                        Toast.makeText(BuyActivity.this, "您当前钱包金额为" + Endmoney + "元,不足以进行抵扣。",
                                Toast.LENGTH_SHORT).show();
                        chooseMoney = "0";
                    } else if (Endmoney > maxCharge) {
                        for (int i = 1; i <= maxCharge; i++) {
                            list.add(String.valueOf(i));
                        }
                        showPopwindow();
                    } else {
                        for (int i = 1; i <= Endmoney; i++) {
                            list.add(String.valueOf(i));
                        }
                        showPopwindow();
                    }


                    isCheck = true;
                }


            }
        });


        //支付方式
        binding.chooseWxLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkWxImage.setImageResource(R.drawable.check_true);
                binding.checkZfbImage.setImageResource(R.drawable.check_false);
                Constant.isWxPay = true;
            }
        });

        binding.chooseZfbLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkWxImage.setImageResource(R.drawable.check_false);
                binding.checkZfbImage.setImageResource(R.drawable.check_true);
                Constant.isWxPay = false;
            }
        });

        binding.vipBen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.vipBen.setBackgroundResource(R.color.white);  //给 linlayout设置背景图片
                binding.vipQuan.setBackgroundResource(R.color.vipTopColor);
                binding.vipHuang.setBackgroundResource(R.color.vipTopColor);

                binding.benBottomline.setVisibility(View.VISIBLE);
                binding.quanBottomline.setVisibility(View.INVISIBLE);
                binding.huangBottomline.setVisibility(View.INVISIBLE);

                binding.benvipmonth1.setVisibility(View.VISIBLE);
                binding.quanvipmonth1.setVisibility(View.INVISIBLE);
                binding.huangvipmonth1.setVisibility(View.INVISIBLE);

                binding.benvipshuoing.setVisibility(View.VISIBLE);
                binding.quanvipshuoing.setVisibility(View.INVISIBLE);
                binding.huangvipshuoing.setVisibility(View.INVISIBLE);
                binding.planB1.callOnClick();
                DissChoose();
            }
        });
        binding.vipQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vipBen.setBackgroundResource(R.color.vipTopColor);  //给 linlayout设置背景图片
                binding.vipQuan.setBackgroundResource(R.color.white);
                binding.vipHuang.setBackgroundResource(R.color.vipTopColor);
                binding.benBottomline.setVisibility(View.INVISIBLE);
                binding.quanBottomline.setVisibility(View.VISIBLE);
                binding.huangBottomline.setVisibility(View.INVISIBLE);

                binding.benvipmonth1.setVisibility(View.INVISIBLE);
                binding.quanvipmonth1.setVisibility(View.VISIBLE);
                binding.huangvipmonth1.setVisibility(View.INVISIBLE);

                binding.benvipshuoing.setVisibility(View.INVISIBLE);
                binding.quanvipshuoing.setVisibility(View.VISIBLE);
                binding.huangvipshuoing.setVisibility(View.INVISIBLE);

                binding.planQ1.callOnClick();
                DissChoose();
            }
        });
        binding.vipHuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vipBen.setBackgroundResource(R.color.vipTopColor);  //给 linlayout设置背景图片
                binding.vipQuan.setBackgroundResource(R.color.vipTopColor);
                binding.vipHuang.setBackgroundResource(R.color.white);
                binding.benBottomline.setVisibility(View.INVISIBLE);
                binding.quanBottomline.setVisibility(View.INVISIBLE);
                binding.huangBottomline.setVisibility(View.VISIBLE);


                binding.benvipmonth1.setVisibility(View.INVISIBLE);
                binding.quanvipmonth1.setVisibility(View.INVISIBLE);
                binding.huangvipmonth1.setVisibility(View.VISIBLE);

                binding.benvipshuoing.setVisibility(View.INVISIBLE);
                binding.quanvipshuoing.setVisibility(View.INVISIBLE);
                binding.huangvipshuoing.setVisibility(View.VISIBLE);

                binding.planH1.callOnClick();
                DissChoose();
            }
        });




        binding.planB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planB1.setBackgroundResource(R.drawable.vipxuanzhong);  //给 linlayout设置背景图片
                binding.planB6.setBackgroundResource(R.drawable.baiban);
                binding.planB12.setBackgroundResource(R.drawable.baiban);
                binding.planB36.setBackgroundResource(R.drawable.baiban);

                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 25 + "";
                amount = 1;
                product_id = 10;
                WIDsubject = "本应用会员会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;

                DissChoose();
            }
        });
        binding.planB6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planB1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planB6.setBackgroundResource(R.drawable.vipxuanzhong);
                binding.planB12.setBackgroundResource(R.drawable.baiban);
                binding.planB36.setBackgroundResource(R.drawable.baiban);

                WIDtatal_fee = 69 + "";
                amount = 6;
                product_id = 10;
                WIDsubject = "本应用会员会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;

                DissChoose();
            }
        });
        binding.planB12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planB1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planB6.setBackgroundResource(R.drawable.baiban);
                binding.planB12.setBackgroundResource(R.drawable.vipxuanzhong);
                binding.planB36.setBackgroundResource(R.drawable.baiban);

                WIDtatal_fee = 99 + "";
                amount = 12;
                product_id = 10;
                WIDsubject = "本应用会员会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;

                DissChoose();
            }
        });
        binding.planB36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planB1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planB6.setBackgroundResource(R.drawable.baiban);
                binding.planB12.setBackgroundResource(R.drawable.baiban);
                binding.planB36.setBackgroundResource(R.drawable.vipxuanzhong);

                WIDtatal_fee = 199 + "";
                amount = 36;
                product_id = 10;
                WIDsubject = "本应用会员会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();
            }
        });

        binding.planQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planQ1.setBackgroundResource(R.drawable.vipxuanzhong);  //给 linlayout设置背景图片
                binding.planQ6.setBackgroundResource(R.drawable.baiban);
                binding.planQ12.setBackgroundResource(R.drawable.baiban);
                binding.planQ36.setBackgroundResource(R.drawable.baiban);


                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 50 + "";
                amount = 1;
                product_id = 0;
                WIDsubject = "全站会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();

            }
        });
        binding.planQ6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planQ1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planQ6.setBackgroundResource(R.drawable.vipxuanzhong);
                binding.planQ12.setBackgroundResource(R.drawable.baiban);
                binding.planQ36.setBackgroundResource(R.drawable.baiban);


                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 198 + "";
                amount = 6;
                product_id = 0;
                WIDsubject = "全站会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();

            }
        });
        binding.planQ12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planQ1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planQ6.setBackgroundResource(R.drawable.baiban);
                binding.planQ12.setBackgroundResource(R.drawable.vipxuanzhong);
                binding.planQ36.setBackgroundResource(R.drawable.baiban);


                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 298 + "";
                amount = 12;
                product_id = 0;
                WIDsubject = "全站会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();
            }
        });
        binding.planQ36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planQ1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planQ6.setBackgroundResource(R.drawable.baiban);
                binding.planQ12.setBackgroundResource(R.drawable.baiban);
                binding.planQ36.setBackgroundResource(R.drawable.vipxuanzhong);


                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 588 + "";
                amount = 36;
                product_id = 0;
                WIDsubject = "全站会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();
            }
        });

        binding.planH1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planH1.setBackgroundResource(R.drawable.vipxuanzhong);  //给 linlayout设置背景图片
                binding.planH3.setBackgroundResource(R.drawable.baiban);
                binding.planH6.setBackgroundResource(R.drawable.baiban);
                binding.planH12.setBackgroundResource(R.drawable.baiban);


                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 98 + "";
                amount = 1;
                product_id = 2;
                WIDsubject = "黄金会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();

            }
        });


        binding.planH3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planH1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planH3.setBackgroundResource(R.drawable.vipxuanzhong);
                binding.planH6.setBackgroundResource(R.drawable.baiban);
                binding.planH12.setBackgroundResource(R.drawable.baiban);


                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 288 + "";
                amount = 3;
                product_id = 2;
                WIDsubject = "黄金会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();

            }
        });


        binding.planH6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planH1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planH3.setBackgroundResource(R.drawable.baiban);
                binding.planH6.setBackgroundResource(R.drawable.vipxuanzhong);
                binding.planH12.setBackgroundResource(R.drawable.baiban);


                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 518 + "";
                amount = 6;
                product_id = 2;
                WIDsubject = "黄金会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();

            }
        });


        binding.planH12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.planH1.setBackgroundResource(R.drawable.baiban);  //给 linlayout设置背景图片
                binding.planH3.setBackgroundResource(R.drawable.baiban);
                binding.planH6.setBackgroundResource(R.drawable.baiban);
                binding.planH12.setBackgroundResource(R.drawable.vipxuanzhong);


                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.uid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 988 + "";
                amount = 12;
                product_id = 2;
                WIDsubject = "黄金会员";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                DissChoose();

            }
        });






        // 监听购买按钮的点击事件
        binding.vipbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                if (binding.vipCbObey.isChecked()) {

                    //Constant.isWxPay   有wx支付后再替换
                    if (false) {
                        //wx支付


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");// HH:mm:ss
                        //获取当前日期
                        Date date = new Date(System.currentTimeMillis());
                        simpleDateFormat.format(date);


                        String sign = MD5.md5(Constant.AppId + Constant.uid + WIDtatal_fee + amount + simpleDateFormat.format(date));

                        if (isCheck) {

                            wxOrderPresenter.getWxOrder(wxKey, wxKey, Constant.AppId, Constant.uid, WIDtatal_fee, amount, product_id, "json", sign, Integer.parseInt(chooseMoney) * 100);

                        } else {
                            wxOrderPresenter.getWxOrder(wxKey, wxKey, Constant.AppId, Constant.uid, WIDtatal_fee, amount, product_id, "json", sign, 0);

                        }
                    } else {
                        //支付宝
                        if (isCheck) {
                            vipBuypresenter.getVip(Constant.AppId, Integer.parseInt(Constant.uid), code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject, Integer.parseInt(chooseMoney) * 100);

                        } else {
                            vipBuypresenter.getVip(Constant.AppId, Integer.parseInt(Constant.uid), code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject, 0);

                        }


                    }
                } else {

                    Toast.makeText(MyApplication.getContext(), "请先阅读并同意会员服务协议", Toast.LENGTH_SHORT).show();


                }


                // 进行支付处理
                // TODO
            }
        });


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
    public void getVip(VipParseBean vipParseBean) {

        if (vipParseBean.getResult().equals("200")) {

            Runnable payRunnable = () -> {
                PayTask alipay = new PayTask(BuyActivity.this);
                Map<String, String> result = alipay.payV2(vipParseBean.getAlipayTradeStr(), true);
                Log.d("wang00321", result.get("resultStatus"));
                if (result.get("resultStatus").equals("9000")) {
                    vipBuypresenter.callbackVip(result.toString());
                } else {
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


//        //微信支付
//        if(vipParseBean.getResult().equals("200")){
//
//            IWXAPI msgApi;
//            //初始化操作
//            msgApi = WXAPIFactory.createWXAPI(this, null);
//            msgApi.registerApp(WeiXinConstants.APP_ID);
//            // code md5时间          WIDtatal_fee价格  amount 购买多久     product_id  区分会员级别得代码  WIDbody 区分会员级别文字   WIDbody账单名字
////           (291, Integer.parseInt(Constant.uid), code, WIDtatal_fee, amount, product_id, WIDbody, WIDbody);
//            //调起支付
//            PayReq request = new PayReq();
//            request.appId = String.valueOf(291);
//            request.partnerId = "";
//            request.prepayId = "";
//            request.packageValue = "Sign=WXPay";
//            request.nonceStr = "";
//            request.timeStamp = "";
//            request.sign = "";
//
//            msgApi.sendReq(request);
//
//
//
//        }


    }

    @Override
    public void callbackVip(VipBean vipBean) {
        //实现用户uid登录
        Log.d("wang00321", vipBean.getMsg());
        if (vipBean.getMsg().equals("Success")) {


        }


    }

    @Override
    public void uidLogin(UidBean uidBean) {


        Constant.vipStatus = Integer.parseInt((uidBean.getVipStatus()));

//        SharedPreferences vipStatusInfo = getSharedPreferences("vipStatus", MODE_PRIVATE);
//        SharedPreferences.Editor editor = vipStatusInfo.edit();

        Constant.money = (uidBean.getMoney() * 0.01);//钱包


        Constant.aiyubi = uidBean.getAmount();//爱语币

        Constant.jifen = Integer.parseInt(uidBean.getCredits());//积分

        Constant.phone = uidBean.getMobile();//手机号

        //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
        //要转成后的时间的格式
        android.icu.text.SimpleDateFormat simpleDateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
        }
        // 时间戳转换成时间
        String vipDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            vipDate = simpleDateFormat.format(new Date(timeStamp));
        }

        Constant.vipDate = vipDate;//vip时间

//        User user = new User();
//        user.vipStatus = String.valueOf(Constant.vipStatus);
//        if (!Constant.vipStatus .equals("0")) {
//            user.vipExpireTime = uidBean.getExpireTime();
//        }
//        user.uid = Integer.parseInt(Constant.uid);
//        user.credit = Integer.parseInt(uidBean.getCredits());
//        user.name = Constant.username;
//        user.imgUrl = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.uid + "&size=big";
//        user.email = uidBean.getEmail();
//        user.mobile = Constant.mobile;
//        user.iyubiAmount = (int) Constant.aiyubi;
//        IyuUserManager.getInstance().setCurrentUser(user);
////

        DissChoose();
        Resfresh();


    }

    private void Resfresh() {

        if (Constant.vipStatus ==0 ) {

            vipDate.setText(Constant.username);
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            money.setText(df.format(Constant.money) + " 元");

        } else {


            vipDate.setText("到期时间: " + Constant.vipDate);
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            money.setText(df.format(Constant.money) + " 元");


        }
    }


    @Override
    protected void onDestroy() {
        //放super上面防止出现空异常
        Constant.isPayTrue = false;
        Constant.isWxPay = true;
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();


        if (FirstSum != 1) {
            String sign = MD5.md5("20001" + Constant.uid + "iyubaV2");
            uidLoginpresenter.uidLogin("android", "json", "20001", Constant.uid, Constant.uid, Constant.AppId, sign);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 在这里执行需要延迟后执行的操作，比如刷新
                    // 比如调用 refresh() 方法
                    String sign = MD5.md5("20001" + Constant.uid + "iyubaV2");
                    uidLoginpresenter.uidLogin("android", "json", "20001", Constant.uid, Constant.uid, Constant.AppId, sign);

                }
            }, 5000); // 3000毫秒即3秒

        } else {
            FirstSum = FirstSum + 1;
        }


        //wx支付


    }

    private void showPopwindow() {


        View popupView = getLayoutInflater().inflate(R.layout.pop_deduction, null);

        WheelView wv = popupView.findViewById(R.id.wv);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView up = popupView.findViewById(R.id.upWindow);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView no = popupView.findViewById(R.id.noChoose);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView yes = popupView.findViewById(R.id.yesChoose);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tip = popupView.findViewById(R.id.vip_Choosetip);

        //展示出来
        wv.setItems(list);
        wv.setSeletion(0);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {

                chooseMoney = String.valueOf(selectedIndex);
            }
        });
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
//        popupWindow.showAsDropDown(popupView, popupView.getWidth(), popWindowHeight); //相对某个控件，有偏移

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);   //解决显示不全的问题
        //透明
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        this.getWindow().setAttributes(lp);

        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.ipopwindow_anim_style);

        //tip
        tip.setText("本次最多可抵扣 :" + list.size() + "元");

        //取消
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindowDismiss();
                DissChoose();
            }
        });
        //确认
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindowDismiss();
                isCheck = true;
                binding.deductionText.setVisibility(View.VISIBLE);

                binding.deductionText.setText(chooseMoney + "元");
            }
        });

    }

    public void popupwindowDismiss() {
        popupWindow.dismiss();
        WindowManager.LayoutParams sp = getWindow().getAttributes();
        sp.alpha = 1.0f;
        getWindow().setAttributes(sp);
    }

    public void DissChoose() {
        binding.deduction.setChecked(false);
        isCheck = false;
        binding.deductionText.setVisibility(View.GONE);

        chooseMoney = "1";
    }

}