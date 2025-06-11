package com.sdiyuba.tedenglish.model;


import com.sdiyuba.tedenglish.model.bean.AcquireWordBean;
import com.sdiyuba.tedenglish.model.bean.CollectBean;
import com.sdiyuba.tedenglish.model.bean.EarningBean;
import com.sdiyuba.tedenglish.model.bean.EvaBean;
import com.sdiyuba.tedenglish.model.bean.EvaCraftBean;
import com.sdiyuba.tedenglish.model.bean.GoldExchangeBean;
import com.sdiyuba.tedenglish.model.bean.HearingBean;
import com.sdiyuba.tedenglish.model.bean.HearingDetailBean;
import com.sdiyuba.tedenglish.model.bean.LoginBean;
import com.sdiyuba.tedenglish.model.bean.LogoutBean;
import com.sdiyuba.tedenglish.model.bean.MyMoneyBean;
import com.sdiyuba.tedenglish.model.bean.MyWordBean;
import com.sdiyuba.tedenglish.model.bean.ProverbBean;
import com.sdiyuba.tedenglish.model.bean.ReadBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterBean;
import com.sdiyuba.tedenglish.model.bean.ReadReporterDetailBean;
import com.sdiyuba.tedenglish.model.bean.RewardsBean;
import com.sdiyuba.tedenglish.model.bean.SpokeBean;
import com.sdiyuba.tedenglish.model.bean.SpokenDetailBean;
import com.sdiyuba.tedenglish.model.bean.StudyRecordByTestModeBean;
import com.sdiyuba.tedenglish.model.bean.TestRecordBean;
import com.sdiyuba.tedenglish.model.bean.UidBean;
import com.sdiyuba.tedenglish.model.bean.UploadBean;
import com.sdiyuba.tedenglish.model.bean.VipBean;
import com.sdiyuba.tedenglish.model.bean.VipParseBean;
import com.sdiyuba.tedenglish.model.bean.WordQueryBean;
import com.sdiyuba.tedenglish.model.bean.WxOrderBean;
import com.sdiyuba.tedenglish.model.bean.enrollBean;
import com.sdiyuba.tedenglish.model.bean.exportpdfBean;
import com.sdiyuba.tedenglish.model.bean.joinWordBean;
import com.sdiyuba.tedenglish.model.bean.oriPagesBean;
import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;
import com.sdiyuba.tedenglish.model.bean.InQuireBean;
import com.sdiyuba.tedenglish.model.bean.KeyWordBean;
import com.sdiyuba.tedenglish.model.bean.RankingBean;
import com.sdiyuba.tedenglish.model.bean.RankingDetailsBean;
import com.sdiyuba.tedenglish.model.bean.homeBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServer {

    //首页列表
    @GET
    Observable<homeBean>getHome(@Url String url, @Query("type") String type,@Query("format") String format,@Query("appId") int appId,@Query("maxid") int maxid,@Query("pages") int pages,@Query("pageNum") int pageNum,@Query("parentID") int parentID);

    //登录
    @GET
    Observable<LoginBean> getLogin(@Url String url, @Query("protocol") int protocol, @Query("username") String username, @Query("password") String password
            , @Query("sign") String sign, @Query("format") String format);

    //uid登录https://apps.iyuba.cn/v2/api.iyuba?platform=ios&format=json&protocol=20001&id=13619630&myid=13619630&appid=423&sign=93274196fdc2d4a0ca898b337ecf5449
    @GET("/v2/api.iyuba?")
    Observable<UidBean> uidLogin(@Query("platform") String platform, @Query("format") String format, @Query("protocol")String protocol
            , @Query("id")String id, @Query("myid") String myid, @Query("appid") int appid, @Query("sign") String sign);


    //注册 https://apps.iyuba.cn/v2/api.iyuba
    @GET("/v2/api.iyuba")
    Observable<enrollBean> getEnroll(@Query("protocol") int protocol, @Query("username") String username, @Query("password")String password
            , @Query("mobile") String mobile, @Query("sign") String sign, @Query("format") String json);

    // 点击收藏  https://apps.iyuba.cn/iyuba/updateCollect.jsp
    @GET("/iyuba/updateCollect.jsp")
    Observable<ResponseBody> getLike(@Query("groupName") String groupName, @Query("sentenceFlg") int sentenceFlg, @Query("appId") int appId, @Query("userId") String userId, @Query("type") String type
            , @Query("voaId") String voaId, @Query("sentenceId") int sentenceId, @Query("topic") String topic, @Query("format") String format);

    //文章收藏列表https://apps.iyuba.cn/dataapi/jsp/getCollect.jsp?userId=9030248&topic=ted&appid=434&sentenceFlg=0&format=json&sign=b2d1554a09b14b3408ba83790aa61099
    @GET
    Observable<CollectBean> getCollectBean(@Url String url, @Query("userId") String userId, @Query("topic") String topic, @Query("appid") int appid, @Query("sentenceFlg") int sentenceFlg,
                                           @Query("format") String format, @Query("sign") String sign);
    //导出PDFhttps://api.qomolama.cn/getPdfFile_new.jsp?idtype=voa&id=202389&isenglish=0
    @GET
    Observable<exportpdfBean> getexport(@Url String url, @Query("idtype") String idtype, @Query("id") String id, @Query("isenglish") int isenglish);


    //获取voa 文章
    @GET
    Observable<oriPagesBean> getOriPages(@Url String url, @Query("type") String type, @Query("parentID") String format, @Query("voaId") int voaid);


    //获取voa 句子
    @GET
    Observable<oriSentencesBean> getOriSentences(@Url String url, @Query("format") String format, @Query("voaid") int voaid);

    //http://api.iyuba.cn/credits/goldCoinRewards.jsp?appid=222&uid=15434590&idindex=321001&sign=9eb222239eabb32585b778314fe659ba&srid=311&noncestr=1714980344
    //倒计时奖励接口
    @GET
    Observable<RewardsBean> getRewards(@Url String url, @Query("appid") int appid, @Query("uid") String uid, @Query("idindex") String idindex, @Query("sign") String sign, @Query("srid") int srid, @Query("noncestr") String noncestr , @Query("goldCoins") int goldCoins);


    // 奖励兑换记录
    @GET
    Observable<EarningBean> getEarning(@Url String url, @Query("appid") int appid, @Query("uid") String uid, @Query("sign") String sign, @Query("noncestr") String noncestr, @Query("pages") int pages, @Query("pageCount") int pageCount, @Query("operation") String operation);


    //金币兑换现金
    @GET
    Observable<GoldExchangeBean> GoldExchange(@Url String url, @Query("appid") int appid, @Query("uid") String uid, @Query("sign") String sign, @Query("noncestr") String noncestr);



    //上传录音
    @POST("")
    Observable<EvaBean> getEvaluating(@Url String url, @Body RequestBody requestBody);

    //发布到排行榜
    @GET
    Observable<UploadBean> uploadList(@Url String url, @Query("platform") String platform, @Query("format") String format, @Query("protocol") int protocol,
                                      @Query("topic") String topic, @Query("userid") String userid, @Query("username") String username,
                                      @Query("voaid") int voaid, @Query("idIndex") int idIndex, @Query("paraid") int paraid, @Query("score") int score,
                                      @Query("shuoshuotype") int shuoshuotype, @Query("content") String content, @Query("rewardVersion") int rewardVersion, @Query("appid") int appid);

    //获取历史评测记录https://ai.iyuba.cn/api/getVoaTestRecord.jsp?userId=9030248&newstype=voa&newsid=16760
    @GET
    Observable<TestRecordBean> getTestRecord(@Url String url, @Query("userId") String userId, @Query("newstype") String newstype, @Query("newsid") String newsid);

    //评测合成
    @GET
    Observable<EvaCraftBean> getEvaCraft(@Url String url, @Query("audios") String audios, @Query("type") String type);


    //排行榜
    @GET("/ecollege/getTopicRanking.jsp")
    Observable<RankingBean> getRanking(@Query("uid") String uid, @Query("type") String type, @Query("total") String total, @Query("start") int start,
                                       @Query("topic") String topic, @Query("topicid") String topicid, @Query("sign") String sign);
    //排行榜详情
    @GET("/voa/getWorksByUserId.jsp")
    Observable<RankingDetailsBean> getRankingDetails(@Query("shuoshuoType") String shuoshuoType, @Query("topic") String topic,
                                                     @Query("topicId") int topicId, @Query("uid") int uid, @Query("sign") String sign);


    //完成阅读接口
    @GET
    Observable<ReadBean> getRead(@Url String url, @Query("format") String format, @Query("uid") String uid, @Query("BeginTime") String BeginTime, @Query("EndTime") String EndTime, @Query("appName") String appName, @Query("Lesson") String Lesson, @Query("LessonId") String LessonId, @Query("appId") int appId, @Query("Device") String Device
            , @Query("DeviceId") String DeviceId, @Query("EndFlg") int EndFlg, @Query("wordcount") int wordcount, @Query("categoryid") int categoryid, @Query("platform") String platform, @Query("rewardVersion") int rewardVersion);


    //取词  https://apps.iyuba.cn/words/apiWordJson.jsp?q=What&format=json
    @GET
    Observable<AcquireWordBean> getAcquireWord(@Url String url, @Query("q") String q, @Query("format") String format);

    //加入生词本https://apps.iyuba.cn/words/updateWord.jsp?groupName=Iyuba&userId=9030248&mod=insert&word=morning&format=json
    @GET("/words/updateWord.jsp")
    Observable<joinWordBean> joinword(@Query("groupName") String groupName, @Query("userId") String userId, @Query("mod") String mod,
                                      @Query("word") String word, @Query("format") String format);

    //我的生词 https://apps.iyuba.cn/words/wordListService.jsp?u=9030248&pageNumber=1&pageCounts=100&format=json
    @GET("/words/wordListService.jsp")
    Observable<MyWordBean> getMyWord(@Query("u") String u, @Query("pageNumber") int pageNumber, @Query("pageCounts") int pageCounts, @Query("format") String format);



    //搜索内包含单词得https://apps.iyuba.cn/iyuba/searchApiNew.jsp?format=json&pages=1&pageNum=3&parentID=0&type=ted&key=obama
    @GET
    Observable<WordQueryBean> getWordQuery(@Url String url, @Query("format") String format, @Query("pages") int pages, @Query("pageNum") int pageNum, @Query("parentID") int parentID, @Query("flg") String flg, @Query("key") String key, @Query("userid") String userid, @Query("appid") int appid);

    //搜索关键词
    @GET
    Observable<KeyWordBean> getKeyWord(@Url String url, @Query("newstype") String newstype);
    //搜索详情 http://cms.iyuba.cn/dataapi/jsp/search.jsp?uid=6307010&appid=221&value=good&pageNum=1&pageSize=10&format=json&sign=6917a09ddc278fcc4532c489a7998671&type=ted
    //https://apps.iyuba.cn/iyuba/recommendByKeyword.jsp?newstype=ted&keyword=believe&userid=9030248&appid=appid
    @GET
    Observable<InQuireBean> getInQuire(@Url String url, @Query("newstype") String newstype, @Query("keyword") String keyword, @Query("userid") String userid, @Query("appid") int appid);


    //谚语http://ai.iyuba.cn/japanapi/getEverydayEnglish.jsp
    @GET
    Observable<ProverbBean> getProverbBean(@Url String url);

    //微信支付  http://vip.iyuba.cn/weixinPay.jsp?wxkey=wxe058ccd3f068897e&weixinApp=wxe058ccd3f068897e&appid=221&uid=6307010&money=30&amount=1&productid=10&format=json&sign=9803c903f23106ae98ccccb4ef9c2bb1
    @GET
    Observable<WxOrderBean> getWxOrder(@Url String url, @Query("wxkey") String wxkey, @Query("weixinApp") String weixinApp, @Query("appid") int appid, @Query("uid") String uid, @Query("money") String money, @Query("amount") int amount, @Query("productid") int productid, @Query("format") String format, @Query("sign") String sign, @Query("deduction") int deduction);

    //支付宝支付
    @GET
    Observable<VipParseBean> getVip(@Url String url, @Query("app_id") int app_id, @Query("userId") int userId,
                                    @Query("code") String code, @Query("WIDtotal_fee") String WIDtotal_fee, @Query("amount") int amount,
                                    @Query("product_id") int product_id, @Query("WIDbody") String WIDbody, @Query("WIDsubject") String WIDsubject, @Query("deduction") int deduction);


    //    http://vip.iyuba.cn/notifyAliNew.jsp
    //更新订单状态
    @GET
    Observable<VipBean> callbackVip(@Url String url, @Query("date") String date);

    //钱包记录 http://api.iyuba.cn/credits/getuseractionrecord.jsp?uid=6307010&pages=1&pageCount=20&sign=0fd32b5d167482f0cc3561b2abc70738
    @GET
    Observable<MyMoneyBean> getMyMoney(@Url String url, @Query("uid") String uid, @Query("pages") int pages, @Query("pageCount") int pageCount, @Query("sign") String sign);



    //学习报告 --阅读 http://cms.iyuba.cn/newsApi/getNewsRanking.jsp?uid=14550251&type=D&total=1&start=0&mode=reading&sign=e2a8232049843094f796ef8bcdc7e2c5
    @GET
    Observable<ReadReporterBean> getReadReporter(@Url String url, @Query("uid") String uid, @Query("type") String type, @Query("total") int total, @Query("start") int start, @Query("mode") String mode, @Query("sign") String sign);

    //学校报告 --阅读详情  http://cms.iyuba.cn/newsApi/getRecentRV.jsp?uid=9030248&format=json&thisNumber=20&startNumber=0
    @GET
    Observable<ReadReporterDetailBean> getReadReporterDetail(@Url String url, @Query("uid") String uid, @Query("format") String format, @Query("thisNumber") int thisNumber, @Query("startNumber") int startNumber);

    //听力报告接口http://daxue.iyuba.cn/ecollege/getStudyRanking.jsp?mode=listening&uid=14044990&type="D"&start=0&total=0&sign=e5d99657930e879053ed98ea98dc622a
    @GET
    Observable<HearingBean> getHearing(@Url String url, @Query("mode") String mode, @Query("uid") String uid, @Query("type") String type, @Query("start") int start, @Query("total") int total, @Query("sign") String sign);

    //口语报告http://ai.iyuba.cn/management/getHomePage.jsp?userId=6307010&newstype=concept&language=English&type=D&sign=e294a41f7a1db3e06a682264f8a54a0c
    @GET
    Observable<SpokeBean> getSpoke(@Url String url, @Query("userId") String userId, @Query("newstype") String newstype, @Query("language") String language, @Query("type") String type, @Query("sign") String sign);

    //听力报告详情 http://daxue.iyuba.cn/ecollege/getStudyRecordByTestMode.jsp?NumPerPage=10&Pageth=1&TestMode=1&uid=14044990&sign=c6f4b185ab5d148b2bfc83920fe70f50
    @GET
    Observable<HearingDetailBean> getHearingDetail(@Url String url, @Query("NumPerPage") int NumPerPage, @Query("Pageth") int Pageth, @Query("TestMode") int TestMode, @Query("uid") String uid, @Query("sign") String sign);

    //口语报告详情http://ai.iyuba.cn/management/getDetailInfo.jsp?userId=6307010&newstype=concept&language=English&lastId=0&pageCounts=20
    @GET
    Observable<SpokenDetailBean> getSpokenDetail(@Url String url, @Query("userId") String userId, @Query("newstype") String newstype, @Query("language") String language, @Query("lastId") int lastId, @Query("pageCounts")int pageCounts);
    /**
     * 上传听力进度
     https://apps.iyuba.cn/ecollege/updateStudyRecordNew.jsp
     */
    @GET("/ecollege/updateStudyRecordNew.jsp")
    Observable<StudyRecordByTestModeBean> UpStudyRecordContract(@Query("format") String format, @Query("uid") String uid, @Query("appId") int appId, @Query("BeginTime") String BeginTime
            , @Query("EndTime") String EndTime, @Query("Lesson") String Lesson, @Query("LessonId") String LessonId, @Query("EndFlg") String EndFlg, @Query("Device") String Device, @Query("platform") String platform
            , @Query("IP") String IP, @Query("sign") String sign, @Query("TestMode") String TestMode, @Query("TestNumber") String TestNumber, @Query("TestWords") String TestWords, @Query("rewardVersion") int rewardVersion);


    // 注销账号   Observable<LogoutBean> getLogout(@Url String logouturl, @Body RequestBody logoutrequestBody);
    @POST
    Observable<LogoutBean> getLogout(@Url String logouturl, @Query("protocol") String protocol, @Query("format") String format, @Query("username") String username,
                                     @Query("password") String password, @Query("sign") String sign);











}


