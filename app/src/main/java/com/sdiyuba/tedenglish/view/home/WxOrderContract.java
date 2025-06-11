package com.sdiyuba.tedenglish.view.home;






import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.WxOrderBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface WxOrderContract {


    interface WxOrderView extends LoadingView {
        void getWxOrder(WxOrderBean wxOrderBean);
    }

    interface WxOrderPresenter extends IBasePresenter<WxOrderView> {
        void getWxOrder(String wxkey,  String weixinApp, int appid,  String uid, String money, int amount, int productid, String format,  String sign,int deduction);

    }
    interface WxOrderModel extends BaseModel {

        Disposable getWxOrder(String wxkey,  String weixinApp, int appid,  String uid, String money, int amount, int productid, String format,  String sign,int deduction, CallBackWxOrder callBackWxOrder);
    }


    interface CallBackWxOrder{

        void successWxOrder(WxOrderBean wxOrderBean);

        void errorWxOrder(Exception e);

    }
}
