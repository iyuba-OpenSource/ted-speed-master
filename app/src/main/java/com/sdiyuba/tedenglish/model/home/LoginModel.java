package com.sdiyuba.tedenglish.model.home;






import com.sdiyuba.tedenglish.model.bean.LoginBean;
import com.sdiyuba.tedenglish.model.bean.LogoutBean;
import com.sdiyuba.tedenglish.view.home.LoginContract;
import com.sdiyuba.tedenglish.model.NetWorkManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginModel implements LoginContract.LoginModel {
    @Override
    public Disposable getLogin(int protocol, String username, String password, String sign, String format, LoginContract.CallBackLogin callBackLogin) {
        return NetWorkManager.getRequest()
                .getLogin("https://apps.iyuba.cn/v2/api.iyuba",protocol, username, password, sign, format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        callBackLogin.successLogin(loginBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackLogin.errorLogin((Exception) throwable);
                    }
                });

    }

    @Override
    public Disposable getLogout(String protocol, String format, String username, String password, String sign, LoginContract.CallBackLogout callBackLogout) {
        return NetWorkManager
                .getRequest()
                .getLogout("http://api.iyuba.com.cn/v2/api.iyuba",protocol,  format,  username,
                        password,  sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LogoutBean>() {
                    @Override
                    public void accept(LogoutBean logoutBean) throws Exception {

                        callBackLogout.successLogout(logoutBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackLogout.errorLogout((Exception) throwable);
                    }
                });
    }

}


