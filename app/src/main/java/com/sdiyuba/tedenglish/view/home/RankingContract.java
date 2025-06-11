package com.sdiyuba.tedenglish.view.home;



import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.bean.RankingBean;
import com.sdiyuba.tedenglish.model.bean.RankingDetailsBean;

import io.reactivex.disposables.Disposable;

public interface RankingContract {
    interface RankingView extends LoadingView {

        void getRanking(RankingBean rankingBean);

        void getRankingDetails(RankingDetailsBean rankingDetailsBean);

    }

    interface RankingPresenter extends IBasePresenter<RankingView> {

        void getRanking(String uid, String type, String total, int start,
                        String topic, String topicid, String sign);

        void getRankingDetails(String shuoshuoType,  String  topic,
                               int topicId,  int uid, String sign);

    }

    interface RankingModel extends BaseModel {
        Disposable getRanking(String uid, String type, String total, int start,
                              String topic, String topicid, String sign, CallBackRanking callBackRanking);

        Disposable getRankingDetails(String shuoshuoType,  String  topic,
                                     int topicId,  int uid, String sign, CallBackRankingDetails callBackRankingDetails);
    }

    interface CallBackRanking {

        void successRanking(RankingBean rankingBean);

        void errorRanking(Exception e);

    }

    interface CallBackRankingDetails {

        void successRankingDetails (RankingDetailsBean rankingDetailsBean);

        void errorRankingDetails (Exception e);

    }


}
