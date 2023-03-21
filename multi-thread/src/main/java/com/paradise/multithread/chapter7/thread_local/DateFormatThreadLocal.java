package com.paradise.multithread.chapter7.thread_local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : PARADISE
 * @ClassName : DateFormatThreadLocal
 * @description :
 * @since : 2023/3/20 21:44
 */
public class DateFormatThreadLocal {
    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String format(Date date) {
        return SIMPLE_DATE_FORMAT.get().format(date);
    }

    public static Date parse(String dateStr) throws ParseException {
        return SIMPLE_DATE_FORMAT.get().parse(dateStr);
    }
}
