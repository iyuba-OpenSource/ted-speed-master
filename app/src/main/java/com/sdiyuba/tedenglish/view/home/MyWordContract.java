package com.sdiyuba.tedenglish.view.home;



import com.sdiyuba.tedenglish.model.bean.MyWordBean;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface MyWordContract {

    interface MyWordView extends LoadingView {
        void getMyWord(MyWordBean myWordBean);

    }

    interface MyWordPresenter extends IBasePresenter<MyWordView> {
        void getMyWord(String u,  int pageNumber, int pageCounts,  String format);
    }
    interface MyWordModel extends BaseModel {

        Disposable getMyWord(String u,  int pageNumber, int pageCounts,  String format, CallBackMyWord callBackMyWord);
    }
    interface CallBackMyWord{

        void successMyWord(MyWordBean myWordBean);

        void errorMyWord(Exception e);

    }
}


