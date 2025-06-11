package com.sdiyuba.tedenglish.presenter.home;





import android.widget.Toast;

import com.sdiyuba.tedenglish.MyApplication;
import com.sdiyuba.tedenglish.model.bean.InQuireBean;
import com.sdiyuba.tedenglish.model.bean.KeyWordBean;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;
import com.sdiyuba.tedenglish.model.home.SearchModel;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.SearchContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class SearchPresenter extends BasePresenter<SearchContract.SearchView,SearchContract.SearchModel> implements SearchContract.SearchPresenter {

    @Override
    protected SearchContract.SearchModel initModel() {
        return new SearchModel();
    }

    @Override
    public void getKeyWord(String newstype) {
        Disposable disposable=model.getKeyWord(newstype, new SearchContract.CallBackKeyWord() {
            @Override
            public void successKeyWord(KeyWordBean keyWordBean) {
                view.getKeyWord(keyWordBean);
            }

            @Override
            public void errorKeyWord(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getInQuire(String newstype,  String keyword,  String userid,  int appid) {
        Disposable disposable=model.getInQuire(newstype,   keyword,   userid,   appid, new SearchContract.CallBackInQuire() {
            @Override
            public void successInQuire(InQuireBean inQuireBean) {
                view.getInQuire(inQuireBean);
            }

            @Override
            public void errorInQuire(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getWordQuery(String format, int pages, int pageNum, int parentID, String flg, String key,String userid,int appid) {
        Disposable disposable=model.getWordQuery(format, pages, pageNum, parentID, flg, key, userid, appid, new SearchContract.CallBackWordQuery() {
            @Override
            public void successWordQuery(WordQueryBean wordQueryBean) {
                view.getWordQuery(wordQueryBean);

            }

            @Override
            public void errorWordQuery(Exception e) {

                Toast.makeText(MyApplication.getContext(), "搜索失败，请稍后重试", Toast.LENGTH_SHORT).show();
                if (e instanceof UnknownHostException) {


                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
