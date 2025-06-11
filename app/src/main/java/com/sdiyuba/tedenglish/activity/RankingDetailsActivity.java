package com.sdiyuba.tedenglish.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.adapter.RankingDetailsAdpter;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.view.home.RankingContract;
import com.bumptech.glide.Glide;
import com.sdiyuba.tedenglish.databinding.ActivityRankingDetailsBinding;
import com.sdiyuba.tedenglish.model.bean.RankingBean;
import com.sdiyuba.tedenglish.model.bean.RankingDetailsBean;
import com.sdiyuba.tedenglish.presenter.home.RankingPresenter;


import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class RankingDetailsActivity extends AppCompatActivity implements RankingContract.RankingView {

    //    private FragmentRankingDetailsBinding binding;
    private ActivityRankingDetailsBinding binding;

    private RankingDetailsAdpter rankingDetailsadpter;

    private RankingPresenter rankingDetailspresenter;

    private int uid, score, sentence;

    private String img, name;

    private boolean isprepar = false;


    private String sign;
    private String sign1;

    MediaPlayer mediaPlayer = new MediaPlayer();


    public void onDestroy() {

        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rankingDetailspresenter = new RankingPresenter();
        rankingDetailspresenter.attchView(this);


        Bundle bundle = this.getIntent().getExtras();
        uid = bundle.getInt("uid");
        score = bundle.getInt("score");

        sentence = bundle.getInt("sentence");

        img = bundle.getString("img");

        name = bundle.getString("name");

        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Date date = new Date(System.currentTimeMillis());

        sign = MD5.md5(uid + "getWorksByUserId" + date);


        rankingDetailspresenter.getRankingDetails("2,4", Constant.EvaType, Constant.orivoaid, uid, sign);


        //activity中获取页面
        binding = ActivityRankingDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.homeRv.setLayoutManager(linearLayoutManager);

        binding.name.setText(name);

        binding.backTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.score.setText("总分：" + score + "");

        binding.sentence.setText("句子数：" + sentence + "");

        Glide.with(this).load(img).into(binding.img);

        if (sentence == 0) {
            binding.avg.setText("平均分：" + 0 + "");
        } else {
            binding.avg.setText("平均分：" + (score / sentence) + "");
        }


    }


    @Override
    public void getRanking(RankingBean rankingBean) {

    }

    @Override
    public void getRankingDetails(RankingDetailsBean rankingDetailsBean) {
        List<RankingDetailsBean.DataDTO> list = rankingDetailsBean.getData();
//        System.out.println(rankingBean);
        //用什么样式就new一个什么样式,下面接收一下

        rankingDetailsadpter = new RankingDetailsAdpter(list, this);
        binding.homeRv.setAdapter(rankingDetailsadpter);

        rankingDetailsadpter.setCallBack(new RankingDetailsAdpter.CallBack() {
            @Override
            public void clickPlay(int position) {
                RankingDetailsBean.DataDTO rankingData = rankingDetailsadpter.getItem(position);

                if (!rankingDetailsadpter.isPlay()) {


                    String src = "http://userspeech.iyuba.cn/voa/" + rankingData.getShuoShuo();

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            isprepar = true;
                        }
                    });

                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(src);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                        }
                    });
                    rankingDetailsadpter.setPlay(true);
                    rankingDetailsadpter.setChoosePosition(position);
                    rankingDetailsadpter.notifyDataSetChanged();


                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            rankingDetailsadpter.setPlay(false);
                            rankingDetailsadpter.setChoosePosition(position);
                            rankingDetailsadpter.notifyDataSetChanged();
                        }
                    });
                } else {
                    mediaPlayer.pause();
                    mediaPlayer.release();
                    rankingDetailsadpter.setPlay(false);
                    rankingDetailsadpter.setChoosePosition(position);
                    rankingDetailsadpter.notifyDataSetChanged();
                }

            }
            //}
        });


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
