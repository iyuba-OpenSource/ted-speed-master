package com.sdiyuba.tedenglish.view.home;



import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.AcquireWordBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface AcquireWordContract {

    interface AcquireWordView extends LoadingView {
        void getAcquireWord(AcquireWordBean acquireWordBean);
    }

    interface AcquireWordPresenter extends IBasePresenter<AcquireWordView> {
        void getAcquireWord(String q,String format);

    }
    interface AcquireWordModel extends BaseModel {

        Disposable getAcquireWord(String q,String format, CallBackAcquireWord callBackAcquireWord);
    }

    interface CallBackAcquireWord{

        void successAcquireWord(AcquireWordBean acquireWordBean);

        void errorAcquireWord(Exception e);

    }

}
