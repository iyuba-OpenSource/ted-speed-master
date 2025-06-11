package com.sdiyuba.tedenglish.presenter.home;




import com.sdiyuba.tedenglish.model.bean.LikeBean;
import com.sdiyuba.tedenglish.model.bean.exportpdfBean;
import com.sdiyuba.tedenglish.model.home.likeModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.util.PullXmlUtil;
import com.sdiyuba.tedenglish.view.home.LikeContract;

import java.io.IOException;
import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class likePresenter extends BasePresenter<LikeContract.likeView, LikeContract.likeModel> implements LikeContract.likePresenter {
    @Override
    protected LikeContract.likeModel initModel() {
        return new likeModel();
    }

    @Override
    public void getLike(String groupName, int sentenceFlg, int appId, String userId, String type, String voaId, int sentenceId, String topic, String format) {
        Disposable disposable = model.getLike(groupName, sentenceFlg, appId, userId, type, voaId, sentenceId, topic, format, new LikeContract.CallBackLike() {


            @Override
            public void successLike(ResponseBody responseBody) {

                try {
                    String s = responseBody.string().trim();
                    if (s != null && !s.equals("")) {

                        //解析json了自定义
                        LikeBean bean = PullXmlUtil.parseXMLWithPull(s);

                        //sentenceId = timing
                        view.getLike(bean.getResult(), bean.getType(), voaId, sentenceId + "");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void errorLike(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getexport(String idtype, String id, int isenglish) {

        Disposable disposable = model.getexport(idtype, id, isenglish, new LikeContract.CallBackexport() {
            @Override
            public void successexport(exportpdfBean exportpdfBean) {
                view.getexport(exportpdfBean);
            }

            @Override
            public void errorexport(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
