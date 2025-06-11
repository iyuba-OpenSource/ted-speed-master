package com.sdiyuba.tedenglish.activity.learningReporter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityLearningReportBinding;
import com.sdiyuba.tedenglish.fragment.LearningReporterTabFragment;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;


/*
学习记录
 */
public class LearningReportActivity extends AppCompatActivity {

    private ActivityLearningReportBinding binding;

    private String[] tabs = {"听力", "口语", "阅读"};
    private List<LearningReporterTabFragment> tabFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityLearningReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TabLayout tabLayout = findViewById(R.id.tab_layout);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ViewPager viewPager = findViewById(R.id.view_pager);
        //添加tab
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            tabFragmentList.add(LearningReporterTabFragment.newInstance(tabs[i]));
        }


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {

                return tabFragmentList.get(position);
            }

            @Override
            public int getCount() {


                return tabFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });


        //设置TabLayout和ViewPager联动
        tabLayout.setupWithViewPager(viewPager, false);



        binding.backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}