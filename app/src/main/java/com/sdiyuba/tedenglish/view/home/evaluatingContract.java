package com.sdiyuba.tedenglish.view.home;


import com.sdiyuba.tedenglish.model.bean.EvaBean;
import com.sdiyuba.tedenglish.model.bean.EvaCraftBean;
import com.sdiyuba.tedenglish.model.bean.UploadBean;
import com.sdiyuba.tedenglish.model.BaseModel;
import com.sdiyuba.tedenglish.model.bean.TestRecordBean;
import com.sdiyuba.tedenglish.presenter.IBasePresenter;
import com.sdiyuba.tedenglish.view.LoadingView;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public interface evaluatingContract {
    interface evaluatingView extends LoadingView {
        //   上传排行榜
        void uploadList(UploadBean uploadBean);

        //评测录音
        void getEvaluating(EvaBean evaBean);

        //历史记录评测
        void getTestRecord(TestRecordBean testRecordBean);

        //合成
        void getEvaCraft(EvaCraftBean evaCraftBean);
    }
    interface evaluatingPresenter extends IBasePresenter<evaluatingView> {

        //评测录音
        void getEvaluating(RequestBody requestBody);

        //上传
        void uploadList(String platform, String format, int protocol, String topic, String userid, String username, int voaid, int idIndex,
                        int paraid, int score, int shuoshuotype, String content,int rewardVersion,int appid);
        //获取历史评测记录
        void getTestRecord(String userId, String newstype,  String newsid);

        //合成
        void getEvaCraft(String audios, String type);
    }
    interface evaluatingModel extends BaseModel {

        Disposable getEvaluating(RequestBody requestBody, CallBackEvaluating callBackEvaluating);


        Disposable uploadList(String platform, String format, int protocol, String topic,
                              String userid, String username, int voaid, int idIndex, int paraid, int score, int shuoshuotype,
                              String content, int rewardVersion, int appid, CallBackuploadList callBackuploadList);


        Disposable getTestRecord(String userId, String newstype, String newsid, CallBackgetTestRecord callBackgetTestRecord);

        Disposable getEvaCraft(String audios, String type, CallBackgetEvaCraft callBackgetEvaCraft);

    }
    interface CallBackEvaluating{

        void successEvaluating(EvaBean evaBean);

        void errorEvaluating(Exception e);

    }
    interface CallBackuploadList{

        void successuploadList(UploadBean uploadBean);

        void erroruploadList(Exception e);

    }


    interface CallBackgetTestRecord{

        void successTestRecord(TestRecordBean testRecordBean);

        void errorupTestRecord(Exception e);

    }

    interface CallBackgetEvaCraft{

        void successgetEvaCraft(EvaCraftBean evaCraftBean);

        void errorgetEvaCraft(Exception e);

    }




}
