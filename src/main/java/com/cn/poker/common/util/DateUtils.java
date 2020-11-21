package com.cn.poker.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * 获取当前时间 后多少天的 时间
     * @param days 天数
     * @return 指定天数之前的日期
     */
    public static Date getDateAfter( int days) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
        return now.getTime();
    }

    public static void main(String[] args) throws ParseException {
        double a = 3.5;
        System.out.println(Math.floor(a));
    }


    // 获取两个时间相差分钟数
    public static long getTime(Date oldTime,Date newTime)  {
        long NTime =newTime.getTime();
        //从对象中拿到时间
        long OTime = oldTime.getTime();
        long diff=(NTime-OTime);
        return diff;
    }




    // 获取两个时间相差分钟数
    public static Integer getDaycount(Date date1,Date date2) throws ParseException {
        // 获取相差的天数
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        Long timeInMillis1 = calendar.getTimeInMillis();
        calendar.setTime(date2);
        long timeInMillis2 = calendar.getTimeInMillis();
        Long betweenDays =  (timeInMillis2 - timeInMillis1) / (1000L*3600L*24L);
        return betweenDays.intValue();
    }



}
