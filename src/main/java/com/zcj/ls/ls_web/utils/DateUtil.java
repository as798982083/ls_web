package com.zcj.ls.ls_web.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 返回当前日期
     * @return yyyy-MM-dd
     */
    public static Date getCurrentDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String nowString = df.format(calendar.getTime());
        Date nowDate = null;
        try {
            nowDate = df.parse(nowString);
        } catch (ParseException e) {
            System.out.println("DateUtil获取系统当前日期出错！！！");
            e.printStackTrace();
        }

        return nowDate;
    }
}
