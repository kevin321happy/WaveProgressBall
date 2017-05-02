package com.fips.huashun.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lgpeng on 2016/3/12 0012.
 */
public class TimeUtils {
    public static final SimpleDateFormat DATE_FORMAT_DATE;

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("MM-dd HH:mm");

    static {
        DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式的时间
     * @return
     */
    public static String getCurrentTimeInStr(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return getTime(getCurrentTimeInLong(),formatter);
    }

    public static String getCurrentTimeInString(SimpleDateFormat simpleDateFormat) {
        return getTime(getCurrentTimeInLong(), simpleDateFormat);
    }

    public static String getTime(long timeMillis) {
        return getTime(timeMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * 长整型数字转日期, 返回字符串形式的日期
     */
    public static String getTime(long timeMillis, SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(new Date(timeMillis));
    }

    public static Date stringToDate(String date) {
        Date localObject = null;
        try {
            localObject = DATE_FORMAT_DATE.parse(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return localObject;
    }

    public static String stringToDateDetail(Date date) {
        String localObject = "";
        try {
            localObject = DATE_FORMAT_DATE.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localObject;
    }

    public static Date stringToDateTime(String date) {
        Date localObject = null;
        try {
            localObject = DEFAULT_DATE_FORMAT.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localObject;
    }

    /**
     * @param mss 要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        StringBuilder sb = new StringBuilder("");
        if (days > 0) {
            sb.append(days).append("天");
        }
        if (hours > 0) {
            sb.append(hours).append("小时");
        }
        if (minutes > 0) {
            sb.append(minutes).append("分");
        }
        sb.append(seconds).append("秒");
        return sb.toString();
    }

    public static long formatDay(long mss) {
        return mss / (1000 * 60 * 60 * 24);
    }

    public static long formatHour(long mss) {
        return (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
    }

    public static long formatMin(long mss) {
        return (mss % (1000 * 60 * 60)) / (1000 * 60);
    }

    public static long formatSec(long mss) {
        return (mss % (1000 * 60)) / 1000;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }
}
