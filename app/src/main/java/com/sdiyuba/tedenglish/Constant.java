package com.sdiyuba.tedenglish;


import android.text.SpannableString;


import com.sdiyuba.tedenglish.model.bean.oriSentencesBean;

import java.util.ArrayList;
import java.util.List;

public class Constant {


    /**
     * 简称
     */
    public final static String NAME = "爱语吧TED演讲";
    public final static String CompanyName = "山东爱语吧信息科技有限公司"; //公司名称

    public final static String EvaType = "ted";
    public final static String yinsi = "http://ai.iyuba.cn/api/protocolpri.jsp?apptype="+ Constant.NAME +"&company=4";  //3是爱语言   10是日照

    public final static String tiaokuan = "https://ai.iyuba.cn/api/protocoluse.jsp?apptype="+ Constant.NAME +"&company=4";
    public static final String AD_ADS1 = "ads1";//倍孜
    public static final String AD_ADS2 = "ads2";//穿山甲
    public static final String AD_ADS3 = "ads3";//百度
    public static final String AD_ADS4 = "ads4";//广点通优量汇
    public static final String AD_ADS5 = "ads5";//快手
    public static final String AD_ADS6 = "ads6";//瑞狮
    public static boolean WX_SHARE =false ;

    public static String DOMAIN = "qomolama.cn";

    public static String DOMAIN_LONG = "qomolama.com.cn";


    public static String API_URL = "http://api." + DOMAIN;


    public static boolean isPlay = true;

    public static  String URL_DEV = "http://dev." + DOMAIN;


    public static int AppId = 229;

    public static int leaveOriVoaid = 0;

    public static String uid ="0";
    public static String oribiaoti;


    public static String orisound = null; //原文音频(视频地址)

    public static String ori_MP3 = null; //原文音频(音频地址)
    public static String userimgUrl; //用户头像

    public static String DOWNLOAD_PIC = "/storage/emulated/0/Android/data/com.sdiyuba.tedenglish/files/"+Constant.EvaType+"downloadpic/";


    public static String downvoaid; //删除下载用的voaid



    public static String oriimage; // 文章图片地址
    public static boolean isZanT = false;//暂停了
    public static long getDuration; //原文音频的getDuration
    public static long getCurrentPosition = 0; //原文音频的getCurrentPosition
    public static int vipStatus = 0;

    public static String username = null;

    //动态创建,保存评测后返回内容
    public static SpannableString[] spannableString = new SpannableString[300];

    public static int[]  evaScore = new int[300];

    public static String[] audioURL = new String[300];
    public static int Eva_Sum=0;  //评测得次数



    public static boolean isAbcEvaFirst = true; //是否第一次打开评测


    public static double money;
    public static String mobile;
    public static int aiyubi;
    public static String vipDate = null;


    public static boolean wordnull = false; //单词的音频和词意设置为空
    //上传听力进度的两个使用常量
    public static int testNumber = 0;

    public static int testWords = 0;
    public static String ranking;
    public static String days;
    public static String sharId;
    public static boolean isPayTrue = false; //vip开通成功
    public static boolean isWxPay = false;  //true 默认微信支付  false是支付宝

    public static int homeadposition =-2; //上拉刷新广告位置
    public static int orivoaid;


    public static int goldCoin;//金币个数
    public static boolean isGold;//判断进入的金币记录还是现金记录    true代表金币
    public static List<oriSentencesBean.VoatextDTO> orilist;//原文句子list
    public static int AdAppID = 2291;
    public static String AllTitleEn;//完整的英文标题

    public static String AllTitleCn;//完整的中文标题
    public static String pageTime;//文章时间

    //首页的分类
    public static String home_type = "{\n" +
            "                \"201\": \"文化\",\n" +
            "                \"202\": \"经济\",\n" +
            "                \"203\": \"政治\",\n" +
            "                \"204\": \"科学\",\n" +
            "                \"205\": \"科技\",\n" +
            "                \"206\": \"健康\",\n" +
            "                \"207\": \"艺术\",\n" +
            "                \"208\": \"音乐\",\n" +
            "                \"209\": \"教育\",\n" +
            "                \"210\": \"天才\",\n" +
            "                \"211\": \"自然\"\n" +
            "        }";
    public static String search_wordKey;//搜索框当前的单词


    public static ArrayList<String> keyWordList = new ArrayList<>(); //请求到的10条关键词

    public static String channel = "vivo";
    public static int abc=0;  //微课直接跳转购买黄金会员
    public static int wkId;
    public static String wkPrice;

    public static String wkBody;
    public static String  wxKey = "wx351abfea12ed92e3";
    public static int jifen = 0;//积分
    public static String phone;//手机号
    public static boolean for_mycollect =false;
    public static boolean video = true; //true = 看视频 ,false = 读新闻
}
