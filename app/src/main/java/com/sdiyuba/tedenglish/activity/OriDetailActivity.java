package com.sdiyuba.tedenglish.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.mob.MobSDK;
import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.MyApplication;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.BuyActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.LoginActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.PersonalInformationActivity;
import com.sdiyuba.tedenglish.adapter.oriAdapter;
import com.sdiyuba.tedenglish.model.bean.AcquireWordBean;
import com.sdiyuba.tedenglish.model.bean.AdEntryBean;
import com.sdiyuba.tedenglish.model.bean.CollectBean;
import com.sdiyuba.tedenglish.model.bean.ReadBean;
import com.sdiyuba.tedenglish.model.bean.RewardsBean;
import com.sdiyuba.tedenglish.model.bean.StudyRecordByTestModeBean;
import com.sdiyuba.tedenglish.model.bean.exportpdfBean;
import com.sdiyuba.tedenglish.model.bean.joinWordBean;
import com.sdiyuba.tedenglish.model.bean.oriPagesBean;
import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;
import com.sdiyuba.tedenglish.presenter.home.AcquireWordPresenter;
import com.sdiyuba.tedenglish.presenter.home.MyCollectPresenter;
import com.sdiyuba.tedenglish.presenter.home.ReadPresenter;
import com.sdiyuba.tedenglish.presenter.home.RequestAdPresenter;
import com.sdiyuba.tedenglish.presenter.home.UpStudyRecordPresenter;
import com.sdiyuba.tedenglish.presenter.home.joinWordPresenter;
import com.sdiyuba.tedenglish.presenter.home.likePresenter;
import com.sdiyuba.tedenglish.presenter.home.oriPresenter;
import com.sdiyuba.tedenglish.presenter.home.rewardsPresenter;
import com.sdiyuba.tedenglish.sql.AppDataBase;
import com.sdiyuba.tedenglish.sql.TestDao;
import com.sdiyuba.tedenglish.sql.TestRoom;
import com.sdiyuba.tedenglish.util.CountDownProgressBar;
import com.sdiyuba.tedenglish.util.DateUtil;
import com.sdiyuba.tedenglish.util.MD5;
import com.sdiyuba.tedenglish.util.Rxtimer;
import com.sdiyuba.tedenglish.util.TimeConvert;
import com.sdiyuba.tedenglish.util.ToastUtil;
import com.sdiyuba.tedenglish.view.home.AcquireWordContract;
import com.sdiyuba.tedenglish.view.home.LikeContract;
import com.sdiyuba.tedenglish.view.home.MyCollectContract;
import com.sdiyuba.tedenglish.view.home.ReadContract;
import com.sdiyuba.tedenglish.view.home.RequestAdContract;
import com.sdiyuba.tedenglish.view.home.UpStudyRecordContract;
import com.sdiyuba.tedenglish.view.home.joinWordContract;
import com.sdiyuba.tedenglish.view.home.oriContract;
import com.sdiyuba.tedenglish.view.home.rewardsContract;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.iyuba.module.toolbox.DensityUtil;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.ActivityOriDetailBinding;

