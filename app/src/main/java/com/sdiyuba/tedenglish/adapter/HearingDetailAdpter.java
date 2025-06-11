package com.sdiyuba.tedenglish.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.model.bean.HearingDetailBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class HearingDetailAdpter extends RecyclerView.Adapter<HearingDetailAdpter.HearingDetailViewHolder>{

    private List<HearingDetailBean.DataDTO> dates;
    private Context context;

    public HearingDetailAdpter(List<HearingDetailBean.DataDTO> dates, Context context) {
        this.dates = dates;
        this.context = context;
    }

    public List<HearingDetailBean.DataDTO> getDates() {
        return dates;
    }

    public void setDates(List<HearingDetailBean.DataDTO> dates) {
        this.dates = dates;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HearingDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_hearingdetail, parent, false);
        return new HearingDetailViewHolder(itemView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HearingDetailViewHolder holder, int position) {
        HearingDetailBean.DataDTO daily=dates.get(position);

        holder.hearingdetailText1.setText(daily.getLesson());


        String title= daily.getTitle();
        if (title!=null){
            if (title.length()>=15){
                String titles= daily.getTitle().substring(0,14);
                holder.hearingdetailText2.setText(titles);
            }else {
                holder.hearingdetailText2.setText(title);
            }
        }


        // 这样得到的差值是微秒级别

//        long diff = Long.parseLong(daily.getEndTime())-Long.parseLong(daily.getBeginTime());
        try {
            Long diff0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(daily.getBeginTime()).getTime();
            Long diff1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(daily.getEndTime()).getTime();
            Long diff=diff1-diff0;
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long s=(diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)-minutes*(1000 * 60))/(1000);
            if (minutes == 0){
                holder.hearingdetailText3.setText(s+"秒");//减法
            }else {
                holder.hearingdetailText3.setText(minutes+"分"+s+"秒");//减法
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        long diff = Long.parseLong(daily.getEndTime())-Long.parseLong(daily.getBeginTime());
//        long days = diff / (1000 * 60 * 60 * 24);
//        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
//        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
//        long s=(diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)-minutes*(1000 * 60))/(1000);

        holder.hearingdetailText4.setText(daily.getBeginTime().substring(0,10));


    }

    @Override
    public int getItemCount() {
        return dates == null ? 0 : dates.size();
    }

    class HearingDetailViewHolder extends RecyclerView.ViewHolder {


        TextView hearingdetailText1;
        TextView hearingdetailText2;
        TextView hearingdetailText3;
        TextView hearingdetailText4;


        public HearingDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            hearingdetailText1=itemView.findViewById(R.id.hearingdetailText1);
            hearingdetailText2=itemView.findViewById(R.id.hearingdetailText2);
            hearingdetailText3=itemView.findViewById(R.id.hearingdetailText3);
            hearingdetailText4=itemView.findViewById(R.id.hearingdetailText4);

        }
    }

}
