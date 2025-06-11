package com.sdiyuba.tedenglish.activity.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.adapter.search.InQuireAdpter;
import com.sdiyuba.tedenglish.adapter.search.pageInfoAdpter;
import com.sdiyuba.tedenglish.model.bean.EvaBean;
import com.sdiyuba.tedenglish.model.bean.EvaCraftBean;
import com.sdiyuba.tedenglish.model.bean.TestRecordBean;
import com.sdiyuba.tedenglish.model.bean.UploadBean;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;
import com.sdiyuba.tedenglish.presenter.home.evaluatingPresenter;
import com.sdiyuba.tedenglish.view.home.evaluatingContract;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.activity.myFragmentDetail.LoginActivity;
import com.sdiyuba.tedenglish.adapter.search.sentenceInfoAdpter;
import com.sdiyuba.tedenglish.databinding.ActivityInQuireBinding;
import com.sdiyuba.tedenglish.model.bean.InQuireBean;
import com.sdiyuba.tedenglish.model.bean.KeyWordBean;
import com.sdiyuba.tedenglish.presenter.home.SearchPresenter;
import com.sdiyuba.tedenglish.view.home.SearchContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/*
搜索结果页
 */
public class InQuireActivity extends AppCompatActivity implements SearchContract.SearchView, evaluatingContract.evaluatingView {

    private ActivityInQuireBinding binding;

    private SearchPresenter searchPresenter;

    private InQuireAdpter inQuireAdpter;

    private pageInfoAdpter pageInfoadpter;

    private sentenceInfoAdpter evaluatingAdpter;


    private MediaRecorder mRecorder;

    File mFileName = null;

    private PopupWindow popupWindow;

    private evaluatingContract.evaluatingPresenter evaluatingpresenter;


    String url;
    boolean isPlaying = true;

    MediaPlayer mediaPlayer = new MediaPlayer();


    private boolean isLuYin = false; //检测是否正在录音  和播放句子得录音  不能试听
    private MediaPlayer oriMediaPlayer = new MediaPlayer(); //例句播放

    private boolean isprepar = false;

    private int sum = 1; //第一次进入   显示第一条得按钮

    private Handler handler = new Handler();
    private int RESULT_CODE_STARTAUDIO = 1;

    MediaPlayer mediaPlayerEva = new MediaPlayer();

    String audioPath = null;

    String sentenceSound;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (evaluatingAdpter == null) {
                Log.d("fang01230", "Kong");
                return;
            }
            if (oriMediaPlayer != null && oriMediaPlayer.isPlaying()) {
                Log.d("fang01230", "暂停");
                oriMediaPlayer.pause();// 到时间后暂停播放
                isLuYin = false;
            }
            evaluatingAdpter.setIsplay(false);
            evaluatingAdpter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityInQuireBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        searchPresenter = new SearchPresenter();
        searchPresenter.attchView(this);

        evaluatingpresenter = new evaluatingPresenter();
        evaluatingpresenter.attchView(this);
//
        //状态栏变色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.InQuireRv.setLayoutManager(linearLayoutManager);


        //返回
        binding.tbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //关键词
        Bundle bundle = getIntent().getExtras();
        String inquire = bundle.getString("countss");


