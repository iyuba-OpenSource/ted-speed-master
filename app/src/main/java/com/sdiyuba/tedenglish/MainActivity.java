package com.sdiyuba.tedenglish;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.iyuba.imooclib.event.ImoocBuyIyubiEvent;
import com.iyuba.imooclib.event.ImoocBuyVIPEvent;
import com.iyuba.imooclib.event.ImoocPayCourseEvent;
import com.iyuba.imooclib.ui.mobclass.MobClassFragment;
import com.iyuba.module.user.IyuUserManager;
import com.iyuba.module.user.User;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.BuyActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.BuyIyubiActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.WKbuyActivity;
import com.sdiyuba.tedenglish.databinding.ActivityMainBinding;
import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.presenter.home.UidLoginPresenter;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.view.home.uidLoginContract;
import com.sdiyuba.rewardgold.fragment.MoneyFragment;

import com.sdiyuba.tedenglish.activity.myFragmentDetail.LoginActivity;
import com.sdiyuba.tedenglish.fragment.heardFragment;
import com.sdiyuba.tedenglish.fragment.homeFragment;
import com.sdiyuba.tedenglish.fragment.myFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements uidLoginContract.uidLoginView, myFragment.OnFragmentInteractionListener {

    private ActivityMainBinding binding;

    private homeFragment homefragment;
    private myFragment myfragment;

    private heardFragment heardfragment;

    private MoneyFragment makeMoneyFragment;

    private MobClassFragment mobClassFragment; //微课

    String useruid;

    private UidLoginPresenter uidLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        uidLoginPresenter = new UidLoginPresenter();
        uidLoginPresenter.attchView(this);

        EventBus.getDefault().register(this);  //集成中需要跳转的地方用

        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR );



        SharedPreferences userInfo = getSharedPreferences("useruid", MODE_PRIVATE); // 获得SharedPreferences对象userInfo
// 方法的第一个String类参数 name 指存储文件名，第二个int型参数 mode 指文件打开方式
        useruid = userInfo.getString("useruid", Constant.uid); // 获得SharedPreferences对象的指定变量的值


        //活动 听文章赚金币的时间统计 - 重置
        SharedPreferences prefs = getSharedPreferences("PageVisitPrefs", MODE_PRIVATE);
        String lastVisitDate = prefs.getString("lastVisitDate", "");
        String currentDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        Log.d("fang01236985", "onCreate: "+currentDate+"*******"+lastVisitDate);
        if (!currentDate.equals(lastVisitDate)) {
            // 如果不是同一天，重置计时器
            SharedPreferences.Editor editor = getSharedPreferences("PageVisitPrefs", MODE_PRIVATE).edit();
            editor.putString("lastVisitDate", currentDate);
            editor.putLong("totalTimeToday", 0);
            editor.apply();
        }



        replaceFragment("home");
        binding.home.setImageResource(R.drawable.shouye1);


        if (!Constant.uid.equals("0")) {


            String sign = MD5.md5("20001" + useruid + "iyubaV2");
            uidLoginPresenter.uidLogin("android", "json", "20001", useruid, useruid, 291, sign);
        }


        //首页
        binding.shouye0.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                binding.home.setImageResource(R.drawable.shouye1);
                binding.heardImage.setImageResource(R.drawable.heard0);
                binding.wkImage.setImageResource(R.drawable.wk0);
                binding.makeMoneyImage.setImageResource(R.drawable.huodong0);
                binding.wode.setImageResource(R.drawable.wode0);

//                binding.toolbarfirst.toolbarBiaoti.setVisibility(View.VISIBLE);
//                binding.toolbarfirst.toolbarFirsttitle.setText("VOA慢速英语极速版");

                replaceFragment("home");

            }
        });

        //听过
        binding.heardLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.home.setImageResource(R.drawable.shouye0);
                binding.heardImage.setImageResource(R.drawable.heard1);
                binding.wkImage.setImageResource(R.drawable.wk0);
                binding.makeMoneyImage.setImageResource(R.drawable.huodong0);
                binding.wode.setImageResource(R.drawable.wode0);


                heardfragment.onResume();
                replaceFragment("heard");
            }
        });

        //微课
        binding.wkLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                binding.home.setImageResource(R.drawable.shouye0);
                binding.heardImage.setImageResource(R.drawable.heard0);
                binding.wkImage.setImageResource(R.drawable.wk1);
                binding.makeMoneyImage.setImageResource(R.drawable.huodong0);
                binding.wode.setImageResource(R.drawable.wode0);

                replaceFragment("wk");
            }
        });

        //活动
        binding.makeMoney.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                binding.home.setImageResource(R.drawable.shouye0);
                binding.heardImage.setImageResource(R.drawable.heard0);
                binding.wkImage.setImageResource(R.drawable.wk0);
                binding.makeMoneyImage.setImageResource(R.drawable.huodong1);
                binding.wode.setImageResource(R.drawable.wode0);



