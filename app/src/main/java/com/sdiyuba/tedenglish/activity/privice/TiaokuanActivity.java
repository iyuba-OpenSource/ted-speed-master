package com.sdiyuba.tedenglish.activity.privice;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityTiaokuanBinding;


public class TiaokuanActivity extends AppCompatActivity {

    private ActivityTiaokuanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTiaokuanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        WebView web_v = findViewById(R.id.web_t);

        web_v.loadUrl(Constant.tiaokuan);


        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
