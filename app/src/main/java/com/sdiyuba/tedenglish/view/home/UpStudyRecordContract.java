package com.sdiyuba.tedenglish.view.home;


import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.StudyRecordByTestModeBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface UpStudyRecordContract {
    interface UpStudyRecordView extends LoadingView {
        void UpStudyRecord(StudyRecordByTestModeBean studyRecordByTestModeBean);
    }

    interface UpStudyRecordPresenter extends IBasePresenter<UpStudyRecordView> {
        void UpStudyRecord(String format, String uid,  int appId, String BeginTime
                , String EndTime,String Lesson, String LessonId, String EndFlg, String Device,  String platform
                ,  String IP,  String sign,  String TestMode,  String TestNumber, String TestWords,int rewardVersion);

    }
    interface UpStudyRecordModel extends BaseModel {

        Disposable UpStudyRecord(String format, String uid,  int appId, String BeginTime
                , String EndTime,String Lesson, String LessonId, String EndFlg, String Device,  String platform
                ,  String IP,  String sign,  String TestMode,  String TestNumber, String TestWords,int rewardVersion, CallBackUpStudyRecord callBackUpStudyRecord);
    }

    interface CallBackUpStudyRecord{

        void successUpStudyRecord(StudyRecordByTestModeBean studyRecordByTestModeBean);

        void errorUpStudyRecord(Exception e);

    }
}
