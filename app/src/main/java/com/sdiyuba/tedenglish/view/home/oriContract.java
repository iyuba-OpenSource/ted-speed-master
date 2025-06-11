package com.sdiyuba.tedenglish.view.home;

import com.sdiyuba.tedenglish.model.bean.oriPagesBean;
import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface oriContract {


    interface oriView extends LoadingView {

        void getOriPages(oriPagesBean oriPagesbean);

        void getOriSentences(oriSentencesBean oriSentencesbean);
    }

    interface oriPresenter extends IBasePresenter<oriContract.oriView> {

        void getOriPages(String type,  String format, int voaid);

        void getOriSentences( String format, int voaid);

    }

    interface oriModel extends BaseModel {

        Disposable getOriPages(String type,  String format, int voaid,oriContract.CallBackOriPages callBackOriPages);

        Disposable getOriSentences(String format,  int voaid,oriContract.CallBackOriSentences callBackOriSentences);

    }


    interface CallBackOriPages {

        void success(oriPagesBean oriPagesBean);

        void error(Exception e);

    }

    interface CallBackOriSentences {

        void success(oriSentencesBean oriSentencesBean);

        void error(Exception e);

    }
}
