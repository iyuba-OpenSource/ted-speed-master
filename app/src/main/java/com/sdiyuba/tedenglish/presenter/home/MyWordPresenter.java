package com.sdiyuba.tedenglish.presenter.home;




import com.sdiyuba.tedenglish.model.home.MyWordModel;
import com.sdiyuba.tedenglish.model.bean.MyWordBean;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.MyWordContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class MyWordPresenter extends BasePresenter<MyWordContract.MyWordView,MyWordContract.MyWordModel> implements  MyWordContract.MyWordPresenter {

    @Override
    protected MyWordContract.MyWordModel initModel() {
        return new MyWordModel();
    }

    @Override
    public void getMyWord(String u, int pageNumber, int pageCounts, String format) {
        Disposable disposable=model.getMyWord(u, pageNumber, pageCounts, format, new MyWordContract.CallBackMyWord() {
            @Override
            public void successMyWord(MyWordBean myWordBean) {
                view.getMyWord(myWordBean);
            }

            @Override
            public void errorMyWord(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
