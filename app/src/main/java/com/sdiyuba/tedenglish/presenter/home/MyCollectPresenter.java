package com.sdiyuba.tedenglish.presenter.home;





import com.sdiyuba.tedenglish.model.bean.CollectBean;
import com.sdiyuba.tedenglish.model.home.MyCollectModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.MyCollectContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class MyCollectPresenter extends BasePresenter<MyCollectContract.MycollectView,MyCollectContract.MycollectModel> implements MyCollectContract.MycollectPresenter{
    @Override
    protected MyCollectContract.MycollectModel initModel() {
        return new MyCollectModel();
    }

    @Override
    public void getCollect(String userId, String topic, int appid, int sentenceFlg, String format, String sign) {

        Disposable disposable = model.getCollect(userId, topic, appid, sentenceFlg, format, sign, new MyCollectContract.CallBackMycollect() {
            @Override
            public void successMycollect(CollectBean collectBean) {
                view.getCollect(collectBean);
            }

            @Override
            public void errorMycollect(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
