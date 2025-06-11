package com.sdiyuba.tedenglish.model.home;

import com.sdiyuba.tedenglish.model.bean.oriPagesBean;
import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.view.home.oriContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class oriModel implements oriContract.oriModel{
    @Override
    public Disposable getOriPages(String type, String format, int voaid, oriContract.CallBackOriPages callBackOriPages) {
        return NetWorkManager.getRequest()
                .getOriPages("https://apps.iyuba.cn/iyuba/titleOneApi.jsp?",type,format,voaid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<oriPagesBean>() {
                    @Override
                    public void accept(oriPagesBean oriPagesBean) throws Exception {
                        callBackOriPages.success(oriPagesBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackOriPages.error((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getOriSentences(String format, int voaid, oriContract.CallBackOriSentences callBackOriSentences) {
        return NetWorkManager
                .getRequest()
                .getOriSentences("https://apps.iyuba.cn/iyuba/textExamApi.jsp?",format,voaid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<oriSentencesBean>() {
                    @Override
                    public void accept(oriSentencesBean oriSentencesBean) throws Exception {
                        callBackOriSentences.success(oriSentencesBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackOriSentences.error((Exception) throwable);
                    }
                });
    }
}
