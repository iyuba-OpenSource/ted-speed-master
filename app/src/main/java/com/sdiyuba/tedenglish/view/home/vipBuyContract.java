package com.sdiyuba.tedenglish.view.home;






import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.VipBean;
import com.sdiyuba.tedenglish.model.bean.VipParseBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface vipBuyContract {

    interface vipBuyView extends LoadingView {
        void getVip(VipParseBean vipParseBean);

        //重新登录
        void callbackVip(VipBean vipBean);
    }

    interface vipBuyParsenter extends IBasePresenter<vipBuyView> {
        void getVip(int app_id, int userId,
                    String code, String WIDtotal_fee, int amount,
                    int product_id, String WIDbody, String WIDsubject,int deduction);

        void callbackVip(String date);
    }

    interface vipBuyModel extends BaseModel {

        Disposable getVip(int app_id, int userId,
                          String code, String WIDtotal_fee, int amount,
                          int product_id, String WIDbody, String WIDsubject,int deduction, CallBackVipBuy callBackVipBuy);

        Disposable callbackVip(String date, Callback_callbackVip callback_callbackVip);
    }


    interface CallBackVipBuy {

        void successVipBuy(VipParseBean vipParseBean);

        void errorVipBuy(Exception e);

    }
    interface Callback_callbackVip{

        void success_callbackVip(VipBean vipBean);

        void error_callbackVip(Exception e);
    }


}
