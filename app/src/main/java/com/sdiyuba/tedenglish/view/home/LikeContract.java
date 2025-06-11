package com.sdiyuba.tedenglish.view.home;



import com.sdiyuba.tedenglish.model.bean.exportpdfBean;
import com.sdiyuba.tedenglish.view.LoadingView;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public interface LikeContract {

    interface likeView extends LoadingView {

        void getLike(int result,String type, String voaId, String timing);

        void getexport(exportpdfBean exportpdfBean);

    }
    interface likePresenter extends IBasePresenter<likeView> {
        void getLike(String groupName, int sentenceFlg, int appId, String userId, String type
                , String voaId, int sentenceId, String topic, String format);

        void getexport(String idtype,String id,  int isenglish);

    }


    interface likeModel extends BaseModel {

        Disposable getLike(String groupName, int sentenceFlg, int appId, String userId, String type
                , String voaId, int sentenceId, String topic, String format, CallBackLike callBackLike );
        Disposable getexport(String idtype, String id, int isenglish, CallBackexport callBackexport);
    }

    interface CallBackLike{



        void successLike(ResponseBody responseBody);

        void errorLike(Exception e);

    }
    interface CallBackexport{

        void successexport(exportpdfBean exportpdfBean);

        void errorexport(Exception e);

    }
}
