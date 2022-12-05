package com.code.util;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 有关日期处理的工具类。
 */
public abstract class DateUtil {

    public static final String DEFAULT_PATTERN = "yyyyMMdd";

    public static final String FULL_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String SHORT_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String SHORT_MONTH_FORMAT_PATTERN = "yyyy-MM";
    /**
     * 日期类型的默认值
     */
    public static final Date DEFAULT = parseDate("1970-01-01 00:00:00");
    public static final Date DEFAULT_GMT8 = parseDate("1970-01-01 08:00:00");

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static Date getCurrentDatetime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 返回去除时分秒的日期对象
     */
    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 返回当前月份
     */

    public static String getYear() {
        return formatDate("yyyy");
    }

    /**
     * 返回 yyyy-MM-dd HH:mm:ss 格式的当前日期
     *
     * @return [yyyy-MM-dd HH:mm:ss]
     */
    public static String getFullPatternNow() {
        return formatDate(FULL_FORMAT_PATTERN);
    }

    /**
     * 格式化日期 yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期 @see Date
     * @return [yyyy-MM-dd HH:mm:ss]
     */
    public static String getFullPatternDate(Date date) {
        return formatDate(date, FULL_FORMAT_PATTERN);
    }

    /**
     * 将日期转换为 <code>yyyyMMdd</code> 的字符串格式
     *
     * @param date 日期 @see Date
     * @return 格式化后的日期字符串
     */
    public static String formatDate(final Date date) {
        return formatDate(date, DEFAULT_PATTERN);
    }

    /**
     * 将日期转换为指定的字符串格式
     *
     * @param date   日期 @see Date
     * @param format 日期格式
     * @return 格式化后的日期字符串，如果<code>date</code>为<code>null</code>或者 <code>format</code>为空，则返回<code>null</code>。
     */
    public static String formatDate(final Date date, String format) {
        if (null == date || StrUtil.isBlank(format)) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将当前日期转换为指定的字符串格式
     *
     * @param format 日期格式
     * @return 格式化后的日期字符串
     */
    public static String formatDate(String format) {
        return formatDate(new Date(), format);
    }

    /**
     * 将<code>yyyyMMdd<code>格式的字符串转变为日期对象
     *
     * @param sDate 日期字符串
     * @return 日期
     */
    public static Date parseDate(String sDate) {
        return parseDate(sDate, DEFAULT_PATTERN, null);
    }

    /**
     * 将字符串转换撑日期对象
     *
     * @param sDate  日期字符串
     * @param format 日期格式 @see DateFormat
     * @return 日期对象 @see Date
     */
    public static Date parseDate(String sDate, String format) {
        return parseDate(sDate, format, null);
    }

    /**
     * 将字符串转换成日期对象
     *
     * @param sDate        日期字符串
     * @param format       日期格式 @see DateFormat
     * @param defaultValue 默认值
     * @return 日期对象，如果格式化失败则返回默认值<code>defaultValue</code>
     */
    public static Date parseDate(String sDate, String format, Date defaultValue) {
        if (StrUtil.isBlank(sDate) || StrUtil.isBlank(format)) {
            return defaultValue;
        }

        DateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(sDate);
        } catch (ParseException e) {
            return defaultValue;
        }

    }

