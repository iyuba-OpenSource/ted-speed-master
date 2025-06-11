package com.sdiyuba.tedenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.model.bean.SpokenDetailBean;

import java.util.List;

public class SpokenDetailAdpter extends RecyclerView.Adapter<SpokenDetailAdpter.SpokenDetailViewHolder> {


    private List<SpokenDetailBean.DataDTO> dates;
    private Context context;

    public SpokenDetailAdpter(List<SpokenDetailBean.DataDTO> dates, Context context) {
        this.dates = dates;
        this.context = context;
    }

    public List<SpokenDetailBean.DataDTO> getDates() {
        return dates;
    }

    public void setDates(List<SpokenDetailBean.DataDTO> dates) {
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
    public SpokenDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_spokendetail, parent, false);
        return new SpokenDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpokenDetailViewHolder holder, int position) {
        SpokenDetailBean.DataDTO daily = dates.get(position);
        holder.spokendetailText1.setText(daily.getNewstype() + "");
        String title = daily.getSentence();
        if (title.length() >= 12) {
            String titles = daily.getSentence().substring(0, 12);
            holder.spokendetailText2.setText(titles+"...");
        } else {
            holder.spokendetailText2.setText(daily.getSentence()+"");
        }

        holder.spokendetailText3.setText(daily.getParaid()+"");
        holder.spokendetailText4.setText(daily.getScore() + "");
        holder.spokendetailText5.setText("" + daily.getCreateTime().substring(0, 10));

    }

    @Override
    public int getItemCount() {
        return dates == null ? 0 : dates.size();
    }

    class SpokenDetailViewHolder extends RecyclerView.ViewHolder {


        TextView spokendetailText1;
        TextView spokendetailText2;
        TextView spokendetailText3;
        TextView spokendetailText4;
        TextView spokendetailText5;


        public SpokenDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            spokendetailText1 = itemView.findViewById(R.id.spokendetailText1);
            spokendetailText2 = itemView.findViewById(R.id.spokendetailText2);
            spokendetailText3 = itemView.findViewById(R.id.spokendetailText3);
            spokendetailText4 = itemView.findViewById(R.id.spokendetailText4);
            spokendetailText5 = itemView.findViewById(R.id.spokendetailText5);

        }
    }
}
