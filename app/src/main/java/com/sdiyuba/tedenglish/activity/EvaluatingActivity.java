package com.sdiyuba.tedenglish.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.fragment.detailFragment.EvaFragment;
import com.sdiyuba.tedenglish.fragment.detailFragment.RankingFragment;
import com.google.android.material.tabs.TabLayout;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityEvaluatingBinding;


public class EvaluatingActivity extends AppCompatActivity {

    private ActivityEvaluatingBinding binding;

    private String[] title = {"评测", "排行"};


    private EvaFragment evaFragment;
    private RankingFragment rankingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEvaluatingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        evaFragment = EvaFragment.newInstance();
        rankingFragment = RankingFragment.newInstance();
        //标题
//        binding.titleEva.setText(Constant.oribiaoti);

        if(Constant.AllTitleCn.length()>=10){
            binding.titleEva.setText(Constant.AllTitleCn.substring(0,10)+"...");
        }else {
            binding.titleEva.setText(Constant.AllTitleCn);
        }


        binding.backTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.Eva_Sum=0;//次数清0
                finish();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()

                .add(R.id.ll_content, evaFragment)
                .add(R.id.ll_content, rankingFragment)
                .show(evaFragment)
                .hide(rankingFragment)
                .commit();


        for (int i = 0; i < title.length; i++) {

            TabLayout.Tab tab = binding.tabTitle.newTab();
            tab.setText(title[i]);
            binding.tabTitle.addTab(tab);
        }

        //判断是哪个item
        binding.tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                showFragment(title[tab.getPosition()]);

               if (title[tab.getPosition()].equals("评测")){
                   evaFragment.initView();
                }else {

                    evaFragment.EvaExchange();
                    rankingFragment.GetRanking();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void showFragment(String name) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

       if (name.equals("评测")) {


            fragmentTransaction.show(evaFragment);
            fragmentTransaction.hide(rankingFragment);
            //

        } else if (name.equals("排行")) {

            fragmentTransaction.hide(evaFragment);
            fragmentTransaction.show(rankingFragment);
        }

        fragmentTransaction.commit();
    }



    @Override
    protected void onDestroy() {
        //我们自己的方法
        Constant.Eva_Sum = 0;

        super.onDestroy();


    }


}