package com.sdiyuba.tedenglish.presenter.home;




import com.sdiyuba.tedenglish.model.bean.LoginBean;
import com.sdiyuba.tedenglish.model.bean.LogoutBean;
import com.sdiyuba.tedenglish.model.home.LoginModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.LoginContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class LoginPresenter extends BasePresenter<LoginContract.LoginView,LoginContract.LoginModel> implements LoginContract.LoginPresenter {
    @Override
    protected LoginContract.LoginModel initModel() {
        return new LoginModel();
    }

    @Override
    public void getLogin(int protocol, String username, String password, String sign, String format) {

        Disposable disposable = model.getLogin(protocol, username, password, sign, format, new LoginContract.CallBackLogin() {
            @Override
            public void successLogin(LoginBean loginBean) {
                view.getLogin(loginBean);
            }

            @Override
            public void errorLogin(Exception e) {

                if (e instanceof UnknownHostException){
                    view.toast("请求失败");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getLogout(String protocol, String format, String username, String password, String sign) {
        Disposable disposable=model.getLogout(protocol, format, username,
                password, sign, new LoginContract.CallBackLogout() {
                    @Override
                    public void successLogout(LogoutBean logoutBean) {
                        view.getLogout(logoutBean);
                    }

                    @Override
                    public void errorLogout(Exception e) {
                        if (e instanceof UnknownHostException) {

                            view.toast("请求超时");
                        }
                    }

                });
        addSubscribe(disposable);
    }
}
