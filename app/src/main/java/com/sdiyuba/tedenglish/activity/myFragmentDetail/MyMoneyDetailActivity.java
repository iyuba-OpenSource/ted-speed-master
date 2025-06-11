package com.sdiyuba.tedenglish.activity.myFragmentDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.adapter.MyMoneyAdpter;
import com.sdiyuba.tedenglish.databinding.ActivityMyMoneyDetailBinding;
import com.sdiyuba.tedenglish.model.bean.MyMoneyBean;
import com.sdiyuba.tedenglish.presenter.home.MyMoneyPresenter;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.util.PullUpLoading;
import com.sdiyuba.tedenglish.view.home.MyMoneyContract;



import java.text.SimpleDateFormat;
import java.util.List;

public class MyMoneyDetailActivity extends AppCompatActivity implements MyMoneyContract.MyMoneyView {

    private ActivityMyMoneyDetailBinding binding;
    private MyMoneyPresenter myMoneyPresenter;

    private MyMoneyAdpter adpter;

    private PullUpLoading pullUpLoading;

    int pages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyMoneyDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        myMoneyPresenter = new MyMoneyPresenter();
        myMoneyPresenter.attchView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.moneyRv.setLayoutManager(linearLayoutManager);

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String sign = MD5.md5(Constant.uid+"iyuba"+df.format(System.currentTimeMillis()));
        myMoneyPresenter.getMyMoney(Constant.uid,1,20,sign);

        binding.tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pullUpLoading = new PullUpLoading(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                binding.loadingLoadend.setVisibility(View.VISIBLE);
                binding.moneyRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adpter == null) {
                            return;
                        }

                        pages = pages+1;
                        myMoneyPresenter.getMyMoney(Constant.uid,pages,20,sign);

                        binding.loadingLoadend.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        };
        binding.moneyRv.addOnScrollListener(pullUpLoading);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMyMoney(MyMoneyBean myMoneyBean) {

        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");
        binding.money.setText( df.format(Constant.money)+"元");  //格式化显示

        if (pullUpLoading.isLoading()) {   //执行上拉刷新
            //如果刷新了
            List<MyMoneyBean.DataDTO> list = myMoneyBean.getData();

            if (adpter == null) {

                //控制定位光标  2行代码
                adpter = new MyMoneyAdpter(this, list);
                binding.moneyRv.setAdapter(adpter);


            } else {
                List<MyMoneyBean.DataDTO> dataDTOS = adpter.getDates();

                dataDTOS.addAll(list);   //添加进去 ,并且刷新
                adpter.notifyDataSetChanged();
            }


        } else {

            List<MyMoneyBean.DataDTO> list = myMoneyBean.getData();
            if (adpter == null) {
                adpter = new MyMoneyAdpter(this, list);
                binding.moneyRv.setAdapter(adpter);
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