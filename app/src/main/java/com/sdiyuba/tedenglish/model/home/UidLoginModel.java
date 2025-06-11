package com.sdiyuba.tedenglish.model.home;






import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.view.home.uidLoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UidLoginModel implements uidLoginContract.uidLoginModel {
    @Override
    public Disposable uidLogin(String platform, String format, String protocol, String id, String myid, int appid, String sign, uidLoginContract.CallBackuidLogin callBackuidLogin) {
        return NetWorkManager
                .getRequest()
                .uidLogin(platform, format, protocol, id, myid, appid, sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UidBean>() {
                    @Override
                    public void accept(UidBean uidBean) throws Exception {
                        callBackuidLogin.successuidLogin(uidBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackuidLogin.erroruidLogin((Exception) throwable);
                    }
                });
    }
}