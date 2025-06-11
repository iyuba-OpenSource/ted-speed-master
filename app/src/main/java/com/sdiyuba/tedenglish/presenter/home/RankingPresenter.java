package com.sdiyuba.tedenglish.presenter.home;


import com.sdiyuba.tedenglish.model.home.RankingModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.RankingContract;
import com.sdiyuba.tedenglish.model.bean.RankingBean;
import com.sdiyuba.tedenglish.model.bean.RankingDetailsBean;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class RankingPresenter extends BasePresenter<RankingContract.RankingView,RankingContract.RankingModel > implements RankingContract.RankingPresenter{
    @Override
    protected RankingContract.RankingModel initModel() {
        return new RankingModel();
    }

    @Override
    public void getRanking(String uid, String type, String total, int start, String topic,String topicid, String sign) {
        Disposable disposable = model.getRanking(uid, type, total, start, topic, topicid,sign,new RankingContract.CallBackRanking() {


            @Override
            public void successRanking(RankingBean rankingBean) {
                view.getRanking(rankingBean);
            }

            @Override
            public void errorRanking(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getRankingDetails(String shuoshuoType, String topic, int topicId, int uid, String sign) {

        Disposable disposable =model.getRankingDetails(shuoshuoType, topic, topicId, uid, sign, new RankingContract.CallBackRankingDetails() {
            @Override
            public void successRankingDetails(RankingDetailsBean rankingDetailsBean) {
                view.getRankingDetails(rankingDetailsBean);
            }

            @Override
            public void errorRankingDetails(Exception e) {

                if (e instanceof  UnknownHostException){
                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
