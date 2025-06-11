package com.sdiyuba.tedenglish.fragment.detailFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.util.MD5;
import com.bumptech.glide.Glide;
import com.sdiyuba.tedenglish.databinding.FragmentRankingBinding;
import com.sdiyuba.tedenglish.activity.RankingDetailsActivity;
import com.sdiyuba.tedenglish.adapter.RankingAdpter;

import com.sdiyuba.tedenglish.model.bean.RankingBean;
import com.sdiyuba.tedenglish.model.bean.RankingDetailsBean;
import com.sdiyuba.tedenglish.presenter.home.RankingPresenter;
import com.sdiyuba.tedenglish.view.home.RankingContract;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 *  排行榜
 */
public class RankingFragment extends Fragment implements RankingContract.RankingView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentRankingBinding binding;

    private RankingPresenter rankingPresenter;
    public RankingFragment() {
        // Required empty public constructor
    }

    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        rankingPresenter= new RankingPresenter();
        rankingPresenter.attchView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRankingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.RankingRv.setLayoutManager(linearLayoutManager);



        return view;
    }

    public void GetRanking(){

//        获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());

//        14044990
        String sign= MD5.md5(Constant.uid+Constant.EvaType +
                +Constant.orivoaid+0+100+date);

        rankingPresenter.getRanking(Constant.uid,"D",100+"",0,Constant.EvaType,Constant.orivoaid+"",sign);

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
    public void getRanking(RankingBean rankingBean) {
        List<RankingBean.DataDTO> list = rankingBean.getData();
//        System.out.println(rankingBean);
        //用什么样式就new一个什么样式,下面接收一下

        RankingAdpter rankingAdpter = new RankingAdpter(list, requireContext());
        binding.RankingRv.setAdapter(rankingAdpter);

        if (rankingBean.getMyranking() ==1){
            binding.mineRank.setText("");
            binding.mineRank.setBackgroundResource(R.drawable.the1);
        }

        if (rankingBean.getMyranking() ==2){
            binding.mineRank.setText("");
            binding.mineRank.setBackgroundResource(R.drawable.the2);
        }
        if (rankingBean.getMyranking() ==3){
            binding.mineRank.setText("");
            binding.mineRank.setBackgroundResource(R.drawable.the3);
        }


        if (Integer.parseInt(Constant.uid)==0){
            binding.myRanking.setVisibility(View.GONE);
            binding.mineName.setText("未登录");
        }else {
            binding.myRanking.setVisibility(View.VISIBLE);
            binding.mineName.setText(rankingBean.getMyname()+"");
        }

        binding.mineSentence.setText("句子数:"+rankingBean.getMycount()+"");
        binding.mineScore.setText(rankingBean.getMyscores()+"");

        if(rankingBean.getMycount() == 0){
            binding.mineAvg.setText("平均分:"+0);
        }else{
            binding.mineAvg.setText(("平均分:"+rankingBean.getMyscores()/rankingBean.getMycount())+"");
        }
        Glide.with(requireContext()).load(Constant.userimgUrl).into(binding.mineImg);

        binding.myRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), RankingDetailsActivity.class);
                Bundle bundle = new Bundle();

//                bundle.putInt("date",1);
                bundle.putInt("uid",rankingBean.getMyid());

                bundle.putString("img",rankingBean.getMyimgSrc());

                bundle.putString("name", rankingBean.getMyname());

                bundle.putInt("score",rankingBean.getMyscores());

                bundle.putInt("sentence",rankingBean.getMycount());

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    @Override
    public void getRankingDetails(RankingDetailsBean rankingDetailsBean) {

    }
}