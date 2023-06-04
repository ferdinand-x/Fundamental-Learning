package com.paradise.code.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT_LOCAL_DATE = "yyyy-MM-dd";

    // Convert Date to String
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    // Convert String to Date
    public static Date parseDate(String dateString, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateString);
    }

    // Convert timestamp to Date
    public static Date timestampToDate(long timestamp) {
        return new Date(timestamp);
    }

    // Convert Date to timestamp
    public static long dateToTimestamp(Date date) {
        return date.getTime();
    }

    // Convert LocalDateTime to String
    public static String formatLocalDateTime(LocalDateTime localDateTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(localDateTimeToDate(localDateTime));
    }

    // Convert String to LocalDateTime
    public static LocalDateTime parseLocalDateTime(String dateString, String format) throws ParseException {
        Date date = parseDate(dateString, format);
        return dateToLocalDateTime(date);
    }

    // Convert Date to LocalDateTime
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    // Convert LocalDateTime to Date
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // Convert String to LocalDate
    public static LocalDate parseLocalDate(String dateString, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateString);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // Convert LocalDate to String
    public static String formatLocalDate(LocalDate localDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return sdf.format(date);
    }

    // Perform date arithmetic with Date
    public static Date addDate(Date date, int amount, ChronoUnit unit) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime result = localDateTime.plus(amount, unit);
        return localDateTimeToDate(result);
    }

    // Perform date arithmetic with LocalDate
    public static LocalDate addLocalDate(LocalDate localDate, int amount, ChronoUnit unit) {
        return localDate.plus(amount, unit);
    }

    // Perform date arithmetic with LocalDateTime
    public static LocalDateTime addLocalDateTime(LocalDateTime localDateTime, int amount, ChronoUnit unit) {
        return localDateTime.plus(amount, unit);
    }

}
