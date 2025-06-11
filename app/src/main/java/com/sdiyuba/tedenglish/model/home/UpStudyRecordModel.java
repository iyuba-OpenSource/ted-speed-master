package com.sdiyuba.tedenglish.model.home;



import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.StudyRecordByTestModeBean;
import com.sdiyuba.tedenglish.view.home.UpStudyRecordContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpStudyRecordModel implements UpStudyRecordContract.UpStudyRecordModel {

    @Override
    public Disposable UpStudyRecord(String format, String uid, int appId, String BeginTime, String EndTime, String Lesson, String LessonId, String EndFlg, String Device, String platform, String IP, String sign, String TestMode, String TestNumber, String TestWords,int rewardVersion, UpStudyRecordContract.CallBackUpStudyRecord callBackUpStudyRecord) {
        return NetWorkManager
                .getRequest()
                .UpStudyRecordContract( format,  uid,  appId,  BeginTime,  EndTime,  Lesson,  LessonId,  EndFlg,  Device,  platform,  IP,  sign,  TestMode,  TestNumber,  TestWords, rewardVersion)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StudyRecordByTestModeBean>() {
                    @Override
                    public void accept(StudyRecordByTestModeBean studyRecordByTestModeBean) throws Exception {
                        callBackUpStudyRecord.successUpStudyRecord(studyRecordByTestModeBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackUpStudyRecord.errorUpStudyRecord((Exception) throwable);
                    }
                });
    }
}