import com.yd.saas.base.interfaces.AdViewBannerListener;
import com.yd.saas.config.exception.YdError;
import com.yd.saas.ydsdk.YdBanner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class OriDetailActivity extends AppCompatActivity implements UpStudyRecordContract.UpStudyRecordView, oriContract.oriView, rewardsContract.RewardsView, ReadContract.ReadView, LikeContract.likeView, MyCollectContract.MycollectView, RequestAdContract.RequestAdView, AdViewBannerListener, AcquireWordContract.AcquireWordView, joinWordContract.joinWordView {

    private com.sdiyuba.tedenglish.presenter.home.oriPresenter oriPresenter;

    private ActivityOriDetailBinding binding;

    private boolean isFirst = true;
    private com.sdiyuba.tedenglish.adapter.oriAdapter oriAdapter;

    private AcquireWordPresenter acquireWordPresenter;


    private PopupWindow popupWindowWord;


    private ImageView wordAudio;
    private TextView wordDetail;
    private TextView wordPron;
    private TextView wordMeaning;

    private TextView wordBook;

    LinearLayout back;

    private MediaPlayer wordMediaPlayer;
    boolean isEnd = false;//监测是否播放到最后

    private com.sdiyuba.tedenglish.presenter.home.rewardsPresenter rewardsPresenter;


    boolean isExoOk = false; //判断音频是否加载完成
    private boolean isplay = true;

    private int startTime = 0;


    int durTime = 30000;

    private boolean isPlaying = true;  //判断是否退出页面  true代表还在页面内

    private boolean isRewardEnd = false; //倒计时完成  点击领取奖励

    private int speed;

    private ReadPresenter readPresenter;

    private boolean islike = true; //判断是否收藏过

    private boolean isVideo = false; //视频模式还是图片模式

    private PopupWindow TextSizepopupWindowWord;

    private likePresenter likepresenter;

    private MyCollectPresenter myCollectPresenter;

    private RequestAdPresenter requestAdPresenter;


    private ImageView textsizebig, textsizemiddle, textsizesmall;

    private joinWordPresenter joinWordpresenter;

    int textsize;

    FrameLayout co_fl_ad;

    int srid;
    int wordCount = 0;

    private boolean isFirstMoveText = true;
    private String adkey = "0593"; //csj  0087

    private boolean isAdFirst = true; //再执行一次广告请求
    private String ad_title = "ads4";
    private boolean isSoundShow = false;


    private Date date1, date2;

    private float dx,dy;
    Handler handler = new Handler(Looper.getMainLooper());

    private boolean isDownload = false; //此片文章是否下载过

    String startTimess;

    String  startTime_study;

    String ip = "127.0.0.1";

    private String checkEnd = "0";

    String endTimess;


    private static final String PREFS_NAME = "PageVisitPrefs";
    private static final String KEY_LAST_VISIT_DATE = "lastVisitDate";
    private static final String KEY_TOTAL_TIME_TODAY = "totalTimeToday";

    private long Gold_startTime;

    private long Gold_totalTimeToday;

    private boolean isGoldTime = false; //点击播放后才开始计时
    List<TestRoom> list00;

    private boolean check = true;  //完成阅读提交时判断是否是第一次,只有第一次有钱


    private ExoPlayer exoPlayer;

    StyledPlayerView styledPlayerView;

    private UpStudyRecordPresenter upStudyRecordPresenter;

    private PopupWindow SharepopupWindow;


    //    private MediaPlayer mediaPlayer = new MediaPlayer();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOriDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        styledPlayerView = new StyledPlayerView(this);
        exoPlayer = new ExoPlayer.Builder(this).build();

        oriPresenter = new oriPresenter();
        oriPresenter.attchView(this);

        rewardsPresenter = new rewardsPresenter();
        rewardsPresenter.attchView(this);

        likepresenter = new likePresenter();
        likepresenter.attchView(this);


        myCollectPresenter = new MyCollectPresenter();
        myCollectPresenter.attchView(this);

        requestAdPresenter = new RequestAdPresenter();
        requestAdPresenter.attchView(this);

        readPresenter = new ReadPresenter();
        readPresenter.attchView(this);


        acquireWordPresenter = new AcquireWordPresenter();
        acquireWordPresenter.attchView(this);

        joinWordpresenter = new joinWordPresenter();
        joinWordpresenter.attchView(this);

        upStudyRecordPresenter = new UpStudyRecordPresenter();
        upStudyRecordPresenter.attchView(this);

        new Thread() {
            @Override
            public void run() {


                AppDataBase db = Room.databaseBuilder(OriDetailActivity.this, AppDataBase.class, "testRoom").fallbackToDestructiveMigration().build();
                TestDao testDao = db.testDao();
                TestRoom testRoom = new TestRoom();

                list00 = testDao.getAll();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 在主线程上运行代码

                        if (list00 != null) {
                            for (int i = 0; i < list00.size(); i++) {
                                if ((Constant.orivoaid == Integer.parseInt(list00.get(i).voaid)) && Constant.uid.equals(list00.get(i).uid)) {
                                    isDownload = true;

                                }
                            }
                        }
                    }
                });


            }
        }.start();

        ////设置状态栏文字颜色及图标为深色
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date1 = new Date(System.currentTimeMillis());
        startTimess = formatter.format(date1);
        startTime_study = (formatter.format(date1));


        //广告
        co_fl_ad = binding.coFlAd.findViewById(R.id.co_fl_ad);


        if (Constant.vipStatus == 0 ) {

            requestAdPresenter.getAdEntryAll(Constant.AdAppID + "", 4, Constant.uid);
        }


        oriPresenter.getOriPages("iphone", "json", Constant.orivoaid);

        oriPresenter.getOriSentences("json", Constant.orivoaid);


        //获取收藏列表   type传的news,添加收藏得也是, 评测处传的headline

        if (Constant.username != null) {

            String sign = MD5.md5("iyuba" + Constant.uid + Constant.EvaType + Constant.AppId + DateUtil.getDays());

            myCollectPresenter.getCollect(Constant.uid, Constant.EvaType, Constant.AppId, 0, "json", sign);
        }


        //获取设置的评测倍速
        speed = MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).getInt("speed", 10);

        if (speed == 10) {

            binding.speedText.setText("1.0x");


        } else if (speed == 12) {
            binding.speedText.setText("1.2x");


        } else if (speed == 14) {
            binding.speedText.setText("1.4x");

        } else if (speed == 16) {
            binding.speedText.setText("1.6x");


        } else if (speed == 18) {
            binding.speedText.setText("1.8x");

        } else if (speed == 20) {

            binding.speedText.setText("2.0x");


        } else if (speed == 22) {
            binding.speedText.setText("0.4x");


        } else if (speed == 6) {
            binding.speedText.setText("0.6x");


        } else if (speed == 8) {
            binding.speedText.setText("0.8x");


        }


        //大标题
        binding.oriTitleEN.setText(Constant.AllTitleEn);
        Glide.with(this).load(Constant.oriimage).into(binding.oriImage);
        binding.oriImage.setScaleType(ImageView.ScaleType.CENTER_CROP);//拉伸图片


        //可拖动的进度
        binding.huadong.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dx = v.getX() - event.getRawX();
                        dy = v.getY() - event.getRawY();
                        return  true;
                    case MotionEvent.ACTION_MOVE:
                        v.animate()
                                .x(event.getRawX()+dx)
                                .y(event.getRawY()+dy)
                                .setDuration(0)
                                .start();
                        return  true;
                    default:
                        return  false;

                }

            }
        });
        //返回
        binding.backTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.Eva_Sum = 0;
                Constant.isPlay = true; //播放状态
                Rxtimer.cancelTimer("OriPlayTag");
                finish();
            }
        });

        //分享
        binding.shareTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.username == null) {
                    startActivity(new Intent(OriDetailActivity.this, LoginActivity.class));
                } else {
                    ShareshowPopwindow();
                }

            }
        });



        //pdf
        binding.pdfTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.username == null) {

//                    if (Constant.mobsign) {
//                        instanttestSign();
//                    } else {
//
//                    }

                    Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {

                    if (Constant.vipStatus == 0) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(OriDetailActivity.this)
                                .setTitle("提示")
                                .setMessage("非会员用户无法使用导出PDF功能，是否开通VIP?")
                                .setPositiveButton("去开通", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(MyApplication.getContext(), BuyActivity.class);

                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .create();
                        alertDialog2.show();

                    } else {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(OriDetailActivity.this)
                                .setTitle("提示")
                                .setMessage("请选择您需要导出的PDF格式")
                                .setPositiveButton("中英双语", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();


                                        likepresenter.getexport(Constant.EvaType, Constant.orivoaid + "", 0);
                                    }
                                })

                                .setNegativeButton("纯英文", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        likepresenter.getexport(Constant.EvaType, Constant.orivoaid + "", 1);
                                    }
                                })
                                .create();
                        alertDialog2.show();

                    }

                }

            }
        });


        //听力
        binding.soundTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSoundShow) {
                    binding.soundTop.setImageResource(R.drawable.erji_top1);
                    binding.soundLine.setVisibility(View.VISIBLE);
                    isSoundShow = false;
                } else {
                    binding.soundTop.setImageResource(R.drawable.erji_top0);
                    binding.soundLine.setVisibility(View.GONE);
                    isSoundShow = true;
                }


            }
        });

        //评测
        binding.mkfTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exchange();
                startActivity(new Intent(OriDetailActivity.this, EvaluatingActivity.class));
            }
        });




        //收藏
        binding.shoucangTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.username == null) {
                    Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
//
                    startActivity(intent);
                } else {
                    if (islike) {

                        likepresenter.getLike("Iyuba", 0, Constant.AppId, Constant.uid, "upt", Constant.orivoaid + "", 0, Constant.EvaType, "json");

                        islike = false;
                    } else {
                        likepresenter.getLike("Iyuba", 0, Constant.AppId, Constant.uid, "del", Constant.orivoaid + "", 0, Constant.EvaType, "json");
                        islike = true;
                    }
                }

            }
        });

        //调节字体大小
        binding.textsizeTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTextSizePop();

            }
        });


        //显示视频切换
        binding.isVideoShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVideo) {
                    binding.isVideoShow.setImageResource(R.drawable.orivideo);
                    binding.exoPlayer.setVisibility(View.GONE);
                    binding.isImage.setVisibility(View.VISIBLE);
                    isVideo = false;

                    Glide.with(OriDetailActivity.this).load(Constant.oriimage).into(binding.oriImage);
                    binding.oriImage.setScaleType(ImageView.ScaleType.CENTER_CROP);//拉伸图片


                } else {
                    binding.isVideoShow.setImageResource(R.drawable.orivideo_true);
                    binding.exoPlayer.setVisibility(View.VISIBLE);
                    binding.isImage.setVisibility(View.GONE);
                    isVideo = true;
                }

            }
        });


        //中英文切换
        binding.qnCnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (oriAdapter != null) {
                    if (!oriAdapter.isChangeEn()) {
                        oriAdapter.setChangeEn(true);  //点击显示中文
                        oriAdapter.notifyDataSetChanged();  //刷新每个item里的内容

                    } else {
                        oriAdapter.setChangeEn(false);  //不显示
                        oriAdapter.notifyDataSetChanged();

                    }
                } else {
                    Toast.makeText(OriDetailActivity.this, "数据加载异常,请退出后重试~", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //下一句  上一句
        binding.downPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oriAdapter != null) {
                    //获取时存在延迟
                    if (oriAdapter.getPositionPlay() == oriAdapter.getItemCount() - 1) {
                        oriSentencesBean.VoatextDTO dataDTO = oriAdapter.getItem(0);

                        exoPlayer.seekTo((int) (dataDTO.getTiming() * 1000));
                    } else {
                        oriSentencesBean.VoatextDTO dataDTO = oriAdapter.getItem(oriAdapter.getPositionPlay() + 1);

                        exoPlayer.seekTo((int) (dataDTO.getTiming() * 1000));

                    }


                } else {
                    Toast.makeText(OriDetailActivity.this, "数据加载异常,请退出后重试~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.upPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oriAdapter != null) {

                    if (!isEnd) {

                        if (oriAdapter.getPositionPlay() != 0) {
                            oriSentencesBean.VoatextDTO dataDTO = oriAdapter.getItem(oriAdapter.getPositionPlay() - 1);

                            exoPlayer.seekTo((int) (dataDTO.getTiming() * 1000));

                        } else {
                            oriSentencesBean.VoatextDTO dataDTO = oriAdapter.getItem(0);

                            exoPlayer.seekTo((int) (dataDTO.getTiming() * 1000));

                        }

                    } else {
                        Toast.makeText(OriDetailActivity.this, "已经是第一句了~", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(OriDetailActivity.this, "数据加载异常,请退出后重试~", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //播放进度条
        binding.originalsSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    exoPlayer.seekTo(progress);
                    Log.d("点击进度显示", "onProgressChanged: --" + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pausePlay();
                Log.d("点击进度显示", "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (seekBar.getProgress()>abEndPosition){
//                    abState = 0;
//                    abStartPosition = 0;
//                    abEndPosition = 0;
//                }


                startPlay();
                Log.d("点击进度显示", "onStopTrackingTouch: ");
            }
        });


        //倍速
        binding.speedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Integer.parseInt(Constant.uid) == 0) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(OriDetailActivity.this)
                            .setTitle("提示")
                            .setMessage("抱歉，您还未登录   是否去登录?")
                            .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
//                                    if (Constant.mobsign) {
//                                        instanttestSign();
//                                    } else {
//
//                                    }

                                    Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MyApplication.getContext(), "您还未登录", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog2.show();


                } else if (Constant.vipStatus == 0) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(OriDetailActivity.this)
                            .setTitle("提示")
                            .setMessage("非会员用户无法使用倍速功能")
                            .setPositiveButton("去开通", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MyApplication.getContext(), BuyActivity.class);

                                    startActivity(intent);
                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog2.show();

                } else {

                    Log.d("wang145236", "onClick: " + "else");
                    if (exoPlayer.isPlaying()) {


                        speed = speed + 2;
                        Log.d("wang145236", "onClick: " + speed);
                        if (speed == 12) {
                            binding.speedText.setText("1.2x");

                            // 保存
                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                        } else if (speed == 14) {
                            binding.speedText.setText("1.4x");


                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                        } else if (speed == 16) {
                            binding.speedText.setText("1.6x");


                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                        } else if (speed == 18) {
                            binding.speedText.setText("1.8x");


                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                        } else if (speed == 20) {
                            binding.speedText.setText("2.0x");


                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                        } else if (speed == 22) {
                            binding.speedText.setText("0.4x");


                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                            speed = 4;//重置speed
                        } else if (speed == 24) {
                            binding.speedText.setText("0.6x");

                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                            speed = 4;//重置speed
                        } else if (speed == 6) {
                            binding.speedText.setText("0.6x");


                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                        } else if (speed == 8) {
                            binding.speedText.setText("0.8x");


                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                        } else if (speed == 10) {
                            binding.speedText.setText("1.0x");


                            MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("speed", speed).apply();

                        }

                    }

                    beisuPlayer();
                }
            }

        });


        //完成阅读
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.username == null) {
                    //这是个按钮  然后我点击判断没登陆就去登录

//                    if (Constant.mobsign) {
//                        sssinstanttestSign();
//                    } else {
//                        Intent intent = new Intent(getActivity(), LoginActivity.class);
//                        startActivity(intent);
//                    }

                    Intent intent = new Intent(OriDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date2 = new Date(System.currentTimeMillis());
                    endTimess = formatter.format(date2);
                    //判断登录后，判断阅读时间后，根据阅读速度判断是否执行上传
                    long time = date2.getTime() - date1.getTime();
                    double min = Double.parseDouble(time + "") / (1000 * 60);
                    long _min = time / 1000 / 60;
                    long _s = time / 1000 - _min * 60;

                    if (wordCount / min >= 600) {

                        Toast.makeText(OriDetailActivity.this, "欲速则不达，你读的太快啦", Toast.LENGTH_SHORT).show();

                    } else {
                        if (isFirst) {
                            int x = (int) (wordCount / min);
                            AlertDialog alertDialog3 = new AlertDialog.Builder(OriDetailActivity.this)
                                    .setTitle("阅读报告")
                                    .setMessage("当前阅读统计:" + "\n" + "文章单词数:" + wordCount + "\n" + "阅读时长:" + _min + "分" + _s + "秒" + "\n" + "阅读速度:" + x + "单词/分钟" + "\n" + "是否确认提交此阅读记录?")
                                    .setPositiveButton("提交", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            Log.d("fang777",Constant.uid+"*"+startTime+"*"+endTime+"*"+Constant.orivoaid+"*"+wordCount+"单词");
                                            readPresenter.getRead("json", Constant.uid, startTimess, endTimess, Constant.NAME, Constant.EvaType, Constant.orivoaid + "", Constant.AppId, "android", "02:00:00:00:00:00", 1, wordCount, 0, "web", 2);

                                        }
                                    })

                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                                Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create();
                            alertDialog3.show();

                            isFirst = false;
                        } else {
                            Toast.makeText(OriDetailActivity.this, "当前文章已经提交过了哦 ~  更换其他文章试试吧", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.OriginalRv.setLayoutManager(linearLayoutManager);


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
    public void getLike(int result, String type, String voaId, String timing) {
        if (result == 1) {

            if (!islike) {
                Toast.makeText(MyApplication.getContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                binding.shoucangTop.setImageResource(R.drawable.shoucang1);

            } else {
                Toast.makeText(MyApplication.getContext(), "取消收藏成功", Toast.LENGTH_SHORT).show();
                binding.shoucangTop.setImageResource(R.drawable.shoucang0);
            }

        } else {
            Toast.makeText(MyApplication.getContext(), "网络异常,请退出后刷新重新", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void getexport(exportpdfBean exportpdfBean) {
        AlertDialog alertDialog2 = new AlertDialog.Builder(OriDetailActivity.this)
                .setTitle("提示")
                .setMessage("链接生成成功，复制链接在任意浏览器打开，亦可使用第三方工具下载该PDF文件" + exportpdfBean.getPath())
                .setPositiveButton("复制", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                        ClipboardManager cm = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
// 将文本内容放到系统剪贴板里。
                        cm.setPrimaryClip(ClipData.newPlainText("text", exportpdfBean.getPath() + ""));//text也可以是"null"
                        if (cm.hasPrimaryClip()) {
                            cm.getPrimaryClip().getItemAt(0).getText();
                        }
//                cm.setText(data.getOrderNo());
                        Toast.makeText(MyApplication.getContext(), "复制成功", Toast.LENGTH_SHORT).show();


                    }
                })

                .create();
        alertDialog2.show();
    }

    @Override
    public void getCollect(CollectBean collectBean) {
        List<CollectBean.DataDTO> list = collectBean.getData();


        String ss = String.valueOf(Constant.orivoaid);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getVoaid().equals(ss)) {
                islike = false;
                break;
            }
        }

        if (!islike) {
            binding.shoucangTop.setImageResource(R.drawable.shoucang1);

        } else {
            binding.shoucangTop.setImageResource(R.drawable.shoucang0);

        }
    }

    @Override
    public void getAdEntryAllComplete(AdEntryBean adEntryBean) {



        AdEntryBean.DataDTO dataDTO = adEntryBean.getData();
        String type = dataDTO.getType();
        if (!dataDTO.getTitle().equals("")){
            ad_title = dataDTO.getTitle();
        }


        if (type.equals(Constant.AD_ADS1) || type.equals(Constant.AD_ADS2) || type.equals(Constant.AD_ADS3)
                || type.equals(Constant.AD_ADS4) || type.equals(Constant.AD_ADS5) || type.equals(Constant.AD_ADS6)){


            if(type.equals(Constant.AD_ADS1)){
                adkey = "0088";
            } else if (type.equals(Constant.AD_ADS2)) {
                //穿山甲
                adkey = "0088";
            }else if (type.equals(Constant.AD_ADS3) ){
                adkey = "0088";
            }else if (type.equals(Constant.AD_ADS4)) {
                //优良回
                adkey = "0227";
            }else if (type.equals(Constant.AD_ADS5) ){
                adkey = "0088";
            }else if (type.equals(Constant.AD_ADS6)) {
                //瑞狮
                adkey = "0594";
            }





            Log.d("fang02365", type+"banner : "+adkey);



            DisplayMetrics displayMetrics = OriDetailActivity.this.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = DensityUtil.dp2px(OriDetailActivity.this, 65);

            YdBanner mBanner = new YdBanner.Builder(OriDetailActivity.this)
                    .setKey(adkey)
                    .setWidth(width)
                    .setHeight(height)
                    .setMaxTimeoutSeconds(5)
                    .setBannerListener(this)
                    .build();


            mBanner.requestBanner();





        }
    }

    @Override
    public void getOriPages(oriPagesBean oriPagesbean) {

        //文章分类
        JSONObject jsonObject = JSON.parseObject(Constant.home_type);

        Log.d("fang0123", "getOriPages: "+jsonObject.getString(oriPagesbean.getData().get(0).getCategory()));
        if (jsonObject.getString(oriPagesbean.getData().get(0).getCategory()).equals("政治")){
            binding.pageType.setVisibility(View.INVISIBLE);
        }else {
            binding.pageType.setText(jsonObject.getString(oriPagesbean.getData().get(0).getCategory()));

        }


        String sound = oriPagesbean.getData().get(0).getVideo();
        Constant.orisound = "http://staticvip.iyuba.cn" + sound;
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(Constant.orisound));

        String mp3 = oriPagesbean.getData().get(0).getSound();
        Constant.ori_MP3 = "http://staticvip.iyuba.cn/sounds/voa" + mp3;



        styledPlayerView = binding.exoPlayer;
        styledPlayerView.setPlayer(exoPlayer);

        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.prepare();

        // 添加监听器
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY) {
                    // 音频加载完成，可以开始播放
                    // 在这里执行你的逻辑
                    isExoOk = true;
                }
            }

        });
