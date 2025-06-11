package com.sdiyuba.tedenglish.activity.learningReporter;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.adapter.SpokenDetailAdpter;
import com.sdiyuba.tedenglish.databinding.ActivitySpokenDetailBinding;
import com.sdiyuba.tedenglish.model.bean.HearingDetailBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterDetailBean;
import com.sdiyuba.tedenglish.model.bean.SpokenDetailBean;
import com.sdiyuba.tedenglish.presenter.home.LearningReportDetailPresenter;
import com.sdiyuba.tedenglish.util.PullUpLoading;
import com.sdiyuba.tedenglish.view.home.LearningReportDetailContract;

import java.util.List;

public class SpokenDetailActivity extends AppCompatActivity implements LearningReportDetailContract.LearningReportDetailView {

    private ActivitySpokenDetailBinding binding;

    private LearningReportDetailPresenter learningReportDetailPresenter;

    private SpokenDetailAdpter adpter;

    private PullUpLoading pullUpLoading;

    int endId = 0;

    int sum =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpokenDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        learningReportDetailPresenter = new LearningReportDetailPresenter();
        learningReportDetailPresenter.attchView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.spokendetailRv.setLayoutManager(linearLayoutManager);

        learningReportDetailPresenter.getSpokenDetail(Constant.uid,Constant.EvaType,"English",0,20);

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
                binding.spokendetailRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adpter == null) {
                            return;
                        }

                        learningReportDetailPresenter.getSpokenDetail(Constant.uid,Constant.EvaType,"English",endId,20);


                        binding.loadingLoadend.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        };
        binding.spokendetailRv.addOnScrollListener(pullUpLoading);
    }

    @Override
    public void getHearingDetail(HearingDetailBean hearingDetailBean) {

    }

    @Override
    public void getSpokenDetail(SpokenDetailBean spokenDetailBean) {

        sum = spokenDetailBean.getData().size();
        if (pullUpLoading.isLoading()) {   //执行上拉刷新
            //如果刷新了
            List<SpokenDetailBean.DataDTO> list = spokenDetailBean.getData();

            endId = list.get(list.size()-1).getId();
            if (adpter == null) {

                //控制定位光标  2行代码
                adpter = new SpokenDetailAdpter( list,this);
                binding.spokendetailRv.setAdapter(adpter);


            } else {
                List<SpokenDetailBean.DataDTO> dataDTOS = adpter.getDates();

                endId = dataDTOS.get(dataDTOS.size()-1).getId();
                dataDTOS.addAll(list);   //添加进去 ,并且刷新
                adpter.notifyDataSetChanged();
            }


        } else {


            List<SpokenDetailBean.DataDTO> list = spokenDetailBean.getData();

            endId = list.get(list.size()-1).getId();
            if (adpter == null) {
                //控制定位光标  2行代码

                adpter = new SpokenDetailAdpter(list, this);
                binding.spokendetailRv.setAdapter(adpter);
            }
            adpter.notifyDataSetChanged();

        }
        pullUpLoading.setLoading(false);
    }

    @Override
    public void getReadReporterDetail(ReadReporterDetailBean readReporterDetailBean) {

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