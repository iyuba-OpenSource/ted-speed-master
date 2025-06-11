package com.sdiyuba.tedenglish.model.home;




import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.MyMoneyBean;
import com.sdiyuba.tedenglish.view.home.MyMoneyContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyMoneyModel implements MyMoneyContract.MyMoneyModel {
    @Override
    public Disposable getMyMoney(String uid, int pages, int pageCount, String sign, MyMoneyContract.CallBackMyMoney callBackMyMoney) {
        return NetWorkManager
                .getRequest()
                .getMyMoney("http://api.iyuba.cn/credits/getuseractionrecord.jsp?",uid,pages,pageCount,sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MyMoneyBean>() {
                    @Override
                    public void accept(MyMoneyBean myMoneyBean) throws Exception {
                        callBackMyMoney.successMyMoney(myMoneyBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackMyMoney.errorMyMoney((Exception) throwable);
                    }
                });
    }
}