//
//        try {
//
//
//
//            exoPlayer.setDataSource("http://staticvip.iyuba.cn/sounds/voa" + sound);
////                    mediaPlayer.prepare();//同步
//            exoPlayer.prepareAsync();//异步加载
//
//            exoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
////                    mediaPlayer.start();
//                    isExoOk = true;
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //播放音频
        binding.pagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oriAdapter != null) {
                    if (isExoOk) {
                        if (isplay) {
                            binding.pagePlay.setImageResource(R.drawable.zanting1);
                            startPlay();
                            binding.cpbCountdown.setVisibility(View.VISIBLE);

                            //奖励倒计时
                            startTime = (int) System.currentTimeMillis();


                            //下载
                           if (Constant.username != null) {

                               if (!isDownload) {

                                   new Thread() {
                                       @Override
                                       public void run() {
                                           //创建一个数据库AppDataBase
                                           AppDataBase db = Room.databaseBuilder(OriDetailActivity.this, AppDataBase.class, "testRoom").fallbackToDestructiveMigration().build();
                                           //数据库可以实现的功能
                                           TestDao testDao = db.testDao();
                                           //需要存放的数据
                                           TestRoom testRoom = new TestRoom();

                                           List<TestRoom> list = testDao.selectUidAndVoaid(Constant.uid,Constant.orivoaid);


                                           Log.d("fang885522", "run: "+list.size());

                                           //没有就添加
                                           if (list.size()  == 0){

                                               new load_Pic()
                                                       .execute(Constant.oriimage);
                                               binding.cpbCountdown.setVisibility(View.VISIBLE);
                                           }else {
                                               isDownload = true;
                                           }

                                       }
                                   }.start();




                               }
                           }

                            //阅读时常开始计时
                            GoldTimeStart();

                            if ((Constant.username != null) && durTime > 0) {
                                binding.cpbCountdown.setDuration(durTime, true, new CountDownProgressBar.OnFinishListener() {
                                    @Override
                                    public void onFinish() {

                                        if (isPlaying) {
                                            ClickAndCollect();
                                        }


//                                    cpb_countdown.getDrawingTime();


                                    }
                                });

                            }

                            //执行进度条语句的关键
//                    handler.sendEmptyMessage(1);
                            isplay = false;

                        } else {
                            binding.pagePlay.setImageResource(R.drawable.bofang1);
//                    videoView.pause();
                            exoPlayer.pause();

                            int endTime = (int) System.currentTimeMillis();

                            int elapsedTime = endTime - startTime;


                            durTime = durTime - elapsedTime;

                            Log.d("fang7787877", "onClick: " + elapsedTime + "//" + durTime);

                            if (!(Constant.username == null)) {
                                binding.cpbCountdown.setDuration(durTime, false, new CountDownProgressBar.OnFinishListener() {
                                    @Override
                                    public void onFinish() {


//                                    cpb_countdown.getDrawingTime();
//                                    Toast.makeText(MyApplication.getContext(), "完成了", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            isplay = true;
                        }
                    } else {
                        Toast.makeText(OriDetailActivity.this, "资源加载中,请稍后重试~", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(OriDetailActivity.this, "资源加载异常,请退出后重试~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getOriSentences(oriSentencesBean oriSentencesbean) {
        List<oriSentencesBean.VoatextDTO> list = oriSentencesbean.getVoatext();

        for (int i = 0; i < list.size(); i++) {
            wordCount = wordCount + splitWord(list.get(i).getSentence()).size();
        }


        //设置时间和阅读数
        binding.oriTime.setText(Constant.pageTime);
        binding.oriReadcount.setText(wordCount + " 词");

        Constant.orilist = list;
        Constant.spannableString = new SpannableString[list.size()];


        oriAdapter = new oriAdapter(MyApplication.getContext(), list);
        binding.OriginalRv.setAdapter(oriAdapter);

//        binding.OriginalRv.addOnScrollListener(new MyScrollListener());
//


        textsize = MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).getInt("textSize", 2);


        if (textsize == 3) {
            textSize(3);
        } else if (textsize == 2) {
            textSize(2);
        } else {
            textSize(1);
        }


        //取词
        oriAdapter.setCallBack(new oriAdapter.CallBack() {
            @Override
            public void clickWord(String s) {
                acquireWordPresenter.getAcquireWord(s, "json");
            }

        });
    }

    @Override
    public void UpStudyRecord(StudyRecordByTestModeBean studyRecordByTestModeBean) {
        if (Integer.parseInt(studyRecordByTestModeBean.getReward()) > 0) {
            AlertDialog alertDialog2 = new AlertDialog.Builder(OriDetailActivity.this)
                    .setTitle("提示消息")
                    .setMessage("本次学习获得" + (Double.parseDouble(studyRecordByTestModeBean.getReward()) / 100) + "元红包奖励，已自动存入您的钱包账户。")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create();
            alertDialog2.show();
        }
    }

    public class MyScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d("MyScrollListener", "onScrollStateChanged: " + newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 当滑动状态变为空闲状态时执行相应操作
                Log.d("MyScrollListener", "RecyclerView is idle");
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Log.d("MyScrollListener", "onScrolled: dx=" + dx + ", dy=" + dy);
        }
    }

    @Override
    public void getRewards(RewardsBean rewardsBean) {


        Log.d("fang789654", rewardsBean.getResult() + rewardsBean.getMessage() + "getRewards: " + "调用" + rewardsBean.getGoldCoins());


        Toast.makeText(OriDetailActivity.this, rewardsBean.getMessage(), Toast.LENGTH_SHORT).show();

        if (srid == 311) {

            if (!isEnd) {
                isRewardEnd = false;
                binding.redbag.setImageResource(R.drawable.redbag);
                startTime = (int) System.currentTimeMillis();
                durTime = 30000;
                if (!(Constant.username == null)) {
                    binding.cpbCountdown.setDuration(durTime, true, new CountDownProgressBar.OnFinishListener() {
                        @Override
                        public void onFinish() {

                            if (isPlaying) {
                                ClickAndCollect();

                            }

                        }


                    });
                }
            } else {
                isRewardEnd = false;
                binding.redbag.setImageResource(R.drawable.redbag);
                binding.cpbCountdown.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onReceived(View view) {
        co_fl_ad.setVisibility(View.VISIBLE);

        co_fl_ad.removeAllViews();
        co_fl_ad.addView(view);
    }

    @Override
    public void onAdExposure() {

    }

    @Override
    public void onAdClick(String s) {

    }

    @Override
    public void onClosed() {
        co_fl_ad.setVisibility(View.GONE);
    }

    @Override
    public void onAdFailed(YdError ydError) {


        if (isAdFirst){

            isAdFirst = false;


            if(ad_title.equals(Constant.AD_ADS1)){
                adkey = "0088";
            } else if (ad_title.equals(Constant.AD_ADS2)) {
                //穿山甲
                adkey = "0088";
            }else if (ad_title.equals(Constant.AD_ADS3) ){
                adkey = "0088";
            }else if (ad_title.equals(Constant.AD_ADS4)) {
                //优良回
                adkey = "0227";
            }else if (ad_title.equals(Constant.AD_ADS5) ){
                adkey = "0088";
            }else if (ad_title.equals(Constant.AD_ADS6)) {
                //瑞狮
                adkey = "0594";
            }

            Log.d("fang014", "banner: "+adkey);



                    DisplayMetrics displayMetrics = OriDetailActivity.this.getResources().getDisplayMetrics();
                    int width = displayMetrics.widthPixels;
                    int height = DensityUtil.dp2px(OriDetailActivity.this, 65);

                    YdBanner mBanner = new YdBanner.Builder(OriDetailActivity.this)
                            .setKey(adkey)
                            .setWidth(width)
                            .setHeight(height)
                            .setMaxTimeoutSeconds(5)
                            .setBannerListener(this)
                            .build();

                    mBanner.requestBanner();




        }

        Log.d("fang02365", ydError.getCode()+"init开屏失败"+ydError.getMsg() + adkey);
    }

    private String showTime(long time) {
        if (time == 0) {
            return "00:00";
        }

        long totalTime = time / 1000;

        long minTime = totalTime / 60;
        long secTime = totalTime % 60;

        String showMin = "";
        String showSec = "";
        if (minTime >= 10) {
            showMin = String.valueOf(minTime);
        } else {
            showMin = "0" + String.valueOf(minTime);
        }
        if (secTime >= 10) {
            showSec = String.valueOf(secTime);
        } else {
            showSec = "0" + String.valueOf(secTime);
        }

        return showMin + ":" + showSec;
    }

    //释放  都加上 防止内存泄露
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (isGoldTime) {
            //结束金币阅读时长计时
            long endTime = SystemClock.elapsedRealtime();
            long timeSpent = endTime - Gold_startTime;

            // 更新今天的总停留时间
            Gold_totalTimeToday += timeSpent;


            // 保存当前日期和累计时间
            String currentDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(KEY_LAST_VISIT_DATE, currentDate);
            editor.putLong(KEY_TOTAL_TIME_TODAY, Gold_totalTimeToday);
            editor.apply();
        }

        Log.d("fang021544", "GoldTimeStart:********* " + Gold_totalTimeToday);
        Constant.Eva_Sum = 0;
        Constant.isPlay = true; //播放状态
        Rxtimer.cancelTimer("OriPlayTag");
        Constant.orilist = null;
        isPlaying = false;

        //上传听力进度
        UpStudy();

        if (exoPlayer != null) {
            exoPlayer.pause();
            exoPlayer.release();
        }


        if (oriAdapter != null) {
            oriAdapter.setPositionPlay(0);//句子变色变为第一句

        }

        if (wordMediaPlayer != null) {

            wordMediaPlayer.release();
        }

        binding = null;
    }

    /**
     * 滑动到指定位置
     *
     * @param rv
     * @param position
     */
//   详情 https://blog.csdn.net/weixin_39961522/article/details/113681362
    private void moveToPosition(RecyclerView rv, int position) {

        if (isFirstMoveText) {
            isFirstMoveText = false;

            int distancePx = dpToPx(600);

            if (isVideo) {

                binding.scrollerFl.scrollBy(0, 100);
            } else {
                binding.scrollerFl.scrollBy(0, distancePx);

            }
        }


        int firstItem = rv.getChildLayoutPosition(rv.getChildAt(0));
        int lastItem = rv.getChildLayoutPosition(rv.getChildAt(rv.getChildCount() - 1));
        if (position < firstItem || position > lastItem) {
            rv.smoothScrollToPosition(position);
        } else {
            int movePosition = position - firstItem;
            int top = rv.getChildAt(movePosition).getTop();
            rv.smoothScrollBy(0, top);
        }
    }

    private void pausePlay() {
        if (exoPlayer != null && exoPlayer.isPlaying()) {
            exoPlayer.pause();
        }

        Rxtimer.cancelTimer("OriPlayTag");
    }


    private void startPlay() {

        //处理 如果未播放点击进度条的情况
        if (!exoPlayer.isPlaying()) {
            binding.pagePlay.setImageResource(R.drawable.zanting1);
            isplay = false;
        }


        if (exoPlayer.getCurrentPosition() >= exoPlayer.getDuration()) {
            exoPlayer.seekTo(0);
        }


        exoPlayer.play();

        isEnd = false;

        int homeSpeed;
        float speed = 1;

        homeSpeed = MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).getInt("speed", 12);
        DecimalFormat df = new DecimalFormat("#0.0");
        if (homeSpeed == 12) {
            speed = Float.parseFloat(df.format(1.2F));//1.2
        } else if (homeSpeed == 14) {
            speed = Float.parseFloat(df.format(1.4F));

        } else if (homeSpeed == 16) {
            speed = Float.parseFloat(df.format(1.6F));

        } else if (homeSpeed == 18) {
            speed = Float.parseFloat(df.format(1.8F));

        } else if (homeSpeed == 20) {
            speed = Float.parseFloat(df.format(2.0F));

        } else if (homeSpeed == 22) {
            speed = Float.parseFloat(df.format(0.4F));

        } else if (homeSpeed == 6) {
            speed = Float.parseFloat(df.format(0.6F));

        } else if (homeSpeed == 8) {
            speed = Float.parseFloat(df.format(0.8F));

        } else if (homeSpeed == 10) {
            speed = Float.parseFloat(df.format(1.0F));

        }
        //系统默认倍速控制   3句
//        PlaybackParams playbackParams = exoPlayer.getPlaybackParams();
//        playbackParams.setSpeed(speed);
//        exoPlayer.setPlaybackParams(playbackParams);

        PlaybackParameters playbackParameters = new PlaybackParameters(speed, 1.0F);
        exoPlayer.setPlaybackParameters(playbackParameters);


        binding.originalsTvPlayTime.setText(showTime(binding.originalsSb.getProgress()));
        binding.originalsTvDuration.setText(showTime(exoPlayer.getDuration()));
        binding.originalsSb.setMax((int) exoPlayer.getDuration());


        Rxtimer.multiTimerInMain("OriPlayTag", 0, 200L, new Rxtimer.RxActionListener() {
            @Override
            public void onAction(long number) {

                if (binding == null) {
                    return;
                }

                if (oriAdapter != null) {
                    long curPlayTime = exoPlayer.getCurrentPosition();

                    binding.originalsSb.setProgress((int) curPlayTime);
                    binding.originalsTvPlayTime.setText(showTime(curPlayTime));
//
//                    exoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            //播放到句子最后
//                            // 在这里添加音频播放结束后的操作
//                            // 例如，处理下一首歌曲或者显示播放完成的提示等
//
//                        }
//                    });


                    if (binding.originalsSb.getProgress() == (exoPlayer.getDuration())) {
                        //播放到句子最后

                        checkEnd = "1";
                        long timestamp = System.currentTimeMillis() / 1000;
                        String sign = MD5.md5(Constant.uid + "iyuba" + timestamp + 0);
                        srid = 312;
                        rewardsPresenter.getRewards(Constant.AppId, Constant.uid, Constant.orivoaid + "", sign, 312, String.valueOf(timestamp), 0);

                        binding.pagePlay.setImageResource(R.drawable.bofang1);
                        exoPlayer.pause();  //暂停播放
                        isplay = true;
                        exoPlayer.seekTo(0);
                        oriAdapter.setPositionPlay(0);//句子变色变为第一句
                        isEnd = true;

                        //上传听力进度
                        UpStudy();

                        if (!(Constant.username == null)) {


                            durTime = 30000;

                            binding.cpbCountdown.setDuration(durTime, false, new CountDownProgressBar.OnFinishListener() {
                                @Override
                                public void onFinish() {

                                    //结束

                                }
                            });

                        }
                    }


                    int s = (int) (binding.originalsSb.getProgress() / 1000);


                    for (int i = 0, size = oriAdapter.getItemCount(); i < size; i++) {

                        oriSentencesBean.VoatextDTO dataDTO = oriAdapter.getItem(i);
//                    Double.parseDouble是把括号里面的内容变成double类型的 ,要变成int 用Integer.parseInt()
                        if (s < Double.parseDouble(dataDTO.getEndTiming() + "") && s > Double.parseDouble(dataDTO.getTiming() + "")) {

                            if (oriAdapter.getPositionPlay() != i) {
                                //句子变色
                                oriAdapter.setPositionPlay(i);  //获取当前位置
                                if (i > 0) {
//                                    moveToPosition(binding.OriginalRv, i-1);

                                    moveToPosition(binding.OriginalRv, i);
                                }

                                oriAdapter.notifyDataSetChanged();
                                break;
                            }

                        }

                    }
                } else {
                    Toast.makeText(OriDetailActivity.this, "数据加载异常,请退出后重试~", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    public void showTextSizePop() {

        View popView = getLayoutInflater().inflate(R.layout.pop_textsize, null);
        TextSizepopupWindowWord = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        TextSizepopupWindowWord.showAtLocation(binding.oriLineDetail, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout lineBig = popView.findViewById(R.id.text_big);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout lineMiddle = popView.findViewById(R.id.text_middle);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout lineSmall = popView.findViewById(R.id.text_small);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout back = popView.findViewById(R.id.textsize_back);
        //获取屏蔽层控件
        textsizebig = popView.findViewById(R.id.text_big_image);

        textsizemiddle = popView.findViewById(R.id.text_middle_image);

        textsizesmall = popView.findViewById(R.id.text_small_image);


        // 获取SharedPreferences对象
        textsize = MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).getInt("textSize", 2);


        if (textsize == 3) {
            textsizebig.setVisibility(View.VISIBLE);
            textsizemiddle.setVisibility(View.INVISIBLE);
            textsizesmall.setVisibility(View.INVISIBLE);
        } else if (textsize == 2) {
            textsizebig.setVisibility(View.INVISIBLE);
            textsizemiddle.setVisibility(View.VISIBLE);
            textsizesmall.setVisibility(View.INVISIBLE);
        } else {
            textsizebig.setVisibility(View.INVISIBLE);
            textsizemiddle.setVisibility(View.INVISIBLE);
            textsizesmall.setVisibility(View.VISIBLE);
        }

        lineBig.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View v) {

                // 获取SharedPreferences对象
                MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("textSize", 3).apply();
                ;

                textsizebig.setVisibility(View.VISIBLE);
                textsizemiddle.setVisibility(View.INVISIBLE);
                textsizesmall.setVisibility(View.INVISIBLE);
                textSize(3);
//                originalfragment.setTextSize(3);
//                originalfragment.textSize();
                dismissTextsize();


            }
        });

        lineMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("textSize", 2).apply();
                ;
                textsizebig.setVisibility(View.INVISIBLE);
                textsizemiddle.setVisibility(View.VISIBLE);
                textsizesmall.setVisibility(View.INVISIBLE);
                textSize(2);
//                originalfragment.setTextSize(2);
//                originalfragment.textSize();
                dismissTextsize();

            }
        });

        lineSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).edit().putInt("textSize", 1).apply();
                ;
                textsizebig.setVisibility(View.INVISIBLE);
                textsizemiddle.setVisibility(View.INVISIBLE);
                textsizesmall.setVisibility(View.VISIBLE);
//                originalfragment.setTextSize(1);
//                originalfragment.textSize();

                textSize(1);

                dismissTextsize();

            }
        });

        //取消
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissTextsize();
            }
        });


    }

    public void dismissTextsize() {
        TextSizepopupWindowWord.dismiss();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    public void textSize(int textSize) {


        if (oriAdapter != null) {
            if (textSize == 1) {
                oriAdapter.setTextSize(1);
                oriAdapter.notifyDataSetChanged();  //刷新每个item里的内容

            } else if (textSize == 2) {

                oriAdapter.setTextSize(2);
                oriAdapter.notifyDataSetChanged();

            } else {
                oriAdapter.setTextSize(3);
                oriAdapter.notifyDataSetChanged();

            }
        } else {
            Toast.makeText(OriDetailActivity.this, "数据加载异常,请退出页面后重试~", Toast.LENGTH_SHORT).show();
        }

    }

    public void beisuPlayer() {

        Log.d("wang145236", "onClick: " + isplay);
        if (!isplay) {


//            if (speed < 2) {
//
//                DecimalFormat df = new DecimalFormat("#0.0");
//                speed = Float.parseFloat(df.format(speed + 0.2F));
//
//
//            } else {
//                DecimalFormat df = new DecimalFormat("#0.0");
//                speed = Float.parseFloat(df.format(0.4F));
//            }

            int homeSpeed;
            float speed = 1;
            //获取
            homeSpeed = MyApplication.getContext().getSharedPreferences("HomeDetail", Context.MODE_PRIVATE).getInt("speed", 12);
            DecimalFormat df = new DecimalFormat("#0.0");
            if (homeSpeed == 12) {
                speed = Float.parseFloat(df.format(1.2F));//1.2
            } else if (homeSpeed == 14) {
                speed = Float.parseFloat(df.format(1.4F));

            } else if (homeSpeed == 16) {
                speed = Float.parseFloat(df.format(1.6F));

            } else if (homeSpeed == 18) {
                speed = Float.parseFloat(df.format(1.8F));

            } else if (homeSpeed == 20) {
                speed = Float.parseFloat(df.format(2.0F));

            } else if (homeSpeed == 22) {
                speed = Float.parseFloat(df.format(0.4F));

            } else if (homeSpeed == 6) {
                speed = Float.parseFloat(df.format(0.6F));

            } else if (homeSpeed == 8) {
                speed = Float.parseFloat(df.format(0.8F));

            } else if (homeSpeed == 10) {
                speed = Float.parseFloat(df.format(1.0F));

            }
            //系统默认倍速控制   3句
//            PlaybackParams playbackParams = mediaPlayer.getPlaybackParams();
//            playbackParams.setSpeed(speed);
//            mediaPlayer.setPlaybackParams(playbackParams);

            PlaybackParameters playbackParameters = new PlaybackParameters(speed, 1.0F);
            exoPlayer.setPlaybackParameters(playbackParameters);

        } else {
            Toast.makeText(OriDetailActivity.this, "抱歉~ 当前还未检测到有音频正在播放,请播放后在尝试调速", Toast.LENGTH_SHORT).show();
        }
    }


    public List<String> splitWord(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return new ArrayList<>();
        }
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("[a-zA-Z-']+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }

    @Override
    public void onResume() {
        super.onResume();


        if (Constant.username == null) {

            binding.redbag.setImageResource(R.drawable.clickbag);

            binding.redbag.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {

                    Log.d("fang01452", "onClick: " + Constant.username);

                    if (Constant.username == null) {

                        Intent intent = new Intent(OriDetailActivity.this, LoginActivity.class);

                        startActivity(intent);

                    }


                }
            });

        } else {

            if (isRewardEnd) {
                binding.redbag.setImageResource(R.drawable.chest);
            } else {
                binding.redbag.setImageResource(R.drawable.redbag);
            }


        }

    }

    public void Exchange() {


        if (exoPlayer != null && exoPlayer.isPlaying()) {
            binding.pagePlay.setImageResource(R.drawable.bofang1);
            isplay = true;
            exoPlayer.pause();


            if (!(Constant.username == null)) {

                int endTime = (int) System.currentTimeMillis();

                int elapsedTime = endTime - startTime;

                durTime = durTime - elapsedTime;


                binding.cpbCountdown.setDuration(durTime, false, new CountDownProgressBar.OnFinishListener() {
                    @Override
                    public void onFinish() {

                    }
                });


            }
        }
    }

    public void ClickAndCollect() {

        isRewardEnd = true;

        binding.redbag.setImageResource(R.drawable.chest);

        binding.redbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isRewardEnd) {
                    if (isPlaying) {


                        long timestamp = System.currentTimeMillis() / 1000;
                        String sign = MD5.md5(Constant.uid + "iyuba" + timestamp + 0);
                        srid = 311;
                        rewardsPresenter.getRewards(Constant.AppId, Constant.uid, Constant.orivoaid + "", sign, 311, String.valueOf(timestamp), 0);
//                                    Toast.makeText(MyApplication.getContext(), "完成了", Toast.LENGTH_SHORT).show();


                    }
                }

            }
        });


    }

    @Override
    public void getRead(ReadBean readBean) {


        if (readBean.getResult().equals("1")) {
            if (check) {
                if (Integer.parseInt(readBean.getReward()) > 0) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(OriDetailActivity.this)
                            .setTitle("提示消息")
                            .setMessage("本次学习获得" + readBean.getReward() + "金币奖励，已自动存入金币收益。")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .create();
                    alertDialog2.show();
                }
                check = false;
            } else {
                Toast.makeText(OriDetailActivity.this, "当前文章已获得过奖励或今日奖励已达上限(每日上制奖励次数10次)", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(OriDetailActivity.this, "当前文章已获得过奖励或今日奖励已达上限(每日上制奖励次数10次)", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void getAcquireWord(AcquireWordBean acquireWordBean) {
        //取词


        if (acquireWordBean.getResult() == 1) {
            //取词成功，生成屏蔽层
            if (popupWindowWord == null) {
                View popView = getLayoutInflater().inflate(R.layout.pop_words, null);
                popupWindowWord = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                popupWindowWord.showAtLocation(binding.oriLineDetail, Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;

                getWindow().setAttributes(lp);

                //获取屏蔽层控件
                back = popView.findViewById(R.id.liubai);

                wordAudio = popView.findViewById(R.id.word_audio);
                wordDetail = popView.findViewById(R.id.word_detail);
                wordPron = popView.findViewById(R.id.word_pron);
                wordMeaning = popView.findViewById(R.id.word_meaning);

                wordBook = popView.findViewById(R.id.word_book);




            }

            wordDetail.setText(acquireWordBean.getKey());
            if (!acquireWordBean.getPron().equals("")) {
                wordPron.setText("[" + acquireWordBean.getPron() + "]");
            }

            wordMeaning.setText(acquireWordBean.getDef());
            //单词播放
            wordAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wordMediaPlayer = MediaPlayer.create(OriDetailActivity.this, Uri.parse(acquireWordBean.getAudio()));

                    if (wordMediaPlayer == null) {
                        Toast.makeText(OriDetailActivity.this, "抱歉,应用暂时没有该单词读音哦~", Toast.LENGTH_SHORT).show();
                    } else {

                        wordMediaPlayer.start();
                    }

                }
            });

            //返回
            back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popupWindowWord.dismiss();
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    getWindow().setAttributes(lp);
                    popupWindowWord = null;
                }
            });

            //监听物理返回键消失popwindow
            popupWindowWord.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    // 在popwindow消失后将背景设置为完全不透明
                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                    layoutParams.alpha = 1.0f;
                    getWindow().setAttributes(layoutParams);
                    popupWindowWord = null;
                }
            });


        } else {
            Toast.makeText(OriDetailActivity.this, "抱歉,应用词库暂时没有该单词哦~", Toast.LENGTH_SHORT).show();

        }


        //加入生词本
        wordBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.username == null) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(OriDetailActivity.this)
                            .setTitle("登录后才可使用此功能")
                            .setMessage("是否去登录?")
                            .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();


                                    if (exoPlayer.isPlaying()) {
                                        binding.pagePlay.setImageResource(R.drawable.bofang1);
                                        exoPlayer.pause();
//                                        EEplay.setImageResource(R.drawable.spbofang0);
                                        isplay = true;
                                    }


                                    Intent intent = new Intent(OriDetailActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(OriDetailActivity.this, "您还未登录", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog2.show();
                } else {

                    joinWordpresenter.joinWord("Iyuba", Constant.uid, "insert", acquireWordBean.getKey(), "json");

                    popupWindowWord.dismiss();
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    getWindow().setAttributes(lp);
                    popupWindowWord = null;

                }

            }
        });

    }

    @Override
    public void joinWord(joinWordBean joinWordBean) {

        //加入生词本


        if (joinWordBean.getResult() == 1) {
            Toast.makeText(OriDetailActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(OriDetailActivity.this, "加入失败,请退出重试", Toast.LENGTH_SHORT).show();
        }

    }

    class load_Pic extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... params) {
            int count;

            File filepath = OriDetailActivity.this.getExternalFilesDir(null);//this.getCacheDir();

            String filepathstr = filepath.toString();

            for (int i = 0; i < params.length; i++) {
                try {


                    URL url = new URL(params[i]);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    int lenghtOfFile = conection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(
                            url.openStream(), 8192);// 1024*8


                    File f = new File(filepathstr, Constant.EvaType + "downloadpic");

                    if (f.exists()) {
                        System.out.println("exist!");
                    } else {
                        System.out.println("not exist!");
                        f.mkdirs();
                    }

                    //创建文件 aaa
                    File f2 = new File(f, Constant.orivoaid + ".jpg");


                    try {
                        f2.createNewFile();//创建文件
                        //file.mkdirs();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // Output stream
                    OutputStream output = new FileOutputStream(f2);

                    byte data[] = new byte[1024];

                    while ((count = input.read(data)) != -1) {
                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            new Thread() {
                @Override
                public void run() {
                    //创建一个数据库AppDataBase
                    AppDataBase db = Room.databaseBuilder(OriDetailActivity.this, AppDataBase.class, "testRoom").fallbackToDestructiveMigration().build();
                    //数据库可以实现的功能
                    TestDao testDao = db.testDao();
                    //需要存放的数据
                    TestRoom testRoom = new TestRoom();

                    testRoom.uid = String.valueOf(Constant.uid);
                    testRoom.voaid = String.valueOf(Constant.orivoaid);
                    testRoom.titleCn = Constant.AllTitleCn;
                    testRoom.titileEn = Constant.AllTitleEn;
                    testRoom.pic = Constant.DOWNLOAD_PIC + Constant.orivoaid + ".jpg";

                    testRoom.storageTime = TimeConvert.GETNOWTIME();

                    testDao.insertAll(testRoom); //添加进去


                }
            }.start();

            isDownload = true;

//            Toast.makeText(OriDetailActivity.this, "下载完成.",
//                    Toast.LENGTH_SHORT).show();

        }

    }


    public void GoldTimeStart() {


        isGoldTime = true;

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String lastVisitDate = prefs.getString(KEY_LAST_VISIT_DATE, "");
        Gold_totalTimeToday = prefs.getLong(KEY_TOTAL_TIME_TODAY, 0);


        String currentDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());

        if (!currentDate.equals(lastVisitDate)) {
            // 如果不是同一天，重置计时器
            Gold_totalTimeToday = 0;
        }

        Log.d("fang021544", "GoldTimeStart: " + Gold_totalTimeToday);
        // 记录当前的开始时间
        Gold_startTime = SystemClock.elapsedRealtime();

        Log.d("fang021544", "GoldTimeStart***************: " + Gold_totalTimeToday);

    }


    private int dpToPx(int dp) {
        Resources resources = getResources();
        return Math.round(dp * (resources.getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void UpStudy() {

        String end = DateUtil.getCurTime();
        String sign = MD5.md5(Constant.uid + startTime_study + end);

        upStudyRecordPresenter.UpStudyRecord("json", Constant.uid, Constant.AppId, startTime_study, end, Constant.EvaType, Constant.orivoaid + "", checkEnd, "ios", "ios", ip, sign, "1", "" + Constant.testNumber, "" + Constant.testWords, 1);

    }


    private void ShareshowPopwindow() {


        View popupView = getLayoutInflater().inflate(R.layout.pop_share, null);
        LinearLayout back = popupView.findViewById(R.id.liubai);

        LinearLayout wx = popupView.findViewById(R.id.wx);
        LinearLayout pyq = popupView.findViewById(R.id.pyq);
        LinearLayout qq = popupView.findViewById(R.id.qq);
        LinearLayout qqZeno = popupView.findViewById(R.id.qqZeno);


        //加载弹出框的布局
//         设置按钮的点击事件
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharepopupWindow.dismiss();
                WindowManager.LayoutParams sp = getWindow().getAttributes();
                sp.alpha = 1.0f;
                getWindow().setAttributes(sp);
            }
        });


        wx.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                downloadPic(ShareSDK.getPlatform(Wechat.NAME).getName());

            }
        });

        qq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                downloadPic(ShareSDK.getPlatform(QQ.NAME).getName());
