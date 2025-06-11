package com.sdiyuba.tedenglish.fragment.detailFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.BuyActivity;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.LoginActivity;
import com.sdiyuba.tedenglish.adapter.EvaluatingAdpter;
import com.sdiyuba.tedenglish.model.bean.EvaBean;
import com.sdiyuba.tedenglish.model.bean.EvaCraftBean;
import com.sdiyuba.tedenglish.model.bean.TestRecordBean;
import com.sdiyuba.tedenglish.model.bean.UploadBean;
import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;
import com.sdiyuba.tedenglish.presenter.home.evaluatingPresenter;
import com.sdiyuba.tedenglish.util.Rxtimer;
import com.sdiyuba.tedenglish.view.home.evaluatingContract;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.databinding.FragmentEvaBinding;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, evaluatingContract.evaluatingView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentEvaBinding binding;

    public EvaFragment() {
        // Required empty public constructor
    }
    boolean isGold = true; //判断不能一直点上传领金币

    String audioPath = null;

    private String playTag = "playEva";

    int EvaSum = 0;

    private String evaAudio;

    private double evaScore;

    private boolean isEvaplay = true;  // 试听合成音频的时候 播放,录音,听录音都不能用

    private boolean isLuYin = false; //检测是否正在录音  和播放句子得录音  不能试听
    double EvaScore;

    String endAudio;
    String endUrl;
    private EvaluatingAdpter evaluatingAdpter;
    MediaPlayer mediaPlayerEva = new MediaPlayer();
    private evaluatingContract.evaluatingPresenter evaluatingPresenter;

    private int RESULT_CODE_STARTAUDIO = 1;
    private String paraid[] = new String[300];
    private int sum = 1; //第一次进入   显示第一条得按钮

    private MediaPlayer audioplayer = new MediaPlayer();

    public static EvaFragment newInstance() {
        EvaFragment fragment = new EvaFragment();
        return fragment;
    }

    private MediaRecorder mRecorder;

    File mFileName = null;

    private Handler handler = new Handler();


    private boolean isprepar = false;

    private PopupWindow popupWindow;




    private MediaPlayer mediaPlayer = new MediaPlayer();


    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (evaluatingAdpter == null) {
                return;
            }
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {

                mediaPlayer.pause();// 到时间后暂停播放
                isLuYin = false;
            }
            evaluatingAdpter.setIsplay(false);
            evaluatingAdpter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        evaluatingPresenter = new evaluatingPresenter();
        evaluatingPresenter.attchView(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEvaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMediaPlayer();
        initView();


    }

    //评测得具体实现
    public void EvaMethod() {


        //获取评测记录
        if (Constant.username != null && evaluatingPresenter != null) {
            evaluatingPresenter.getTestRecord(Constant.uid + "", Constant.EvaType, Constant.orivoaid + "");
        }

        binding.hecheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaAudio = "";
                evaScore = 0;
                EvaSum = 0;

                //拼接录音和 分数,第一个特殊处理
                if (Constant.audioURL[0] != null) {
                    evaAudio = Constant.audioURL[0];
                    evaScore = Constant.evaScore[0];
                    EvaSum++;
                }

                boolean isOneAudio = true;
                for (int i = 1; i < Constant.orilist.size(); i++) {
                    if (Constant.audioURL[i] != null) {
                        if (Constant.audioURL[0] == null && isOneAudio) {
                            evaAudio = Constant.audioURL[i];
                            isOneAudio = false;
                        } else {
                            evaAudio = evaAudio + "," + Constant.audioURL[i];
                        }
                        evaScore = evaScore + Constant.evaScore[i];
                        EvaSum++;
                    }
                }
                //如果录音数目>2
                if (EvaSum >= 2) {
                    isGold = true;
                    EvaScore = (evaScore) / (EvaSum);
                    Log.d("fang111", evaAudio + "");
                    evaluatingPresenter.getEvaCraft(evaAudio, Constant.EvaType);
                    binding.hechengScore.setText(((int) EvaScore) + "分");
                    Toast.makeText(getActivity(), "合成成功!", Toast.LENGTH_SHORT).show();
                    binding.hecheng.setVisibility(View.GONE);
                    binding.shiting.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), "至少评测两句才可以合成!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (endUrl == null) {

                    Toast.makeText(requireContext(), "合成后才可以上传", Toast.LENGTH_SHORT).show();

                }
            }
        });


        //第一句的按钮显示出来
        if (sum == 1) {
            evaluatingAdpter.setHide(true);
            evaluatingAdpter.setShow(false);
            evaluatingAdpter.setChoosePosition(0);
            evaluatingAdpter.notifyDataSetChanged();
            sum = 2;
        }

        //点击进度条
        binding.evaSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    audioplayer.seekTo(progress);
                    Log.d("点击进度显示", "onProgressChanged: --" + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                pausePlay();
                Log.d("点击进度显示", "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (audioplayer.isPlaying()){
//                    startPlay();
                    ProgressStartPlay();
                }

                Log.d("点击进度显示", "onStopTrackingTouch: ");
            }
        });


        evaluatingAdpter.setCallBack(new EvaluatingAdpter.CallBack() {

            //英语句子
            @Override
            public void clickText(int position) throws IOException {

                if (evaluatingAdpter.isEva()) {
                    mRecorder.stop();
                    evaluatingAdpter.setEva(false);
                }
                if (evaluatingAdpter.isEvaPlay()) {
                    clickListen(position);
                }
                if (evaluatingAdpter.isIsplay()) {

                    clickPlay(position);
                    evaluatingAdpter.setIsplay(false);

                }
                handler.removeCallbacks(runnable);//每点击一个新的句子要清空计时器!!!!

                evaluatingAdpter.setHide(true);
                evaluatingAdpter.setShow(false);
                evaluatingAdpter.setChoosePosition(position);
                evaluatingAdpter.notifyDataSetChanged();
            }

            @Override
            public void clickPlay(int position) throws IOException {

                //点击这个播放原文音频   后面的录音和播放录音都在这个位置
                if (evaluatingAdpter.isEva()) {
                    clickEva(position);
                }
                if (evaluatingAdpter.isEvaPlay()) {
                    clickListen(position);
                }

                if (isEvaplay) {
                    //获取点击的数组返回值        这个bean不清楚  是eva得还是original得
                    oriSentencesBean.VoatextDTO news = evaluatingAdpter.getItem(position);

                    if (isprepar) {

                        if (evaluatingAdpter != null && !evaluatingAdpter.isIsplay()) {

                            //定时器，写入播放暂停时间
                            double time = news.getEndTiming() - news.getTiming();

                            mediaPlayer.seekTo((int) (news.getTiming() * 1000));


                            mediaPlayer.start();

                            isLuYin = true;
                            //播放段落
                            evaluatingAdpter.setIsplay(true);
                            evaluatingAdpter.setChoosePosition(position);
                            evaluatingAdpter.notifyDataSetChanged();

                            //播放多久然后暂停
                            handler.postDelayed(runnable, (long) time * 1000+500);//加5s延时
                        } else {
                            if (runnable != null) {
                                handler.removeCallbacks(runnable);
                            }
                            mediaPlayer.pause();
                            handler.post(runnable);
                            isLuYin = false;
                        }

                    }

                }


            }


            @Override
            public void clickEva(int position) throws IOException {

                if (evaluatingAdpter.isIsplay()) {
                    clickPlay(position);
                }
                if (evaluatingAdpter.isEvaPlay()) {
                    clickListen(position);
                }

                //录音动态申请
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                        checkSelfPermission(getActivity(), android.Manifest.permission.RECORD_AUDIO)) {

                    //判断是否登录     登陆后才可以测评        最外层
                    if ((Integer.parseInt(Constant.uid) == 0)) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                .setTitle("登录后才可评测")
                                .setMessage("是否去登录")
                                .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

//                                        if (Constant.mobsign){
//                                            instanttestSign();
//                                        }else {
//                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
//                                            startActivity(intent);
//                                        }

                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        alertDialog2.show();

                    } else {


                        if (isEvaplay) {
                            oriSentencesBean.VoatextDTO news = evaluatingAdpter.getItem(position);  //用bean里面的数据


                            if (!evaluatingAdpter.isEva()) {
                                //判断是否满足评测条件  Constant.vipStatus != 0

//
                                if (Constant.vipStatus != 0 || Constant.Eva_Sum < 3 || Constant.spannableString[position] != null) {  //!=

                                    isLuYin = true;  //录音中


                                    evaluatingAdpter.setEva(true);

                                    evaluatingAdpter.setChoosePosition(position);
                                    evaluatingAdpter.notifyDataSetChanged();

                                    mRecorder = new MediaRecorder();
                                    //设置录入音频源

                                    mFileName = new File(requireActivity().getExternalCacheDir().getAbsolutePath() + "/audio_test.amr");
                                    //MIC 麦克风音频源
                                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                    //设置输出格式
                                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
                                    //设置输出文件
                                    mRecorder.setOutputFile(mFileName.getAbsolutePath());
                                    //设置音频编码
                                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
                                    //准备编码
                                    try {
                                        mRecorder.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    //开始录制
                                    mRecorder.start();
                                    mRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                                        @Override
                                        public void onError(MediaRecorder mr, int what, int extra) {
                                            //mRecorder.reset();

                                        }
                                    });

                                } else {
                                    AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                            .setTitle("系统信息")
                                            .setMessage("非会员用户每篇文章最多评测三句，是否去开通会员?")
                                            .setPositiveButton("去开通", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(requireActivity(), BuyActivity.class);

                                                    startActivity(intent);
                                                }
                                            })

                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Toast.makeText(requireActivity(), "非会员用户每篇文章最多评测三句", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .create();
                                    alertDialog2.show();

//                            Toast.makeText(requireActivity(), "非会员用户每篇文章最多评测三句", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                evaluatingAdpter.setEva(false);
                                evaluatingAdpter.setChoosePosition(position);
                                evaluatingAdpter.notifyDataSetChanged();


                                //post 请求
//
                                if (mRecorder != null) {
                                    mRecorder.stop();
                                    mRecorder.release();
                                    mRecorder = null;
                                }

                                MediaType type = MediaType.parse(         "application/octet-stream");
                                RequestBody fileBody = RequestBody.create(type, mFileName);
                                RequestBody requestBody = new MultipartBody.Builder()
                                        .setType(MultipartBody.FORM)
                                        .addFormDataPart("sentence", news.getSentence())
                                        .addFormDataPart("paraId", news.getParaId())
                                        .addFormDataPart("newsId", Constant.orivoaid + "")
                                        .addFormDataPart("IdIndex", news.getIdIndex())
                                        .addFormDataPart("type", Constant.EvaType)
                                        .addFormDataPart("appId", String.valueOf(Constant.AppId))
                                        .addFormDataPart("wordId", "0")
                                        .addFormDataPart("flg", "0")
                                        .addFormDataPart("userId", Constant.uid + "")
                                        .addFormDataPart("file", mFileName.getName(), fileBody)
                                        .build();

//                                Log.d("liu00",news.getSentence()+"/"+ news.getParaId()+"/"+Constant.orivoaid+"/"+news.getIdIndex()+"/"+mFileName.getName());

                                evaluatingPresenter.getEvaluating(requestBody);


                                //评测遮蔽层
                                View popupView = getLayoutInflater().inflate(R.layout.pop, null);

                                //配置高的手机遮罩不过来
                                popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                                popupWindow.showAtLocation(binding.EVaLinearlayout, Gravity.BOTTOM, 0, 0);

                                Toast.makeText(requireContext(), "正在评测中", Toast.LENGTH_SHORT).show();


                            }
                        }

                    }

                } else {
                    //提示用户开户权限音频

                    AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                            .setTitle("系统信息")
                            .setMessage("录音权限将用于上传用户录音，语音评测打分，是否同意开启权限?")
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                    String[] perms = {"android.permission.RECORD_AUDIO"};
                                    ActivityCompat.requestPermissions(getActivity(), perms, RESULT_CODE_STARTAUDIO);

                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(requireActivity(), "权限未开启,可自行前往权限设置中打开", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog2.show();

                }


            }

            @Override
            public void clickListen(int position) throws IOException {

                if (evaluatingAdpter.isIsplay()) {
                    clickPlay(position);
                }
                if (evaluatingAdpter.isEva()) {
                    clickEva(position);
                }

                if (isEvaplay) {
                    if (!evaluatingAdpter.isEvaPlay()) {
                        evaluatingAdpter.setEvaPlay(true);
                        evaluatingAdpter.setChoosePosition(position);
                        evaluatingAdpter.notifyDataSetChanged();


                        if (Constant.audioURL[position] != null) {


                            String audio = "http://userspeech.iyuba.cn/voa/" + Constant.audioURL[position];


                            mediaPlayerEva = MediaPlayer.create(requireActivity(), Uri.parse(audio));
                            mediaPlayerEva.start();
                            isLuYin = true;
                            mediaPlayerEva.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    isLuYin = false;
                                    evaluatingAdpter.setEvaPlay(false);
                                    evaluatingAdpter.setChoosePosition(position);  //当前点击的位置
                                    evaluatingAdpter.notifyDataSetChanged();  //刷新列表数据   每个操作的类里2都要用
                                }
                            });

                        }
                    } else {
                        evaluatingAdpter.setEvaPlay(false);
                        evaluatingAdpter.setChoosePosition(position);
                        evaluatingAdpter.notifyDataSetChanged();

                        if (audioPath != null) {
                            mediaPlayerEva.pause();
                        }
                    }
                }

            }

            //上传排行榜
            @Override
            public void clickUpload(int position) {


                oriSentencesBean.VoatextDTO news = evaluatingAdpter.getItem(position);
                evaluatingPresenter.uploadList("android", "json", 60003, Constant.EvaType, Constant.uid, Constant.username, Constant.orivoaid, Integer.parseInt(news.getIdIndex()), Integer.parseInt(news.getParaId()), Constant.evaScore[position], 2, Constant.audioURL[position], 2, Constant.AppId);
            }


        });

    }

    public void initView() {

        Log.d("wang01245", "initView: ");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.EvaluatingRv.setLayoutManager(linearLayoutManager);


        if (Constant.orilist!=null){
            evaluatingAdpter = new EvaluatingAdpter(Constant.orilist, requireContext());
            binding.EvaluatingRv.setAdapter(evaluatingAdpter);

            for (int i = 0; i < Constant.orilist.size(); i++) {
                paraid[i] = Constant.orilist.get(i).getParaId();
            }
            EvaMethod();
        }else {
            Toast.makeText(requireActivity(),"数据加载异常,请退出后重试~",Toast.LENGTH_SHORT).show();
        }




    }

    public void initMediaPlayer() {
        //文章音频初始化


        if (Constant.orisound!=null){
            try {


                mediaPlayer.setDataSource(Constant.ori_MP3);
                mediaPlayer.prepareAsync();//异步加载

            } catch (IOException e) {

                throw new RuntimeException(e);
            }

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
//                    mediaPlayer.start();
                    isprepar = true;
                }
            });


        }


