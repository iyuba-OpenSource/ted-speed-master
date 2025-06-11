package com.sdiyuba.tedenglish.activity.myFragmentDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityPersonalInformationBinding;
import com.sdiyuba.tedenglish.databinding.ActivitySeetingBinding;
import com.sdiyuba.tedenglish.util.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 个人信息
 */
public class PersonalInformationActivity extends AppCompatActivity {

    private ActivityPersonalInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //数据展示


        Glide.with(this).load(Constant.userimgUrl).into(binding.showImage);

        binding.showUsername.setText(Constant.username);

        if (Constant.phone.equals("0")||Constant.phone.equals("")){

            binding.phoneLine.setVisibility(View.GONE);

        }else {
            String phone = Constant.phone.substring(0, 2) + "*******" + Constant.phone.substring(9);

            binding.showPhone.setText(phone);

        }


        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");
        binding.showMoney.setText( df.format(Constant.money)+" 元");  //格式化显示

        binding.showIyubi.setText(Constant.aiyubi+" 个");
        binding.showJifen.setText(Constant.jifen+" 分");


        //忘记密码
        binding.mimaLine.setOnClickListener(new View.OnClickListener() {
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


        //钱包
        binding.showMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if (Constant.username != null) {
                    intent = new Intent(PersonalInformationActivity.this, MyMoneyDetailActivity.class);
                } else {

                    intent = new Intent(PersonalInformationActivity.this, LoginActivity.class);


                }
                startActivity(intent);

            }
        });




        //积分

        binding.jifenLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(Constant.uid) != 0) {
                    String sign0 = MD5.md5("iyuba" + Constant.uid + "camstory");
                    String username;
                    try {
                        username = URLEncoder.encode(Constant.username, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    String url = "http://m.iyuba.cn/mall/index.jsp?"
                            + "&uid=" + Constant.uid
                            + "&sign=" + sign0
                            + "&username=" + username
                            + "&platform=android&appid="
                            + Constant.AppId;
                    IntegralShopActivity.startActivity(PersonalInformationActivity.this, url, "积分商城");

                } else {

                    startActivity(new Intent(PersonalInformationActivity.this, LoginActivity.class));
//                    if (Constant.mobsign) {
//
//                        instanttestSign();
//                    } else {
//                        Intent intent = new Intent(getActivity(), SignActivity.class);
//                        startActivity(intent);
//                    }
                }
            }
        });


    }

}