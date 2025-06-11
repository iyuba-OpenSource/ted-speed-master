package com.sdiyuba.tedenglish.activity.learningReporter;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.adapter.HearingDetailAdpter;
import com.sdiyuba.tedenglish.databinding.ActivityHearingDetailBinding;
import com.sdiyuba.tedenglish.model.bean.HearingDetailBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterDetailBean;
import com.sdiyuba.tedenglish.model.bean.SpokenDetailBean;
import com.sdiyuba.tedenglish.presenter.home.LearningReportDetailPresenter;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.util.PullUpLoading;
import com.sdiyuba.tedenglish.view.home.LearningReportDetailContract;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class HearingDetailActivity extends AppCompatActivity implements LearningReportDetailContract.LearningReportDetailView {

    private ActivityHearingDetailBinding binding;

    private LearningReportDetailPresenter learningReportDetailPresenter;

    private HearingDetailAdpter adpter;

    private PullUpLoading pullUpLoading;

    int pages = 1;

    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHearingDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        learningReportDetailPresenter = new LearningReportDetailPresenter();
        learningReportDetailPresenter.attchView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.hearingdetailRv.setLayoutManager(linearLayoutManager);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String sign= MD5.md5(Constant.uid+simpleDateFormat.format(date));
        learningReportDetailPresenter.getHearingDetail(25,1,1, Constant.uid,sign);


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

                            pages = pages+1;
                            learningReportDetailPresenter.getHearingDetail(25,pages,1, Constant.uid,sign);

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


        if (pullUpLoading.isLoading()) {   //执行上拉刷新
            //如果刷新了
            List<HearingDetailBean.DataDTO> list = hearingDetailBean.getData();

            if (adpter == null) {

                //控制定位光标  2行代码
                adpter = new HearingDetailAdpter( list,this);
                binding.hearingdetailRv.setAdapter(adpter);


            } else {
                List<HearingDetailBean.DataDTO> dataDTOS = adpter.getDates();
                count = dataDTOS.size();
                dataDTOS.addAll(list);   //添加进去 ,并且刷新
                adpter.notifyDataSetChanged();
            }


        } else {

            List<HearingDetailBean.DataDTO> list = hearingDetailBean.getData();
            //控制定位光标  2行代码


            if (adpter == null) {
                adpter = new HearingDetailAdpter(list, this);
                binding.hearingdetailRv.setAdapter(adpter);
                count = list.size();
            }

            adpter.notifyDataSetChanged();

        }
        pullUpLoading.setLoading(false);
    }

    @Override
    public void getSpokenDetail(SpokenDetailBean spokenDetailBean) {

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