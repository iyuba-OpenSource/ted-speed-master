package com.sdiyuba.tedenglish.view.home;






import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.InQuireBean;
import com.sdiyuba.tedenglish.model.bean.KeyWordBean;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface SearchContract {

    interface SearchView extends LoadingView {
        void getKeyWord(KeyWordBean keyWordBean);

        void getInQuire(InQuireBean inQuireBean);

        void getWordQuery(WordQueryBean wordQueryBean);

    }
    interface SearchPresenter extends IBasePresenter<SearchView> {
        void getKeyWord(String newstype);

        void getInQuire(String newstype,  String keyword,  String userid,  int appid);

        void getWordQuery( String format,  int pages,  int pageNum,  int parentID, String flg, String key,String userid,int appid);
    }
    interface SearchModel extends BaseModel {

        Disposable getKeyWord(String newstype, CallBackKeyWord callBackKeyWord);
        Disposable getInQuire(String newstype, String keyword, String userid, int appid, CallBackInQuire callBackInQuire);

        Disposable getWordQuery(String format, int pages, int pageNum, int parentID, String flg, String key,String userid,int appid,   CallBackWordQuery callBackWordQuery);

    }


    interface CallBackKeyWord{

        void successKeyWord(KeyWordBean keyWordBean);

        void errorKeyWord(Exception e);

    }
    interface CallBackInQuire{

        void successInQuire(InQuireBean inQuireBean);

        void errorInQuire(Exception e);

    }

    interface CallBackWordQuery{

        void successWordQuery(WordQueryBean wordQueryBean);

        void errorWordQuery(Exception e);

    }
}
