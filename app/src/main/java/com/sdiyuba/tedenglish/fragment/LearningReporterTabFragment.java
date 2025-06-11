package com.sdiyuba.tedenglish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.activity.learningReporter.HearingDetailActivity;
import com.sdiyuba.tedenglish.activity.learningReporter.ReadReporterDetailActivity;
import com.sdiyuba.tedenglish.activity.learningReporter.SpokenDetailActivity;
import com.sdiyuba.tedenglish.databinding.FragmentTabLearningreportBinding;
import com.sdiyuba.tedenglish.model.bean.HearingBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterBean;
import com.sdiyuba.tedenglish.model.bean.SpokeBean;
import com.sdiyuba.tedenglish.presenter.home.LearningReportPresenter;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.view.home.LearningReportContract;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

import io.reactivex.annotations.Nullable;

public class LearningReporterTabFragment extends Fragment implements LearningReportContract.LearningReportView,SwipeRefreshLayout.OnRefreshListener{

   private LearningReportPresenter learningReportPresenter;

    private FragmentTabLearningreportBinding binding;


    String label;

    int parentID=-1;
    int page = 1;


    public static LearningReporterTabFragment newInstance(String label) {
        Bundle args = new Bundle();
        args.putString("label", label);
        LearningReporterTabFragment fragment = new LearningReporterTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentTabLearningreportBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        learningReportPresenter = new LearningReportPresenter();
        learningReportPresenter.attchView(this);


        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        assert getArguments() != null;
        if (Objects.equals(getArguments().getString("label"), "听力")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());

            String sign = MD5.md5(Constant.uid+"D"+0+0+ simpleDateFormat.format(date));

            learningReportPresenter.getHearing("listening", Constant.uid,"D",0,0,sign);

        } else if (Objects.equals(getArguments().getString("label"), "口语")) {
            String sign= MD5.md5(Constant.EvaType+"D");
            learningReportPresenter.getSpoke(Constant.uid,Constant.EvaType,"English","D",sign);
        } else if (Objects.equals(getArguments().getString("label"), "阅读")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            String sign = MD5.md5(Constant.uid + "D" + 0 + "reading" + simpleDateFormat.format(date));

            learningReportPresenter.getReadReporter(Constant.uid, "D", 1, 0, "reading", sign);
        }


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
    public void onRefresh() {

    }

    @Override
    public void getHearing(HearingBean hearingBean) {

        binding.learningReporterTabImage.setImageResource(R.drawable.erji_green1);
        binding.learningReporterTabId.setText("听力学习报告");
        binding.learningReporterTabText1.setText("时长");
        binding.learningReporterTabText2.setText("排名");
        binding.learningReporterTabText3.setText("单词数");
        binding.learningReporterTabText11.setText(hearingBean.getTotalTime()+"s");
        binding.learningReporterTabText22.setText(hearingBean.getMyranking()+"");
        binding.learningReporterTabText33.setText(hearingBean.getTotalWord()+"");

        binding.learningReporterTabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HearingDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getSpoke(SpokeBean spokeBean) {

        binding.learningReporterTabImage.setImageResource(R.drawable.evaluyin0);
        binding.learningReporterTabId.setText("口语学习报告");
        binding.learningReporterTabText1.setText("句子数");
        binding.learningReporterTabText2.setText("平均分");
        binding.learningReporterTabText3.setText("排名");
        binding.learningReporterTabText11.setText(spokeBean.getTotal()+"句");
        binding.learningReporterTabText22.setText(spokeBean.getAvg()+"分");
        binding.learningReporterTabText33.setText(spokeBean.getRankNum()+"");

        binding.learningReporterTabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpokenDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getReadReporter(ReadReporterBean readReporterBean) {


        if (readReporterBean.getResult() == 1) {
            binding.learningReporterTabImage.setImageResource(R.drawable.learning_reading);
            binding.learningReporterTabId.setText("阅读学习报告");
            binding.learningReporterTabText1.setText("阅读量");
            binding.learningReporterTabText2.setText("单词数");
            binding.learningReporterTabText3.setText("WPM");
            binding.learningReporterTabText11.setText(readReporterBean.getMycnt() +"篇");
            binding.learningReporterTabText22.setText(readReporterBean.getMywords()+"");
            binding.learningReporterTabText33.setText(readReporterBean.getMywpm()+"");
        } else {
            Toast.makeText(requireContext(), "数据请求失败",
                    Toast.LENGTH_SHORT).show();
        }

        binding.learningReporterTabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReadReporterDetailActivity.class);
                startActivity(intent);
            }
        });

    }


}
