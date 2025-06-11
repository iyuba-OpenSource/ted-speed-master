package com.sdiyuba.tedenglish.presenter.home;






import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.model.home.UidLoginModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.uidLoginContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class UidLoginPresenter extends BasePresenter<uidLoginContract.uidLoginView, uidLoginContract.uidLoginModel> implements uidLoginContract.uidLoginPresenter {
    @Override
    protected uidLoginContract.uidLoginModel initModel() {
        return new UidLoginModel();
    }

    @Override
    public void uidLogin(String platform, String format, String protocol, String id, String myid, int appid, String sign) {
        Disposable disposable = model.uidLogin(platform, format, protocol, id, myid, appid, sign, new uidLoginContract.CallBackuidLogin() {
            @Override
            public void successuidLogin(UidBean uidBean) {

                view.uidLogin(uidBean);
            }

            @Override
            public void erroruidLogin(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });

    }
}
