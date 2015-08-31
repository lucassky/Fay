package com.lucassky.fay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/31.
 */
public class StringUtil {
    private static long MINUTE_MILLISECOND = 60*1000;
    private static long HOUR_MILLISECOND = 60*60*1000;
    private static long DATE_MILLISECOND = 24*60*60*1000;
    private static SimpleDateFormat sdHour = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sdDate = new SimpleDateFormat("HH:mm yyyy.MM.dd");
    public static String formarTime(String timeStr){
        String str = "刚刚";
        Date date = new Date(timeStr);
        Date dateNow = new Date();
        long dateLong = date.getTime();
        long dateNowLong = dateNow.getTime();
        long offset = dateNowLong - dateLong;
        if(offset < MINUTE_MILLISECOND){
            str = "一分钟内";
        }else if(offset < HOUR_MILLISECOND){
           str =  offset/MINUTE_MILLISECOND + "分钟之前";
        }else if(offset < DATE_MILLISECOND){
            str = sdHour.format(date);
        }else{
            str = sdDate.format(date);
        }
        return str;
    }
}
