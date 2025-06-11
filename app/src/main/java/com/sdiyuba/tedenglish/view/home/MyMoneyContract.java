package com.sdiyuba.tedenglish.view.home;



import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.MyMoneyBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface MyMoneyContract {

    interface MyMoneyView extends LoadingView {
        void getMyMoney(MyMoneyBean myMoneyBean);
    }

    interface MyMoneyPresenter extends IBasePresenter<MyMoneyView> {
        void getMyMoney(String uid, int pages,  int pageCount,  String sign);

    }
    interface MyMoneyModel extends BaseModel {

        Disposable getMyMoney(String uid, int pages, int pageCount, String sign, CallBackMyMoney callBackMyMoney);
    }

    interface CallBackMyMoney{

        void successMyMoney(MyMoneyBean myMoneyBean);

        void errorMyMoney(Exception e);

    }
}
