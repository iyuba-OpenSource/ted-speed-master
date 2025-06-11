package com.sdiyuba.tedenglish.model.home;



import com.sdiyuba.tedenglish.model.bean.ReadBean;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.view.home.ReadContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReadModel implements ReadContract.ReadModel {
    @Override
    public Disposable getRead(String format, String uid, String BeginTime, String EndTime, String appName, String Lesson, String LessonId, int appId, String Device, String DeviceId, int EndFlg, int wordcount, int categoryid, String platform, int rewardVersion, ReadContract.CallBackRead callBackRead) {
        return NetWorkManager
                .getRequest()
                .getRead("http://daxue.iyuba.cn/ecollege/updateNewsStudyRecord.jsp?",format, uid,  BeginTime,  EndTime,  appName,  Lesson,  LessonId,  appId,  Device,  DeviceId,  EndFlg,  wordcount,  categoryid, platform,rewardVersion)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReadBean>() {
                    @Override
                    public void accept(ReadBean readBean) throws Exception {
                        callBackRead.successRead(readBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackRead.errorRead((Exception) throwable);
                    }
                });
    }
}
