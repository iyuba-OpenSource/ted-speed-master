package com.sdiyuba.tedenglish.view.home;







import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.HearingBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterBean;
import com.sdiyuba.tedenglish.model.bean.SpokeBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface LearningReportContract {

    interface LearningReportView extends LoadingView {
        void getHearing(HearingBean hearingBean);

        void getSpoke(SpokeBean spokeBean);

        void getReadReporter(ReadReporterBean readReporterBean);
    }

    interface LearningReportPresenter extends IBasePresenter<LearningReportView> {
        void getHearing(String mode,  String uid,  String type,  int start, int total, String sign);

        void getSpoke(String userId,  String newstype,String language, String type,  String sign);

        void getReadReporter(String uid, String type, int total, int start, String mode, String sign);
    }

    interface LearningReportModel extends BaseModel {

        Disposable getHearing(String mode, String uid, String type, int start, int total, String sign, CallBackHearing callBackHearing);

        Disposable getSpoke(String userId,  String newstype,String language, String type,  String sign,CallBackSpoke callBackSpoke);

        Disposable getReadReporter(String uid, String type, int total, int start, String mode, String sign, CallBackReadReporter callBackReadReporter);

    }



    interface CallBackHearing{

        void successHearing(HearingBean hearingBean);

        void errorHearing(Exception e);

    }

    interface CallBackSpoke{

        void successSpoke(SpokeBean spokeBean);

        void errorSpoke(Exception e);

    }

    interface CallBackReadReporter {

        void successReadReporter(ReadReporterBean readReporterBean);

        void errorReadReporter(Exception e);

    }
}
