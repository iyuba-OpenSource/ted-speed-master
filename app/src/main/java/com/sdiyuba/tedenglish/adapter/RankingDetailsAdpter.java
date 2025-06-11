package com.sdiyuba.tedenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.Constant;

import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.model.bean.RankingDetailsBean;

import java.util.ArrayList;
import java.util.List;

public class RankingDetailsAdpter extends RecyclerView.Adapter<RankingDetailsAdpter.RankingDetailsViewHoler> {

    private List<RankingDetailsBean.DataDTO> datas = new ArrayList<>();


    private Context context;


    //这三个是加上的
    private CallBack callBack;
    private int choosePosition = 0;

    private boolean isPlay = false;


    public RankingDetailsAdpter(List<RankingDetailsBean.DataDTO> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public List<RankingDetailsBean.DataDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<RankingDetailsBean.DataDTO> datas) {
        this.datas = datas;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getChoosePosition() {
        return choosePosition;
    }

    public void setChoosePosition(int choosePosition) {
        this.choosePosition = choosePosition;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }


    public RankingDetailsBean.DataDTO getItem(int position) {
        return datas.get(position);
    }


    @NonNull
    @Override
    public RankingDetailsViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rankingdetails, parent, false);
        return new RankingDetailsViewHoler(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RankingDetailsViewHoler holder, int position) {
        RankingDetailsBean.DataDTO daily = datas.get(position);


        holder.score.setText("分数:" + daily.getScore() + "");


        String time = daily.getCreateDate();
        String day = time.substring(0, 11);
        holder.creattime.setText(day);


        if ((daily.getParaid() == 0) && ((daily.getIdIndex() == 0))) {
//            Glide.with(context).load(R.drawable.jiaobiao).into(holder.Sentence_rankingdetails);
            holder.score.setText("分数:" + daily.getScore() + "");
            holder.creattime.setText(day);
            holder.sentenceEn.setText("                   文章合成音频");
        }



        if (Constant.orilist!=null){
            //原文句子
            for (int i = 0; i < Constant.orilist.size(); i++) {

                if (((daily.getParaid() + "").equals(Constant.orilist.get(i).getParaId())) && ((daily.getIdIndex() + "").equals(Constant.orilist.get(i).getIdIndex()))) {
                    holder.sentenceEn.setText(Constant.orilist.get(i).getSentence());
//                Glide.with(context).load(R.drawable.jiaobiao).into(holder.Sentence_rankingdetails);
                }
            }
        }else {

            Toast.makeText(context.getApplicationContext(), "数据请求异常,请退出到首页后重新进入", Toast.LENGTH_SHORT).show();

        }



        if (isPlay) {

            if (choosePosition == position) {
                holder.sentencePlay.setImageResource(R.drawable.zanting1);
            } else {
                holder.sentencePlay.setImageResource(R.drawable.bofang1);
            }
        } else {

            holder.sentencePlay.setImageResource(R.drawable.bofang1);
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class RankingDetailsViewHoler extends RecyclerView.ViewHolder {
        //第二步   用于解析item里面得东西


        TextView ranking_rank;
        ImageView ranking_img;
        TextView name;
        TextView sentence;
        ImageView sentencePlay;
        ImageView Sentence_rankingdetails;
        TextView score;
        TextView creattime;
        TextView sentenceEn;

        public RankingDetailsViewHoler(@NonNull View itemView) {
            super(itemView);

            Sentence_rankingdetails = itemView.findViewById(R.id.sentence_rankingdetails);
            sentencePlay = itemView.findViewById(R.id.play);
            score = itemView.findViewById(R.id.score1);
            creattime = itemView.findViewById(R.id.time);
            sentenceEn = itemView.findViewById(R.id.sentenceEn);
            sentencePlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        callBack.clickPlay(getAdapterPosition());
                    }
                }
            });

        }
    }


    public interface CallBack {
        void clickPlay(int position);
    }
}















