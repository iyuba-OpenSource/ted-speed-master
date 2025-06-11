package com.sdiyuba.tedenglish.util;






import com.sdiyuba.tedenglish.model.bean.LikeBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class PullXmlUtil {


    public static LikeBean parseXMLWithPull(String xml) {

        LikeBean bean = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xml));
            int type = xmlPullParser.getEventType();

            bean = new LikeBean();

            while (type != XmlPullParser.END_DOCUMENT) {
                String node = xmlPullParser.getName();
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("msg".equals(node)) {//是否是name的节点
                            //获取节点的具体内容
                            bean.setMsg(xmlPullParser.nextText());
                        } else if ("result".equals(node)) {
                            bean.setResult(Integer.parseInt(xmlPullParser.nextText()));
                        } else if ("type".equals(node)) {
                            bean.setType(xmlPullParser.nextText());
                        } else if ("topic".equals(node)) {
                            bean.setTopic(xmlPullParser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //当读取完Book节点时，输出一下获取的内容
//                        if ("Book".equals(node)) {
//                            Log.i("123123", "name:" + name + ","
//                                    + "author:" + author + ","
//                                    + "price:" + prices);
//                        }
                        break;
                }
                type = xmlPullParser.next();
            }//while
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bean;
    }
}

