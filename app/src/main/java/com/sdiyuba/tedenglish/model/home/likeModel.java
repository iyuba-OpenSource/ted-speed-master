package com.sdiyuba.tedenglish.model.home;



import com.sdiyuba.tedenglish.model.bean.exportpdfBean;
import com.sdiyuba.tedenglish.view.home.LikeContract;
import com.sdiyuba.tedenglish.model.NetWorkManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class likeModel implements LikeContract.likeModel {


    @Override
    public Disposable getLike(String groupName, int sentenceFlg, int appId, String userId, String type, String voaId, int sentenceId, String topic, String format, LikeContract.CallBackLike callBackLike) {

        return NetWorkManager
                .getRequest()
                .getLike(groupName, sentenceFlg,appId,userId, type,  voaId,  sentenceId,  topic, format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        callBackLike.successLike(responseBody);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackLike.errorLike((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getexport(String idtype, String id, int isenglish, LikeContract.CallBackexport callBackexport) {

        return NetWorkManager
                .getRequest()
                .getexport( "http://api.qomolama.cn/getPdfFile_new.jsp?",idtype,  id,  isenglish)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<exportpdfBean>() {
                    @Override
                    public void accept(exportpdfBean exportpdfBean) throws Exception {

                        callBackexport.successexport(exportpdfBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackexport.errorexport((Exception) throwable);
                    }
                });
    }
}
