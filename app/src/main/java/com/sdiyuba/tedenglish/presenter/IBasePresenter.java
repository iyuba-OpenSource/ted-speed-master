package com.sdiyuba.tedenglish.presenter;





import com.sdiyuba.tedenglish.view.BaseView;

import io.reactivex.disposables.Disposable;

public interface IBasePresenter<V extends BaseView> {


    void attchView(V view);

    void detachView();

    void unSubscribe();

    void addSubscribe(Disposable disposable);
}
