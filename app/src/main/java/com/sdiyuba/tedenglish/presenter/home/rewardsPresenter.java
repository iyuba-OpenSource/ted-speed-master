package com.sdiyuba.tedenglish.presenter.home;

import com.sdiyuba.tedenglish.model.bean.RewardsBean;
import com.sdiyuba.tedenglish.model.home.rewardsModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.rewardsContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class rewardsPresenter extends BasePresenter<rewardsContract.RewardsView,rewardsContract.RewardsModel> implements rewardsContract.RewardsPresenter {
    @Override
    protected rewardsContract.RewardsModel initModel() {
        return  new rewardsModel();
    }

    @Override
    public void getRewards(int appid, String uid, String idindex, String sign, int srid, String noncestr, int goldCoins) {

        Disposable disposable  = model.getRewards(appid, uid, idindex, sign, srid, noncestr,  goldCoins, new rewardsContract.CallBackRewards() {
            @Override
            public void success(RewardsBean rewardsBean) {
                view.getRewards(rewardsBean);
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
