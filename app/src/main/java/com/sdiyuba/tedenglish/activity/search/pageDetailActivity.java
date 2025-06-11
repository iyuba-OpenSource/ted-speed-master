package com.sdiyuba.tedenglish.activity.search;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.adapter.search.pageInfoAdpter;
import com.sdiyuba.tedenglish.databinding.ActivityPageDetailBinding;
import com.sdiyuba.tedenglish.model.bean.InQuireBean;
import com.sdiyuba.tedenglish.model.bean.KeyWordBean;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;
import com.sdiyuba.tedenglish.presenter.home.SearchPresenter;
import com.sdiyuba.tedenglish.view.home.SearchContract;
import com.sdiyuba.rewardgold.util.PullUpLoading;

import java.util.List;

public class pageDetailActivity extends AppCompatActivity implements SearchContract.SearchView {

    private ActivityPageDetailBinding binding;

    private SearchPresenter searchPresenter;

    private com.sdiyuba.tedenglish.adapter.search.pageInfoAdpter pageInfoAdpter;

    private PullUpLoading pullUpLoading;

    int pages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPageDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        searchPresenter= new SearchPresenter();
        searchPresenter.attchView(this);


        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);



        searchPresenter.getWordQuery("json",1,10,0,"1", Constant.search_wordKey, Constant.uid,Constant.AppId);




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.pageDetailRv.setLayoutManager(linearLayoutManager);


        binding.tbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pullUpLoading = new PullUpLoading(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                binding.loadingLoadend.setVisibility(View.VISIBLE);
                binding.pageDetailRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pageInfoAdpter == null) {
                            return;
                        }

                        pages = pages+1;
                        searchPresenter.getWordQuery("json",pages,10,0,"1",Constant.search_wordKey, Constant.uid,Constant.AppId);


                        binding.loadingLoadend.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        };
        binding.pageDetailRv.addOnScrollListener(pullUpLoading);
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
    public void getKeyWord(KeyWordBean keyWordBean) {

    }

    @Override
    public void getInQuire(InQuireBean inQuireBean) {

    }

    @Override
    public void getWordQuery(WordQueryBean wordQueryBean) {


        Log.d("20145666333", "getWordQuery: "+"执行了");




            if (pullUpLoading.isLoading()) {   //执行上拉刷新
                //如果刷新了
                List<WordQueryBean.TitleDataDTO> lists = wordQueryBean.getTitleData();

                if (pageInfoAdpter == null) {

                    //控制定位光标  2行代码

                    pageInfoAdpter = new pageInfoAdpter(lists,this);

                    binding.pageDetailRv.setAdapter(pageInfoAdpter);

                } else {
                    List<WordQueryBean.TitleDataDTO> dataDTOS = pageInfoAdpter.getDatas();

                    dataDTOS.addAll(lists);   //添加进去 ,并且刷新
                    pageInfoAdpter.notifyDataSetChanged();
                }


            } else {
                List<WordQueryBean.TitleDataDTO> lists = wordQueryBean.getTitleData();
                if (pageInfoAdpter == null) {
                    pageInfoAdpter = new pageInfoAdpter( lists,this);
                    binding.pageDetailRv.setAdapter(pageInfoAdpter);
                }
                pageInfoAdpter.notifyDataSetChanged();

            }
            pullUpLoading.setLoading(false);








    }
}