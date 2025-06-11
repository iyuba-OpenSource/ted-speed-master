package com.sdiyuba.tedenglish.model.home;



import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.VipBean;
import com.sdiyuba.tedenglish.model.bean.VipParseBean;
import com.sdiyuba.tedenglish.view.home.vipBuyContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class vipBuyModel implements vipBuyContract.vipBuyModel {
    @Override
    public Disposable getVip(int app_id, int userId, String code, String WIDtotal_fee, int amount, int product_id, String WIDbody, String WIDsubject,int deduction, vipBuyContract.CallBackVipBuy callBackVipBuy) {
        return NetWorkManager
                .getRequest()
                .getVip("http://vip.iyuba.cn/alipay.jsp", app_id, userId, code, WIDtotal_fee, amount, product_id, WIDbody, WIDsubject, deduction)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VipParseBean>() {
                    @Override
                    public void accept(VipParseBean vipParseBean) throws Exception {
                        callBackVipBuy.successVipBuy(vipParseBean);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackVipBuy.errorVipBuy((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable callbackVip(String date, vipBuyContract.Callback_callbackVip callback_callbackVip) {
        return NetWorkManager
                .getRequest()
                .callbackVip("http://vip.iyuba.cn/notifyAliNew.jsp",date)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VipBean>() {
                    @Override
                    public void accept(VipBean vipBean) throws Exception {

                        callback_callbackVip.success_callbackVip(vipBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback_callbackVip.error_callbackVip((Exception) throwable);
                    }
                });

    }
}
