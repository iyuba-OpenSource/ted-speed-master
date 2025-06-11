package com.sdiyuba.tedenglish.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class ToastUtil {

    private static Toast mTextToast;
    private static Toast mViewToast;

    @SuppressLint({"ShowToast", "WrongConstant"})
    public static void showToast(Context context, String msg) {
        if (null == mTextToast) {
            mTextToast = new Toast(context);
            mTextToast = Toast.makeText(context, msg, 2000);
        }
        mTextToast.setText(msg);
        mTextToast.show();
    }

    public static void showToast(Context context, View view) {
        if (null == mViewToast) {
            mViewToast = Toast.makeText(context, (CharSequence) view, (int) 1);
        }
        mViewToast.show();
    }

    public static void toastShort(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void destroyMesInstance() {
        mTextToast = null;
    }

    public static void destroyViewInstance() {
        mViewToast = null;
    }

}
