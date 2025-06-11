package com.sdiyuba.tedenglish.util;



import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @title: 计时器
 * @date: 2023/5/7 13:58
 * @author: liang_mu
 * @email: liang.mu.cn@gmail.com
 * @description: 使用rxjava2的相关api处理
 */
public class Rxtimer {
    private static Map<String,Disposable> disMap = new HashMap<>();

    //延迟xx毫秒后在io线程启动
    public static void timerInIO(String tag,long delayTime,RxActionListener listener){
        if (disMap.get(tag)!=null){
            return;
        }
        Observable.timer(delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disMap.put(tag,d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (listener!=null){
                            listener.onAction(aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                       RxUtil.unDisposable(disMap.get(tag));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //延迟xx毫秒后在主线程启动
    public static void timerInMain(String tag,long delayTime,RxActionListener listener){
        if (disMap.get(tag)!=null){
            return;
        }
        Observable.timer(delayTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disMap.put(tag,d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (listener!=null){
                            listener.onAction(aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                       RxUtil.unDisposable(disMap.get(tag));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    //延迟xx毫秒后在io线程每隔xx毫秒启动
    public static void multiTimerInIO(String tag,long delayTime,long intervalTime,RxActionListener listener){
        if (disMap.get(tag)!=null){
            return;
        }
        Observable.interval(delayTime,intervalTime,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disMap.put(tag,d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (listener!=null){
                            listener.onAction(aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RxUtil.unDisposable(disMap.get(tag));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //延迟xx毫秒后在主线程每隔xx毫秒启动
    public static void multiTimerInMain(String tag,long delayTime,long intervalTime,RxActionListener listener){
        if (disMap.get(tag)!=null){
            return;
        }
        Observable.interval(delayTime,intervalTime,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disMap.put(tag,d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (listener!=null){
                            listener.onAction(aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RxUtil.unDisposable(disMap.get(tag));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //解除计时
    public static void cancelTimer(String tag){
        if (TextUtils.isEmpty(tag)||disMap==null||disMap.keySet().size()==0){
            return;
        }

        RxUtil.unDisposable(disMap.get(tag));
        disMap.remove(tag);
    }

    /************回调****************/
    public interface RxActionListener{
        void onAction(long number);
    }
}

