package cn.migu.log.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/*
 * @version 1.0 created by LXW on 2018/10/11 13:51
 */
public class DateTimeUtil {

    //年-月-日
    public static final String FULLTIMEBY_yMd = "yyyy-MM-dd";
    //时:分:秒
    public static final String FULLTIMEBY_Hms = "HH:mm:ss";
    //时:分:秒.毫秒
    public static final String FULLTIMEBY_HmsS = "HH:mm:ss.SSS";
    //年月日 无分割
    public static final String FULLTIMEBY_yMd_NoSegmentation = "yyyyMMdd";
    //时分秒 无分割
    public static final String FULLTIMEBY_Hms_NoSegmentation = "HHmmss";
    //时分秒毫秒 无分割
    public static final String FULLTIMEBY_HmsS_NoSegmentation = "HHmmssSSS";
    //年-月-日 时:分:秒
    public static final String FULLTIMEBY_yMdHms = "yyyy-MM-dd HH:mm:ss";
    //年-月-日 时:分:秒.毫秒
    public static final String FULLTIMEBY_yMdHmsS = "yyyy-MM-dd HH:mm:ss.SSS";
    //格式化 年-月-日
    public static final String FULLTIMEFORMAT_yMd = "yyyy年MM月dd日";
    //格式化 时:分:秒
    public static final String FULLTIMEFORMAT_Hms = "HH时mm分ss秒";
    //格式化 年-月-日 时:分:秒
    public static final String FULLTIMEFORMAT_yMdHms = "yyyy年MM月dd日 HH时mm分ss秒";
    //格式化 年-月-日 时:分:秒.毫秒
    public static final String FULLTIMEFORMAT_yMdHmsS = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒";


    /**
     * 获取当前日期的过去指定时间长度的零点时时间戳，包含当天
     * @param maxDays 3 (2018/10/11 0:0:0)
     * @return 2018/10/09 0:0:0 的时间戳
     */
    public static long getLastDaysTimestamp(int maxDays){
        //当前时间毫秒数
        long current=System.currentTimeMillis();
        //今天零点零分零秒的毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();
        long lastThirty = zero - 24*60*60*1000*(maxDays-1);
        return lastThirty;
    }

    /**
     * 获取过去指定时间的时间戳
     * @param pastTime 指定的时间数量 5
     * @param type 时间的类型 d日 H时 m分 s秒 S毫秒
     * @param timeFormat 转换后的时间戳格式化的标准
     * @return
     */
    public static String getThePastTime(int pastTime, String type, String timeFormat){
        if (type == null){
            type = "s";
        }
        if (timeFormat == null){
            timeFormat = FULLTIMEBY_yMdHmsS;
        }
        long date = System.currentTimeMillis();
        Long now_time = new Long(1);
        if ("S".equals(type)){
            now_time = date - pastTime;
        }else if ("s".equals(type)){
            now_time = date - pastTime*1000;
        }else if ("m".equals(type)){
            now_time = date - pastTime*1000*60;
        }else if ("H".equals(type)){
            now_time = date - pastTime*1000*60*60;
        }else if ("d".equals(type)){
            now_time = date - pastTime*1000*60*60*24;
        }
        String finalTime = tranlateTimeToDate(now_time, timeFormat);
        return finalTime;
    }

    public static String getThePastTime(int pastTime){
        return getThePastTime(pastTime, null, null);
    }

    public static String getThePastTime(int pastTime, String type){
        return getThePastTime(pastTime, type, null);
    }

    /**
     * 获取当前系统时间的时间戳
     * @return 时间戳
     */
    public static Long getCurrentTime(){
        return System.currentTimeMillis();
    }

    /**
     * 获取指定格式的当前时间
     * @return 时间
     */
    public static String getNowTimeByFormat(String timeFormat){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        String today = simpleDateFormat.format(date);
        return today;
    }

    /**
     * 转换时间戳位时间格式
     * @param timestamp 时间戳
     * @param timeFormat 转换的时间格式
     * @return 时间
     */
    public static String tranlateTimeToDate(Long timestamp, String timeFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        String da = simpleDateFormat.format(timestamp);
        return da;
    }

    /**
     * 时间转时间戳
     * @param time 需要转换的时间
     * @param timeFormat 时间对应的格式
     * @return 时间戳
     */
    public static Long translateDate(String time, String timeFormat){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long res = date.getTime();
        return res;
    }


    /**
     * 序列化字符串为时间格式
     * @param time 需要转换的字符串 (20181011 10:11:12.013)
     * @param timeFormat 字符串对应的时间格式 (yyyyMMdd HH:mm:ss.SSS)
     * @param timeTranselate 转换后需要的字符串格式 (yyyy年M月dd日 HH时mm分ss秒SSS毫秒)
     * @return 2018年10月11日 10时11分12秒13毫秒
     */
    public static String serializationDate(String time, String timeFormat, String timeTranselate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        try {
            Date date = simpleDateFormat.parse(time);
            SimpleDateFormat dateFormat = new SimpleDateFormat(timeTranselate);
            String formatTime = dateFormat.format(date);
            return formatTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