    /**
     * 给指定日期增加月份数
     *
     * @param date   指定日期 @see Date
     * @param months 增加的月份数
     * @return 增加月份后的日期
     */
    public static Date addMonths(Date date, int months) {
        if (months == 0) {
            return date;
        }

        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 给指定日期增加天数
     *
     * @param date 指定日期 @see Date
     * @param days 增加的天数
     * @return 增加天数后的日期
     */
    public static Date addDays(final Date date, int days) {

        return add(date, Calendar.DAY_OF_MONTH, days);
    }

    /**
     * 给指定日期增加分钟
     *
     * @param date 指定日期 @see Date
     * @param mins 增加的分钟
     * @return 增加分钟后的日期
     */
    public static Date addMins(final Date date, int mins) {
        return add(date, Calendar.MINUTE, mins);
    }

    /**
     * 给指定日期增加秒
     *
     * @param date    指定日期 @see Date
     * @param seconds 增加的秒
     * @return 增加秒后的日期
     */
    public static Date addSeconds(final Date date, int seconds) {
        return add(date, Calendar.SECOND, seconds);
    }

    /**
     * @param type {Calendar.MINUTE, Calendar.DAY_OF_MONTH}
     */
    public static Date add(final Date date, int type, int value) {
        if (value == 0) {
            return date;
        }

        if (date == null) {
            return null;
        }

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.add(type, value);

        return cal.getTime();
    }


    public static boolean between(Date value, Date min, Date max) {
        if (value != null && (min != null || max != null)) {
            BigDecimal zero = new BigDecimal(0);
            BigDecimal longMax = new BigDecimal(Long.MAX_VALUE);
            boolean in = NumberUtil.isIn(new BigDecimal(value.getTime()), min == null ? zero : new BigDecimal(min.getTime()), max == null ? longMax: new BigDecimal(max.getTime()));
            return in;
        }
        return false;
    }

    /**
     * 生成int类型日期
     */
    public static int toInt(Date date) {
        if (date != null) {
            return Integer.parseInt(formatDate(date, DEFAULT_PATTERN));
        }
        return 0;
    }

    /**
     * 生成java.util.Date类型的对象
     */
    public static Date getDate(int year, int month, int day) {
        GregorianCalendar d = new GregorianCalendar(year, month - 1, day);
        return d.getTime();
    }

    /**
     * 获取时间间隔 秒
     */
    public static int minusSeconds(Date startTime, Date endTime) {
        long value = endTime.getTime() - startTime.getTime();
        return (int) (value / 1000);
    }

    /**
     * 获取时间间隔 天
     */
    public static int minusDays(Date startTime, Date endTime) {

        return minusSeconds(startTime, endTime) / (60 * 60 * 24);
    }

    /**
     * 获取当天的结束时间
     * yyyy-MM-dd 23:59:59
     */
    public static Date getCurrentDateEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的结束时间
     *
     * @param date
     * @return
     */
    public static Date getDateEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天剩余秒数
     */
    public static int getDailyRemainingSeconds() {
        return minusSeconds(new Date(), getCurrentDateEnd());
    }


    /**
     * 日期比较 thisDate 是否小于 anotherDate
     */
    public static boolean lessThan(Date thisDate, Date anotherDate) {
        if (thisDate == null || anotherDate == null) return false;
        return thisDate.before(anotherDate);
    }

    /**
     * 日期比较 thisDate 是否大于 anotherDate
     */
    public static boolean greaterThan(Date thisDate, Date anotherDate) {
        if (thisDate == null || anotherDate == null) return false;
        return thisDate.after(anotherDate);
    }


    /**
     * 两个时间段是否有交集
     *
     * @return true 有交集
     */
    public static boolean isOverlap(Date leftStartDate, Date leftEndDate, Date rightStartDate, Date rightEndDate) {
        return !lessThan(leftEndDate, rightStartDate) && !lessThan(rightEndDate, leftStartDate);
    }

    /**
     * 默认时间转为空
     */
    public static Date defaultDateToNull(Date date) {
        if (DEFAULT.equals(date) || DEFAULT_GMT8.equals(date)) {
            return null;
        }
        return date;
    }

    /**
     * 获取指定星期几的日期
     *
     * @param num       -1上周,0本周,1下周,2下下周 依次类推
     * @param dayOfWeek 星期几 java.util.Calendar#SUNDAY
     * @return 对应日期
     */
    public static Date getWeekDate(int num, int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, num * 7);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return cal.getTime();
    }

    /**
     * 获取指定星期几的日期
     *
     * @param num       -1上周,0本周,1下周,2下下周 依次类推
     * @param dayOfWeek 星期几 java.util.Calendar#SUNDAY
     * @return 对应日期
     */
    public static Date getWeekDate(int num, int dayOfWeek, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, num * 7);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return cal.getTime();
    }

    /**
     * 获取当前是星期几
     *
     * @return 星期一返回1  星期日返回7
     */
    public static int getCurWeekDay() {
        Calendar now = Calendar.getInstance();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;

    }

    /**
     * 返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回当前时间的小时值
     *
     * @return 返回小时
     */
    public static int getCurHour() {
        return getHour(new Date());
    }


    /**
     * 判断是否在规定的时间内 nowTimeParam 当前时间 beginTime规定开始时间 endTime规定结束时间
     */
    public static boolean timeCalendar(Date nowTimeParam, String beginTimeStr, String endTimeStr) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date beginTime = null;
        Date endTime = null;
        Date nowTime = null;
        try {
            beginTime = df.parse(beginTimeStr);
            endTime = df.parse(endTimeStr);
            String nowFormat = df.format(nowTimeParam);
            nowTime = df.parse(nowFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //设置当前时间
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        //设置开始时间
        Calendar amBegin = Calendar.getInstance();
        amBegin.setTime(beginTime);
        //设置结束时间
        Calendar pmEnd = Calendar.getInstance();
        pmEnd.setTime(endTime);
        //处于开始时间之后，和结束时间之前的判断
        if (date.after(amBegin) && date.before(pmEnd)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 设置为当日 00:00:00
     */
    public static Date floor(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 设置为当日 23:59:59.999
     *
     * @return
     */
    public static Date ceiling(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 设置为当日 23:59:59
     *
     * @return
     */
    public static Date ceilingDaySecond(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }


    /**
     * 获取日期为周几
     * 周日=1 周一=2
     */
    public static int dayInWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 设置为当日 23:59:59.000 防止mysql保存数据自动进位
     *
     * @return
     */
    public static Date notCarryCeiling(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前分钟的开始时间 8:10:00
     *
     * @param date
     * @return
     */
    public static Date getMinuteFloor(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前分钟的结束时间 8:10:59
     *
     * @param date
     * @return
     */
    public static Date getMinuteCeiling(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取指定日期中的月份
     *
     * @param date
     * @return
     */
    public static Integer getDateTimeMonthNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int deadLineMonth = cal.get(Calendar.MONTH) + 1;
        return deadLineMonth;
    }

    /**
     * 获取指定日期中的年份
     *
     * @param date
     * @return
     */
    public static Integer getDateTimeYearNum(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int deadLineMonth = cal.get(Calendar.YEAR);
        return deadLineMonth;
    }

}
