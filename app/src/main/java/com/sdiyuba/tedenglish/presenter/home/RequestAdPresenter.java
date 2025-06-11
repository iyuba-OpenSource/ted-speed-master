package com.sdiyuba.tedenglish.presenter.home;



import com.sdiyuba.tedenglish.model.bean.AdEntryBean;
import com.sdiyuba.tedenglish.model.home.RequestAdModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.RequestAdContract;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class RequestAdPresenter extends BasePresenter<RequestAdContract.RequestAdView, RequestAdContract.RequestAdModel>
        implements RequestAdContract.RequestAdPresenter {


    @Override
    protected RequestAdContract.RequestAdModel initModel() {
        return new RequestAdModel();
    }

    @Override
    public void getAdEntryAll(String appId, int flag, String uid) {

        Disposable disposable = model.getAdEntryAll(appId, flag, uid, new RequestAdContract.Callback() {
            @Override
            public void success(List<AdEntryBean> adEntryBeans) {

                if (adEntryBeans.size() != 0) {

                    AdEntryBean adEntryBean = adEntryBeans.get(0);
                    if (adEntryBean.getResult().equals("1")) {

                        view.getAdEntryAllComplete(adEntryBean);
                    } else if (adEntryBean.getResult().equals("-1")) {

                        view.getAdEntryAllComplete(adEntryBean);
                    }
                }
            }

            @Override
            public void error(Exception e) {


            }
        });
        addSubscribe(disposable);
    }
}
