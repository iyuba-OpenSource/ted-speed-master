package com.sdiyuba.tedenglish.activity.privice;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityYinsiBinding;


public class YinsiActivity extends AppCompatActivity {

    private ActivityYinsiBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityYinsiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        WebView web_v = findViewById(R.id.web_v);

        web_v.loadUrl(Constant.yinsi);


//        //设置自动触发  主线程
//        Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                binding.webV.callOnClick();//在这里触发你的button并执行onClick函数
//            }
//        }, 500);//延迟时间自己设定
//
//
//        //facebook点击事件
//        binding.webV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //通过WebView控件实现跳转网页的主要代码
//                WebView web_v = findViewById(R.id.web_v);
//
//                web_v.loadUrl(Constant.yinsi);
//            }
//        });


        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setToolbar\_title("Community");
//        //facebook点击事件
//        mBaseBinding.facebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //通过WebView控件实现跳转网页的主要代码
//                WebView web\_v = findViewById(R.id.web\_v);
//
//                web\_v.loadUrl("www.facebook.com/");
//            }
//        });
//    }

}