package com.sdiyuba.tedenglish.view.home;


import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.LoginBean;
import com.sdiyuba.tedenglish.model.bean.LogoutBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface LoginContract {
    interface LoginView extends LoadingView {
        //获取了数据以后从这里修改
        void getLogin(LoginBean loginBean);

        //注销
        void getLogout(LogoutBean logoutbean);


    }

    //格式差不多,请求的参数对应括号里得参数修改
    interface LoginPresenter extends IBasePresenter<LoginView> {
        void getLogin(int protocol,String username,String password,String sign,String format);

        void getLogout(String protocol,  String format,  String username,
                       String password,  String sign);
    }

    interface LoginModel extends BaseModel {

        Disposable getLogin(int protocol,String username,String password,String sign,String format, CallBackLogin callBackLogin);

        Disposable getLogout(String protocol,  String format,  String username,
                             String password,  String sign, CallBackLogout callBackLogout);
    }

    interface CallBackLogin{
        //获取了以后修改
        void successLogin(LoginBean loginBean);

        void errorLogin(Exception e);
    }

    interface CallBackLogout {

        void successLogout (LogoutBean logoutBean);

        void errorLogout (Exception e);

    }

}
