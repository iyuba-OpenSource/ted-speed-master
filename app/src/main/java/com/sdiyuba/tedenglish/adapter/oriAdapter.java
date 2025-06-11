package com.sdiyuba.tedenglish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;
import com.sdiyuba.tedenglish.R;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class oriAdapter extends RecyclerView.Adapter<oriAdapter.CustomViewHolder> {

    private List<oriSentencesBean.VoatextDTO> datas;

    private Context context;


    private int textSize = 15;


    private int positionPlay = 0;

    private int wordStart = 0;

    private CallBack callBack;

    private boolean changeEn = false;

    //private int gColor = Color.parseColor("#1DC9F5");
    private int gColor = Color.parseColor("#72A638");

    private int blackColor = Color.BLACK;

    private int graycolor = Color.parseColor("#A9A9A9");

    public int getPositionPlay() {
        return positionPlay;
    }

    public void setPositionPlay(int positionPlay) {
        this.positionPlay = positionPlay;
    }

    public boolean isChangeEn() {
        return changeEn;
    }

    public void setChangeEn(boolean changeEn) {
        this.changeEn = changeEn;
    }

    public oriSentencesBean.VoatextDTO getItem(int position) {
        return datas.get(position);
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public oriAdapter(Context context, List<oriSentencesBean.VoatextDTO> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull

    @Override
    public oriAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_voa_page, parent, false);

        return new oriAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull oriAdapter.CustomViewHolder holder, int position) {
        oriSentencesBean.VoatextDTO voaDetailBean = datas.get(position);

        //切割字符，并获取字符长度
        List<String> evaSplit = splitWord(voaDetailBean.getSentence());
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(voaDetailBean.getSentence());

        for (int i = 0; i < evaSplit.size(); i++) {
            int start = voaDetailBean.getSentence().indexOf(evaSplit.get(i), wordStart);
            wordStart = start;
            if (start < 0) {
                start = 0;
            }
            int finalI = i;
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View view) {

                    if (callBack != null) {
                        callBack.clickWord(evaSplit.get(finalI));
                    }
                }

                //取消掉变色和下划线
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }


            };
            spannableString.setSpan(clickableSpan, start, start + evaSplit.get(i).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        holder.nameTxt.setText(spannableString);
        holder.nameTxt.setMovementMethod(LinkMovementMethod.getInstance());
        holder.nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        holder.nameTxtTitle.setText(voaDetailBean.getSentenceCn());

        //变色
        if (holder.getLayoutPosition() == positionPlay) {

            holder.nameTxt.setTextColor(gColor);
            holder.nameTxtTitle.setTextColor(gColor);
        } else {
            holder.nameTxt.setTextColor(blackColor);
            holder.nameTxtTitle.setTextColor(graycolor);
        }
        //变化中英文
        if (!changeEn) {
            holder.nameTxtTitle.setVisibility(View.VISIBLE);
        } else {
            holder.nameTxtTitle.setVisibility(View.GONE);
        }

        //字体大小
        if (textSize==1){
            holder.nameTxt.setTextSize(15);
            holder.nameTxtTitle.setTextSize(15);
        }else if (textSize == 2){
            holder.nameTxt.setTextSize(18);
            holder.nameTxtTitle.setTextSize(18);
        }else {
            holder.nameTxt.setTextSize(21);
            holder.nameTxtTitle.setTextSize(21);
        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        TextView nameTxtTitle;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.voa_title);
            nameTxtTitle = itemView.findViewById(R.id.voa_title_cn);
            nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        }


    }

    /**
     * 英文文本获取所有单词
     */
    public List<String> splitWord(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return new ArrayList<>();
        }
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("[a-zA-Z-']+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }

    public interface CallBack{
        void clickWord(String s);
    }


}
