package com.sdiyuba.tedenglish.view.home;

import com.sdiyuba.tedenglish.model.bean.RewardsBean;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;

public interface rewardsContract {

    interface RewardsView extends LoadingView {
        void getRewards(RewardsBean rewardsBean);
    }

    interface RewardsPresenter extends IBasePresenter<rewardsContract.RewardsView> {
        void getRewards(int appid, String uid, String idindex, String sign, int srid, String noncestr,int goldCoins);

    }

    interface RewardsModel extends BaseModel {

        Disposable getRewards(int appid, String uid, String idindex, String sign, int srid, String noncestr,int goldCoins, rewardsContract.CallBackRewards callBackRewards);
    }


    interface CallBackRewards {

        void success(RewardsBean rewardsBean);

        void error(Exception e);

    }
}
