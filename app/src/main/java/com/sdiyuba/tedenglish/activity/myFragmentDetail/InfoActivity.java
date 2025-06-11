package com.sdiyuba.tedenglish.activity.myFragmentDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.MyApplication;
import com.sdiyuba.tedenglish.databinding.ActivityInfoBinding;
import com.sdiyuba.tedenglish.util.APKVersionCodeUtils;


public class InfoActivity extends AppCompatActivity {

    private ActivityInfoBinding binding;

    public static final String VERSION_CODE = APKVersionCodeUtils.getVerName(MyApplication.getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //activity中获取页面
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        binding.jianjie.setText("简介: 学英语，交朋友。标准英音，单句跟读，听说结合，是您提高英语听说能力的好帮手。"+ Constant.CompanyName+"为您提供精品外语学习应用。");

        binding.gsname.setText(Constant.CompanyName);

        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        binding.version.setText("version : "+VERSION_CODE);

        binding.beian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现跳转网页的主要代码
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://beian.miit.gov.cn/#/Integrated/index");
                intent.setData(content_url);
                startActivity(intent);
            }
        });


    }
}