package com.sdiyuba.tedenglish.view.home;


import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.joinWordBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface joinWordContract {

    interface joinWordView extends LoadingView {
        void joinWord(joinWordBean joinWordBean);

    }

    interface joinWordPresenter extends IBasePresenter<joinWordView> {
        void joinWord(String groupName,  String userId,String mod,
                      String word,  String format);

    }

    interface joinWordModel extends BaseModel {

        Disposable joinWord(String groupName,  String userId,String mod,
                            String word,  String format, CallBackjoinWord callBackjoinWord);
    }

    interface CallBackjoinWord{

        void successjoinWord(joinWordBean joinWordBean);

        void errorjoinWord(Exception e);

    }
}
