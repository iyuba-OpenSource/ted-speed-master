package com.sdiyuba.tedenglish.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.activity.AdageActivity;
import com.sdiyuba.tedenglish.activity.search.SearchActivity;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;
import com.google.android.material.tabs.TabLayout;


import com.paradoxie.autoscrolltextview.VerticalTextview;
import com.sdiyuba.tedenglish.databinding.FragmentHomeBinding;
import com.sdiyuba.tedenglish.model.bean.InQuireBean;
import com.sdiyuba.tedenglish.model.bean.KeyWordBean;
import com.sdiyuba.tedenglish.presenter.home.SearchPresenter;
import com.sdiyuba.tedenglish.view.home.SearchContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * 首页
 */
public class homeFragment extends Fragment implements SearchContract.SearchView  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    private FragmentHomeBinding binding;

    private String[] tabs = {"全部", "文化", "经济", "科学", "科技", "健康", "艺术", "音乐", "教育", "天才","自然"};

    private SearchPresenter presenter;

    private List<TabFragment> tabFragmentList = new ArrayList<>();

    TabLayout tabLayout;
    ViewPager tabviewPager;

    private int graycolor = Color.parseColor("#656161");

    private VerticalTextview textView;
//    private ArrayList<String> titleList = new ArrayList<>();
    String date;

    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        presenter = new SearchPresenter();
        presenter.attchView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        tabLayout = binding.tabLayout;
        tabviewPager = binding.viewPager;
        textView =  binding.textVerticalTextview;

        //左上角日期
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH 是从 0 开始计数的，所以要加 1
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);



        if (month<10){
            // 格式化成 "几月几日" 的形式
            date = "0"+month + "/" + dayOfMonth ;

        }else {
            date = month + "/" + dayOfMonth ;
        }

        binding.todayDate.setText(date);
        binding.weekDate.setText(getDayOfWeek());


        presenter.getKeyWord(Constant.EvaType);


        //谚语
        binding.adage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(requireContext(), AdageActivity.class));
            }
        });


            //添加tab
        for (int i = 0; i < tabs.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
            tabFragmentList.add(TabFragment.newInstance(tabs[i]));
        }



        tabviewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
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
        tabLayout.setupWithViewPager(tabviewPager, false);



//
        return view;
    }

    // 获取当前日期对应的星期几
    public String getDayOfWeek() {
        // 获取当前日期时间
        Calendar calendar = Calendar.getInstance();
        // 获取星期几，其中星期日为1，星期一为2，以此类推，星期六为7
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 将数字转换为星期几的文字描述
        String dayName = "";
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                dayName = "星期日";
                break;
            case Calendar.MONDAY:
                dayName = "星期一";
                break;
            case Calendar.TUESDAY:
                dayName = "星期二";
                break;
            case Calendar.WEDNESDAY:
                dayName = "星期三";
                break;
            case Calendar.THURSDAY:
                dayName = "星期四";
                break;
            case Calendar.FRIDAY:
                dayName = "星期五";
                break;
            case Calendar.SATURDAY:
                dayName = "星期六";
                break;
        }
        return dayName;
    }


    @Override
    public void onResume() {
        super.onResume();

//        Log.d("fang77889900", "onResume: "+Constant.keyWordList.size());
//        if (Constant.keyWordList!=null){
//            if (Constant.keyWordList.size()!=0){
//                binding.textVerticalTextview.startAutoScroll();
//            }
//        }


    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("fang77889900", "onPause: ");
//        if (Constant.keyWordList.size()!=0){
//            binding.textVerticalTextview.stopAutoScroll();
//        }

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


//        if ( keyWordBean.getResult().equals("200")){
//
//        }


        Log.d("fang012365412", "getKeyWord: "+keyWordBean.getResult());
        for (int i = 0; i < keyWordBean.getData().size(); i++) {
            Constant.keyWordList.add(keyWordBean.getData().get(i));
//            titleList.add(keyWordBean.getData().get(i));
        }


        textView.setTextList(Constant.keyWordList);
        textView.setText(11, 5, graycolor);//设置属性
        textView.setTextStillTime(5000);//设置停留时长间隔
        textView.setAnimTime(400);//设置进入和退出的时间间隔
        textView.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Constant.search_wordKey =  Constant.keyWordList.get(position);

                startActivity(new Intent(requireContext(), SearchActivity.class));
            }
        });

        textView.startAutoScroll();
    }

    @Override
    public void getInQuire(InQuireBean inQuireBean) {

    }

    @Override
    public void getWordQuery(WordQueryBean wordQueryBean) {

    }


}