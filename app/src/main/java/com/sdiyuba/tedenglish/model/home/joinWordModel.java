package com.sdiyuba.tedenglish.model.home;



import com.sdiyuba.tedenglish.view.home.joinWordContract;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.joinWordBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class joinWordModel implements joinWordContract.joinWordModel {
    @Override
    public Disposable joinWord(String groupName, String userId, String mod, String word, String format, joinWordContract.CallBackjoinWord callBackjoinWord) {
        return NetWorkManager
                .getRequest()
                .joinword(groupName, userId, mod, word, format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<joinWordBean>() {
                    @Override
                    public void accept(joinWordBean joinWordBean) throws Exception {
                        callBackjoinWord.successjoinWord(joinWordBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackjoinWord.errorjoinWord((Exception) throwable);
                    }
                });
    }
}
