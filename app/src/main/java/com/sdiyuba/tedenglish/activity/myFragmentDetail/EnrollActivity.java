package com.sdiyuba.tedenglish.activity.myFragmentDetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.MainActivity;
import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.model.bean.enrollBean;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.util.TimeCount;
import com.sdiyuba.tedenglish.util.ToastUtil;
import com.sdiyuba.tedenglish.view.home.enrollContract;
import com.sdiyuba.tedenglish.view.home.uidLoginContract;
import com.alibaba.fastjson.JSONObject;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ZhuceBinding;

import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.presenter.home.UidLoginPresenter;
import com.sdiyuba.tedenglish.presenter.home.enrollPresenter;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.wrapper.TokenVerifyResult;


/*
用户注册
 */
public class EnrollActivity extends AppCompatActivity implements enrollContract.enrollView, uidLoginContract.uidLoginView {


    private ZhuceBinding binding;

    private enrollPresenter enrollpresenter;


    private UidLoginPresenter uidLoginpresenter;
    private EditText userphone;
    private EditText username;
    private EditText userPas;
    private EditText userPasOk;
    private EditText usercode;
    private TextView getcode;

    private boolean isOK = false; //无重复,可以注册

    private Button register;
    private String useruid;
    private ProgressDialog dialog;
    private Context context = this;
    private String phoneNumber;
    private TimeCount timeCount;//倒计时

    private CheckBox cb_obey;

    private TextView tv_rule;
    private TextView tv_privacy;

    // 短信验证的回调监听
    private EventHandler ev = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            System.out.println("==========SMSSDK回调" + event + "===" + result + "===" + data);
            // TODO 此处为子线程！不可直接处理UI线程！处理后续操作需传到主线程中操作！
            if (result == SMSSDK.RESULT_COMPLETE) {
                //成功回调
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交短信、语音验证码成功
                    dialog.dismiss();
                    System.out.println("==========验证成功");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            binding.userCodeVis.setVisibility(View.GONE);
                            binding.phoneVis.setVisibility(View.GONE);

                            binding.nameVis.setVisibility(View.VISIBLE);
                            binding.password00Vis.setVisibility(View.VISIBLE);
                            binding.password11Vis.setVisibility(View.VISIBLE);
                            binding.signRegister.setVisibility(View.VISIBLE);
                            binding.register.setVisibility(View.GONE);
                            binding.zhuceAgree.setVisibility(View.GONE);


                            ToastUtil.destroyMesInstance();
                            ToastUtil.showToast(context, "验证成功，请填写您的用户名和密码");
                        }
                    });
//                    Intent intent = new Intent(context, RegisterDetailActivity.class);
//                    intent.putExtra("phoneNumber", phoneNumber);
//                    startActivity(intent);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取短信验证码成功
                    System.out.println("==========获取短信验证码成功");
                    dialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.destroyMesInstance();
                            ToastUtil.showToast(context, "验证码已发送，请查收");
//                            Toast.makeText(EnrollActivity.this, "验证码已发送，请查收", Toast.LENGTH_LONG).show();

                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                    //获取语音验证码成功
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                } else if (event == SMSSDK.EVENT_GET_VERIFY_TOKEN_CODE) {
                    //本机验证获取token成功
                    TokenVerifyResult tokenVerifyResult = (TokenVerifyResult) data;
                    //SMSSDK.login(phoneNum,tokenVerifyResult);
                } else if (event == SMSSDK.EVENT_VERIFY_LOGIN) {
                    //本机验证登陆成功
                }
            } else {
                //其他失败回调
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ToastUtil.destroyMesInstance();
                        ToastUtil.showToast(context, "验证码错误,请重新输入!");
                        System.out.println("==========SMSSDK其他失败回调" + event + "===" + result + "===" + data);
//                ((Throwable) data).printStackTrace();
                    }
                });

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ZhuceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        SMSSDK.registerEventHandler(ev);//短信

        NetWorkManager.getInstance().init();
        enrollpresenter = new enrollPresenter();
        enrollpresenter.attchView(this);

        uidLoginpresenter=new UidLoginPresenter();
        uidLoginpresenter.attchView(this);

        binding.nameVis.setVisibility(View.GONE);
        binding.password00Vis.setVisibility(View.GONE);
        binding.password11Vis.setVisibility(View.GONE);


        userphone = findViewById(R.id.user_phone);
        username = findViewById(R.id.username);
        userPas = findViewById(R.id.user_pas);
        userPasOk = findViewById(R.id.user_pas_ok);
        usercode = findViewById(R.id.user_code);
        getcode = findViewById(R.id.get_code_btn);
        register = findViewById(R.id.register); //注册
        cb_obey = findViewById(R.id.cb_obey); //条款和协议
        tv_rule = findViewById(R.id.rule_tv2);
        tv_privacy = findViewById(R.id.rule_tv4);
        tv_rule.setText(Html.fromHtml("<u>" + "使用条款" + "</u>"));
        tv_privacy.setText(Html.fromHtml("<u>" + "隐私协议" + "</u>"));


        tv_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(Constant.tiaokuan);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
            }
        });
        tv_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri2 = Uri.parse(Constant.yinsi);
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.VIEW");
                intent2.setData(uri2);
                startActivity(intent2);
            }
        });
        binding.tbEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Drawable da_click = getResources().getDrawable(R.drawable.btn_view_shape);
        Drawable da_un_click = getResources().getDrawable(R.drawable.btn_view_shape_unclick);
        timeCount = new TimeCount(da_click, da_un_click, getcode, 60000, 1000);

