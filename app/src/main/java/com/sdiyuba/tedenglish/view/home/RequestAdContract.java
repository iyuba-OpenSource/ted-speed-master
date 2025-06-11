package com.sdiyuba.tedenglish.view.home;

import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.bean.AdEntryBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface RequestAdContract {


    interface RequestAdView extends LoadingView {

        void getAdEntryAllComplete(AdEntryBean adEntryBean);
    }


    interface RequestAdPresenter extends IBasePresenter<RequestAdView> {

        void getAdEntryAll(String appId, int flag, String uid);
    }


    interface RequestAdModel extends BaseModel {

        Disposable getAdEntryAll(String appId, int flag, String uid, Callback callback);
    }

    interface Callback {

        void success(List<AdEntryBean> adEntryBeans);

        void error(Exception e);
    }
}
