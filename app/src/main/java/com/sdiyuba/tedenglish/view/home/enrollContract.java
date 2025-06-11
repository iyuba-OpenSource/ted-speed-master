package com.sdiyuba.tedenglish.view.home;




import com.sdiyuba.tedenglish.model.bean.enrollBean;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface enrollContract {


    interface enrollView extends LoadingView {
        void getEnroll(enrollBean enrollbean);
    }

    interface enrollPresenter extends IBasePresenter<enrollView> {
        void getEnroll( int protocol,  String username, String password
                ,  String mobile,  String sign,  String json);

    }
    interface enrollModel extends BaseModel {

        Disposable getEnroll(int protocol,  String username, String password
                ,  String mobile,  String sign,  String json, CallBackEnroll callBackEnroll);
    }


    interface CallBackEnroll{

        void successEnroll(enrollBean enrollbean);

        void errorEnroll(Exception e);

    }


}
