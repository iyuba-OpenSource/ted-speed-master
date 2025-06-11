package com.sdiyuba.tedenglish.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iyuba.imooclib.ui.record.PurchaseRecordActivity;
import com.iyuba.module.user.IyuUserManager;
import com.iyuba.module.user.User;
import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.MyApplication;
import com.sdiyuba.tedenglish.activity.Seeting.SeetingActivity;
import com.sdiyuba.tedenglish.activity.learningReporter.LearningReportActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.BuyActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.InfoActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.IntegralShopActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.MyMoneyDetailActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.MyWordActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.PersonalInformationActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.VideoActivity;
import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.view.home.uidLoginContract;
import com.bumptech.glide.Glide;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.FragmentMyBinding;
import com.sdiyuba.rewardgold.activity.EarningsActivity;

import com.sdiyuba.tedenglish.activity.myFragmentDetail.LoginActivity;

import com.sdiyuba.tedenglish.presenter.home.UidLoginPresenter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myFragment extends Fragment implements uidLoginContract.uidLoginView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public myFragment() {
        // Required empty public constructor
    }

    private UidLoginPresenter uidLoginPresenter;

    private FragmentMyBinding binding;

    // 定义接口
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String data);
    }

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static myFragment newInstance(String param1, String param2) {
        myFragment fragment = new myFragment();
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

        uidLoginPresenter = new UidLoginPresenter();
        uidLoginPresenter.attchView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //登录
        binding.sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.uid.equals("0")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }

            }

        });
        //文章收藏
        binding.mycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.username == null) {
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                } else {

                    someMethod();



//                    startActivity(new Intent(requireContext(), PersonalInformationActivity.class));
                }
            }
        });

        //个人信息
        binding.PersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.username == null) {
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(requireContext(), PersonalInformationActivity.class));
                }

            }
        });
        //看视频
        binding.video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.video = true;
                startActivity(new Intent(requireContext(), VideoActivity.class));

            }
        });

        //读新闻
        binding.news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.video = false;
                startActivity(new Intent(requireContext(), VideoActivity.class));

            }
        });

        //购买记录
        binding.BuyRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Constant.username == null) {
                      Intent intent = new Intent(getActivity(), LoginActivity.class);
                      startActivity(intent);

                } else {

                      startActivity(PurchaseRecordActivity.buildIntent(getActivity()));


                }
            }
        });

        //会员中心

        binding.vipPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.username == null) {
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                } else {

                    startActivity(new Intent(requireContext(), BuyActivity.class));
                }
            }
        });


        //金币
        binding.goldEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.username != null) {

                    Constant.isGold = true;
                    Intent intent = new Intent(getActivity(), EarningsActivity.class);
                    startActivity(intent);

                    //爱语吧ted英语演讲修改bug,并打包发版   abc英语处理平台反馈得问题打包发版  ted英语演讲极速版处理原文奖励倒计时错误得问题,界面优化,添加生词本
                }

            }
        });

        //现金收益
        binding.cashEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.username != null) {

                    Constant.isGold = false;

                    Intent intent = new Intent(getActivity(), EarningsActivity.class);

                    startActivity(intent);
                }

            }
        });

        //生词本
        binding.wordsNotebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if (Constant.username != null) {
                    intent = new Intent(getActivity(), MyWordActivity.class);
                } else {

                    intent = new Intent(getActivity(), LoginActivity.class);


                }
                startActivity(intent);

            }

        });
        //我的钱包
        binding.mymoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if (Constant.username != null) {
                    intent = new Intent(getActivity(), MyMoneyDetailActivity.class);
                } else {

                    intent = new Intent(getActivity(), LoginActivity.class);


                }
                startActivity(intent);
            }
        });

        //学习记录
        binding.studyrecordsLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if (Constant.username != null) {
                    intent = new Intent(getActivity(), LearningReportActivity.class);
                } else {

                    intent = new Intent(getActivity(), LoginActivity.class);


                }
                startActivity(intent);
            }
        });


        //积分商城
        binding.pointshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(Constant.uid) != 0) {
                    String sign0 = MD5.md5("iyuba" + Constant.uid + "camstory");
                    String username;
                    try {
                        username = URLEncoder.encode(Constant.username, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    String url = "http://m.iyuba.cn/mall/index.jsp?"
                            + "&uid=" + Constant.uid
                            + "&sign=" + sign0
                            + "&username=" + username
                            + "&platform=android&appid="
                            + Constant.AppId;
                    IntegralShopActivity.startActivity(getActivity(), url, "积分商城");

                } else {

                    startActivity(new Intent(requireActivity(), LoginActivity.class));
//                    if (Constant.mobsign) {
//
//                        instanttestSign();
//                    } else {
//                        Intent intent = new Intent(getActivity(), SignActivity.class);
//                        startActivity(intent);
//                    }
                }
            }
        });

        //意见反馈
        binding.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=572828703";
                    //uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "您还没有安装QQ，请先安装软件", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //关于
        binding.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(requireActivity(), InfoActivity.class);
                startActivity(intent);
            }
        });

        //设置
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SeetingActivity.class);

                startActivity(intent);
            }
        });


        return view;
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

            Constant.money = (uidBean.getMoney() * 0.01);//钱包

            Constant.aiyubi = uidBean.getAmount();//爱语币

            Constant.goldCoin = uidBean.getGoldCoin();//金币

            Constant.jifen = Integer.parseInt(uidBean.getCredits());//积分

            Constant.phone = uidBean.getMobile();//手机号

            Log.d("wang147852", "uidLogin: " + uidBean.getGoldCoin());

            Constant.userimgUrl = "https://apps.iyuba.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.uid + "&size=big";

            Constant.username = uidBean.getUsername();

            //会员有效期=========
            long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
            //要转成后的时间的格式
            android.icu.text.SimpleDateFormat simpleDateFormat = null;
            simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
            // 时间戳转换成时间
            String vipDate = null;
            vipDate = simpleDateFormat.format(new Date(timeStamp));
            Constant.vipDate = vipDate;//vip时间
            //=============================


            if (!Constant.uid.equals("0")) {
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

                uidloginExchange();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void uidloginExchange() {

        if (Constant.username == null) {
            binding.username.setText("");
            binding.sign.setVisibility(View.VISIBLE);
            binding.vipbiaozhi.setVisibility(View.INVISIBLE);

            binding.moneyNum.setText("0");
            binding.goldNum.setText("0");

            binding.quit.setVisibility(View.GONE);

            binding.myPhoto.setImageResource(R.drawable.iyubauserimage);
//            Glide.with(requireActivity()).load(Constant.userimgUrl).into(binding.myPhoto);

            binding.sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            //

//            //爱语币
//            binding.lineiyubi.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent = new Intent(getActivity(), BuyIyubiActivity.class);
//                    startActivity(intent);
//                }
//            });


        } else {

            if (Constant.vipStatus == 0) {
                Glide.with(requireActivity()).load(R.drawable.vipnew2).into(binding.vipbiaozhi);

            } else {
                Glide.with(requireActivity()).load(R.drawable.vipnew).into(binding.vipbiaozhi);
            }
            binding.sign.setVisibility(View.INVISIBLE);
            binding.vipbiaozhi.setVisibility(View.VISIBLE);
            java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
            binding.moneyNum.setText(df.format(Constant.money) + " 元");  //格式化显示
            binding.moneyNum.setVisibility(View.VISIBLE);

            binding.goldNum.setText(Constant.goldCoin + "");
//
            binding.quit.setVisibility(View.VISIBLE);
            binding.username.setText(Constant.username);

            Glide.with(requireActivity()).load(Constant.userimgUrl).into(binding.myPhoto);


            //退出
            binding.quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences userInfo = requireActivity().getSharedPreferences("useruid", MODE_PRIVATE);
                    SharedPreferences.Editor editor = userInfo.edit();
                    editor.remove("useruid");
                    editor.commit();

                    //倍速重置(因为是会员权限)
                    MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", 10).apply();
                    Constant.username = null;
                    Constant.uid = String.valueOf(0);
                    Constant.vipStatus = 0;
                    binding.myPhoto.setImageResource(R.drawable.noavatar_middle);


                    IyuUserManager.getInstance().logout();//视频集成请0
                    uidloginExchange();


                }
            });


        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!Constant.uid.equals("0")){
            String sign = MD5.md5("20001" + Constant.uid + "iyubaV2");
            uidLoginPresenter.uidLogin("android", "json", "20001", Constant.uid, Constant.uid, Constant.AppId, sign);

        }else {
            uidloginExchange();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    // 在需要时调用接口方法
    private void someMethod() {
        if (mListener != null) {

            Constant.for_mycollect = true; //跳转到收藏页面
            mListener.onFragmentInteraction("Some data");
        }
    }
}