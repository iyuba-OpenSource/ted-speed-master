package com.sdiyuba.tedenglish.view.home;

import com.sdiyuba.tedenglish.model.bean.EarningBean;
import com.sdiyuba.tedenglish.model.bean.GoldExchangeBean;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface earningContract {

    interface earningView extends LoadingView {
        //获取奖励记录
        void getEarning(EarningBean earningBean);

        void GoldExchange(GoldExchangeBean goldExchangeBean);



    }

    interface EarningPresenter extends IBasePresenter<earningContract.earningView> {
        void getEarning(int appid, String uid, String sign,String noncestr, int pages,  int pageCount, String operation);

        void GoldExchange(int appid, String uid,  String sign,  String noncestr);
    }

    interface EarningModel extends BaseModel {

        Disposable getEarning(int appid, String uid, String sign,String noncestr, int pages,  int pageCount, String operation, earningContract.CallBackEarning callBackEarning );

        Disposable GoldExchange(int appid, String uid,  String sign,  String noncestr,earningContract.CallBackGoldExchange callBackGoldExchange);
    }


    interface CallBackEarning {

        void successEarning(EarningBean earningBean);

        void errorEarning(Exception e);

    }

    interface CallBackGoldExchange{

        void successGoldExchange(GoldExchangeBean goldExchangeBean);

        void errorGoldExchange(Exception e);

    }
}