        if (inquire != null && inquire.equals("1")) {
            String words = bundle.getString("words");
            binding.searchLlWord.setVisibility(View.GONE);
            binding.pageTitle.setVisibility(View.GONE);
            searchPresenter.getInQuire(Constant.EvaType, words, Constant.uid, Constant.AppId);

        } else {
            //自己输入
            Intent intent = getIntent();
            Bundle bundleExtra = intent.getBundleExtra("bundle");
            String words = bundleExtra.getString("word");


            if (words != null) {
                binding.searchLlWord.setVisibility(View.VISIBLE);

                searchPresenter.getWordQuery("json", 1, 3, 0, "0", words, Constant.uid, Constant.AppId);
            }

        }


    }


    @Override
    public void getKeyWord(KeyWordBean keyWordBean) {


    }

    @Override
    public void getInQuire(InQuireBean inQuireBean) {


        //点击关键词获取到的接口数据


        List<InQuireBean.DataDTO> list = inQuireBean.getData();

        if (list.size() != 0) {
            binding.pageTitle.setVisibility(View.VISIBLE);
        }
        inQuireAdpter = new InQuireAdpter(list, this);
        binding.InQuireRv.setAdapter(inQuireAdpter);

        binding.sentenceLine.setVisibility(View.GONE);
        binding.morePage.setVisibility(View.INVISIBLE);

    }

    @Override
    public void getWordQuery(WordQueryBean wordQueryBean) {


        if (wordQueryBean.getPhEn().equals("")) {
            Toast.makeText(this, "抱歉,没有搜索到该词的相关数据", Toast.LENGTH_SHORT).show();
            binding.pageTitle.setText("抱歉,没有搜索到该词的相关数据");
            binding.morePage.setVisibility(View.INVISIBLE);
            binding.sentenceLine.setVisibility(View.INVISIBLE);
            binding.searchLlWord.setVisibility(View.GONE);
        } else {

            binding.searchTvWord.setText(wordQueryBean.getWord());


            binding.phSound.setVisibility(View.VISIBLE);
            //URLDecoder.decode(url, "UTF-8");
            try {
                binding.yingText.setText("英 : [ " + URLDecoder.decode(wordQueryBean.getPhEn(), "UTF-8") + " ]");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            try {
                binding.meiText.setText("美 : [ " + URLDecoder.decode(wordQueryBean.getPhAm(), "UTF-8") + " ]");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            url = wordQueryBean.getPhAmMp3();
            if (url.length() > 5) {
                binding.yingSound.setImageResource(R.drawable.megaphone);
                binding.meiSound.setImageResource(R.drawable.megaphone);
            } else {
                binding.yingSound.setVisibility(View.INVISIBLE);
                binding.meiSound.setImageResource(View.INVISIBLE);
            }
            binding.searchTvDef.setText(wordQueryBean.getDef());
            binding.yingSound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    url = wordQueryBean.getPhEnMp3();

                    mediaPlayer = MediaPlayer.create(InQuireActivity.this, Uri.parse(url));

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // 在这里处理音频播放完成的逻辑
                            // 比如播放下一首歌曲或者显示提示信息

                            isPlaying = true;
                        }
                    });

                    if (isPlaying) {
                        mediaPlayer.start();
                        isPlaying = false;
                    }

                }
            });


            binding.meiSound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url = wordQueryBean.getPhAmMp3();

                    mediaPlayer = MediaPlayer.create(InQuireActivity.this, Uri.parse(url));


                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // 在这里处理音频播放完成的逻辑
                            // 比如播放下一首歌曲或者显示提示信息

                            isPlaying = true;
                        }
                    });

                    if (isPlaying) {
                        mediaPlayer.start();
                        isPlaying = false;
                    }
                }
            });

            //句子
            List<WordQueryBean.TextDataDTO> list = wordQueryBean.getTextData();
            //文章
            List<WordQueryBean.TitleDataDTO> lists = wordQueryBean.getTitleData();

            binding.pageLine.setVisibility(View.VISIBLE);
            binding.sentenceLine.setVisibility(View.VISIBLE);

            //文章
            Log.d("fang01456", wordQueryBean.getTitleToal() + "");

            if (lists!=null){
                if (wordQueryBean.getTitleToal() == 0) {
                    binding.pageLine.setVisibility(View.GONE);
                } else {
                    pageInfo(lists);
                }

            }


            if (list!=null){
                if (wordQueryBean.getTextToal() == 0) {
                    binding.sentenceLine.setVisibility(View.GONE);
                } else {
                    sentenceInfo(list);
                }
            }

            //句子


        }


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

    public void pageInfo(List<WordQueryBean.TitleDataDTO> lists) {

        Log.d("fang01456", lists.size() + "*******");
        if (lists.size() == 0) {
            binding.pageLine.setVisibility(View.GONE);
        } else {
            binding.pageTitle.setVisibility(View.VISIBLE);


            pageInfoadpter = new pageInfoAdpter(lists, this);

            binding.InQuireRv.setAdapter(pageInfoadpter);
        }


        binding.morePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InQuireActivity.this, pageDetailActivity.class));
            }
        });


    }

    public void sentenceInfo(List<WordQueryBean.TextDataDTO> lists) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.sentenceRv.setLayoutManager(linearLayoutManager);


        if (lists.size() == 0) {
            binding.sentenceLine.setVisibility(View.GONE);
        } else {

            evaluatingAdpter = new sentenceInfoAdpter(lists, this);

            binding.sentenceRv.setAdapter(evaluatingAdpter);
        }

        //第一句的按钮显示出来
        if (sum == 1) {
            evaluatingAdpter.setHide(true);
            evaluatingAdpter.setShow(false);
            evaluatingAdpter.setChoosePosition(0);
            evaluatingAdpter.notifyDataSetChanged();
            sum = 2;
        }


        //按钮监听回调
        evaluatingAdpter.setCallBack(new sentenceInfoAdpter.CallBack() {

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

//                    if (runnable != null) {
//                        handler.removeCallbacks(runnable);
//                    }
//                    oriMediaPlayer.pause();
//                    handler.post(runnable);
//                    isLuYin = false;

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


                //获取点击的数组返回值        这个bean不清楚  是eva得还是original得
                WordQueryBean.TextDataDTO news = evaluatingAdpter.getItem(position);


                if (evaluatingAdpter != null && !evaluatingAdpter.isIsplay()) {

                    sentenceSound = news.getSoundText();

                    oriMediaPlayer = new MediaPlayer();

                    try {
                        // 设置音频文件的路径（这里假设音频文件在res/raw目录下）
                        oriMediaPlayer.setDataSource(InQuireActivity.this, Uri.parse(sentenceSound));

                        // 准备 MediaPlayer
                        oriMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(InQuireActivity.this, "无法播放音频文件", Toast.LENGTH_SHORT).show();
                    }

                    // 开始播放

                    oriMediaPlayer.start();


                    oriMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            oriMediaPlayer.pause();// 到时间后暂停播放
                            isLuYin = false;

                            evaluatingAdpter.setIsplay(false);
                            evaluatingAdpter.notifyDataSetChanged();

                        }
                    });
                    isLuYin = true;
                    //播放段落
                    evaluatingAdpter.setIsplay(true);
                    evaluatingAdpter.setChoosePosition(position);
                    evaluatingAdpter.notifyDataSetChanged();

