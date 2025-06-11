package com.sdiyuba.tedenglish.model.home;

import com.sdiyuba.tedenglish.model.bean.EarningBean;
import com.sdiyuba.tedenglish.model.bean.GoldExchangeBean;
import com.sdiyuba.tedenglish.view.home.earningContract;
import com.sdiyuba.tedenglish.model.NetWorkManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EarningModel implements earningContract.EarningModel
{
    @Override
    public Disposable getEarning(int appid, String uid, String sign, String noncestr, int pages, int pageCount, String operation, earningContract.CallBackEarning callBackEarning) {
        return NetWorkManager
                .getRequest()
                .getEarning("http://api.iyuba.cn/credits/getGoldCoinRecords.jsp?",appid,  uid,  sign,  noncestr,  pages,  pageCount,  operation)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EarningBean>() {
                    @Override
                    public void accept(EarningBean earningBean) throws Exception {
                        callBackEarning.successEarning(earningBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackEarning.errorEarning((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable GoldExchange(int appid, String uid, String sign, String noncestr, earningContract.CallBackGoldExchange callBackGoldExchange) {
        return NetWorkManager
                .getRequest()
                .GoldExchange("http://api.iyuba.cn/credits/goldCoinExchange.jsp?",appid,  uid,  sign,  noncestr)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GoldExchangeBean>() {
                    @Override
                    public void accept(GoldExchangeBean goldExchangeBean) throws Exception {
                        callBackGoldExchange.successGoldExchange(goldExchangeBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackGoldExchange.errorGoldExchange((Exception) throwable);
                    }
                });
    }
}
