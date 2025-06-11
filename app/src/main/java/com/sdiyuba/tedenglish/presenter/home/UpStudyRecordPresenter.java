package com.sdiyuba.tedenglish.presenter.home;






import com.sdiyuba.tedenglish.model.bean.StudyRecordByTestModeBean;
import com.sdiyuba.tedenglish.model.home.UpStudyRecordModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.UpStudyRecordContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class UpStudyRecordPresenter extends BasePresenter<UpStudyRecordContract.UpStudyRecordView,UpStudyRecordContract.UpStudyRecordModel> implements UpStudyRecordContract.UpStudyRecordPresenter{

    @Override
    protected UpStudyRecordContract.UpStudyRecordModel initModel() {
        return new UpStudyRecordModel();
    }

    @Override
    public void UpStudyRecord(String format, String uid, int appId, String BeginTime, String EndTime, String Lesson, String LessonId, String EndFlg, String Device, String platform, String IP, String sign, String TestMode, String TestNumber, String TestWords,int rewardVersion) {
        Disposable disposable=model.UpStudyRecord(format, uid, appId, BeginTime, EndTime, Lesson, LessonId, EndFlg, Device, platform, IP, sign, TestMode, TestNumber, TestWords, rewardVersion, new UpStudyRecordContract.CallBackUpStudyRecord() {
            @Override
            public void successUpStudyRecord(StudyRecordByTestModeBean studyRecordByTestModeBean) {
                view.UpStudyRecord(studyRecordByTestModeBean);
            }

            @Override
            public void errorUpStudyRecord(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
