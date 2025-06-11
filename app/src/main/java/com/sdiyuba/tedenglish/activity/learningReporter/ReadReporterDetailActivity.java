package com.sdiyuba.tedenglish.activity.learningReporter;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.adapter.ReadReporterAdpter;
import com.sdiyuba.tedenglish.databinding.ActivityReadReporterDetailBinding;
import com.sdiyuba.tedenglish.model.bean.HearingDetailBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterDetailBean;
import com.sdiyuba.tedenglish.model.bean.SpokenDetailBean;
import com.sdiyuba.tedenglish.presenter.home.LearningReportDetailPresenter;
import com.sdiyuba.tedenglish.util.PullUpLoading;
import com.sdiyuba.tedenglish.view.home.LearningReportDetailContract;

import java.util.List;

public class ReadReporterDetailActivity extends AppCompatActivity implements LearningReportDetailContract.LearningReportDetailView {

    private ActivityReadReporterDetailBinding binding;

    private LearningReportDetailPresenter learningReportDetailPresenter;


    private ReadReporterAdpter adpter;


    private PullUpLoading pullUpLoading;

    int pages = 1;

    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReadReporterDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        learningReportDetailPresenter = new LearningReportDetailPresenter();
        learningReportDetailPresenter.attchView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.hearingdetailRv.setLayoutManager(linearLayoutManager);


        learningReportDetailPresenter.getReadReporterDetail(Constant.uid,"json",20,0);

        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        pullUpLoading = new PullUpLoading(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                binding.loadingLoadend.setVisibility(View.VISIBLE);
                binding.hearingdetailRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (count>=20){
                            if (adpter == null) {
                                return;
                            }

                            pages = pages+20;
                            learningReportDetailPresenter.getReadReporterDetail(Constant.uid,"json",20,pages);


                            binding.loadingLoadend.setVisibility(View.GONE);
                        }

                    }
                }, 1000);
            }
        };
        binding.hearingdetailRv.addOnScrollListener(pullUpLoading);


    }



    @Override
    public void getHearingDetail(HearingDetailBean hearingDetailBean) {

    }

    @Override
    public void getSpokenDetail(SpokenDetailBean spokenDetailBean) {

    }

    @Override
    public void getReadReporterDetail(ReadReporterDetailBean readReporterDetailBean) {

        if (pullUpLoading.isLoading()) {   //执行上拉刷新
            //如果刷新了
            List<ReadReporterDetailBean.DataDTO> list = readReporterDetailBean.getData();


            if (adpter == null) {

                //控制定位光标  2行代码
                adpter = new ReadReporterAdpter( this,list);
                binding.hearingdetailRv.setAdapter(adpter);


            } else {
                List<ReadReporterDetailBean.DataDTO> dataDTOS = adpter.getDates();
                count = dataDTOS.size();
                dataDTOS.addAll(list);   //添加进去 ,并且刷新
                adpter.notifyDataSetChanged();
            }


        } else {

            List<ReadReporterDetailBean.DataDTO> list = readReporterDetailBean.getData();



            if (adpter == null) {
                //控制定位光标  2行代码
                adpter = new ReadReporterAdpter( this,list);
                binding.hearingdetailRv.setAdapter(adpter);
                count = list.size();
            }
            adpter.notifyDataSetChanged();

        }
        pullUpLoading.setLoading(false);

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