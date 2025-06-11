package com.sdiyuba.tedenglish.model.home;



import com.sdiyuba.tedenglish.model.bean.CollectBean;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.view.home.MyCollectContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyCollectModel implements MyCollectContract.MycollectModel {
    @Override
    public Disposable getCollect(String userId, String topic, int appid, int sentenceFlg, String format, String sign, MyCollectContract.CallBackMycollect callBackMycollect) {
        return NetWorkManager
                .getRequest()
                .getCollectBean("http://cms.iyuba.cn/dataapi/jsp/getCollect.jsp?" ,userId,topic,appid,sentenceFlg,format,sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CollectBean>() {
                    @Override
                    public void accept(CollectBean collectBean) throws Exception {
                        callBackMycollect.successMycollect(collectBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackMycollect.errorMycollect((Exception) throwable);
                    }
                });

    }
}
