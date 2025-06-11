package com.sdiyuba.tedenglish.adapter.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.activity.search.InQuireActivity;

import java.util.List;

public class SearchAdpter extends RecyclerView.Adapter<SearchAdpter.SearchAdpterViewHolder> {


    private List<String> dates;
    private Context context;

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SearchAdpter(List<String> dates, Context context) {
        this.dates = dates;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAdpterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_keyword, parent, false);
        return new SearchAdpterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdpterViewHolder holder, @SuppressLint("RecyclerView") int position) {
            //设置值

        holder.keyword.setText(dates.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                //需要更改跳转界面  点击进入activity
                Intent intent = new Intent(context, InQuireActivity.class);

                //bunder用来传输数据   通过key值进行传输
                Bundle bundle =new Bundle();

                //将字符串数据传送过去
                bundle.putString("words", dates.get(position));
                bundle.putString("countss", "1");

//
//                bundle.putString("sound",voaBean.getSound());
                //固定格式
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });


    }

//    @Override
//    public void onBindViewHolder(@NonNull abcHomeViewHolder holder, int position) {
//
//        AbcHomeBean.DataDTO daily =datas.get(position);
//
//        String title= daily.getTitle();
//        if (title.length()<30){
//            holder.wenben.setText(daily.getTitle());
//        }else {
//            String gettitle= title.substring(0,30);
//            holder.wenben.setText(gettitle+"...");
//        }
//
//        holder.nameImage.setScaleType(ImageView.ScaleType.CENTER_CROP);//拉伸图片
//        Glide.with(context).load(daily.getAllFilePath()).into(holder.nameImage); //设置图片
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//
//
//
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
        return   dates == null ? 0 : dates.size() ;
    }

    class SearchAdpterViewHolder extends RecyclerView.ViewHolder {

        TextView keyword;


        public SearchAdpterViewHolder(@NonNull View itemView) {
            super(itemView);

            keyword=itemView.findViewById(R.id.keyword);

        }
    }
}
