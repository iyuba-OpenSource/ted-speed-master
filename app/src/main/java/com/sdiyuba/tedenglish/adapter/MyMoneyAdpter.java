package com.sdiyuba.tedenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.model.bean.MyMoneyBean;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyMoneyAdpter extends RecyclerView.Adapter<MyMoneyAdpter.MyMoneyViewHolder> {

    private Context context;
    private List<MyMoneyBean.DataDTO> dates;

    public MyMoneyAdpter(Context context, List<MyMoneyBean.DataDTO> dates) {
        this.context = context;
        this.dates = dates;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<MyMoneyBean.DataDTO> getDates() {
        return dates;
    }

    public void setDates(List<MyMoneyBean.DataDTO> dates) {
        this.dates = dates;
    }

    @NonNull
    @Override
    public MyMoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_mymoney, parent, false);
        return new MyMoneyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyMoneyViewHolder holder, int position) {
        MyMoneyBean.DataDTO daily=dates.get(position);


        if (Integer.parseInt(daily.getScore())>0){
            holder.num.setText("+ "+Double.parseDouble(daily.getScore())/100+"元");
        }else {
            holder.num.setText(Double.parseDouble(daily.getScore())/100+"元");
        }

        holder.text.setText(daily.getType());

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
       if (daily.getTime().substring(0,10).equals(df.format(System.currentTimeMillis()))){
           holder.time.setText(daily.getTime().substring(10,16));
       }else {
           holder.time.setText(daily.getTime().substring(0,10));
       }


    }

    @Override
    public int getItemCount() {
        return dates == null ? 0 : dates.size();
    }

    class  MyMoneyViewHolder extends RecyclerView.ViewHolder {


        TextView num;
        TextView time;
        TextView text;


        public MyMoneyViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.money_num);
            time = itemView.findViewById(R.id.end_texttime);
            text = itemView.findViewById(R.id.end_text);


        }
    }
}
