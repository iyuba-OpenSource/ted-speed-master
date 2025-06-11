package com.sdiyuba.tedenglish.model.home;




import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.homeBean;
import com.sdiyuba.tedenglish.view.home.homesContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import io.reactivex.schedulers.Schedulers;

public class HomeModel implements homesContract.HomeModel {

    @Override
    public Disposable getHome(String type, String format, int appId, int maxid ,int pages, int pageNum,  int parentID, homesContract.CallBackHome callBackHome) {
        return NetWorkManager
                .getRequest()
                .getHome("https://apps.iyuba.cn/iyuba/titleTed.jsp?", type,  format,  appId,  maxid , pages,  pageNum,   parentID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<homeBean>() {
                    @Override
                    public void accept(homeBean homeBean) throws Exception {
                        callBackHome.successHome(homeBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        callBackHome.errorHome((Exception) throwable);
                    }
                });
    }
}