//                    mediaPlayer.prepare();//同步






    }


    public void onDestroy() {

        super.onDestroy();



        if (runnable != null) {
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;  //移除子进程  解决播放评测的音频突然点返回闪退的问题
        }

//        if (adhandler != null) {
//            adhandler = null; // 将线程引用置为 null
//            bannerhandler = null;
//        }
//
//        isADshow=false;
//
//

        Rxtimer.cancelTimer(playTag);

        if (audioplayer != null) {

            audioplayer.release();
        }

        if (mediaPlayer != null) {

            mediaPlayer.release();
        }

        if (mediaPlayerEva != null) {

            mediaPlayerEva.release();
        }

        try {
            FileInputStream fis = new FileInputStream(audioPath);
            FileChannel channel = fis.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
            }
            byte[] dataByte = byteBuffer.array();
            channel.close(); //这里需要关闭channel，否则就会打印A resource failed to call close
            //return dataByte;
        } catch (Throwable t) {
        }


    }


    @Override
    public void onRefresh() {

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
    public void uploadList(UploadBean uploadBean) {

        String result = uploadBean.getResultCode();

        if (result.equals("501")) {
            Toast.makeText(requireContext(), "上传成功", Toast.LENGTH_SHORT).show();
            if (Integer.parseInt(uploadBean.getReward()) > 0) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                        .setTitle("提示消息")
                        .setMessage("本次学习获得 " + uploadBean.getReward()  + " 金币奖励，已自动存入金币收益。")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog2.show();
            }
        } else {
            Toast.makeText(requireContext(), "上传失败,请重新评测后重试", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getEvaluating(EvaBean evaBean) {

        Constant.Eva_Sum++; //评测次数+1

        List<EvaBean.DataDTO.WordsDTO> list = evaBean.getData().getWords();  //返回单词变色
        String url = evaBean.getData().getUrl();

        evaluatingAdpter.setAudioUrl(url);
        audioPath = evaBean.getData().getUrl();

        String result = evaBean.getResult();

        isLuYin= false; //录音结束
        //合成得显示
        binding.hecheng.setVisibility(View.VISIBLE);
        binding.shiting.setVisibility(View.GONE);

        if (result.equals("1")) {
            Toast.makeText(requireContext(), "评测成功", Toast.LENGTH_SHORT).show();


//            Log.d("liu",evaBean.getData().getTotalScore());
//            把背景还原
            popupWindow.dismiss();
            WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
            lp.alpha = 1.0f;
            requireActivity().getWindow().setAttributes(lp);


            evaluatingAdpter.setEvaList(list);  //返回单词变色
            //Log.d("chen",list+"");
            evaluatingAdpter.setShow(true);
            evaluatingAdpter.setChoosePosition(evaluatingAdpter.getChoosePosition());
//            Log.d("liu",evaluatingAdpter.getChoosePosition()+"");
            Constant.audioURL[evaluatingAdpter.getChoosePosition()] = evaBean.getData().getUrl();//录音数组对应下标
            Constant.evaScore[evaluatingAdpter.getChoosePosition()] = (int) (Double.parseDouble(evaBean.getData().getTotalScore()) * 20);

//            Log.d("liu",Constant.evaScore[evaluatingAdpter.getChoosePosition()]+"");
            evaluatingAdpter.notifyDataSetChanged();


            if (evaluatingAdpter.getChoosePosition() < 0) {
                if (Constant.audioURL[0] == null) {
                    Constant.audioURL[0] = evaBean.getData().getUrl();
                }
            } else {

                if (Constant.audioURL[evaluatingAdpter.getChoosePosition()] == null) {
                    Constant.audioURL[evaluatingAdpter.getChoosePosition()] = evaBean.getData().getUrl();

                }

            }


        } else {

            popupWindow.dismiss();
            WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
            lp.alpha = 1.0f;
            requireActivity().getWindow().setAttributes(lp);

            Toast.makeText(requireContext(), "评测失败", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void getTestRecord(TestRecordBean testRecordBean) {

        //评测记录
        for (int i = 0; i < Constant.orilist.size(); i++) {
            for (int j = 0; j < testRecordBean.getData().size(); j++) {
                if ((Constant.orilist.get(i).getParaId().equals(testRecordBean.getData().get(j).getParaid() + "")) && (Constant.orilist
                        .get(i).getIdIndex().equals(testRecordBean.getData().get(j).getIdindex() + ""))) {
                    Constant.audioURL[i] = testRecordBean.getData().get(j).getUrl();
                    Constant.evaScore[i] = (int) (Double.parseDouble(testRecordBean.getData().get(j).getScore()) * 20);
                    Constant.Eva_Sum++;
                    break;
                }
            }
        }

        //获得评测历史，传入adapter
        evaluatingAdpter.setEvaHistoryList(testRecordBean.getData());


        evaluatingAdpter.notifyDataSetChanged();
    }

    @Override
    public void getEvaCraft(EvaCraftBean evaCraftBean) {


        endUrl = evaCraftBean.getUrl();
        endAudio = "http://userspeech.iyuba.cn/voa/" + endUrl;


//        试听
        binding.shiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLuYin){

                    if (endAudio != null) {

                        if (!audioplayer.isPlaying()){
                            isEvaplay =false;
                            audioplayer = MediaPlayer.create(requireActivity(), Uri.parse(endAudio));

                            ProgressStartPlay();
                        }

                    }else {
                        Toast.makeText(requireActivity(), "音频为空~", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(requireActivity(), "音频正在播放或正在录音评测中,请结束后再试~", Toast.LENGTH_SHORT).show();

                }

            }
        });

//        合成后发布
        binding.fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isGold) {
                    isGold = false;
                    evaluatingPresenter.uploadList("android", "json", 60003, Constant.EvaType, Constant.uid, Constant.username, Constant.orivoaid, 0, 0, (int) EvaScore, 4, endUrl, 2, Constant.AppId);

                }else {
                    Toast.makeText(requireActivity(), "您暂时没有评测新的句子，请评测合成后上传~", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void ProgressStartPlay(){


        if (audioplayer.getCurrentPosition() >= audioplayer.getDuration()) {
            audioplayer.seekTo(0);
        }

        audioplayer.start();
        binding.evaTvPlayTime.setText(showTime(binding.evaSb.getProgress()));
        binding.evaTvDuration.setText(showTime(audioplayer.getDuration()));
        binding.evaSb.setMax((int) audioplayer.getDuration());
        Rxtimer.multiTimerInMain(playTag, 0, 200L, new Rxtimer.RxActionListener() {
            @Override
            public void onAction(long number) {
                if (binding == null) {
                    return;
                }

                long curPlayTime = audioplayer.getCurrentPosition();

//                Log.d("时间进度", "onAction: --" + exoPlayer.getContentPosition());
                binding.evaSb.setProgress((int) curPlayTime);
                binding.evaTvPlayTime.setText(showTime(curPlayTime));

                if (binding.evaSb.getProgress() == (audioplayer.getDuration())) {
                    //播放到句子最后
//                    checkEnd = "1";
//                    binding.pagePlay.setImageResource(R.drawable.bofang1);
                    audioplayer.pause();  //暂停播放

                    audioplayer.seekTo(0);

                    isEvaplay= true;
                }

            }
        });
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

    //切换页面 --暂停音频播放
    public void EvaExchange(){

        //三个图标状态处理
        if (evaluatingAdpter!=null){
            evaluatingAdpter.setIsplay(false);
            evaluatingAdpter.setEva(false);
            evaluatingAdpter.setEvaPlay(false);
            evaluatingAdpter.notifyDataSetChanged();
        }
         isEvaplay = true;  // 试听合成音频的时候 播放,录音,听录音都不能用

         isLuYin = false;





        Rxtimer.cancelTimer(playTag);

        if (audioplayer != null) {

            audioplayer.pause();
        }

        if (mediaPlayer != null) {


            mediaPlayer.pause();
        }

        if (mediaPlayerEva != null) {

            mediaPlayerEva.pause();
        }
    }
}