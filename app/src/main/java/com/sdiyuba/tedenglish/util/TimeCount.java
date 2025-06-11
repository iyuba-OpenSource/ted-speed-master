package com.sdiyuba.tedenglish.util;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.widget.TextView;

public class TimeCount extends CountDownTimer {
    private TextView btn;
    private Drawable un_click;
    private Drawable click;

    public TimeCount(Drawable click, Drawable un_click, TextView btn, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
        this.click = click;
        this.un_click = un_click;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // btn.setBackground(un_click);
        btn.setClickable(false);
        btn.setText(millisUntilFinished / 1000 + "秒");
    }

    @Override
    public void onFinish() {
        // btn.setBackground(click);
        btn.setText("重新获取");
        btn.setClickable(true);
    }
}