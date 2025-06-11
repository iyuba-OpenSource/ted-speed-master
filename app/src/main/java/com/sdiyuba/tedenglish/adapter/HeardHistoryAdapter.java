package com.sdiyuba.tedenglish.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdiyuba.tedenglish.Constant;
import com.bumptech.glide.Glide;

import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.activity.OriDetailActivity;
import com.sdiyuba.tedenglish.sql.TestRoom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *   听过的  adapter
 */

public class HeardHistoryAdapter extends RecyclerView.Adapter<HeardHistoryAdapter.HeardHistoryViewHolder>{

    private Context context;

    private List<TestRoom> datas;

    private CallBack callBack;

    private List<Integer> Positions = new ArrayList<>();

    List<TestRoom> timesdata;


    public HeardHistoryAdapter(Context context, List<TestRoom> datas,  List<TestRoom> times) {
        this.context = context;
        this.datas = datas;
        this.timesdata = times;



        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Positions.add(0);

        int sum = 0;
        for(int i = 0; i < times.size() - 1; i++){




//            else {
//                Positions.add(times.size()-sum);
//            }
//            sum += Integer.parseInt(times.get(i).getStorageTime());
//
//            sum += times.size();


//            Positions.add(sum);

        }

        for (int j = 0; j < times.size(); j++) {
            String dateline = times.get(j).getStorageTime().split(" ")[0];

            System.out.println(currentDate+"*******888****/"+dateline);

            if (currentDate.equals(dateline)){

                sum = sum+1;
            }

        }

        Log.d("fang021544", times.size()+"HeardHistoryAdapter: "+sum);
        Positions.add(sum);


    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public HeardHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.item_home,parent,false);
        return new HeardHistoryAdapter.HeardHistoryViewHolder(itemview);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HeardHistoryViewHolder holder, int position) {

//        Collections.reverse(datas);



        TestRoom daily = datas.get(position);



        for(int i = 0; i < Positions.size(); i++){

            String dateline =daily.getStorageTime().split(" ")[0];
            if(position == Positions.get(i)){
                System.out.println(position + " 555555555555555" + dateline);
                holder.timeandwords.setVisibility(View.VISIBLE);

                String currentDatess = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());



                if (currentDatess.equals(dateline)){
                    holder.timeandwords.setText("今天" );
                }else {
                    holder.timeandwords.setText("更早" );
                }

            }

        }

        holder.title.setText(daily.titleCn);


        if (daily.titileEn.length()>=30){
            holder.title_en.setText(daily.titileEn.substring(0,30)+"...");
        }else {

            holder.title_en.setText(daily.titileEn);
        }


        holder.time.setText(daily.storageTime);


        Log.d("fang012369852", "onBindViewHolder: "+daily.pic);
        Glide.with(context).load(daily.pic).into(holder.photo); //设置图片
        //CENTER_CROP拉伸图片使其充满视图,并位于视图中间
        holder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                //需要更改跳转界面  点击进入activity
                Intent intent = new Intent(context, OriDetailActivity.class);
                Constant.oriimage = daily.pic;

                Constant.AllTitleEn = daily.titileEn;

                Constant.AllTitleCn = daily.titileEn;
                Bundle bundle = new Bundle();
                Constant.orivoaid = Integer.parseInt(daily.voaid);
                intent.putExtras(bundle);

                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class HeardHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView title,title_en;
        TextView sum;
        TextView time;

        LinearLayout kuangjia;
        ImageView photo;

        TextView timeandwords;
        public HeardHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            kuangjia = itemView.findViewById(R.id.kuangjia);
            title = itemView.findViewById(R.id.home_texttitle_cn);
            title_en = itemView.findViewById(R.id.home_texttitle);
            time = itemView.findViewById(R.id.home_texttime);
            sum = itemView.findViewById(R.id.home_textreadsum);
            photo = itemView.findViewById(R.id.photo);
            timeandwords = itemView.findViewById(R.id.timeandwords);
        }
    }
    public interface CallBack {

        void delete(TestRoom testRoom);
    }


}
