package com.xueqiu.utils;

/**
 * @Author:ggq
 * @Date:2018/11/20
 * @Description:时间工具类
 */
public class DateUtils {

    public static String formatDateTime(long timeMills){
        long day = timeMills/(24*60*60*1000);
        long hour = timeMills/(60*60*1000) - day*24;
        long min = timeMills/(60*1000) - day*24 - hour*60;
        long s = timeMills/(1000) - day*24 - hour*60 - min*60;
        long sss = timeMills - day*24 - hour*60 - min*60 - s*1000;
        return (day>0?day+"天,":"")+hour+":"+min+":"+s+"."+sss;
    }
}
