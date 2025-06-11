package com.sdiyuba.tedenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.model.bean.ReadReporterDetailBean;

import java.util.List;

public class ReadReporterAdpter extends RecyclerView.Adapter<ReadReporterAdpter.ReadReporterViewHolder>{


    private Context context;
    private List<ReadReporterDetailBean.DataDTO> dates;

    public ReadReporterAdpter(Context context, List<ReadReporterDetailBean.DataDTO> dates) {
        this.context = context;
        this.dates = dates;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<ReadReporterDetailBean.DataDTO> getDates() {
        return dates;
    }

    public void setDates(List<ReadReporterDetailBean.DataDTO> dates) {
        this.dates = dates;
    }

    @NonNull
    @Override
    public ReadReporterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_hearingdetail, parent, false);
        return new ReadReporterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadReporterViewHolder holder, int position) {

        ReadReporterDetailBean.DataDTO daily=dates.get(position);

        String title= daily.getTitle();
        if (title.length()>=15){
            String titles= daily.getTitle().substring(0,14);
            holder.hearingdetailText1.setText(titles);
        }else {
            holder.hearingdetailText1.setText(title);
        }

        holder.hearingdetailText2.setText(daily.getWordcount());
        holder.hearingdetailText3.setText(daily.getWpm());

        holder.hearingdetailText4.setText(daily.getTime().substring(0,10));
    }

    @Override
    public int getItemCount() {
        return dates == null ? 0 : dates.size();
    }

    class ReadReporterViewHolder extends RecyclerView.ViewHolder {


        TextView hearingdetailText1;
        TextView hearingdetailText2;
        TextView hearingdetailText3;
        TextView hearingdetailText4;


        public ReadReporterViewHolder(@NonNull View itemView) {
            super(itemView);

            hearingdetailText1 = itemView.findViewById(R.id.hearingdetailText1);
            hearingdetailText2 = itemView.findViewById(R.id.hearingdetailText2);
            hearingdetailText3 = itemView.findViewById(R.id.hearingdetailText3);
            hearingdetailText4 = itemView.findViewById(R.id.hearingdetailText4);

        }
    }
}
