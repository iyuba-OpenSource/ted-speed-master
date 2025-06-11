package com.sdiyuba.tedenglish.presenter.home;

import com.sdiyuba.tedenglish.model.bean.oriPagesBean;
import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;
import com.sdiyuba.tedenglish.model.home.oriModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.oriContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class oriPresenter extends BasePresenter<oriContract.oriView,oriContract.oriModel> implements oriContract.oriPresenter {
    @Override
    protected oriContract.oriModel initModel() {
        return new oriModel();
    }


    @Override
    public void getOriPages(String type, String format, int voaid) {

        Disposable disposable = model.getOriPages(type, format, voaid, new oriContract.CallBackOriPages() {
            @Override
            public void success(oriPagesBean oriPagesBean) {
                view.getOriPages(oriPagesBean);
            }

            @Override
            public void error(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");

                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getOriSentences(String format, int voaid) {

        Disposable disposable = model.getOriSentences(format, voaid, new oriContract.CallBackOriSentences() {
            @Override
            public void success(oriSentencesBean oriSentencesBean) {
                view.getOriSentences(oriSentencesBean);
            }

            @Override
            public void error(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");

                }
            }
        });
        addSubscribe(disposable);
    }
}
