package com.sdiyuba.tedenglish.model.home;




import com.sdiyuba.tedenglish.view.home.RankingContract;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.RankingBean;
import com.sdiyuba.tedenglish.model.bean.RankingDetailsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RankingModel implements RankingContract.RankingModel {
    @Override
    public Disposable getRanking(String uid, String type, String total, int start, String topic, String topicid, String sign, RankingContract.CallBackRanking callBackRanking) {
        return NetWorkManager.getRequest()
                .getRanking(uid, type, total, start, topic, topicid, sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RankingBean>() {
                               @Override
                               public void accept(RankingBean rankingBean) throws Exception {
                                   callBackRanking.successRanking(rankingBean);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   callBackRanking.errorRanking((Exception) throwable);
                               }
                           }
                )
                ;
    }

    @Override
    public Disposable getRankingDetails(String shuoshuoType, String topic, int topicId, int uid, String sign, RankingContract.CallBackRankingDetails callBackRankingDetails) {
        return NetWorkManager
                .getRequest()
                .getRankingDetails( shuoshuoType,  topic,  topicId,  uid,  sign )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RankingDetailsBean>() {
                    @Override
                    public void accept(RankingDetailsBean rankingDetailsBean) throws Exception {
                        callBackRankingDetails.successRankingDetails(rankingDetailsBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackRankingDetails.errorRankingDetails((Exception) throwable);
                    }
                });
    }


}
