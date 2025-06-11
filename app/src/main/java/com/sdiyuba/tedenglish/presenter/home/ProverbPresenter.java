package com.sdiyuba.tedenglish.presenter.home;


import com.sdiyuba.tedenglish.model.bean.ProverbBean;
import com.sdiyuba.tedenglish.view.home.ProverbContract;
import com.sdiyuba.tedenglish.model.home.ProverbModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class ProverbPresenter extends BasePresenter<ProverbContract.ProverbView, ProverbContract.ProverbModel> implements ProverbContract.ProverbPresenter {
    @Override
    protected ProverbContract.ProverbModel initModel() {
        return new ProverbModel();
    }


    @Override
    public void getProverb() {

        Disposable disposable = model.getProverb(new ProverbContract.CallBackProverb() {
            @Override
            public void successProverb(ProverbBean proverbBean) {
                view.getProverb(proverbBean);
            }

            @Override
            public void errorProverb(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
