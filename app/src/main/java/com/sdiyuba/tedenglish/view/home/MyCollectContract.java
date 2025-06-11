package com.sdiyuba.tedenglish.view.home;




import com.sdiyuba.tedenglish.model.bean.CollectBean;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface MyCollectContract {

    interface MycollectView extends LoadingView {
        void getCollect(CollectBean collectBean);
    }

    interface MycollectPresenter extends IBasePresenter<MycollectView> {
        void getCollect(String userId, String topic, int appid, int sentenceFlg,
                        String format, String sign);

    }

    interface MycollectModel extends BaseModel {

        Disposable getCollect(String userId, String topic, int appid, int sentenceFlg,
                              String format, String sign, CallBackMycollect callBackMycollect);
    }

    interface CallBackMycollect{

        void successMycollect(CollectBean collectBean);

        void errorMycollect(Exception e);

    }





}
