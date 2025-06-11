package com.sdiyuba.tedenglish.presenter.home;

import com.sdiyuba.tedenglish.model.bean.homeBean;
import com.sdiyuba.tedenglish.model.home.HomeModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.homesContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class homePresenter extends BasePresenter<homesContract.homeView,homesContract.HomeModel> implements homesContract.HomePresenter{
    @Override
    protected homesContract.HomeModel initModel() {
        return new HomeModel();
    }

    @Override
    public void getHome(String type, String format, int appId, int maxid ,int pages, int pageNum,  int parentID) {

        Disposable disposable = model.getHome( type,  format,  appId,  maxid , pages,  pageNum,   parentID, new homesContract.CallBackHome() {
            @Override
            public void successHome(homeBean homebean) {
                view.getHome(homebean);

            }

            @Override
            public void errorHome(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");

                }
            }
        });
        addSubscribe(disposable);
    }
}
