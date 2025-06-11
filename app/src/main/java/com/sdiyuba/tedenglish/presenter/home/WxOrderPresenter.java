package com.sdiyuba.tedenglish.presenter.home;







import com.sdiyuba.tedenglish.model.bean.WxOrderBean;
import com.sdiyuba.tedenglish.model.home.WxOrderModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.WxOrderContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class WxOrderPresenter extends BasePresenter<WxOrderContract.WxOrderView,WxOrderContract.WxOrderModel> implements WxOrderContract.WxOrderPresenter {
    @Override
    protected WxOrderContract.WxOrderModel initModel() {
        return new WxOrderModel();
    }

    @Override
    public void getWxOrder(String wxkey, String weixinApp, int appid, String uid, String money, int amount, int productid, String format, String sign,int deduction) {

        Disposable disposable = model.getWxOrder(wxkey, weixinApp, appid, uid, money, amount, productid, format, sign, deduction ,new WxOrderContract.CallBackWxOrder() {
            @Override
            public void successWxOrder(WxOrderBean wxOrderBean) {
                view.getWxOrder(wxOrderBean);
            }

            @Override
            public void errorWxOrder(Exception e) {

                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
