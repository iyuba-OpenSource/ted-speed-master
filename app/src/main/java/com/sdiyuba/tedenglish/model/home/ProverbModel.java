package com.sdiyuba.tedenglish.model.home;


import com.sdiyuba.tedenglish.model.bean.ProverbBean;
import com.sdiyuba.tedenglish.view.home.ProverbContract;
import com.sdiyuba.tedenglish.model.NetWorkManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProverbModel implements ProverbContract.ProverbModel {


    @Override
    public Disposable getProverb(ProverbContract.CallBackProverb callBackProverb) {
        return NetWorkManager
                .getRequest()
                .getProverbBean("http://ai.iyuba.cn/japanapi/getEverydayEnglish.jsp")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProverbBean>() {
                    @Override
                    public void accept(ProverbBean proverbBean) throws Exception {
                        callBackProverb.successProverb(proverbBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackProverb.errorProverb((Exception) throwable);
                    }
                });
    }
}
