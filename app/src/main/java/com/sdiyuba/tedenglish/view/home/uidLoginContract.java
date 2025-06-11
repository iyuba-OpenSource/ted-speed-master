package com.sdiyuba.tedenglish.view.home;





import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface uidLoginContract {
    interface uidLoginView extends LoadingView {

        void uidLogin(UidBean uidBean);
    }

    interface uidLoginPresenter extends IBasePresenter<uidLoginView> {

        void uidLogin(String platform, String format, String protocol
                , String id, String myid, int appid, String sign);
    }
    interface  uidLoginModel extends BaseModel {
        Disposable uidLogin(String platform, String format, String protocol
                , String id, String myid, int appid, String sign, CallBackuidLogin callBackuidLogin);
    }


    interface CallBackuidLogin {

        void successuidLogin (UidBean uidBean);

        void erroruidLogin (Exception e);

    }
}
