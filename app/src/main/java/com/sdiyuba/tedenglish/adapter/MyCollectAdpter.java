package com.sdiyuba.tedenglish.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.sdiyuba.tedenglish.model.bean.CollectBean;
import com.bumptech.glide.Glide;
import com.sdiyuba.tedenglish.R;

import com.sdiyuba.tedenglish.activity.OriDetailActivity;
import com.sdiyuba.tedenglish.sql.TestRoom;


import java.util.List;

public class MyCollectAdpter extends RecyclerView.Adapter<MyCollectAdpter.homeViewHolder> {

    private List<CollectBean.DataDTO> datas;
    private Context context;

    private CallBack callBack;

    private List<TestRoom> testdatas;


    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public List<TestRoom> getTestdatas() {
        return testdatas;
    }

    public void setTestdatas(List<TestRoom> testdatas) {
        this.testdatas = testdatas;
    }

    public MyCollectAdpter(List<CollectBean.DataDTO> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public MyCollectAdpter(Context context, List<TestRoom> testdatas) {
        this.context = context;
        this.testdatas = testdatas;
    }

    public List<CollectBean.DataDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<CollectBean.DataDTO> datas) {
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
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new homeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull homeViewHolder holder, int position) {


        CollectBean.DataDTO daily = datas.get(position);



        if (daily.getTitle().length()>=30){

            holder.title_en.setText(daily.getTitle().substring(0,30)+"...");
        }else {

            holder.title_en.setText(daily.getTitle());
        }

        if (daily.getTitleCn().length()>=12){

            holder.title.setText(daily.getTitleCn().substring(0,12)+"...");

        }else {

            holder.title.setText(daily.getTitleCn());

        }

        //时间不对
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));

        int year = calendar.get(calendar.YEAR);

        int month = calendar.get(calendar.MONTH) + 1;

        String month_copy = null;

        int day = calendar.get(calendar.DAY_OF_MONTH);

        String day_copy = null;

        if ((month + "").length() == 1) {
            month_copy = "0" + (month + "");
        } else {
            month_copy = (month + "");
        }

        if ((day + "").length() == 1) {
            day_copy = "0" + (day + "");
        } else {
            day_copy = (day + "");
        }


        if (!(year + "").equals(daily.getCollectDate().substring(0, 4))) {
            holder.time.setText(daily.getCollectDate().substring(0, 10));
        } else if (!month_copy.equals(daily.getCollectDate().substring(5, 7))) {
            holder.time.setText(daily.getCollectDate().substring(0, 10));
        } else if (!(day_copy + "").equals(daily.getCollectDate().substring(8, 10))) {
            holder.time.setText(daily.getCollectDate().substring(0, 10));
        } else {
            holder.time.setText("今天" + daily.getCollectDate().substring(11, 16));

        }


//        holder.eye.setVisibility(View.INVISIBLE);


        String imageurl = daily.getPic();

        Glide.with(context).load(imageurl).into(holder.photo);

        holder.photo.setScaleType(ImageView.ScaleType.CENTER_CROP);//拉伸图片

        //setOnLongClickListener 长按监听
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog alertDialog2 = new AlertDialog.Builder(context)
                        .setTitle("是否取消收藏?")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                Constant.orivoaid = Integer.parseInt(daily.getVoaid());
                                if (callBack != null) {
                                    callBack.clickDel();
                                }
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create();
                alertDialog2.show();
                return true;
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //需要更改跳转界面  点击进入activity
                Intent intent = new Intent(context, OriDetailActivity.class);


                Constant.orivoaid = Integer.parseInt(daily.getId());

                Constant.AllTitleEn = daily.getTitle();

                Constant.oriimage = daily.getPic();
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
        return datas.size();
    }

    class homeViewHolder extends RecyclerView.ViewHolder {

        TextView title,title_en;
        TextView sum;
        TextView time;

        LinearLayout kuangjia;
        ImageView photo;


        public homeViewHolder(@NonNull View itemView) {
            super(itemView);
            kuangjia = itemView.findViewById(R.id.kuangjia);
            title = itemView.findViewById(R.id.home_texttitle_cn);
            title_en = itemView.findViewById(R.id.home_texttitle);
            time = itemView.findViewById(R.id.home_texttime);

            photo = itemView.findViewById(R.id.photo);
        }

    }

    public interface CallBack {
        void clickDel();

    }
}