//                qqShare(mTencent, HomeDetailActivity.this);
            }
        });

        pyq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                downloadPic(ShareSDK.getPlatform(WechatMoments.NAME).getName());

            }
        });
        qqZeno.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                downloadPic(ShareSDK.getPlatform(QZone.NAME).getName());

//                qqZenoShare(mTencent, HomeDetailActivity.this);

            }
        });

        SharepopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        SharepopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);   //解决底部tab显示不全的问题

        //透明
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        this.getWindow().setAttributes(lp);

        SharepopupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        SharepopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        SharepopupWindow.setOutsideTouchable(true);
        //设置可以点击
        SharepopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        SharepopupWindow.setAnimationStyle(R.style.ipopwindow_anim_style);


    }    // 按下android回退物理键 PopipWindow消失解决


    private void downloadPic(String platform) {

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), Constant.orivoaid + ".jpg");
        if (file.exists()) {

            shareToPlatform(platform);
        } else {

            Glide.with(this)
                    .asBitmap()
                    .load(Constant.oriimage)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            // 在此处处理 Bitmap 对象
                            File saveFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), Constant.orivoaid + ".jpg");
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                                bitmap.recycle();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            shareToPlatform(platform);
                        }
                    });


        }
    }

    private void shareToPlatform(String platform) {

        String biaoticn,biaoti;

        if (Constant.AllTitleCn.length() >= 30) {
            biaoticn = Constant.AllTitleCn.substring(0, 30) + "...";
        } else {
            biaoticn = Constant.AllTitleCn;
        }

        if (Constant.AllTitleEn.length() >= 40) {
            biaoti = Constant.AllTitleEn.substring(0, 40) + "...";
        } else {
            biaoti = Constant.AllTitleEn;
        }

        OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        // oks.setTitle("爱语吧TED演讲");
        oks.setTitle(biaoticn);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://m.iyuba.cn/news.html?id=" + Constant.orivoaid + "&type="+Constant.EvaType);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(biaoti);
        // oks.setText(desc);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博

        oks.setImageUrl(Constant.oriimage);
        oks.setImagePath(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + Constant.orivoaid + ".jpg");

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://m.iyuba.cn/news.html?id=" + Constant.orivoaid + "&type="+Constant.EvaType);
        //分享回调
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(cn.sharesdk.framework.Platform platform, int i, HashMap<String, Object> hashMap) {
                // 分享成功回调
                ToastUtil.showToast(OriDetailActivity.this, "分享成功");

                int srid = 0;
                if (Objects.equals(platform.getName(), QQ.NAME) || Objects.equals(platform.getName(), Wechat.NAME)) {

                    srid = 49;
                } else {
                    srid = 19;
                }

                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
                String flag = null;
                try {
                    flag = Base64.encodeToString(
                            URLEncoder.encode(df.format(new Date(System.currentTimeMillis())), "UTF-8").getBytes(), Base64.DEFAULT);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(cn.sharesdk.framework.Platform platform, int i, Throwable throwable) {

                // 分享失败回调
                // 失败的回调，arg:平台对象，arg1:表示当前的动作(9表示分享)，arg2:异常信息
                ToastUtil.showToast(OriDetailActivity.this, "分享失败");
            }

            @Override
            public void onCancel(cn.sharesdk.framework.Platform platform, int i) {
                // 分享取消回调
                ToastUtil.showToast(OriDetailActivity.this, "分享取消");
            }
        });
        // 启动分享
        oks.show(MobSDK.getContext());

    }


}