package com.sdiyuba.tedenglish.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.model.bean.homeBean;
import com.bumptech.glide.Glide;
import com.sdiyuba.tedenglish.R;

import com.sdiyuba.tedenglish.activity.OriDetailActivity;


import java.util.List;

public class HomeAdpter extends RecyclerView.Adapter<HomeAdpter.homeViewHolder> {

    private List<homeBean.DataDTO> datas;
    private Context context;



    public HomeAdpter(List<homeBean.DataDTO> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public List<homeBean.DataDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<homeBean.DataDTO> datas) {
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
    public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_home_up_and_down, parent, false);
        return new homeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull homeViewHolder holder, int position) {


        homeBean.DataDTO daily = datas.get(position);


        holder.title.setText(daily.getTitleCn());


        //时间不对
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));

        int year = calendar.get(calendar.YEAR);

        int month = calendar.get(calendar.MONTH)+1;

        String month_copy = null;

        int day = calendar.get(calendar.DAY_OF_MONTH);

        String day_copy = null;

        if((month+"").length()==1){
            month_copy = "0"+ (month+"");
        }else{
            month_copy = (month+"");
        }

        if((day+"").length()==1){
            day_copy = "0"+ (day+"");
        }else{
            day_copy = (day+"");
        }



        String formattedDate = daily.getCreatTime().substring(0, daily.getCreatTime().length() - 2);


        if (!(year + "").equals(formattedDate.substring(0, 4))) {
            holder.time.setText(formattedDate.substring(0, 10));
        }else if(!month_copy.equals(formattedDate.substring(5, 7))){
            holder.time.setText(formattedDate.substring(0, 10));
        }else if(!(day_copy+"").equals(formattedDate.substring(8,10))){
            holder.time.setText(formattedDate.substring(0, 10));
        }else{
            holder.time.setText("今天"+formattedDate.substring(11, 16));

        }



        holder.sum.setText(daily.getReadCount()+" 阅读");

        //CENTER_CROP拉伸图片使其充满视图,并位于视图中间
        holder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);

        String imageurl =daily.getPic();

        Glide.with(context).load(imageurl).into(holder.photo);


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //需要更改跳转界面  点击进入activity
                Intent intent = new Intent(context, OriDetailActivity.class);
                Constant.orivoaid = Integer.parseInt(daily.getVoaId());

                Constant.oriimage = daily.getPic();

                Constant.AllTitleEn = daily.getTitle();

                Constant.AllTitleCn = daily.getTitleCn();

                //时间
                String formattedDate = daily.getCreatTime().substring(0, daily.getCreatTime().length() - 2);

                //时间不对
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));

                int year = calendar.get(calendar.YEAR);

                int month = calendar.get(calendar.MONTH)+1;

                String month_copy = null;

                int day = calendar.get(calendar.DAY_OF_MONTH);

                String day_copy = null;

                if((month+"").length()==1){
                    month_copy = "0"+ (month+"");
                }else{
                    month_copy = (month+"");
                }

                if((day+"").length()==1){
                    day_copy = "0"+ (day+"");
                }else{
                    day_copy = (day+"");
                }

                if (!(year + "").equals(formattedDate.substring(0, 4))) {
                    holder.time.setText(formattedDate.substring(0, 10));
                }else if(!month_copy.equals(formattedDate.substring(5, 7))){
                    holder.time.setText(formattedDate.substring(0, 10));
                }else if(!(day_copy+"").equals(formattedDate.substring(8,10))){
                    holder.time.setText(formattedDate.substring(0, 10));
                }else{
                    holder.time.setText("今天"+formattedDate.substring(11, 16));

                }

                Constant.pageTime = formattedDate.substring(0, 10);

                if (daily.getTitle().length()>=20){
                    Constant.oribiaoti=daily.getTitle().substring(0,20)+"...";
                }else {
                    Constant.oribiaoti=daily.getTitle();
                }






                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class homeViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView sum;
        TextView time;

        LinearLayout kuangjia;
        ImageView photo;


        public homeViewHolder(@NonNull View itemView) {
            super(itemView);

            kuangjia = itemView.findViewById(R.id.kuangjia);
            title = itemView.findViewById(R.id.home_texttitle);

            time = itemView.findViewById(R.id.home_texttime);
            sum = itemView.findViewById(R.id.home_textreadsum);
            photo = itemView.findViewById(R.id.photo);
        }

    }
}