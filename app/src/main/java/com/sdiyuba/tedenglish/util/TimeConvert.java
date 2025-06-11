package com.sdiyuba.tedenglish.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeConvert {

    /**
     * 函数功能：
     * 将当前的时间转成到东八区的日期数量
     * @return
     */
    public static long toEpochDay(){
//        Calendar calendar = Calendar.getInstance();
//
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        String dateString = year + "-" + month + "-" + day;

        Date mydate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(mydate);

        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.parse(format, formatter);
        }
        return date.toEpochDay();
    }

    public static String checkYMD(String year,String month,String day){

        if(month.length() == 1){
            month = "0" + month;


        }

        if(day.length() == 1){
            day = "0" + day;
        }

        return year + month + day;
    }

    public static String checkYM(String year,String month){

        if(month.length() == 1){
            month = "0" + month;
        }
        return year + month;
    }

    /**
     * 今天时间
     * @return
     */
    public static String TodayTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        format += " 00:00:00";
        return format;
    }

    /**
     * 明天时间
     * @return
     */
    public static String TomorrowTime(){

        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 计算明天的日期
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        // 获取明天的日期
        Date tomorrow = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(tomorrow);
        format += " 00:00:00";
        return format;
    }

    /**
     * 6天后时间
     * @return
     */

    public static String SixTime(){

        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        // 计算明天的日期
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        // 获取明天的日期
        Date tomorrow = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(tomorrow);
        format += " 00:00:00";
        return format;
    }
    /**
     * 昨天时间
     * @return
     */
    public static String YesterdayTime(){

        // 创建一个 Calendar 对象
        Calendar calendar = Calendar.getInstance();

        // 获取当前时间
        calendar.setTimeInMillis(System.currentTimeMillis());

        // 将日期向前推两天
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // 获取前天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(calendar.getTime());

        formattedDate += " 00:00:00";
        return formattedDate;
    }


    /**
     * 前天时间
     * @return
     */
    public static String BeforTime(){

        // 创建一个 Calendar 对象
        Calendar calendar = Calendar.getInstance();

        // 获取当前时间
        calendar.setTimeInMillis(System.currentTimeMillis());

        // 将日期向前推两天
        calendar.add(Calendar.DAY_OF_MONTH, -2);

        // 获取前天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(calendar.getTime());

        formattedDate += " 00:00:00";
        return formattedDate;
    }

    /**
     * 前天时间
     * @return
     */
    public static String BeforeTime(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        format += " 00:00:00";
        return format;
    }

    /**
     * 获取 当前的 时间 精确到 时分秒
     * @return currentTime
     */
    public static String GETNOWTIME(){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = dateFormat.format(calendar.getTime());

        return currentTime;
    }

    /**
     * 获取 当前的 时间
     * @return currentTime
     */
    public static String GETYYYYMMDD(){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String currentTime = dateFormat.format(calendar.getTime());

        return currentTime;
    }
}
