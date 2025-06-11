package com.sdiyuba.tedenglish.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sdiyuba.tedenglish.databinding.ActivityLearningReportBinding;
import com.sdiyuba.tedenglish.databinding.ActivityYanyuBinding;
import com.sdiyuba.tedenglish.model.bean.ProverbBean;
import com.sdiyuba.tedenglish.presenter.home.ProverbPresenter;
import com.sdiyuba.tedenglish.view.home.ProverbContract;


/*
经典谚语
 */
public class AdageActivity extends AppCompatActivity implements ProverbContract.ProverbView {

    private ActivityYanyuBinding binding;

    private ProverbPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        binding = ActivityYanyuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        presenter = new ProverbPresenter();
        presenter.attchView(this);

        presenter.getProverb();






        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //换一个
        binding.nextone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getProverb();
            }
        });

    }



    @Override
    public void getProverb(ProverbBean proverbBean) {

        binding.LearningReportText.setText(proverbBean.getData().get(0).getSentence()+"\n"+proverbBean.getData().get(0).getSentenceCn());
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
}