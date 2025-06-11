package com.sdiyuba.tedenglish.model.home;






import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.WxOrderBean;
import com.sdiyuba.tedenglish.view.home.WxOrderContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WxOrderModel implements WxOrderContract.WxOrderModel {
    @Override
    public Disposable getWxOrder(String wxkey, String weixinApp, int appid, String uid, String money, int amount, int productid, String format, String sign,int deduction, WxOrderContract.CallBackWxOrder callBackWxOrder) {
        return NetWorkManager
                .getRequest()
                .getWxOrder("http://vip.iyuba.cn/weixinPay.jsp?",wxkey,  weixinApp,  appid,  uid,  money,  amount,  productid,  format,  sign, deduction)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WxOrderBean>() {
                    @Override
                    public void accept(WxOrderBean wxOrderBean) throws Exception {
                        callBackWxOrder.successWxOrder(wxOrderBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackWxOrder.errorWxOrder((Exception) throwable);
                    }
                });
    }
}
