package com.sdiyuba.tedenglish.presenter.home;




import com.sdiyuba.tedenglish.model.bean.VipBean;
import com.sdiyuba.tedenglish.model.bean.VipParseBean;
import com.sdiyuba.tedenglish.model.home.vipBuyModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.vipBuyContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class vipBuyPresenter extends BasePresenter<vipBuyContract.vipBuyView,vipBuyContract.vipBuyModel> implements vipBuyContract.vipBuyParsenter{


    @Override
    protected vipBuyContract.vipBuyModel initModel() {
        return new vipBuyModel();
    }



    @Override
    public void getVip(int app_id, int userId, String code, String WIDtotal_fee, int amount, int product_id, String WIDbody, String WIDsubject,int deduction) {
        Disposable disposable=model.getVip(app_id, userId, code, WIDtotal_fee, amount, product_id, WIDbody, WIDsubject, deduction ,new vipBuyContract.CallBackVipBuy() {
            @Override
            public void successVipBuy(VipParseBean vipParseBean) {
                view.getVip(vipParseBean);
            }

            @Override
            public void errorVipBuy(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);

    }

    @Override
    public void callbackVip(String date) {
        Disposable disposable =model.callbackVip(date, new vipBuyContract.Callback_callbackVip() {
            @Override
            public void success_callbackVip(VipBean vipBean) {
                view.callbackVip(vipBean);
            }

            @Override
            public void error_callbackVip(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
    }
}
