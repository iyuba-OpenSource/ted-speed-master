package com.sdiyuba.tedenglish.presenter.home;





import com.sdiyuba.tedenglish.model.bean.HearingBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterBean;
import com.sdiyuba.tedenglish.model.bean.SpokeBean;
import com.sdiyuba.tedenglish.model.home.LearningReportModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.LearningReportContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class LearningReportPresenter extends BasePresenter<LearningReportContract.LearningReportView, LearningReportContract.LearningReportModel> implements LearningReportContract.LearningReportPresenter {
    @Override
    protected LearningReportContract.LearningReportModel initModel() {
        return new LearningReportModel();
    }


    @Override
    public void getHearing(String mode, String uid, String type, int start, int total, String sign) {
        Disposable disposable = model.getHearing(mode, uid, type, start, total, sign, new LearningReportContract.CallBackHearing() {
            @Override
            public void successHearing(HearingBean hearingBean) {
                view.getHearing(hearingBean);
            }

            @Override
            public void errorHearing(Exception e) {

                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getSpoke(String userId, String newstype, String language, String type, String sign) {
        Disposable disposable= model.getSpoke(userId, newstype, language, type, sign, new LearningReportContract.CallBackSpoke() {
            @Override
            public void successSpoke(SpokeBean spokeBean) {
                view.getSpoke(spokeBean);
            }

            @Override
            public void errorSpoke(Exception e) {

                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getReadReporter(String uid, String type, int total, int start, String mode, String sign) {
        Disposable disposable= model.getReadReporter(uid, type, total, start, mode, sign, new LearningReportContract.CallBackReadReporter() {
            @Override
            public void successReadReporter(ReadReporterBean readReporterBean) {
                view.getReadReporter(readReporterBean);
            }

            @Override
            public void errorReadReporter(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
