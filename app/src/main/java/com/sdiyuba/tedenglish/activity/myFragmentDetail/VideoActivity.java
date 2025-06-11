package com.sdiyuba.tedenglish.activity.myFragmentDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.iyuba.headlinelibrary.HeadlineType;
import com.iyuba.headlinelibrary.event.HeadlineGoVIPEvent;
import com.iyuba.headlinelibrary.ui.title.DropdownTitleFragmentNew;
import com.iyuba.headlinelibrary.ui.title.HolderType;
import com.iyuba.headlinelibrary.ui.title.TitleCateFragment;
import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VideoActivity extends AppCompatActivity {

    private DropdownTitleFragmentNew dropdownTitleFragmentNew;

    private TitleCateFragment dailyfragment;


    private int green = Color.parseColor("#72A638");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //状态栏自定义颜色
        getWindow().setStatusBarColor(green);

        EventBus.getDefault().register(this);  //集成中需要跳转的地方用

        if (Constant.video){
            replaceFragment("video");
        }else {
            replaceFragment("news");
        }


    }

    private void replaceFragment(String name) {

        FragmentManager fragmentManager = getSupportFragmentManager();


        //日报
        dailyfragment = (TitleCateFragment) fragmentManager.findFragmentByTag("daily");
        if (dailyfragment == null) {


//            String title = context.getString(Integer.parseInt("日报"));
//            dailyfragment =  TitleCateFragment.newInstance(TitleCateFragment.buildArguments(10, HeadlineType.NEWS, HolderType.SMALL, false, true, Constant.AUDIO, 500L, title));

//            String types = HeadlineType.NEWS;
//
//            int categoryCode = getResources().getInteger(HeadlineType.getDefaultCategoryCodeId(types));



            Bundle bundle = TitleCateFragment.buildArguments(10, HeadlineType.NEWS, HolderType.SMALL);

            dailyfragment = TitleCateFragment.newInstance(bundle);
//            Bundle bundle = TitleFragment.buildArguments(15,types , "", categoryCode, 0, "audio");
//            dailyfragment = TitleFragment.newInstance(bundle);
        }
        //视频
        dropdownTitleFragmentNew = (DropdownTitleFragmentNew) fragmentManager.findFragmentByTag("video");
        if (dropdownTitleFragmentNew == null) {

            String[] types = {HeadlineType.SMALLVIDEO, HeadlineType.MEIYU, HeadlineType.VOAVIDEO, HeadlineType.TOPVIDEOS, HeadlineType.HEADLINE};


            Bundle bundle = DropdownTitleFragmentNew.buildArguments(10, types, false);
            dropdownTitleFragmentNew = DropdownTitleFragmentNew.newInstance(bundle);
        }


        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (!dailyfragment.isAdded()) {

            transaction.add(R.id.fl, dailyfragment, "news");
        }

        if (!dropdownTitleFragmentNew.isAdded()) {

            transaction.add(R.id.fl, dropdownTitleFragmentNew, "video");
        }


       if (name.equals("news")) {

            transaction.show(dailyfragment);

            transaction.hide(dropdownTitleFragmentNew);


        } else if (name.equals("video")) {

//            transaction.hide(homefragment);
            transaction.hide(dailyfragment);

            transaction.show(dropdownTitleFragmentNew);

//            binding.lunbo.setVisibility(View.VISIBLE);

        }
        transaction.commit();
    }

    //日报购买会员
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HeadlineGoVIPEvent event) {

//        SharedPreferences userSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//        SharedPreferences.Editor userEditor = userSharedPreferences.edit();
//        isLogin = userSharedPreferences.getBoolean("isLogin", false);
//        vipTime = userSharedPreferences.getString("vip_time", "0");

        if (Constant.username!=null){
            Intent intent = new Intent(this, BuyActivity.class);
//            intent.putExtra("isLogin", isLogin);
//            intent.putExtra("vip_time", vipTime);
            startActivity(intent);
        }else {
            Toast.makeText(VideoActivity.this,"请先登录~",Toast.LENGTH_SHORT).show();
        }


    }

}