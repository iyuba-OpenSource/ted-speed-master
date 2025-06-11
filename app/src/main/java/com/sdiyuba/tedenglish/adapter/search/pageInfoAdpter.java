package com.sdiyuba.tedenglish.adapter.search;

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

import com.bumptech.glide.Glide;
import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.R;
import com.sdiyuba.tedenglish.activity.OriDetailActivity;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;


import java.util.List;

public class pageInfoAdpter extends RecyclerView.Adapter<pageInfoAdpter.InQuireViewHolder>{

    private List<WordQueryBean.TitleDataDTO> datas ;
    private Context context;

    int count = 1;

    public pageInfoAdpter(List<WordQueryBean.TitleDataDTO> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public List<WordQueryBean.TitleDataDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<WordQueryBean.TitleDataDTO> datas) {
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
    public InQuireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new InQuireViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull InQuireViewHolder holder, int position) {
        WordQueryBean.TitleDataDTO daily =datas.get(position);

        if (daily.getTitleCn().length()>=25){
            holder.title.setText(daily.getTitleCn().substring(0,25)+"...");
        }else {

            holder.title.setText(daily.getTitleCn());
        }


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


        if (!(year + "").equals(daily.getCreateTime().substring(0, 4))) {
            holder.time.setText(daily.getCreateTime().substring(0, 10));
        }else if(!month_copy.equals(daily.getCreateTime().substring(5, 7))){
            holder.time.setText(daily.getCreateTime().substring(0, 10));
        }else if(!(day_copy+"").equals(daily.getCreateTime().substring(8,10))){
            holder.time.setText(daily.getCreateTime().substring(0, 10));
        }else{
            holder.time.setText("今天"+daily.getCreateTime().substring(11, 16));
        }

        Glide.with(context).load(daily.getPic()).into(holder.photo); //设置图片
        holder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //需要更改跳转界面  点击进入activity
//                Intent intent = new Intent(context, HomeDetailActivity.class);
//
//                //将字符串数据传送过去
//                Constant.orisound = "http://staticvip.iyuba.cn/news/essay"+daily.getSound();
//
//                Log.d("fang0021",Constant.orisound);
//
//
//                context.startActivity(intent);


                Intent intent = new Intent(context, OriDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //信息流广告相关
                Constant.orivoaid = Integer.parseInt(daily.getVoaId());


                Constant.AllTitleEn = daily.getTitle();

                Constant.AllTitleCn = daily.getTitleCn();


                Constant.oriimage=daily.getPic();

                Constant.orisound =daily.getSound();

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size()  ;
    }


    class InQuireViewHolder extends RecyclerView.ViewHolder {
        //第二步   用于解析item里面得东西
        TextView title,title_en;
        TextView sum;
        TextView time;

        LinearLayout kuangjia;
        ImageView photo;

        TextView timeandwords;

        public InQuireViewHolder(@NonNull View itemView) {
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

}
