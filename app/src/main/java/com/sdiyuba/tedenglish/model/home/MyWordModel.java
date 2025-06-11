package com.sdiyuba.tedenglish.model.home;





import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.MyWordBean;
import com.sdiyuba.tedenglish.view.home.MyWordContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyWordModel implements MyWordContract.MyWordModel {
    @Override
    public Disposable getMyWord(String u, int pageNumber, int pageCounts, String format, MyWordContract.CallBackMyWord callBackMyWord) {
        return NetWorkManager
                .getRequest()
                .getMyWord( u,  pageNumber,  pageCounts,  format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MyWordBean>() {
                    @Override
                    public void accept(MyWordBean myWordBean) throws Exception {
                        callBackMyWord.successMyWord(myWordBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackMyWord.errorMyWord((Exception) throwable);
                    }
                });
    }
}