//        SMSSDK.registerEventHandler(ev);


        //点击获取验证码
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_obey.isChecked()) {
                    phoneNumber = String.valueOf(userphone.getText());
                    if (phoneNumber.length() != 11) {
                        ToastUtil.destroyMesInstance();
                        Toast.makeText(EnrollActivity.this, "请输入正确的手机号码!", Toast.LENGTH_LONG).show();

                    } else {
                        try {
                            boolean isRegFlag = isRegister(phoneNumber);  //发送请求检测手机号是否注册
                            if (isRegFlag) {
                                ToastUtil.destroyMesInstance();
                                Toast.makeText(EnrollActivity.this, "手机号已注册，请换一个号试试~", Toast.LENGTH_LONG).show();
                            } else {

                                sendMessage(phoneNumber);//发短信
                                timeCount.start();

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    ToastUtil.destroyMesInstance();
                    Toast.makeText(EnrollActivity.this, "请阅读并同意使用条款和隐私协议!", Toast.LENGTH_LONG).show();

                }
            }
        });

        //注册
        binding.signRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_pas = userPas.getText().toString().trim();
                String user_pasok = userPasOk.getText().toString().trim();

                if (!user_pas.equals(user_pasok)) {
                    Toast.makeText(EnrollActivity.this, "两次输入的密码不正确!", Toast.LENGTH_LONG).show();

                }else {
                    String user_name = username.getText().toString().trim();

                    String user_phone = userphone.getText().toString().trim();

                    String code = usercode.getText().toString().trim(); //输入的验证码

                    String usernamecode;
                    try {
                        usernamecode = URLEncoder.encode(user_name, "UTF-8");
                    } catch (
                            UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    String user_pascode = MD5.md5(user_pas);

                    String sign = MD5.md5("11002" + user_name + user_pascode + "iyubaV2");


                    Log.d("fang789",usernamecode+"**"+user_pascode+"**"+user_phone+"**"+sign);
                    enrollpresenter.getEnroll(11002, usernamecode, user_pascode, user_phone, sign, "json");

                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = username.getText().toString().trim();

                String user_pas = userPas.getText().toString().trim();
                String user_pasok = userPasOk.getText().toString().trim();
                String user_phone = userphone.getText().toString().trim();

                String code = usercode.getText().toString().trim(); //输入的验证码

                String user_pascode = MD5.md5(user_pas);

                String sign = MD5.md5("11002" + user_name + user_pascode + "iyubaV2");


                if (cb_obey.isChecked()) {
                    if ("".equals(code) || "".equals(phoneNumber)) {
                        ToastUtil.destroyMesInstance();
                        ToastUtil.showToast(EnrollActivity.this, "错误,请检查输入的手机号和验证码是否正确!");
                    } else  {
                        //判断验证码是否正确
                        sendCheckCode(code);
                    }
                } else {
                    ToastUtil.destroyMesInstance();
                    ToastUtil.showToast(EnrollActivity.this, "请阅读并同意使用条款和隐私协议!");
                }


                //加上个验证码判断正确

//             if (!user_pas.equals(user_pasok)){
//                 Toast.makeText(EnrollActivity.this, "两次输入的密码不正确!", Toast.LENGTH_LONG).show();
//             } else if (!code.equals(codes)){
//                 Toast.makeText(EnrollActivity.this, "验证码错误!", Toast.LENGTH_LONG).show();
//             }else {
//                 enrollpresenter.getEnroll(11002,usernamecode,user_pascode,user_phone,sign,"json");
//             }


            }
        });


    }

    boolean isRegister(String phoneNum) throws InterruptedException {
        final boolean[] isRegFlag = {false};
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                StringBuilder sb = new StringBuilder("");
                sb.append("http://api.iyuba.com.cn/v2/api.iyuba?protocol=10009&username=")
                        .append(phoneNum)
                        .append("&format=json");
                try {
                    URL url = new URL(sb.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));

                    // 得到结果
                    StringBuilder response = new StringBuilder("");
                    String tempLine;
                    while ((tempLine = reader.readLine()) != null) {
                        response.append(tempLine);
                    }

                    // 结果转json
                    JSONObject jo = JSONObject.parseObject(response.toString());
                    int resultCode = jo.getInteger("result");
                    if (resultCode == 101) {
                        isRegFlag[0] = true;
                    } else {
                        isRegFlag[0] = false;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null)
                        connection.disconnect();
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t.start();
        t.join();

        return isRegFlag[0];
    }

    // 发送验证码
    void sendMessage(String phoneNum) throws InterruptedException {
        System.out.println("=========" + phoneNum);
        //发送短信，传入国家号和电话号码
        SMSSDK.getVerificationCode("86", phoneNum);

    }

    /**
     * 向服务器提交验证码，在监听回调中监听是否验证
     */
    private void sendCheckCode(String checkCode) {
        dialog = ProgressDialog.show(this, null, "正在验证...", false, true); //遮罩
        //提交短信验证码
        SMSSDK.submitVerificationCode("86", phoneNumber, checkCode);//国家号，手机号码，验证码
        System.out.println("=========验证信息(电话+验证码): " + phoneNumber + "===" + checkCode);
    }


    @Override
    public void getEnroll(enrollBean enrollbean) {




        if (enrollbean.getResult().equals("111")) {

            Toast.makeText(EnrollActivity.this, "注册成功!", Toast.LENGTH_LONG).show();

            useruid =enrollbean.getUid();
            Constant.userimgUrl =enrollbean.getImgSrc();

            String sign = MD5.md5("20001" + useruid + "iyubaV2");
            uidLoginpresenter.uidLogin("android", "json", "20001", useruid, useruid, Constant.AppId, sign);


        } else if (enrollbean.getResult().equals("114")) {
            Toast.makeText(EnrollActivity.this, "用户名太长或太短!", Toast.LENGTH_LONG).show();
        } else if (enrollbean.getResult().equals("112")) {
            Toast.makeText(EnrollActivity.this, "用户名已经存在!", Toast.LENGTH_LONG).show();
        } else if (enrollbean.getResult().equals("110")) {
            Toast.makeText(EnrollActivity.this, "注册失败!", Toast.LENGTH_LONG).show();
        } else if (enrollbean.getResult().equals("115")) {
            Toast.makeText(EnrollActivity.this, "此手机号已经注册过了!", Toast.LENGTH_LONG).show();
        }


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
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(ev);
        super.onDestroy();
    }

    @Override
    public void uidLogin(UidBean uidBean) {

        if (uidBean.getResult() == 201) {



            Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());

            Constant.money = (uidBean.getMoney() * 0.01);//钱包

            Constant.aiyubi = uidBean.getAmount();//爱语币

            Constant.jifen = Integer.parseInt(uidBean.getCredits());//积分

            Constant.phone = uidBean.getMobile();//手机号

            long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
            //要转成后的时间的格式
            SimpleDateFormat simpleDateFormat = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
            // 时间戳转换成时间
            String vipDate = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                vipDate = simpleDateFormat.format(new Date(timeStamp));
            }

            Constant.vipDate = vipDate;//vip时间

            Constant.uid = useruid;

            Constant.username = uidBean.getUsername();


            //存一下
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
//        PersonalHome.setSaveUserinfo(Integer.parseInt(uid), userName, vipStatus + "");
            editor.putBoolean("isLogin", true);
            editor.putString("nickname", Constant.username);
//        editor.putString("head", head);
            editor.putString("uid", Constant.uid);
            editor.putString("vip_time", Constant.vipDate);
            editor.putInt("iYuBi", Constant.aiyubi);
//        editor.putInt("jiFen", jiFen);
            editor.putInt("vipStatus",  Constant.vipStatus);
//        editor.putBoolean("isVip", System.currentTimeMillis() <= Long.parseLong(Constant.vipDate + "000"));
            editor.commit();

            if (Constant.username == null) {


            } else {


//                User user = new User();
//                user.vipStatus = String.valueOf(Constant.vipStatus);
//                if (Constant.vipStatus != 0) {
//                    user.vipExpireTime = uidBean.getExpireTime();
//                }
//                user.uid = Integer.parseInt(Constant.uid);
//                user.credit = Integer.parseInt(uidBean.getCredits());
//                user.name = Constant.username;
//                user.imgUrl = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.uid + "&size=big";
//                user.email = uidBean.getEmail();
//                user.mobile = Constant.mobile;
//                user.iyubiAmount = (int) Constant.aiyubi;
//                IyuUserManager.getInstance().setCurrentUser(user);
            }

//            finish();
//            Constant.EnrollOK = true;
            Intent intent =new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }
}