package com.sdiyuba.tedenglish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.model.bean.EvaBean;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.Constant;

import com.sdiyuba.tedenglish.model.bean.TestRecordBean;
import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvaluatingAdpter extends RecyclerView.Adapter<EvaluatingAdpter.EvaViewHolder> {


    private List<oriSentencesBean.VoatextDTO> datas;

    private List<TestRecordBean.DataDTO> evaHistoryList = new ArrayList<>();

    private List<TestRecordBean.DataDTO> lists =new ArrayList<>();
    private Context context;

    private String index;

    private  String paraId;
    private List<EvaBean.DataDTO.WordsDTO> evaList;     //这个list只在onbindview里面用了 加上getset方法   每个单词

    private String evaData = null;
    private boolean init =false;

    private int choosePosition = 0;
    private boolean isplay = false;

    private boolean isEva = false;

    private boolean isword = false;

    private boolean isEvaPlay = false;

    private boolean isHide = false;

    private boolean isShow = false;

    private boolean isEvass = true;

    private int green = Color.parseColor("#72A638");

    public List<TestRecordBean.DataDTO> getLists() {
        return lists;
    }

    public void setLists(List<TestRecordBean.DataDTO> lists) {
        this.lists = lists;
    }

    public List<TestRecordBean.DataDTO> getEvaHistoryList() {
        return evaHistoryList;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getParaId() {
        return paraId;
    }

    public void setParaId(String paraId) {
        this.paraId = paraId;
    }

    public List<EvaBean.DataDTO.WordsDTO> getEvaList() {
        return evaList;
    }

    public void setEvaList(List<EvaBean.DataDTO.WordsDTO> evaList) {
        this.evaList = evaList;
    }

    public String getEvaData() {
        return evaData;
    }

    public void setEvaData(String evaData) {
        this.evaData = evaData;
    }

    public void setEvaHistoryList(List<TestRecordBean.DataDTO> evaHistoryList) {
        this.evaHistoryList = evaHistoryList;
    }

    public int getChoosePosition() {
        return choosePosition;
    }

    public void setChoosePosition(int choosePosition) {
        this.choosePosition = choosePosition;
    }

    public boolean isIsplay() {
        return isplay;
    }

    public void setIsplay(boolean isplay) {
        this.isplay = isplay;
    }

    public boolean isEva() {
        return isEva;
    }

    public void setEva(boolean eva) {
        isEva = eva;
    }

    public boolean isIsword() {
        return isword;
    }

    public void setIsword(boolean isword) {
        this.isword = isword;
    }

    public boolean isEvaPlay() {
        return isEvaPlay;
    }

    public void setEvaPlay(boolean evaPlay) {
        isEvaPlay = evaPlay;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isEvass() {
        return isEvass;
    }

    public void setEvass(boolean evass) {
        isEvass = evass;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    private CallBack callBack;     //最后得callback接口


    private String audioUrl = null;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public EvaluatingAdpter(List<oriSentencesBean.VoatextDTO> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    //getItem  手动添加
    public oriSentencesBean.VoatextDTO getItem(int position) {
        return datas.get(position);
    }

    public TestRecordBean.DataDTO getItem0(int position) {
        return lists.get(position);
    }



    public List<oriSentencesBean.VoatextDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<oriSentencesBean.VoatextDTO> datas) {
        this.datas = datas;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EvaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_evaluating, parent, false);
        return new EvaViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull EvaViewHolder holder, int position) {


        oriSentencesBean.VoatextDTO daily = datas.get(position);

        //单词分割并变色
        if (evaList != null) {
            //List<EvaBean.DataDTO.WordsDTO> evaListCopy = null;
            for (int i = 0; i < evaList.size(); i++) {
                if (evaData != null) {
                    evaData = evaData + " " + evaList.get(i).getContent();
                } else {
                    evaData = evaList.get(i).getContent();
                }
            }

            List<String> evaSplit = Arrays.asList((evaData.split(" ")));
            ArrayList<Integer> wordNumber = new ArrayList<>();


            for (int i = 0; i < evaSplit.size(); i++) {
                wordNumber.add(evaSplit.get(i).length());
            }


            //获取到句子中每个单词的位置
            Constant.spannableString[choosePosition] = new SpannableString(evaData);

            int wordLength = 0;
            for (int i = 0; i < wordNumber.size(); i++) {
                if (Double.parseDouble(evaList.get(i).getScore()) < 2.0) {
                    Constant.spannableString[choosePosition].setSpan(new ForegroundColorSpan(Color.RED), wordLength, wordLength + wordNumber.get(i), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                } else if (Double.parseDouble(evaList.get(i).getScore()) > 3.0) {
                    Constant.spannableString[choosePosition].setSpan(new ForegroundColorSpan(green), wordLength, wordLength + wordNumber.get(i), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                wordLength = wordNumber.get(i) + wordLength + 1;
            }

            evaData = null;
            wordNumber = null;
            evaList = null;

        }

        //接收句子 和序号
        if (Constant.spannableString[position] != null) {

            holder.SentenceEn.setText(Constant.spannableString[position]);
            holder.SentenceCn.setText(daily.getSentenceCn());

        } else {
            holder.SentenceEn.setText(daily.getSentence());
            holder.SentenceCn.setText(daily.getSentenceCn());
        }

        for (int i = 1; i <= datas.size(); i++) {
            holder.sentenceEva.setText((position+1)+"");
        }


        //录音按钮得状态
        if (isEva) {

            if (choosePosition == position) {

                holder.imageEva.setImageResource(R.drawable.evaluyin1);


            } else {
                holder.imageEva.setImageResource(R.drawable.evaluyin0);
            }
        } else {

            holder.imageEva.setImageResource(R.drawable.evaluyin0);
        }

        //音乐播放得状态
        if (isplay) {

            if (choosePosition == position) {
                holder.imageViewPlay.setImageResource(R.drawable.zanting1);

            } else {
                holder.imageViewPlay.setImageResource(R.drawable.bofang1);
            }
        } else {

            holder.imageViewPlay.setImageResource(R.drawable.bofang1);
        }


        //有录音 录音按钮得状态
        if (isEvaPlay) {

            if (choosePosition == position) {

                holder.imageListen.setImageResource(R.drawable.erji_green1);

            } else {
                holder.imageListen.setImageResource(R.drawable.erjigreen);
            }
        } else {

            holder.imageListen.setImageResource(R.drawable.erjigreen);
        }

        //图标隐藏    如果数组里有这个录音
        if (isHide || Constant.spannableString[position] != null) {

            holder.scoreText.setText(Constant.evaScore[choosePosition] + "");

//        判断点击得段落显示下面得按钮控件
            if (choosePosition == position) {
                holder.linearLayout.setVisibility(View.VISIBLE);  //显示图标
            } else {
                holder.linearLayout.setVisibility(View.GONE);  //用完了  隐藏图标
            }
        } else {
            holder.linearLayout.setVisibility(View.GONE);

        }

        //点击的位置的样式显示
        if (isShow || Constant.spannableString[position] != null) {

            //如果这个数组里面有录音   并且点击得这个段落位置是当前段落   显示那3个按钮
            if (choosePosition == position) {
                holder.imageListen.setVisibility(View.VISIBLE); //显示
                holder.imageUpload.setVisibility(View.VISIBLE);
                holder.scoreText.setVisibility(View.VISIBLE);


            } else {
                holder.imageListen.setVisibility(View.INVISIBLE); //不显示
                holder.imageUpload.setVisibility(View.INVISIBLE);
                holder.scoreText.setVisibility(View.INVISIBLE);
                if (evaHistoryList != null) {
                    for (int i = 0; i < evaHistoryList.size(); i++) {
                        if (daily.getParaId().equals(evaHistoryList.get(i).getParaid() + "") && daily.getIdIndex().equals(evaHistoryList.get(i).getIdindex() + "")) {
                            holder.imageListen.setVisibility(View.VISIBLE);
                            holder.imageUpload.setVisibility(View.VISIBLE);
                            holder.scoreText.setVisibility(View.VISIBLE);
                            holder.scoreText.setText((Math.round(Double.parseDouble(evaHistoryList.get(i).getScore()) * 20)) + "");
                            break;
                        }
                    }

                }
            }
        } else {
            holder.imageListen.setVisibility(View.INVISIBLE);
            holder.imageUpload.setVisibility(View.INVISIBLE);
            holder.scoreText.setVisibility(View.INVISIBLE);
            if (evaHistoryList != null) {
                for (int i = 0; i < evaHistoryList.size(); i++) {
                    if (daily.getParaId().equals(evaHistoryList.get(i).getParaid() + "") && daily.getIdIndex().equals(evaHistoryList.get(i).getIdindex() + "")) {
                        holder.imageListen.setVisibility(View.VISIBLE);
                        holder.imageUpload.setVisibility(View.VISIBLE);
                        holder.scoreText.setVisibility(View.VISIBLE);

                        holder.scoreText.setText((Math.round(Double.parseDouble(evaHistoryList.get(i).getScore()) * 20)) + "");

                        break;
                    }
                }

            }
        }

        if (!init) {
            if (evaHistoryList != null) {
                for (int i = 0; i < evaHistoryList.size(); i++) {
                    if (daily.getParaId().equals(evaHistoryList.get(i).getParaid() + "") && daily.getIdIndex().equals(evaHistoryList.get(i).getIdindex() + "")) {
                        holder.imageListen.setVisibility(View.VISIBLE);
                        holder.imageUpload.setVisibility(View.VISIBLE);
                        holder.scoreText.setVisibility(View.VISIBLE);
//                        holder.scoreText.setText(Constant.evaScore[choosePosition] + "");
                        holder.scoreText.setText((Math.round(Double.parseDouble(evaHistoryList.get(i).getScore()) * 20)) + "");

                        break;
                    }
                }

            }
            init = true;

        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class EvaViewHolder extends RecyclerView.ViewHolder {


        TextView SentenceEn;
        TextView SentenceCn;


        ImageView imageViewPlay;

        ImageView imageEva;

        ImageView imageListen;

        ImageView imageUpload;

        LinearLayout linearLayout,item_line;

        TextView scoreText, sentenceEva;


        public EvaViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.isShow);
            item_line = itemView.findViewById(R.id.item_line);
            imageViewPlay = itemView.findViewById(R.id.play_stop);
            imageEva = itemView.findViewById(R.id.eva_play);
            imageListen = itemView.findViewById(R.id.listen);
            imageUpload = itemView.findViewById(R.id.upload);
            scoreText = itemView.findViewById(R.id.score_eva);
            sentenceEva = itemView.findViewById(R.id.sentence_eva);
            SentenceEn = itemView.findViewById(R.id.sentenceEn);
            SentenceCn = itemView.findViewById(R.id.sentenceCn);


            // 原文英语句子
            item_line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickText(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });


            //原文播放暂停
            imageViewPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickPlay(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });


            //网络请求返回信息   评测
            imageEva.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickEva(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });
            //已经录音的音频
            imageListen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickListen(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            //点击上传
            imageUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.clickUpload(getAdapterPosition());
                    }
                }
            });
        }

    }

    public interface CallBack {
        void clickPlay(int position) throws IOException;

        void clickEva(int position) throws IOException;

        void clickListen(int position) throws IOException;

        void clickUpload(int position);

        void clickText(int position) throws IOException;
    }


}