//                binding.toolbarfirst.toolbarBiaoti.setVisibility(View.VISIBLE);
//                binding.toolbarfirst.toolbarFirsttitle.setText("VOA慢速英语极速版");

                if (Constant.username == null){

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));

                }else {

//                    makeMoneyFragment.init(Constant.uid,"229");

                }


                SharedPreferences prefs = getSharedPreferences("PageVisitPrefs", MODE_PRIVATE);

                long s_time = prefs.getLong("totalTimeToday", 0);

                makeMoneyFragment.init(Constant.uid,String.valueOf(Constant.AppId),s_time);

                makeMoneyFragment.onResume();


                replaceFragment("money");

            }
        });




        //我得
        binding.wode0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.home.setImageResource(R.drawable.shouye0);
                binding.heardImage.setImageResource(R.drawable.heard0);
                binding.wkImage.setImageResource(R.drawable.wk0);
                binding.makeMoneyImage.setImageResource(R.drawable.huodong0);
                binding.wode.setImageResource(R.drawable.wode1);
//                binding.toolbarfirst.toolbarBiaoti.setVisibility(View.VISIBLE);
//                binding.toolbarfirst.toolbarFirsttitle.setText("个人中心");


                replaceFragment("my");
            }
        });

    }


    private void replaceFragment(String name) {

        FragmentManager fragmentManager = getSupportFragmentManager();


        //首页
        homefragment = (homeFragment) fragmentManager.findFragmentByTag("home");
        if (homefragment == null) {
            homefragment = homeFragment.newInstance(null, null);
        }

        //heardFragment

        heardfragment = (heardFragment) fragmentManager.findFragmentByTag("heard");
        if (heardfragment == null) {
            heardfragment = heardFragment.newInstance(null, null);
        }


        //微课
        mobClassFragment = (MobClassFragment) fragmentManager.findFragmentByTag("wk");
        if (mobClassFragment == null) {

            ArrayList<Integer> tempList = new ArrayList<>();
            tempList.add(21);
            tempList.add(-2);
            tempList.add(2);
            tempList.add(3);
            tempList.add(4);
            tempList.add(7);
            tempList.add(8);
            tempList.add(9);

            tempList.add(22);
            tempList.add(91);
            tempList.add(26);

            Bundle bundle = MobClassFragment.buildArguments(21, false, tempList);
            mobClassFragment = MobClassFragment.newInstance(bundle);
        }


        //活动
        makeMoneyFragment = (MoneyFragment) fragmentManager.findFragmentByTag("money");
        if (makeMoneyFragment == null) {
            makeMoneyFragment = MoneyFragment.newInstance(null, null);
            //初始化  活动模块
            SharedPreferences prefs = getSharedPreferences("PageVisitPrefs", MODE_PRIVATE);
            long s_time = prefs.getLong("totalTimeToday", 0);
            makeMoneyFragment.init(Constant.uid, String.valueOf(Constant.AppId),s_time);
        }


        //我的
        myfragment = (myFragment) fragmentManager.findFragmentByTag("my");
        if (myfragment == null) {
            myfragment = myFragment.newInstance(null, null);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (!homefragment.isAdded()) {

            transaction.add(R.id.fl, homefragment, "home");
        }

        if (!heardfragment.isAdded()) {

            transaction.add(R.id.fl, heardfragment, "heard");
        }


        if (!mobClassFragment.isAdded()) {
            transaction.add(R.id.fl, mobClassFragment, "wk");
        }

        if (!makeMoneyFragment.isAdded()) {

            transaction.add(R.id.fl, makeMoneyFragment, "money");
        }



        if (!myfragment.isAdded()) {

            transaction.add(R.id.fl, myfragment, "my");
        }


        if (name.equals("home")) {

            transaction.show(homefragment);
            transaction.hide(heardfragment);
            transaction.hide(mobClassFragment);
            transaction.hide(makeMoneyFragment);
            transaction.hide(myfragment);

        } else if (name.equals("heard")){

            transaction.hide(homefragment);
            transaction.show(heardfragment);
            transaction.hide(mobClassFragment);
            transaction.hide(makeMoneyFragment);
            transaction.hide(myfragment);

        } else if (name.equals("wk")) {

            transaction.hide(homefragment);
            transaction.hide(heardfragment);
            transaction.show(mobClassFragment);
            transaction.hide(makeMoneyFragment);
            transaction.hide(myfragment);

        }else if (name.equals("money")){

            transaction.hide(homefragment);
            transaction.hide(heardfragment);
            transaction.hide(mobClassFragment);
            transaction.show(makeMoneyFragment);
            transaction.hide(myfragment);

        }else {

            transaction.hide(homefragment);
            transaction.hide(heardfragment);
            transaction.hide(mobClassFragment);
            transaction.hide(makeMoneyFragment);
            transaction.show(myfragment);

        }
        transaction.commit();
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d("fang012222222", useruid+"onResume: Activity" + Constant.uid);

//        if (!(useruid.equals(Constant.uid))){
//
//            homefragment.getAdShow();
//
//        }
//
//        useruid = Constant.uid;
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
    public void uidLogin(UidBean uidBean) {

        if (uidBean.getResult() == 201) {

            Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());
            Log.d("fang0147", Constant.vipStatus+"uidBean.getVipStatus(): "+uidBean.getVipStatus() );
            SharedPreferences vipStates = MyApplication.getContext().getSharedPreferences("vipStates", MODE_PRIVATE);
            SharedPreferences.Editor vipeditor = vipStates.edit();
            vipeditor.putInt("vipStates", Constant.vipStatus);
            vipeditor.commit(); // 提交修改并保存

            Constant.money = (uidBean.getMoney() * 0.01);//钱包

            Constant.aiyubi = uidBean.getAmount();//爱语币

            Constant.jifen = Integer.parseInt(uidBean.getCredits());//积分

            Constant.phone = uidBean.getMobile();//手机号
            long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
            //要转成后的时间的格式
            android.icu.text.SimpleDateFormat simpleDateFormat = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
            }
            // 时间戳转换成时间
            String vipDate = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                vipDate = simpleDateFormat.format(new java.sql.Date(timeStamp));
            }

            Constant.vipDate = vipDate;//vip时间

            Constant.uid = useruid;

            Constant.username = uidBean.getUsername();

//            SharedPreferences urlInfo = getSharedPreferences("imageUrl", MODE_PRIVATE);
//            imageUrl = urlInfo.getString("imageUrl", Constant.userimgUrl);
//
//            Constant.userimgUrl = imageUrl;

//
            Log.d("fang5555", "uidLogin: "+Constant.uid);
            if (!Constant.uid .equals("0")) {
                User user = new User();
                user.vipStatus = String.valueOf(Constant.vipStatus);
                if (Constant.vipStatus != 0) {
                    user.vipExpireTime = uidBean.getExpireTime();
                }
                user.uid = Integer.parseInt(Constant.uid);
                user.credit = Integer.parseInt(uidBean.getCredits());
                user.name = Constant.username;
                user.imgUrl = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.uid + "&size=big";
                user.email = uidBean.getEmail();
                user.mobile = Constant.mobile;
                user.iyubiAmount = (int) Constant.aiyubi;
                IyuUserManager.getInstance().setCurrentUser(user);


                myfragment.uidloginExchange();

            }
        }
    }



    //微课直购
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImoocPayCourseEvent event) {
        // TODO Go Pay Order to buy course


        if (Integer.parseInt(Constant.uid) == 0) {

            Toast.makeText(MainActivity.this, "请先登录~", Toast.LENGTH_SHORT).show();
        } else {
            Constant.wkId = event.courseId;
            Constant.wkPrice = event.price;
            Constant.wkBody = event.body;
            Intent intent = new Intent(this, WKbuyActivity.class);
            startActivity(intent);
        }


    }

    //购买爱与币
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImoocBuyIyubiEvent event) {
        // TODO Go Pay Order to buy course
        if (Integer.parseInt(Constant.uid) == 0) {

            Toast.makeText(MainActivity.this, "请先登录~", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, BuyIyubiActivity.class);
            startActivity(intent);

        }

    }



    /**
     * 微课购买会员
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fromMoocBuyGoldenVip(ImoocBuyVIPEvent event) {
        if (Integer.parseInt(Constant.uid) == 0) {

            Toast.makeText(MainActivity.this, "请先登录~", Toast.LENGTH_SHORT).show();
        } else {
            Constant.abc = 3;
            Intent intent = new Intent(this, BuyActivity.class);
            startActivity(intent);
        }

    }



    @Override
    public void onFragmentInteraction(String data) {
        // 实现接口方法
        binding.heardLine.callOnClick();
//        replaceFragment("heard");
    }
}