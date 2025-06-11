package com.sdiyuba.tedenglish.model.home;





import com.sdiyuba.tedenglish.model.bean.EvaBean;
import com.sdiyuba.tedenglish.model.bean.EvaCraftBean;
import com.sdiyuba.tedenglish.model.bean.UploadBean;
import com.sdiyuba.tedenglish.model.NetWorkManager;
import com.sdiyuba.tedenglish.model.bean.TestRecordBean;
import com.sdiyuba.tedenglish.view.home.evaluatingContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class evaluatingModel implements evaluatingContract.evaluatingModel {
    @Override
    public Disposable getEvaluating(RequestBody requestBody, evaluatingContract.CallBackEvaluating callBackEvaluating) {
        return NetWorkManager
                .getRequest()
//        https://iuserspeech.iyuba.cn:444/test/ai/              sentence, paraId, newsId, IdIndex, type, appId, wordId, flg, userId, file
                .getEvaluating("http://iuserspeech.iyuba.cn:9001/test/concept/", requestBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EvaBean>() {

                    @Override
                    public void accept(EvaBean evaBean) throws Exception {
                        callBackEvaluating.successEvaluating(evaBean);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackEvaluating.errorEvaluating((Exception) throwable);

                    }
                });
    }


    @Override
    public Disposable uploadList(String platform, String format, int protocol, String topic, String userid, String username, int voaid, int idIndex, int paraid, int score, int shuoshuotype, String content,int rewardVersion ,int appid ,evaluatingContract.CallBackuploadList callBackuploadList) {
        return NetWorkManager
                .getRequest()
                .uploadList("http://voa.iyuba.cn/voa/UnicomApi?",platform, format, protocol, topic, userid, username, voaid, idIndex,
                        paraid, score, shuoshuotype, content, rewardVersion, appid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UploadBean>() {
                    @Override
                    public void accept(UploadBean uploadBean) throws Exception {
                        callBackuploadList.successuploadList(uploadBean);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackuploadList.erroruploadList((Exception) throwable);
                    }

    });
    }


    @Override
    public Disposable getTestRecord(String userId, String newstype, String newsid, evaluatingContract.CallBackgetTestRecord callBackgetTestRecord) {
        return NetWorkManager
                .getRequest()
                .getTestRecord( "http://ai.iyuba.cn/api/getVoaTestRecord.jsp",userId,  newstype,  newsid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestRecordBean>() {
                    @Override
                    public void accept(TestRecordBean testRecordBean) throws Exception {
                        callBackgetTestRecord.successTestRecord(testRecordBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackgetTestRecord.errorupTestRecord((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getEvaCraft(String audios, String type, evaluatingContract.CallBackgetEvaCraft callBackgetEvaCraft) {
        return NetWorkManager
                .getRequest()
                .getEvaCraft( "http://ai.iyuba.cn/test/merge",audios,  type )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EvaCraftBean>() {
                    @Override
                    public void accept(EvaCraftBean evaCraftBean) throws Exception {
                        callBackgetEvaCraft.successgetEvaCraft(evaCraftBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackgetEvaCraft.errorgetEvaCraft((Exception) throwable);
                    }
                });
    }
}