//
                } else {

                    if (runnable != null) {
                        handler.removeCallbacks(runnable);
                    }
                    oriMediaPlayer.pause();

                    handler.post(runnable);
                    isLuYin = false;
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
                        checkSelfPermission(InQuireActivity.this, android.Manifest.permission.RECORD_AUDIO)) {

                    //判断是否登录     登陆后才可以测评        最外层
                    if ((Integer.parseInt(Constant.uid) == 0)) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(InQuireActivity.this)
                                .setTitle("登录后才可评测")
                                .setMessage("是否去登录?")
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

                                        Intent intent = new Intent(InQuireActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(InQuireActivity.this, "您还未登录", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        alertDialog2.show();

                    } else {


                        WordQueryBean.TextDataDTO news = evaluatingAdpter.getItem(position);  //用bean里面的数据


                        if (!evaluatingAdpter.isEva()) {
                            //判断是否满足评测条件

//
                            if (Constant.vipStatus != 0 || Constant.Eva_Sum < 3 || Constant.spannableString[position] != null) {  //!=

                                isLuYin = true;  //录音中

                                Constant.Eva_Sum++;
                                evaluatingAdpter.setEva(true);

                                evaluatingAdpter.setChoosePosition(position);
                                evaluatingAdpter.notifyDataSetChanged();

                                mRecorder = new MediaRecorder();
                                //设置录入音频源

                                mFileName = new File(InQuireActivity.this.getExternalCacheDir().getAbsolutePath() + "/audio_test.amr");
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
                                AlertDialog alertDialog2 = new AlertDialog.Builder(InQuireActivity.this)
                                        .setTitle("系统信息")
                                        .setMessage("非会员用户每篇文章最多评测三句，是否去开通会员?")
                                        .setPositiveButton("去开通", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

//                                                    Intent intent = new Intent(requireActivity(), VipPersonActivity.class);
//
//                                                    startActivity(intent);
                                            }
                                        })

                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(InQuireActivity.this, "非会员用户每篇文章最多评测三句", Toast.LENGTH_SHORT).show();
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
                            mRecorder.stop();
                            MediaType type = MediaType.parse("application/octet-stream");
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

                            evaluatingpresenter.getEvaluating(requestBody);


                            //评测遮蔽层
                            View popupView = getLayoutInflater().inflate(R.layout.pop, null);
//                                popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
//                                popupWindow.showAsDropDown(popupView, popupView.getWidth(), popupView.getHeight()); //相对某个控件，有偏移
//
//
//                                WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
//                                lp.alpha = 0.5f;
//                                requireActivity().getWindow().setAttributes(lp);

                            //配置高的手机遮罩不过来
                            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                            popupWindow.showAtLocation(binding.inquireLine, Gravity.BOTTOM, 0, 0);

                            Toast.makeText(InQuireActivity.this, "正在评测中", Toast.LENGTH_SHORT).show();


                        }


                    }

                } else {
                    //提示用户开户权限音频

                    AlertDialog alertDialog2 = new AlertDialog.Builder(InQuireActivity.this)
                            .setTitle("系统信息")
                            .setMessage("录音权限将用于上传用户录音，语音评测打分，是否同意开启权限?")
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                    String[] perms = {"android.permission.RECORD_AUDIO"};
                                    ActivityCompat.requestPermissions(InQuireActivity.this, perms, RESULT_CODE_STARTAUDIO);

                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(InQuireActivity.this, "权限未开启,可自行前往权限设置中打开", Toast.LENGTH_SHORT).show();
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

                if (!evaluatingAdpter.isEvaPlay()) {
                    evaluatingAdpter.setEvaPlay(true);
                    evaluatingAdpter.setChoosePosition(position);
                    evaluatingAdpter.notifyDataSetChanged();


                    if (Constant.audioURL[position] != null) {


                        String audio = "http://userspeech.iyuba.cn/voa/" + Constant.audioURL[position];


                        mediaPlayerEva = MediaPlayer.create(InQuireActivity.this, Uri.parse(audio));
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

            //上传排行榜
            @Override
            public void clickUpload(int position) {


                WordQueryBean.TextDataDTO news = evaluatingAdpter.getItem(position);  //用bean里面的数据
                evaluatingpresenter.uploadList("android", "json", 60003, Constant.EvaType, Constant.uid, Constant.username, Constant.orivoaid, Integer.parseInt(news.getIdIndex()), Integer.parseInt(news.getParaId()), Constant.evaScore[position], 2, Constant.audioURL[position], 1, Constant.AppId);
            }


        });

        binding.moreSentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(InQuireActivity.this,sentenceDetailActivity.class));
            }
        });
    }


    @Override
    public void uploadList(UploadBean uploadBean) {

        String result = uploadBean.getResultCode();

        if (result.equals("501")) {
            Toast.makeText(InQuireActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
            if (Integer.parseInt(uploadBean.getReward()) > 0) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(InQuireActivity.this)
                        .setTitle("提示消息")
                        .setMessage("本次学习获得" + (Double.parseDouble(uploadBean.getReward()) / 100) + "元红包奖励，已自动存入您的钱包账户。" + "\n" + "红包可在[爱语吧]微信公众号提现。继续学习领取更多奖励吧!")
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
            Toast.makeText(InQuireActivity.this, "上传失败,请重新评测后重试", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void getEvaluating(EvaBean evaBean) {


        List<EvaBean.DataDTO.WordsDTO> list = evaBean.getData().getWords();  //返回单词变色
        String url = evaBean.getData().getUrl();

        evaluatingAdpter.setAudioUrl(url);
        audioPath = evaBean.getData().getUrl();

        String result = evaBean.getResult();

        isLuYin = false; //录音结束

        if (result.equals("1")) {
            Toast.makeText(InQuireActivity.this, "评测成功", Toast.LENGTH_SHORT).show();


//            Log.d("liu",evaBean.getData().getTotalScore());
//            把背景还原
            popupWindow.dismiss();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 1.0f;
            getWindow().setAttributes(lp);


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
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 1.0f;
            getWindow().setAttributes(lp);

            Toast.makeText(InQuireActivity.this, "评测失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getTestRecord(TestRecordBean testRecordBean) {

    }

    @Override
    public void getEvaCraft(EvaCraftBean evaCraftBean) {

    }


    public void onDestroy() {

        super.onDestroy();

        Constant.spannableString = new SpannableString[100];

        if (runnable != null) {
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;  //移除子进程  解决播放评测的音频突然点返回闪退的问题
        }

        if (mediaPlayer != null) {

            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (oriMediaPlayer != null) {

            oriMediaPlayer.release();
            oriMediaPlayer = null;
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


}