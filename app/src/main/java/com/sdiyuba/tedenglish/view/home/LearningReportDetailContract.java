package com.sdiyuba.tedenglish.view.home;




import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.HearingDetailBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterDetailBean;
import com.sdiyuba.tedenglish.model.bean.SpokenDetailBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface LearningReportDetailContract {

    interface LearningReportDetailView extends LoadingView {
        void getHearingDetail(HearingDetailBean hearingDetailBean);

        void getSpokenDetail(SpokenDetailBean spokenDetailBean);

        void getReadReporterDetail(ReadReporterDetailBean readReporterDetailBean);


    }

    interface LearningReportDetailPresenter extends IBasePresenter<LearningReportDetailView> {
        void getHearingDetail(int NumPerPage,  int Pageth,  int TestMode, String uid,  String sign);

        void getSpokenDetail(String userId, String newstype,String language, int lastId,int pageCounts);

        void getReadReporterDetail( String uid, String format, int thisNumber,int startNumber);
    }

    interface LearningReportDetailModel extends BaseModel {

        Disposable  getHearingDetail(int NumPerPage, int Pageth, int TestMode, String uid, String sign, CallBackLearningReportDetail callBackLearningReportDetail);

        Disposable getSpokenDetail(String userId, String newstype, String language, int lastId, int pageCounts, CallBackSpokenDetail callBackSpokenDetail);

        Disposable getReadReporterDetail( String uid, String format, int thisNumber,int startNumber, CallBackReadReporterDetail callBackReadReporterDetail );

    }




    interface CallBackLearningReportDetail{

        void successLearningReportDetail(HearingDetailBean hearingDetailBean);

        void errorLearningReportDetail(Exception e);

    }

    interface CallBackSpokenDetail{

        void successSpokenDetail(SpokenDetailBean spokenDetailBean);

        void errorSpokenDetail(Exception e);

    }

    interface CallBackReadReporterDetail {

        void successReadReporterDetail (ReadReporterDetailBean readReporterDetailBean);

        void errorReadReporter(Exception e);

    }



}
