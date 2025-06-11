package com.sdiyuba.tedenglish.model.home;




import com.sdiyuba.tedenglish.model.bean.enrollBean;
import com.sdiyuba.tedenglish.view.home.enrollContract;
import com.sdiyuba.tedenglish.model.NetWorkManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class enrollModel implements enrollContract.enrollModel {
    @Override
    public Disposable getEnroll(int protocol, String username, String password, String mobile, String sign, String json, enrollContract.CallBackEnroll callBackEnroll) {
        return NetWorkManager
                .getRequest()
                .getEnroll( protocol,  username, password,  mobile,  sign,  json)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<enrollBean>() {
                    @Override
                    public void accept(enrollBean enrollBean) throws Exception {
                        callBackEnroll.successEnroll(enrollBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackEnroll.errorEnroll((Exception) throwable);
                    }
                });
    }
}
