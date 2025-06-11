package com.sdiyuba.tedenglish.presenter.home;



import com.sdiyuba.tedenglish.model.bean.AcquireWordBean;
import com.sdiyuba.tedenglish.model.home.AcquireWordModel;
import com.sdiyuba.tedenglish.view.home.AcquireWordContract;
import com.sdiyuba.tedenglish.presenter.BasePresenter;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class AcquireWordPresenter extends BasePresenter<AcquireWordContract.AcquireWordView,AcquireWordContract.AcquireWordModel> implements AcquireWordContract.AcquireWordPresenter {
    @Override
    protected AcquireWordContract.AcquireWordModel initModel() {
        return new AcquireWordModel();
    }

    @Override
    public void getAcquireWord(String q, String format) {

        Disposable disposable = model.getAcquireWord(q, format, new AcquireWordContract.CallBackAcquireWord() {
            @Override
            public void successAcquireWord(AcquireWordBean acquireWordBean) {
                view.getAcquireWord(acquireWordBean);
            }

            @Override
            public void errorAcquireWord(Exception e) {

                if (e instanceof UnknownHostException){
                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
