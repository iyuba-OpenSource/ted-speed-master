package com.sdiyuba.tedenglish.presenter.home;




import com.sdiyuba.tedenglish.model.bean.EvaBean;
import com.sdiyuba.tedenglish.model.bean.EvaCraftBean;
import com.sdiyuba.tedenglish.model.bean.UploadBean;
import com.sdiyuba.tedenglish.model.home.evaluatingModel;
import com.sdiyuba.tedenglish.model.bean.TestRecordBean;
import com.sdiyuba.tedenglish.presenter.BasePresenter;
import com.sdiyuba.tedenglish.view.home.evaluatingContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class evaluatingPresenter extends BasePresenter<evaluatingContract.evaluatingView, evaluatingContract.evaluatingModel> implements evaluatingContract.evaluatingPresenter {


    @Override
    protected evaluatingContract.evaluatingModel initModel() {
        return new evaluatingModel();
    }

    @Override
    public void getEvaluating(RequestBody requestBody) {
        Disposable disposable = model.getEvaluating(requestBody, new evaluatingContract.CallBackEvaluating() {
            @Override
            public void successEvaluating(EvaBean evaBean) {
                view.getEvaluating(evaBean);
            }

            @Override
            public void errorEvaluating(Exception e) {

                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }

            }
        });
        addSubscribe(disposable);
    }


    @Override
    public void uploadList(String platform, String format, int protocol, String topic, String userid, String username, int voaid, int idIndex, int paraid, int score, int shuoshuotype, String content,int rewardVersion,int appid) {
        Disposable disposable = model.uploadList(platform, format, protocol, topic, userid, username, voaid, idIndex,
                paraid, score, shuoshuotype, content, rewardVersion, appid, new evaluatingContract.CallBackuploadList() {
                    @Override
                    public void successuploadList(UploadBean uploadBean) {
                        view.uploadList(uploadBean);

                    }

                    @Override
                    public void erroruploadList(Exception e) {
                        if (e instanceof UnknownHostException) {

                            view.toast("请求超时");
                        }
                    }
                });
        addSubscribe(disposable);
    }


    @Override
    public void getTestRecord(String userId, String newstype, String newsid) {
        Disposable disposable= model.getTestRecord(userId, newstype, newsid, new evaluatingContract.CallBackgetTestRecord() {
            @Override
            public void successTestRecord(TestRecordBean testRecordBean) {
                view.getTestRecord(testRecordBean);
            }

            @Override
            public void errorupTestRecord(Exception e) {

                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });

        addSubscribe(disposable);
    }

    @Override
    public void getEvaCraft(String audios, String type) {
        Disposable disposable =model.getEvaCraft(audios, type, new evaluatingContract.CallBackgetEvaCraft() {
            @Override
            public void successgetEvaCraft(EvaCraftBean evaCraftBean) {
                view.getEvaCraft(evaCraftBean);
            }

            @Override
            public void errorgetEvaCraft(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }


}


