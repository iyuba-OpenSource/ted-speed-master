package com.sdiyuba.tedenglish.view.home;



import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.ProverbBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface ProverbContract {


    interface ProverbView extends LoadingView {
        void getProverb(ProverbBean proverbBean);
    }

    interface ProverbPresenter extends IBasePresenter<ProverbView> {
        void getProverb();

    }
    interface ProverbModel extends BaseModel {

        Disposable getProverb(CallBackProverb callBackProverb);
    }

    interface CallBackProverb{

        void successProverb(ProverbBean proverbBean);

        void errorProverb(Exception e);

    }
}
