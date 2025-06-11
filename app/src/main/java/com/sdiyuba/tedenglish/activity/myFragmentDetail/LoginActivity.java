package com.sdiyuba.tedenglish.activity.myFragmentDetail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.model.bean.LoginBean;
import com.sdiyuba.tedenglish.model.bean.LogoutBean;
import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.util.ToastUtil;
import com.sdiyuba.tedenglish.view.home.LoginContract;
import com.sdiyuba.tedenglish.view.home.uidLoginContract;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityLoginBinding;
import com.sdiyuba.rewardgold.fragment.MoneyFragment;

import com.sdiyuba.tedenglish.presenter.home.LoginPresenter;
import com.sdiyuba.tedenglish.presenter.home.UidLoginPresenter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView, uidLoginContract.uidLoginView {

    private ActivityLoginBinding binding;

    private EditText edtUsername;
    private EditText edtPassword;
    private TextView wangjimima;
    private TextView zhuce;

    MoneyFragment makeMoneyFragment ;
    private boolean XSmima = true;

    private LoginPresenter loginPresenter;
    private UidLoginPresenter uidLoginPresenter;

    private CheckBox cb_obey;
    private TextView tv_rule;
    private TextView tv_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        wangjimima = findViewById(R.id.wangjimima);
        zhuce = findViewById(R.id.quzhuce);


        loginPresenter = new LoginPresenter();
        loginPresenter.attchView(this);

        uidLoginPresenter = new UidLoginPresenter();
        uidLoginPresenter.attchView(this);

        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cb_obey = findViewById(R.id.cb_obeys); //条款和协议
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

        //  注册
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, EnrollActivity.class);
                startActivity(intent);
            }
        });

        binding.tbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.wangjimima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现跳转网页的主要代码
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://m.iyuba.cn/m_login/inputPhonefp.jsp");
                intent.setData(content_url);
                startActivity(intent);
            }
        });


        //显示隐藏密码
        binding.XSmima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (XSmima) {
                    // 用户想要显示密码
                    binding.etPsd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.XSmima.setImageResource(R.drawable.yincang);
                    XSmima = false;
                } else {
                    // 用户想要隐藏密码
                    binding.etPsd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    XSmima = true;
                    binding.XSmima.setImageResource(R.drawable.xianshi);
                }
                CharSequence charSequence = binding.etPsd.getText();
                if (charSequence != null) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }

        });


        //得到用户输入得数据,登录信息得加密,加密完从下面进行判断
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_obey.isChecked()) {

                    String username = null;
                    try {
                        username = URLEncoder.encode(binding.etName.getText().toString().trim(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    String password = binding.etPsd.getText().toString().trim();

                    String pwd = MD5.md5(password);
                    String sign = MD5.md5("11004" + binding.etName.getText().toString().trim() + pwd + "iyubaV2");
                    loginPresenter.getLogin(11004, username, pwd, sign, "json");

                }else {

                    ToastUtil.destroyMesInstance();
                    ToastUtil.showToast(LoginActivity.this, "请阅读并同意使用条款和隐私协议!");

                }




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
    public void getLogin(LoginBean loginBean) {


        //从这里判断
        if ((loginBean.getResult()).equals("101")) {
            Toast.makeText(LoginActivity.this, "登陆成功!", Toast.LENGTH_LONG).show();
            Constant.uid = (String.valueOf(loginBean.getUid()));
            Constant.username = loginBean.getUsername();
//            Constant.imgUrl = signBean.getImgSrc();
            Constant.userimgUrl = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.uid + "&size=big";
            SharedPreferences urlInfo = getSharedPreferences("imageUrl", MODE_PRIVATE);
            SharedPreferences userInfo = getSharedPreferences("useruid", MODE_PRIVATE);

            SharedPreferences.Editor editor = userInfo.edit();
            SharedPreferences.Editor urleditor = urlInfo.edit();// 获取Editor

            editor.putString("useruid", Constant.uid); // 写入int型数据
            urleditor.putString("imageUrl", Constant.userimgUrl);

            editor.commit(); // 提交修改并保存
            urleditor.commit();

            //获取得到得信息


            String sign = MD5.md5("20001" + Constant.uid + "iyubaV2");
            uidLoginPresenter.uidLogin("android", "json", "20001", Constant.uid, Constant.uid, Constant.AppId, sign);



        } else {
            Toast.makeText(LoginActivity.this, "登陆失败,用户名或密码错误.", Toast.LENGTH_LONG).show();
            // 清空用户名和密码文本框
//            edtUsername.setText("");
            edtPassword.setText("");
            //让用户名文本框获取焦点
            edtUsername.requestFocus();
        }
    }

    @Override
    public void getLogout(LogoutBean logoutbean) {

    }


    @Override
    public void uidLogin(UidBean uidBean) {


        Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());

        Constant.money = uidBean.getMoney() * 0.01;//钱包

        Constant.mobile = uidBean.getMobile();

        Constant.username = uidBean.getUsername();

        Constant.aiyubi = uidBean.getAmount();//爱语币

        Constant.jifen = Integer.parseInt(uidBean.getCredits());//积分

        Constant.phone = uidBean.getMobile();//手机号
        long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
        //要转成后的时间的格式
        android.icu.text.SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
        // 时间戳转换成时间
        String vipDate = null;
        vipDate = simpleDateFormat.format(new Date(timeStamp));

        Constant.vipDate = vipDate;//vip时间


        //活动集成部分
        SharedPreferences prefs = getSharedPreferences("PageVisitPrefs", MODE_PRIVATE);

        long s_time = prefs.getLong("totalTimeToday", 0);
        makeMoneyFragment = MoneyFragment.newInstance(null, null);
        makeMoneyFragment.init(Constant.uid, String.valueOf(Constant.AppId),s_time);


//        User user = new User();
//        user.vipStatus = String.valueOf(Constant.vipStatus);
//        if (Constant.vipStatus != 0) {
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


        finish();
    }


}