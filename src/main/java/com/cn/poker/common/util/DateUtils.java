package com.cn.poker.common.util;

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

    public static void main(String[] args) {
        Date dateAfter = getDateAfter(30);
        System.out.println(dateAfter);
    }
}
