package com.sdiyuba.tedenglish.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.Constant;
import com.sdiyuba.tedenglish.model.bean.MyWordBean;
import com.sdiyuba.tedenglish.R;


import java.util.List;

public class MyWordAdpter extends RecyclerView.Adapter<MyWordAdpter.MyWordViewHoler> {


    private  boolean delWord = false;
    String wordKey ;

    private CallBack callBack;

    private String wordKeyAudio;
    private List<MyWordBean.DataDTO> datas ;
    private Context context;

    public MyWordAdpter(List<MyWordBean.DataDTO> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public boolean isDelWord() {
        return delWord;
    }

    public void setDelWord(boolean delWord) {
        this.delWord = delWord;
    }

    public String getWordKey() {
        return wordKey;
    }

    public void setWordKey(String wordKey) {
        this.wordKey = wordKey;
    }

    public String getWordKeyAudio() {
        return wordKeyAudio;
    }

    public void setWordKeyAudio(String wordKeyAudio) {
        this.wordKeyAudio = wordKeyAudio;
    }

    public List<MyWordBean.DataDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<MyWordBean.DataDTO> datas) {
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
    public MyWordViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_myword, parent, false);
        return new MyWordViewHoler(itemView);
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyWordViewHoler holder, int position) {
        MyWordBean.DataDTO word = datas.get(position);

        holder.wordEn.setText(word.getWord());

        if (word.getPron().length()>=2){
            holder.yinbiao.setText("["+word.getPron()+"]");
        }else {
            holder.yinbiao.setText("");
            Constant.wordnull= true;
        }

        holder.translation.setText(word.getDef());

        //setOnLongClickListener 长按监听
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog alertDialog2 = new AlertDialog.Builder(context)
                        .setTitle("移除生词本")
                        .setPositiveButton("移除", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                                delWord = true;
                                wordKey = word.getWord(); //从样式里拿出点击得单词

                                if (callBack != null) {
                                    callBack.clickDelWord();
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


        //单词音频   点击触发在adpter里
        holder.wordplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wordKeyAudio = word.getAudio();
                if (callBack!=null){
                    callBack.clickListenWord();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyWordViewHoler extends RecyclerView.ViewHolder {

        ImageView wordplay;
        TextView wordEn;
        TextView yinbiao;
        TextView translation;


        public MyWordViewHoler(@NonNull View itemView) {
            super(itemView);

            wordplay=itemView.findViewById(R.id.wordplay);
            wordEn=itemView.findViewById(R.id.wordEn);
            yinbiao=itemView.findViewById(R.id.yinbiao);
            translation=itemView.findViewById(R.id.Translation);


        }


    }

    public interface CallBack {
        void clickDelWord();

        void clickListenWord();
    }
}
