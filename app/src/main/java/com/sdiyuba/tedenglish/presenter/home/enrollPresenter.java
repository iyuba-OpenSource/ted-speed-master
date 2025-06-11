package com.sdiyuba.tedenglish.presenter.home;





import com.sdiyuba.tedenglish.model.bean.enrollBean;
import com.sdiyuba.tedenglish.model.home.enrollModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.enrollContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class enrollPresenter extends BasePresenter<enrollContract.enrollView,enrollContract.enrollModel> implements enrollContract.enrollPresenter{
    @Override
    protected enrollContract.enrollModel initModel() {
        return new enrollModel();
    }

    @Override
    public void getEnroll(int protocol, String username, String password, String mobile, String sign, String json) {
        Disposable disposable=model.getEnroll(protocol, username, password, mobile, sign, json, new enrollContract.CallBackEnroll() {
            @Override
            public void successEnroll(enrollBean enrollbean) {
                view.getEnroll(enrollbean);
            }

            @Override
            public void errorEnroll(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }

            }
        });
        addSubscribe(disposable);
    }
}
