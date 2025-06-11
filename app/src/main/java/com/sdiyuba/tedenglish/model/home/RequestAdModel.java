package com.sdiyuba.tedenglish.model.home;

import com.sdiyuba.tedenglish.model.bean.AdEntryBean;
import com.sdiyuba.tedenglish.view.home.RequestAdContract;
import com.sdiyuba.tedenglish.model.NetWorkManager;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RequestAdModel implements RequestAdContract.RequestAdModel {

    @Override
    public Disposable getAdEntryAll(String appId, int flag, String uid, RequestAdContract.Callback callback) {
        return NetWorkManager.getRequestForDev()
                .getAdEntryAll(appId, flag, uid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AdEntryBean>>() {
                    @Override
                    public void accept(List<AdEntryBean> adEntryBeans) throws Exception {

                        callback.success(adEntryBeans);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        callback.error((Exception) throwable);
                    }
                });
    }
}