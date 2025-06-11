package com.sdiyuba.tedenglish.model.home;

import com.sdiyuba.tedenglish.model.bean.RewardsBean;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.view.home.rewardsContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class rewardsModel implements rewardsContract.RewardsModel{


    @Override
    public Disposable getRewards(int appid, String uid, String idindex, String sign, int srid, String noncestr, int goldCoins, rewardsContract.CallBackRewards callBackRewards) {
        return NetWorkManager
                .getRequest()
                .getRewards("http://api.iyuba.cn/credits/goldCoinRewards.jsp?", appid,  uid,  idindex,  sign,  srid,  noncestr,  goldCoins)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RewardsBean>() {
                    @Override
                    public void accept(RewardsBean rewardsBean) throws Exception {
                        callBackRewards.success(rewardsBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackRewards.error((Exception) throwable);
                    }
                });
    }
}
