package com.lawu.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Leach
 * @date 2017/3/22
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    // 默认日期格式
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

    // 斜杠日期格式
    public static final String DATE_SPRIT_FORMAT = "yyyy/MM/dd";

    // int日期格式
    public static final String DATE_INT_FORMAT = "yyyyMMdd";

    public static final String DATE_YYYYMM_FORMAT = "yyyyMM";

    // 年月日期格式
    public static final String DATE_YYYY_MM_FORMAT = "yyyy-MM";

    // 月日日期格式
    public static final String DATE_MM_DD_FORMAT = "MM-dd";

    // 默认日期时间格式
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 斜杠日期时间格式
    public static final String DATETIME_SPRIT_FORMAT = "yyyy/MM/dd HH:mm:ss";

    // int日期时间格式
    public static final String DATETIME_INT_FORMAT = "yyyyMMddHHmmss";

    // 默认时间格式
    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";

    // int时间格式
    public static final String TIME_INT_FORMAT = "HHmmss";

    private DateUtil() {
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        return dateFormat.format(new Date());
    }

    /**
     * 获取当前日期 yyyy/MM/dd
     *
     * @return String
     */
    public static String getSpritDate() {
        DateFormat dateSpritFormat = new SimpleDateFormat(DATE_SPRIT_FORMAT);
        return dateSpritFormat.format(new Date());
    }

    /**
     * 获取当前日期 yyyyMMdd
     *
     * @return String
     */
    public static String getIntDate() {
        DateFormat dateIntFormat = new SimpleDateFormat(DATE_INT_FORMAT);
        return dateIntFormat.format(new Date());
    }

    /**
     * 获取当前日期 yyyyMM
     *
     * @return String
     */
    public static String getYearMonthDate() {
        DateFormat dateIntFormat = new SimpleDateFormat(DATE_YYYYMM_FORMAT);
        return dateIntFormat.format(new Date());
    }

    /**
     * 获取当前日期时间 yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String getDateTime() {
        DateFormat dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        return dateTimeFormat.format(new Date());
    }

    /**
     * 获取当前日期时间 yyyy/MM/dd HH:mm:ss
     *
     * @return String
     */
    public static String getSpritDateTime() {
        DateFormat dateTimeSpritFormat = new SimpleDateFormat(DATETIME_SPRIT_FORMAT);
        return dateTimeSpritFormat.format(new Date());
    }

    /**
     * 获取当前日期时间 yyyyMMddHHmmss
     *
     * @return String
     */
    public static String getIntDateTime() {
        DateFormat dateTimeIntFormat = new SimpleDateFormat(DATETIME_INT_FORMAT);
        return dateTimeIntFormat.format(new Date());
    }

    /**
     * 获取当前时间 HH:mm:ss
     *
     * @return String
     */
    public static String getTime() {
        DateFormat timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
        return timeFormat.format(new Date());
    }

    /**
     * 获取当前时间 HHmmss
     *
     * @return String
     */
    public static String getIntTime() {
        DateFormat timeIntFormat = new SimpleDateFormat(TIME_INT_FORMAT);
        return timeIntFormat.format(new Date());
    }

    /**
     * date格式转换为double格式
     *
     * @param date
     * @return
     */
    public static Double dateDateToDoubleDate(Date date) {
        DateFormat dateTimeIntFormat = new SimpleDateFormat(DATETIME_INT_FORMAT);
        return Double.valueOf(dateTimeIntFormat.format(date));
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeFormat(Date date) {
        DateFormat dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        return dateTimeFormat.format(date);
    }

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        return dateFormat.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 字符串转换为Date
     *
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        DateFormat dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            logger.error("字符串转换为Date异常：{}", e);
        }
        return null;
    }

    /**
     * 两个时间相隔天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static int daysOfTwo(Date beginDate, Date endDate) {
        if (null == beginDate || null == endDate) {
            return -1;
        }

        long intervalMilli = endDate.getTime() - beginDate.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     * 两个时间相隔天数
     *
     * @param beginDate 开始日期
     * @return
     */
    public static int daysOfTwo(Date beginDate) {
        if (null == beginDate) {
            return -1;
        }

        Date endDate = new Date();
        long intervalMilli = endDate.getTime() - beginDate.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    public static int monthsOfTwo(Date beginDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(beginDate);
        c2.setTime(new Date());
        int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        if (year < 0) {
            year = -year;
            return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        } else {
            return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        }
    }

    /**
     * 判断是否超过当前时间
     *
     * @param beginDate
     * @return
     */
    public static Boolean isOverdue(Date beginDate) {
        if (null == beginDate) {
            return false;
        }

        try {
            Date endDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
            String date = dateFormat.format(beginDate) + " 23:59:59";
            DateFormat dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
            long intervalMilli = endDate.getTime() - dateTimeFormat.parse(date).getTime();
            return intervalMilli > 0;
        } catch (Exception e) {
            logger.error("判断是否超过当前时间异常：{}", e);
            return false;
        }
    }

    /**
     * 短信是否超时
     *
     * @param beginDate
     * @param minutes
     * @return
     */
    public static Boolean smsIsOverdue(Date beginDate, int minutes) {
        if (null == beginDate) {
            return false;
        }

        try {
            Date endDate = new Date();
            long intervalMilli = endDate.getTime() - beginDate.getTime();
            int intervalMinutes = (int) (intervalMilli / (60 * 1000));
            return intervalMinutes > minutes;
        } catch (Exception e) {
            logger.error("短信是否超时异常：{}", e);
            return false;
        }
    }

    // -------------------------------------------------------------网上下载暂未用到-------------------------------------------------

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @param format
     * @return
     */
    public static Date formatDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            logger.error("日期格式化yyyy-MM-dd异常：{}", e);
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return HH:mm:ss
     */
    public static String getTimeFormat(Date date) {
        DateFormat timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
        return timeFormat.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param
     * @return
     */
    public static String getDateFormat(Date date, String formatStr) {

        return new SimpleDateFormat(formatStr).format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    public static Date getDateFormat(String date) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("日期格式化异常：{}", e);
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static Date getDateTimeFormat(String date) {
        DateFormat dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            logger.error("时间格式化异常：{}", e);
        }
        return null;
    }

    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @param
     * @return
     */
    public static Date getNowDate() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        return DateUtil.getDateFormat(dateFormat.format(new Date()));
    }

    /**
     * 获取当前日期星期一日期
     *
     * @return date
     */
    public static Date getFirstDayOfWeek() {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前日期星期日日期
     *
     * @return date
     */
    public static Date getLastDayOfWeek() {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @param
     * @return date
     */
    public static Date getFirstDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek()); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期星期一日期
     *
     * @param
     * @return date
     */
    public static Date getLastDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK, gregorianCalendar.getFirstDayOfWeek() + 6); // Monday
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的第一天
     *
     * @return date
     */
    public static Date getFirstDayOfMonth() {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, 1);
        gregorianCalendar.add(Calendar.MONTH, 1);
        gregorianCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定年的第一个月
     *
     * @param date
     * @return
     */
    public static Date getFirstMonthOfYear(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.MONTH, gregorianCalendar.getActualMinimum(Calendar.MONTH));
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定年的最后一个月
     *
     * @param date
     * @return
     */
    public static Date getLastMonthOfYear(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.MONTH, gregorianCalendar.getActualMaximum(Calendar.MONTH));
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定天的第一秒
     *
     * @param date
     * @return
     */
    public static Date getFirstSecondOfDay(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, gregorianCalendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        gregorianCalendar.set(Calendar.MINUTE, gregorianCalendar.getActualMinimum(Calendar.MINUTE));
        gregorianCalendar.set(Calendar.SECOND, gregorianCalendar.getActualMinimum(Calendar.SECOND));
        return gregorianCalendar.getTime();
    }

    /**
     * 获取指定天的最后一秒
     *
     * @param date
     * @return
     */
    public static Date getLastSecondOfDay(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, gregorianCalendar.getMaximum(Calendar.HOUR_OF_DAY));
        gregorianCalendar.set(Calendar.MINUTE, gregorianCalendar.getMaximum(Calendar.MINUTE));
        gregorianCalendar.set(Calendar.SECOND, gregorianCalendar.getMaximum(Calendar.SECOND));
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期前一天
     *
     * @param date
     * @return
     */
    public static Date getDayBefore(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day - 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期前两天
     *
     * @param date
     * @return
     */
    public static Date getDayBeforeTwo(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day - 2);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期后一天
     *
     * @param date
     * @return
     */
    public static Date getDayAfter(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day + 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取日期前一月
     *
     * @param date
     * @return
     */
    public static Date getMonthBefore(Date date) {
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.MONTH);
        gregorianCalendar.set(Calendar.MONTH, day - 1);
        return gregorianCalendar.getTime();
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getNowYear() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当月天数
     *
     * @return
     */
    public static int getNowMonthDay() {
        Calendar d = Calendar.getInstance();
        return d.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取时间段的每一天
     *
     * @param
     * @param
     * @return
     */
    public static List<Date> getEveryDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        Calendar gregorianCalendar = new GregorianCalendar();
        // 格式化日期(yy-MM-dd)
        startDate = DateUtil.getDateFormat(DateUtil.getDateFormat(startDate));
        endDate = DateUtil.getDateFormat(DateUtil.getDateFormat(endDate));
        List<Date> dates = new ArrayList<Date>();
        gregorianCalendar.setTime(startDate);
        dates.add(gregorianCalendar.getTime());
        while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
            // 加1天
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(gregorianCalendar.getTime());
        }
        return dates;
    }

    /**
     * 获取提前多少个月
     *
     * @param month
     * @return
     */
    public static Date getFirstMonth(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -month);
        return c.getTime();
    }

    /**
     * 判断从开始的时间到现在的时间是否超过overTime数值的时间
     *
     * @param fromDate 开始时间
     * @param toDate   现在时间
     * @param overTime 超过的时间
     * @param field    单位
     * @return
     */
    public static boolean isExceeds(Date fromDate, Date toDate, int overTime, int field) {
        // 所有参数不能为空
        if (fromDate == null || toDate == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(fromDate);// 把当前时间赋给日历
        calendar.add(field, overTime); // 添加超过时间
        Date afterDate = calendar.getTime();

        if (toDate.getTime() >= afterDate.getTime()) {
            return true;
        }

        return false;
    }

    /**
     * 获取overTime之后的时间
     *
     * @param from     开始时间
     * @param overTime 超过的时间
     * @param field    单位
     * @return
     */
    public static Date add(Date from, int overTime, int field) {
        // 所有参数不能为空
        if (from == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(from);// 把当前时间赋给日历
        calendar.add(field, overTime); // 添加超过时间

        return calendar.getTime();
    }

    /**
     * @param from  开始时间
     * @param to
     * @param field
     * @return
     * @author Sunny
     */
    public static Long interval(Date from, Date to, int field) {
        Long rtn = null;
        // 所有参数不能为空
        if (from == null || to == null) {
            return rtn;
        }

        long interval = to.getTime() - from.getTime();

        switch (field) {
            case Calendar.MILLISECOND:
                rtn = interval;
                break;
            case Calendar.SECOND:
                rtn = interval / 1000;
                break;
            case Calendar.MINUTE:
                rtn = interval / 1000 / 60;
                break;
            case Calendar.HOUR:
                rtn = interval / 1000 / 60 / 60;
                break;
            default:
                break;
        }
        return rtn;
    }

    /**
     * 获取date中field的值
     *
     * @param date
     * @param field
     * @return
     * @author Sunny
     * @date 2017年7月3日
     * @see {@link java.util.Calendar#Calendar Calendar}
     */
    public static int getFieldValue(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    /**
     * 获取date中field的最大值
     *
     * @param date
     * @param field
     * @return
     * @author Sunny
     * @date 2017年7月3日
     * @see {@link java.util.Calendar#Calendar Calendar}
     */
    public static int getFieldMaxValue(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getMaximum(field);
    }

    public static String getIntDateByTime(Date time) {
        DateFormat dateIntFormat = new SimpleDateFormat(DATE_INT_FORMAT);
        return dateIntFormat.format(time);
    }

    /**
     * 校验时间格式是否符合指定格式
     *
     * @param dateStr   时间字符串
     * @param formatStr 格式
     * @return
     */
    public static boolean checkDateFormat(String dateStr, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            format.parse(dateStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @param date
     * @param day  想要获取的日期与传入日期的差值 比如想要获取传入日期前四天的日期 day=-4即可
     * @return
     */
    public static Date getSomeDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 传入日期的往后的第几个月的第一天
     *
     * @return
     */
    public static Date nextMonthFirstDate(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取指定小时时间
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date getAppointTime(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }
    
    /**
     * 获取指定分钟时间
     *
     * @param minute
     * @return
     */
    public static Date getMinutepointTime(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     *  判断当前时间是否在两个时间之间
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取前几个小时时间
     * @return
     */
    public static Date getBeforeHourDate(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateTimeFormat(df.format(calendar.getTime()));
    }




}