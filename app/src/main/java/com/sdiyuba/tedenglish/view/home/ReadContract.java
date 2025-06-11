package com.sdiyuba.tedenglish.view.home;




import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.ReadBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface ReadContract {

    interface ReadView extends LoadingView {
        void getRead(ReadBean readBean);
    }

    interface ReadPresenter extends IBasePresenter<ReadView> {
        void getRead(String format, String uid,  String BeginTime,  String EndTime,  String appName,  String Lesson, String LessonId,int appId,  String Device
                ,  String DeviceId,  int EndFlg,  int wordcount,  int categoryid,  String platform,int rewardVersion);
    }

    interface ReadModel extends BaseModel {

        Disposable getRead(String format, String uid,  String BeginTime,  String EndTime,  String appName,  String Lesson, String LessonId,int appId,  String Device
                ,  String DeviceId,  int EndFlg,  int wordcount,  int categoryid,  String platform,int rewardVersion, CallBackRead callBackRead);
    }


    interface CallBackRead{

        void successRead(ReadBean readBean);

        void errorRead(Exception e);

    }
}
