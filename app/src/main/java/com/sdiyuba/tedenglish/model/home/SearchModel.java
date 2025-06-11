package com.sdiyuba.tedenglish.model.home;






import com.sdiyuba.tedenglish.model.bean.InQuireBean;
import com.sdiyuba.tedenglish.model.bean.KeyWordBean;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.view.home.SearchContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchModel implements SearchContract.SearchModel {

    @Override
    public Disposable getKeyWord(String newstype, SearchContract.CallBackKeyWord callBackKeyWord) {
        return NetWorkManager
                .getRequest()
                .getKeyWord("https://apps.iyuba.cn/iyuba/recommend.jsp?",newstype)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<KeyWordBean>() {
                    @Override
                    public void accept(KeyWordBean keyWordBean) throws Exception {
                        callBackKeyWord.successKeyWord(keyWordBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackKeyWord.errorKeyWord((Exception) throwable);
                    }
                });
    }



    @Override
    public Disposable getInQuire(String newstype,  String keyword,  String userid,  int appid, SearchContract.CallBackInQuire callBackInQuire) {
        return NetWorkManager
                .getRequest()
                .getInQuire("https://apps.iyuba.cn/iyuba/recommendByKeyword.jsp?",newstype,   keyword,   userid,   appid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<InQuireBean>() {
                    @Override
                    public void accept(InQuireBean inQuireBean) throws Exception {
                        callBackInQuire.successInQuire(inQuireBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackInQuire.errorInQuire((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getWordQuery(String format, int pages, int pageNum, int parentID, String flg, String key, String userid,int appid,   SearchContract.CallBackWordQuery callBackWordQuery) {
        return NetWorkManager
                .getRequest()
                .getWordQuery("https://apps.iyuba.cn/iyuba/searchApiNew_20200612.jsp?",format,  pages,  pageNum,  parentID,  flg,  key,userid,appid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WordQueryBean>() {
                    @Override
                    public void accept(WordQueryBean wordQueryBean) throws Exception {
                        callBackWordQuery.successWordQuery(wordQueryBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackWordQuery.errorWordQuery((Exception) throwable);
                    }
                });
    }
}
