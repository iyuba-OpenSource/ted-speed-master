package com.sdiyuba.tedenglish.presenter.home;





import com.sdiyuba.tedenglish.model.home.joinWordModel;
import com.sdiyuba.tedenglish.view.home.joinWordContract;
import com.sdiyuba.tedenglish.model.bean.joinWordBean;
import com.sdiyuba.tedenglish.presenter.BasePresenter;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class joinWordPresenter extends BasePresenter<joinWordContract.joinWordView,joinWordContract.joinWordModel> implements joinWordContract.joinWordPresenter{
    @Override
    protected joinWordContract.joinWordModel initModel() {
        return new joinWordModel();
    }

    @Override
    public void joinWord(String groupName, String userId, String mod, String word, String format) {
        Disposable disposable=model.joinWord(groupName, userId, mod, word, format, new joinWordContract.CallBackjoinWord() {
            @Override
            public void successjoinWord(joinWordBean joinWordBean) {
                view.joinWord(joinWordBean);
            }

            @Override
            public void errorjoinWord(Exception e) {

                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
