package com.paradise.multithread.chapter7.thread_local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : PARADISE
 * @ClassName : DateFormatWrapper SimpleDateFormat包装类
 * @description :
 * @since : 2023/3/19 22:01
 */
public class DateFormatWrapper {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }

    public static Date parse(String dateStr) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(dateStr);
    }
}
