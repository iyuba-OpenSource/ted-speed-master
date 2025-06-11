package com.sdiyuba.tedenglish.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.LoginActivity;
import com.sdiyuba.tedenglish.fragment.heardDetailFragment.HeardHistoryFragment;
import com.sdiyuba.tedenglish.fragment.heardDetailFragment.SentenceCollectFragment;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.FragmentHeardBinding;


/**
 *  听 过
 */
public class heardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public heardFragment() {
        // Required empty public constructor
    }

    private HeardHistoryFragment heardHistoryFragment;

    private SentenceCollectFragment collectfragment;

    private FragmentHeardBinding binding;

    private int blackColor = Color.parseColor("#FF000000");


    private int hintcolor = Color.parseColor("#656161");

    public static heardFragment newInstance(String param1, String param2) {
        heardFragment fragment = new heardFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHeardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (Constant.for_mycollect){
            replaceFragment("collect");
            Constant.for_mycollect = false; //跳转到收藏页面
        }else {
            replaceFragment("History");
        }



        binding.heardHistoryText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {


                binding.heardHistoryText.setTextColor(blackColor);
                binding.collectText.setTextColor(hintcolor);
                binding.heardHistoryText.setTextSize(20);
                binding.collectText.setTextSize(13);
                Constant.for_mycollect = false; //跳转到收藏页面
                replaceFragment("History");
            }
        });

        binding.collectText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {


                if (Constant.username == null){

                    startActivity(new Intent(requireContext(), LoginActivity.class));

                }else {
                    Constant.for_mycollect = true; //跳转到收藏页面
                    binding.heardHistoryText.setTextColor(hintcolor);
                    binding.collectText.setTextColor(blackColor);

                    binding.heardHistoryText.setTextSize(13);
                    binding.collectText.setTextSize(20);



                    replaceFragment("collect");
                }

            }
        });

        return view;
    }

    private void replaceFragment(String name) {

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();


        //首页
        heardHistoryFragment = (HeardHistoryFragment) fragmentManager.findFragmentByTag("History");
        if (heardHistoryFragment == null) {
            heardHistoryFragment = heardHistoryFragment.newInstance(null, null);
        }


        //我的
        collectfragment = (SentenceCollectFragment) fragmentManager.findFragmentByTag("collect");
        if (collectfragment == null) {
            collectfragment = SentenceCollectFragment.newInstance(null, null);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (!heardHistoryFragment.isAdded()) {

            transaction.add(R.id.heard_fl, heardHistoryFragment, "History");
        }




        if (!collectfragment.isAdded()) {

            transaction.add(R.id.heard_fl, collectfragment, "collect");
        }


        if (name.equals("History")) {

            transaction.show(heardHistoryFragment);

            transaction.hide(collectfragment);

        }else {

            transaction.hide(heardHistoryFragment);

            transaction.show(collectfragment);

        }
        transaction.commit();
    }


    @Override
    public void onResume() {
        super.onResume();


        if (Constant.for_mycollect){

            binding.collectText.callOnClick();

        }else {

            binding.heardHistoryText.callOnClick();
        }


        heardHistoryFragment.init();

        collectfragment.init();

    }
}