package com.sdiyuba.tedenglish.activity.Seeting;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityTiaokuanBinding;


public class TripartitelistActivity extends AppCompatActivity {

    private ActivityTiaokuanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTiaokuanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.title.setText("第三方信息共享清单");



        //设置自动触发  主线程
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.webT.callOnClick();//在这里触发你的button并执行onClick函数
            }
        }, 500);//延迟时间自己设定


        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //facebook点击事件
        binding.webT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过WebView控件实现跳转网页的主要代码
                WebView web_v = findViewById(R.id.web_t);

                web_v.loadUrl("http://www.qomolama.com.cn/thirdSDKInfosharing.jsp?apptype="+ Constant.NAME);
            }
        });
    }

}
