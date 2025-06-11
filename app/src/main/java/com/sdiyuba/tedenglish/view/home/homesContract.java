package com.sdiyuba.tedenglish.view.home;

import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.homeBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface homesContract {


    interface homeView extends LoadingView {
        void getHome(homeBean homebean);
    }

    interface HomePresenter extends IBasePresenter<homeView> {
        void getHome( String type, String format, int appId, int maxid ,int pages, int pageNum,  int parentID);

    }

    interface HomeModel extends BaseModel {

        Disposable getHome(String type, String format, int appId, int maxid ,int pages, int pageNum,  int parentID, homesContract.CallBackHome callBackAbcHome);
    }


    interface CallBackHome {

        void successHome(homeBean homebean);

        void errorHome(Exception e);

    }


}
