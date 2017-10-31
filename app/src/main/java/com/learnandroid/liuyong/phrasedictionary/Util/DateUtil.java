package com.learnandroid.liuyong.phrasedictionary.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class DateUtil {
    /**
     * 获取系统当前日期字符串
     */
    public static String getCurrentDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当日期
        return formatter.format(curDate);
    }

    /**
     * 获得前几天的日期
     * @param date
     * @param day
     * @return
     */
    public static Date getNextDay(Date date,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        return date;
    }

    /**
     * 格式化日期，返回字符串
     * @param date
     * @return
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
