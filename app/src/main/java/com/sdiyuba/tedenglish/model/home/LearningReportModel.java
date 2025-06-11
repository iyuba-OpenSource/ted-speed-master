package com.sdiyuba.tedenglish.model.home;



import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.HearingBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterBean;
import com.sdiyuba.tedenglish.model.bean.SpokeBean;
import com.sdiyuba.tedenglish.view.home.LearningReportContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LearningReportModel implements LearningReportContract.LearningReportModel {


    @Override
    public Disposable getHearing(String mode, String uid, String type, int start, int total, String sign, LearningReportContract.CallBackHearing callBackHearing) {
        return NetWorkManager
                .getRequest()
                .getHearing("http://daxue.iyuba.cn/ecollege/getStudyRanking.jsp?",mode,  uid,  type,  start,  total,  sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HearingBean>() {
                    @Override
                    public void accept(HearingBean hearingBean) throws Exception {
                        callBackHearing.successHearing(hearingBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackHearing.errorHearing((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getSpoke(String userId, String newstype, String language, String type, String sign, LearningReportContract.CallBackSpoke callBackSpoke) {
        return NetWorkManager
                .getRequest()
                .getSpoke("http://ai.iyuba.cn/management/getHomePage.jsp?",userId,  newstype,  language,  type,  sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SpokeBean>() {
                    @Override
                    public void accept(SpokeBean spokeBean) throws Exception {
                        callBackSpoke.successSpoke(spokeBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackSpoke.errorSpoke((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getReadReporter(String uid, String type, int total, int start, String mode, String sign, LearningReportContract.CallBackReadReporter callBackReadReporter) {
        return NetWorkManager
                .getRequest()
                .getReadReporter("http://cms.iyuba.cn/newsApi/getNewsRanking.jsp?",uid,  type,  total,  start,  mode,  sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReadReporterBean>() {
                    @Override
                    public void accept(ReadReporterBean readReporterBean) throws Exception {
                        callBackReadReporter.successReadReporter(readReporterBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackReadReporter.errorReadReporter((Exception) throwable);
                    }
                });
    }


}
