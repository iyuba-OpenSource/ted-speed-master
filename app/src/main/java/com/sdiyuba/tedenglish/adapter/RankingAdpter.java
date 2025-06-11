package com.sdiyuba.tedenglish.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdiyuba.tedenglish.activity.RankingDetailsActivity;
import com.bumptech.glide.Glide;
import com.sdiyuba.tedenglish.R;

import com.sdiyuba.tedenglish.model.bean.RankingBean;


import java.util.ArrayList;
import java.util.List;

public class RankingAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RankingBean.DataDTO> datas = new ArrayList<>();

    private Context context;
    private int green = Color.parseColor("#72A638");

    public RankingAdpter(List<RankingBean.DataDTO> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM1 = 1;

    public List<RankingBean.DataDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<RankingBean.DataDTO> datas) {
        this.datas = datas;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    @Override
    public int getItemViewType(int position) {
        if (position <=2) {
            return TYPE_HEADER; // 头部视图类型
        } else {
            return TYPE_ITEM1; // 类型为 Item2 的项目视图类型
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            // 创建头部视图持有者，第一个视图
            View view = inflater.inflate(R.layout.recyclerview_ranking_item, parent, false);
            return new RankingViewHoler(view);
        } else {
            // 创建类型为 Item2 的项目视图持有者，第二个视图
            View view = inflater.inflate(R.layout.recyclerview_ranking_item, parent, false);
            return new Item2ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RankingViewHoler) {
            RankingBean.DataDTO daily = datas.get(position);
//        holder.wenben.setText(daily.getSentence());
//        holder.wenben2.setText(daily.getSentenceCn());

            Glide.with(context).load(daily.getImgSrc()).into(((RankingViewHoler) holder).ranking_img);
            ((RankingViewHoler) holder).name.setText(daily.getName());
            ((RankingViewHoler) holder).sentence.setText("句子数：" + daily.getCount() + "");
            ((RankingViewHoler) holder).score.setText(daily.getScores() + "");
            ((RankingViewHoler) holder).avg.setText("平均分：" + (daily.getScores() / daily.getCount()) + "");
//            ((RankingViewHoler) holder).ranking_rank.setText(daily.getRanking() + ""); //用户uid

            ((RankingViewHoler) holder).ranking_rank.setText(""); //用户uid
            ((RankingViewHoler) holder).ranking_rank.setBackgroundResource(R.drawable.the1);

            Log.d("fang01232145", "onBindViewHolder: " + position);
        if (position<=2){
            if (position == 0 ){


                ((RankingViewHoler) holder).ranking_rank.setText(""); //用户uid
                ((RankingViewHoler) holder).ranking_rank.setBackgroundResource(R.drawable.the1);

            }
            if (position == 1){

                ((RankingViewHoler) holder).ranking_rank.setText(""); //用户uid
                ((RankingViewHoler) holder).ranking_rank.setBackgroundResource(R.drawable.the2);
            }
            if (position == 2){

                ((RankingViewHoler) holder).ranking_rank.setText(""); //用户uid
                ((RankingViewHoler) holder).ranking_rank.setBackgroundResource(R.drawable.the3);
            }
        }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //携带参数跳转界面
                    Intent intent = new Intent(context, RankingDetailsActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putInt("uid", daily.getUid());

                    bundle.putString("img", daily.getImgSrc());

                    bundle.putString("name", daily.getName());

                    bundle.putInt("score", daily.getScores());

                    bundle.putInt("sentence", daily.getCount());

                    intent.putExtras(bundle);

                    //rankingBean.getUid();
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof Item2ViewHolder)  {
            RankingBean.DataDTO daily = datas.get(position);
//        holder.wenben.setText(daily.getSentence());
//        holder.wenben2.setText(daily.getSentenceCn());

            Glide.with(context).load(daily.getImgSrc()).into(((Item2ViewHolder) holder).ranking_img);
            ((Item2ViewHolder) holder).name.setText(daily.getName());
            ((Item2ViewHolder) holder).sentence.setText("句子数：" + daily.getCount() + "");
            ((Item2ViewHolder) holder).score.setText(daily.getScores() + "");
            ((Item2ViewHolder) holder).avg.setText("平均分：" + (daily.getScores() / daily.getCount()) + "");
            ((Item2ViewHolder) holder).ranking_rank.setText(daily.getRanking() + ""); //用户uid

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //携带参数跳转界面
                    Intent intent = new Intent(context, RankingDetailsActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putInt("uid", daily.getUid());

                    bundle.putString("img", daily.getImgSrc());

                    bundle.putString("name", daily.getName());

                    bundle.putInt("score", daily.getScores());

                    bundle.putInt("sentence", daily.getCount());

                    intent.putExtras(bundle);

                    //rankingBean.getUid();
                    context.startActivity(intent);
                }
            });
        }




    }


//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View itemView = LayoutInflater.from(context).inflate(R.layout.recyclerview_ranking_item, parent, false);
////        return new RankingViewHoler(itemView);
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        if (viewType == TYPE_HEADER) {
//            // 创建头部视图持有者，第一个视图
//            View view = inflater.inflate(R.layout.recyclerview_ranking_item, parent, false);
//            return new RankingViewHoler(view);
//        } else {
//            // 创建类型为 Item2 的项目视图持有者，第二个视图
//            View view = inflater.inflate(R.layout.recyclerview_ranking_item, parent, false);
//            return new Item2ViewHolder(view);
//        }
//
//    }

//    @SuppressLint("ResourceAsColor")
//    @Override
//    public void onBindViewHolder(@NonNull RankingViewHoler holder, int position) {
//
//
//
//
//
//    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class RankingViewHoler extends RecyclerView.ViewHolder {
        //第二步   用于解析item里面得东西


        TextView ranking_rank;
        ImageView ranking_img;
        TextView name;
        TextView sentence;
        TextView score;
        TextView avg;


        public RankingViewHoler(@NonNull View itemView) {
            super(itemView);

            ranking_rank = itemView.findViewById(R.id.ranking_rank);  //排名
            ranking_img = itemView.findViewById(R.id.ranking_img);
            name = itemView.findViewById(R.id.name);
            sentence = itemView.findViewById(R.id.sentence);
            score = itemView.findViewById(R.id.score);
            avg = itemView.findViewById(R.id.avg);


        }
    }


    class Item2ViewHolder extends RecyclerView.ViewHolder {
        //第二步   用于解析item里面得东西


        TextView ranking_rank;
        ImageView ranking_img;
        TextView name;
        TextView sentence;
        TextView score;
        TextView avg;


        public Item2ViewHolder(@NonNull View itemView) {
            super(itemView);

            ranking_rank = itemView.findViewById(R.id.ranking_rank);  //排名
            ranking_img = itemView.findViewById(R.id.ranking_img);
            name = itemView.findViewById(R.id.name);
            sentence = itemView.findViewById(R.id.sentence);
            score = itemView.findViewById(R.id.score);
            avg = itemView.findViewById(R.id.avg);


        }
    }


}
