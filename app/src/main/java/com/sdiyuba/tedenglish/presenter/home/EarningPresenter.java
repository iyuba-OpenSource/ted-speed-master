package com.sdiyuba.tedenglish.presenter.home;

import com.sdiyuba.tedenglish.model.bean.EarningBean;
import com.sdiyuba.tedenglish.model.bean.GoldExchangeBean;
import com.sdiyuba.tedenglish.model.home.EarningModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.earningContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class EarningPresenter extends BasePresenter<earningContract.earningView,earningContract.EarningModel> implements earningContract.EarningPresenter {
    @Override
    protected earningContract.EarningModel initModel() {
        return new EarningModel();
    }

    @Override
    public void getEarning(int appid, String uid, String sign, String noncestr, int pages, int pageCount, String operation) {

        Disposable disposable = model.getEarning(appid, uid, sign, noncestr, pages, pageCount, operation, new earningContract.CallBackEarning() {
            @Override
            public void successEarning(EarningBean earningBean) {
                view.getEarning(earningBean);
            }

            @Override
            public void errorEarning(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");

                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void GoldExchange(int appid, String uid, String sign, String noncestr) {
        Disposable disposable = model.GoldExchange(appid, uid, sign, noncestr, new earningContract.CallBackGoldExchange() {
            @Override
            public void successGoldExchange(GoldExchangeBean goldExchangeBean) {
                view.GoldExchange(goldExchangeBean);
            }

            @Override
            public void errorGoldExchange(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");

                }
            }
        });
        addSubscribe(disposable);
    }
}
