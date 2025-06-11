package com.sdiyuba.tedenglish.model.home;


import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.AcquireWordBean;
import com.sdiyuba.tedenglish.view.home.AcquireWordContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AcquireWordModel implements AcquireWordContract.AcquireWordModel {
    @Override
    public Disposable getAcquireWord(String q, String format, AcquireWordContract.CallBackAcquireWord callBackAcquireWord) {
        return NetWorkManager
                .getRequest()
                .getAcquireWord("http://word.iyuba.cn/words/apiWordJson.jsp?",q,format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AcquireWordBean>() {
                    @Override
                    public void accept(AcquireWordBean acquireWordBean) throws Exception {
                        callBackAcquireWord.successAcquireWord(acquireWordBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackAcquireWord.errorAcquireWord((Exception) throwable);
                    }
                });
    }
}
