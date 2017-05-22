/**
 * UpgradeDateUtil.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.xiaoka.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;

import com.uxuexi.core.common.util.StringUtil;
import com.uxuexi.core.common.util.Util;

/**
 * TODO(DateUtil工具类  优化)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年5月3日 	 
 *
 *
 *
 * 关于Calendar：
 * set(int field, int value) - 是用来设置"年/月/日/小时/分钟/秒/微秒"等
 * 		field 的定义在 Calendar 中，分别用来代表年，月，日等...
 * 		注意：     1，月份是从0开始计数，要设置8月，参数应给7。从一个Calendar对象中取月份，最大值只能取到11，代表12月
 * 			  2，set方法不会马上刷新内部记录
 * 			  3，设置 Calendar 为 Lenient false 时，它会依据特定的月份检查出错误的赋值。如：
 * 				 cal.setLenient(false);
 *				 cal.set(2000, 1, 32, 0, 0, 0);
 *			  4，Calendar 序列化的时候，如果serialize GregorianCalendar 时没有保存所有的信息，
 *				 当它被恢复到内存中，又缺少足够的信息时，Calendar 会被恢复到 EPOCH 的起始值。
 *               Calendar 对象由两部分构成：字段和相对于 EPOC 的微秒时间差。字段信息是由微秒时间差计算出的，
 *               而 set() 方法不会强制 Calendar 重新计算字段。这样字段值就不对了。可以通过调用get方法解决
 *               
 *            5，add(field, -value)方法：如果需要减去值，那么使用负数值就可以了
 *               calendar.add(Calendar.MONTH, 1) ;//在calendar代表的日期上加上1个月
 */
public class UpgradeDateUtil {

	private int MaxDate; // 一月最大天数   
	private int MaxYear; // 一年最大天数 
	private int weeks = 0;// 用来全局控制 上一周，本周，下一周的周数变化   

	/**4位年*/
	public static final String FORMAT_YYYY = "yyyy";

	/**年月，4位年，2位月*/
	public static final String FORMAT_YYYYMM = "yyyyMM";

	/**年月日格式*/
	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";

	/**时分秒格式*/
	public static final String FORMAT_HHMMSS = "HHmmss";

	/**两位的天*/
	public static final String FORMAT_DD = "dd";

	/**年月日时分秒格式*/
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String FORMAT_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final SimpleDateFormat FORMAT_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat FORMAT_DEFAULT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat FORMAT_DEFAULT_DATE_SS = new SimpleDateFormat("yyyy.MM.dd");
	public static final SimpleDateFormat FORMAT_DEFAULT_TIME = new SimpleDateFormat("HH:mm:ss");

	/**年-月-日格式*/
	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	
	/**年.月.日格式*/
	public static final String FORMAT_YYYY_MM_DD_UP = "yyyy.MM.dd";

	/**默认的日期分隔符*/
	public static final String DATE_SEPARATOR_DEFAULT = "-";

	/**日期分隔符：斜杠‘/’*/
	public static final String DATE_SEPARATOR_SLASH = "/";

	/**默认的时间分隔符，冒号*/
	public static final String TIME_SEPARATOR_DEFAULT = ":";

	/**一天代表的毫秒数*/
	public static final int MILLIS_OF_DAY = 1000 * 60 * 60 * 24;

	/**根据日期字符串中的数字个数判断使用对应的日期格式化方式*/
	private static String getFormatPattern(String dateString) {
		int len = dateString.length();
		String formatPattern = null;

		switch (len) {
		case 2:
			formatPattern = FORMAT_DD;
			break;
		case 4:
			formatPattern = FORMAT_YYYY;
			break;
		case 6:
			formatPattern = FORMAT_YYYYMM;
			break;
		case 8:
			formatPattern = FORMAT_YYYYMMDD;
			break;
		case 14:
			formatPattern = FORMAT_YYYYMMDDHHMMSS;
			break;
		default:
			throw new IllegalArgumentException("日期格式不正确");
		}
		return formatPattern;
	}

	/**==========================================日期转换格式化等=========================================================*/

	/**
	 * 处理给出的日期字符串，将其换算为正确的日期字符串，使用给定的格式返回，比如:
	 * 日期："2012-01-32"
	 * 格式："yyyy/MM/dd"
	 * 返回: "2012/02/01"
	 */
	public static String format(String dateString, String formatPattern) {
		Calendar calendar = parse2Calendar(dateString);
		return format(calendar, formatPattern);
	}

	/**处理给出的日期字符串，将其换算为正确的日期字符串，使用默认的yyyyMMddHHmmss的形式返回*/
	public static String format(String dateString) {
		Calendar calendar = parse2Calendar(dateString);
		return format(calendar);
	}

	/**将Calendar转为yyyyMMdd形式的字符串*/
	public static String format(Calendar calendar) {
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
		return dateFormat.format(calendar.getTime());
	}

	/**将Calendar转为指定形式的字符串*/
	public static String format(Calendar calendar, String formatPattern) {
		DateFormat dateFormat = new SimpleDateFormat(formatPattern);
		return dateFormat.format(calendar.getTime());
	}

	public static String Date2String(Date date) {
		return FORMAT_FULL.format(date);
	}

	/**
	 * 将日期字符串转为日期类型,系统自动根据所提供的日期字符串中数字的个数选择格式化方式
	 * @param dateStr   日期字符串
	 * @return 
	 */
	public static Date string2Date(String dateStr) {
		if (Util.isEmpty(dateStr)) {
			throw new IllegalArgumentException("日期字符串不能为空");
		} else {
			dateStr = StringUtil.drawNumber(dateStr);
		}
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(getFormatPattern(dateStr));
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date string2Date(String dateStr, String formatPattern) {
		if (Util.isEmpty(dateStr)) {
			throw new IllegalArgumentException("日期字符串不能为空");
		}
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(formatPattern);
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将字符串表示的日期转化为Calendar
	 */
	public static Calendar parse2Calendar(String dateString) {
		//只保留数字
		dateString = StringUtil.drawNumber(dateString);
		String formatPattern = getFormatPattern(dateString);
		return parse2Calendar(dateString, formatPattern);
	}

	/**日期字符串转为日历*/
	public static Calendar parse2Calendar(String dateString, String formatPattern) {
		Date date = string2Date(dateString, formatPattern);
		return parse2Calendar(date);
	}

	/**日期转为日历*/
	public static Calendar parse2Calendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * 获取DateTime对象（org.joda.time.DateTime）
	 *
	 * @return dateTime对象
	 */
	public final static DateTime dateTime(Date date) {
		return new DateTime(date);
	}

	/**==========================================BETWEEN=========================================================*/

	//years between

	/**两个指定日期之间间隔的年数*/
	public static int yearsBetween(String datePre, String dateLatter) {
		datePre = StringUtil.drawNumber(datePre);
		dateLatter = StringUtil.drawNumber(dateLatter);

		if (datePre.length() != dateLatter.length()) {
			throw new IllegalArgumentException("两个日期的格式要相同");
		}

		String formatPattern = getFormatPattern(datePre);
		return yearsBetween(datePre, dateLatter, formatPattern);
	}

	/**两个指定日期之间间隔的年数*/
	public static int yearsBetween(String datePre, String dateLatter, String formatPattern) {
		Calendar pre = parse2Calendar(datePre, formatPattern);
		Calendar later = parse2Calendar(dateLatter, formatPattern);

		return yearsBetween(pre, later);
	}

	/**两个指定日期之间间隔的年数*/
	private static int yearsBetween(Calendar pre, Calendar later) {
		return fieldsBetween(pre, later, Calendar.YEAR);
	}

	//months between

	/**两个指定日期之间间隔的月数*/
	public static int monthsBetween(String datePre, String dateLatter) {
		datePre = StringUtil.drawNumber(datePre);
		dateLatter = StringUtil.drawNumber(dateLatter);

		if (datePre.length() != dateLatter.length()) {
			throw new IllegalArgumentException("两个日期的格式要相同");
		}

		String formatPattern = getFormatPattern(datePre);
		return monthsBetween(datePre, dateLatter, formatPattern);
	}

	/**两个指定日期之间间隔的月数*/
	public static int monthsBetween(String datePre, String dateLatter, String formatPattern) {
		Calendar pre = parse2Calendar(datePre, formatPattern);
		Calendar later = parse2Calendar(dateLatter, formatPattern);
		return monthsBetween(pre, later);
	}

	/**两个指定日期之间间隔的月数*/
	private static int monthsBetween(Calendar pre, Calendar later) {
		return fieldsBetween(pre, later, Calendar.MONTH);
	}

	//days between

	/**
	 * 返回两个日期之间相差的天数，如果第1个日期小于第二个日期，将返回正数，否则返回负数。
	 * 使用yyyyMMdd格式化日期
	 */
	public static int daysBetween(String datePre, String dateLatter) {
		datePre = StringUtil.drawNumber(datePre);
		dateLatter = StringUtil.drawNumber(dateLatter);

		if (datePre.length() != dateLatter.length()) {
			throw new IllegalArgumentException("两个日期的格式要相同");
		}

		String formatPattern = getFormatPattern(datePre);
		return daysBetween(datePre, dateLatter, formatPattern);
	}

	/**
	 * 返回两个日期之间相差的天数，如果第1个日期小于第二个日期，将返回正数，否则返回负数。
	 * 使用指定的格式formatPattern，格式化日期
	 */
	public static int daysBetween(String datePre, String dateLatter, String formatPattern) {
		Calendar pre = parse2Calendar(datePre, formatPattern);
		Calendar later = parse2Calendar(dateLatter, formatPattern);
		return daysBetween(pre, later);
	}

	/**
	 * 返回两个Calendar之间相差的天数,使用数学的方式计算
	 */
	public static int daysBetween(Calendar pre, Calendar later) {
		long millis = millisBetween(pre, later);
		return (int) (millis / MILLIS_OF_DAY);
	}

	/**
	 * 返回两个日期之间相差的天数，如果第1个日期小于第二个日期，将返回正数，否则返回负数。
	 * 使用yyyyMMdd格式化日期
	 */
	public static int daysBetween(Date datePre, Date dateLatter) {
		String pre = FORMAT_FULL.format(datePre);
		String later = FORMAT_FULL.format(dateLatter);
		return daysBetween(pre, later);
	}

	//millis between

	/**两个日期之间相差的毫秒数*/
	public static long millisBetween(Date datePre, Date dateLatter) {
		String pre = FORMAT_FULL.format(datePre);
		String later = FORMAT_FULL.format(dateLatter);
		return millisBetween(pre, later);
	}

	/**两个日期之间相差的毫秒数*/
	public static long millisBetween(String datePre, String dateLatter) {
		Calendar pre = parse2Calendar(datePre);
		Calendar later = parse2Calendar(dateLatter);
		return millisBetween(pre, later);
	}

	/**两个日期之间相差的毫秒数*/
	public static long millisBetween(Calendar pre, Calendar later) {
		long preMillis = pre.getTimeInMillis();
		long latterMillis = later.getTimeInMillis();
		long millis = latterMillis - preMillis;
		return millis;
	}

	/**判断一个日期是否在某个区间*/
	public static boolean dateBetween(Date date, Date pre, Date later) {
		return (date.after(pre) && date.before(later));
	}

	/**判断一个日期是否在某个区间*/
	public static boolean dateBetween(Date date, String preStr, String laterStr) {
		Date pre = string2Date(preStr);
		Date later = string2Date(laterStr);
		return dateBetween(date, pre, later);
	}

	private static int fieldsBetween(Calendar pre, Calendar later, int dateType) {
		Calendar calBefore, calAfter;

		//用于判断应该返回正数还是负数
		boolean positive = false;

		if (pre.before(later)) {
			positive = true;
			calBefore = pre;
			calAfter = later;
		} else {
			calBefore = later;
			calAfter = pre;
		}

		int fieldCount = 0;
		while (calBefore.before(calAfter)) {
			calBefore.add(dateType, 1);
			fieldCount++;
		}

		if (positive)
			return fieldCount;
		else
			return -fieldCount;
	}

	/**==========================================获取日期=========================================================*/

	//now

	/**
	 * 获取当前日期（java.util.Date）
	 */
	public static Date nowDate() {
		return new java.util.Date();
	}

	/**
	 * 获取当前时间的DateTime对象（org.joda.time.DateTime）
	 *
	 * @return 当前dateTime对象
	 */
	public final static DateTime nowDateTime() {
		return new DateTime();
	}

	/**
	 * 获取当前java.sql.Timestamp
	 * @return 当前java.sql.Timestamp
	 * @see java.util.Date
	 */
	public final static Timestamp nowTimeStamp() {
		return new Timestamp(nowDate().getTime());
	}

	/**
	 * 当前日期字符串（yyyy-MM-dd HH:mm:ss）
	 */
	public static String nowDateTimeString() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0);
		return FORMAT_FULL.format(c.getTime());
	}

	/**
	 * 当前日期字符串（yyyy-MM-dd）
	 */
	public static String nowDateString() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0);
		return FORMAT_DEFAULT_DATE.format(c.getTime());
	}

	/**
	 * 当前时间字符串（HH:mm:ss）
	 */
	public static String nowTimeString() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 0);
		return FORMAT_DEFAULT_TIME.format(c.getTime());
	}

	/******************************************** 年月日  ******************************************/

	/**
	 * 获取一个日期的年份
	 */
	public static int getYear(Date date) {
		Calendar c = parse2Calendar(date);
		return getDateType(c, Calendar.YEAR);
	}

	/**
	 * 获取一个日期的年份
	 */
	public static int getYear(Calendar calendar) {
		return getDateType(calendar, Calendar.YEAR);
	}

	/**
	 * 获取一个日期的年份
	 */
	public static int getYear(String dateString) {
		Calendar calendar = parse2Calendar(dateString);
		return getYear(calendar);
	}

	/**
	 * 获取当前年份
	 */
	public static int getCurrentYear() {
		return getYear(nowDate());
	}

	/******************************************** 月 ******************************************/

	/**
	 * 获取一个日期的月份
	 */
	public static int getMonth(Date date) {
		Calendar c = parse2Calendar(date);
		return getDateType(c, Calendar.MONTH) + 1;
	}

	/**
	 * 获取一个日期的月份
	 */
	public static int getMonth(Calendar calendar) {
		return getDateType(calendar, Calendar.MONTH) + 1;
	}

	/**
	 * 获取一个日期的月份
	 */
	public static int getMonth(String dateString) {
		Calendar calendar = parse2Calendar(dateString);
		return getMonth(calendar);
	}

	/**  
	 * 获得当前月份  
	 */
	public static int getThisMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取一个日期的天(在当月的号数)
	 */
	public static int getDay(Date date) {
		Calendar c = parse2Calendar(date);
		return getDateType(c, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取一个日期的天(在当月的号数)
	 */
	public static int getDay(String dateString) {
		Calendar calendar = parse2Calendar(dateString);
		return getDay(calendar);
	}

	/**
	 * 获取一个日期的天(在当月的号数)
	 */
	public static int getDay(Calendar calendar) {
		return getDateType(calendar, Calendar.DAY_OF_MONTH);
	}

	private static int getDateType(Calendar calendar, int dateType) {
		return calendar.get(dateType);
	}

	/*******************************  今天 操作     **********************************/
	/**  
	 * 获得今天在本年的第几天  
	 */
	public static int getDayOfYear() {
		return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}

	/**  
	 * 获得今天在本月的第几天(获得当前日)  
	 *   
	 * @return  
	 */
	public static int getDayOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**  
	 * 获得今天在本周的第几天    
	 * 
	 * 周一为第一天
	 *   
	 * @return  
	 */
	public static int getDayOfWeek() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		if (day == 0) {
			day = 7;
		}
		return day;
	}

	/**  
	 * 获得今天是这个月的第几周  
	 *   
	 * @return  
	 */
	public static int getWeekOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	/**
	 * 取得日期所在周的第一天(星期一)
	 * @param date
	 * @return
	 */
	public static Date getFirstWeekDay(Date date) {
		Calendar c = parse2Calendar(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return c.getTime();
	}

	/**
	 * 取得日期所在周的最后一天(星期天)
	 * @param date
	 * @return
	 */
	public static Date getLastWeekDay(Date date) {
		Calendar c = parse2Calendar(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return c.getTime();
	}

	/**
	 * 取得日期所在月的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstMonthDay(Date date) {
		Calendar c = parse2Calendar(date);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DAY_OF_MONTH, 1 - dayOfMonth);
		return c.getTime();
	}

	/**
	 * 取得日期所在月的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastMonthDay(Date date) {
		Calendar c = parse2Calendar(date);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		int maxDaysOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.add(Calendar.DAY_OF_MONTH, maxDaysOfMonth - dayOfMonth);
		return c.getTime();
	}

	/**  
	 * 获取当月第一天  
	 */
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号   
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**  
	 * 计算当月最后一天,返回字符串  
	 *   
	 * @return  
	 */
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号   
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号   
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天   

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**  
	 * 上月第一天  
	 */
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号   
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号   
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天   

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**  
	 * 获得上月最后一天的日期  
	 *   
	 * @return  
	 */
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月   
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天   
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天   
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**  
	 * 获得下个月第一天的日期  
	 *   
	 * @return  
	 */
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月   
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天   
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**  
	 * 获得下个月最后一天的日期  
	 *   
	 * @return  
	 */
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月   
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天   
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天   
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得上年第一天的日期
	 */
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式   
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	/**
	 * 获得上年最后一天的日期   
	 */
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	/**  
	 * 获得本年第一天的日期  
	 *   
	 * @return  
	 */
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *   
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式   
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	/**  
	 * 获得明年最后一天的日期  
	 *   
	 * @return  
	 */
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年   
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**  
	 * 获得明年第一天的日期  
	 *   
	 * @return  
	 */
	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年   
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	/**  
	 * 获得本年有多少天  
	 *   
	 * @return  
	 */
	@SuppressWarnings("unused")
	private int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天   
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。   
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	/**  
	 * 获得本季度第一天  
	 *   
	 * @param month  
	 * @return  
	 */
	public String getThisSeasonFirstTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式   
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);   
		@SuppressWarnings("unused")
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + addZero(start_month) + "-" + addZero(start_days);
		return seasonDate;
	}

	/**  
	 * 获得本季度最后一天  
	 *   
	 * @param month  
	 * @return  
	 */
	@SuppressWarnings("unused")
	public String getThisSeasonFinallyTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式   
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);   
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + addZero(end_month) + "-" + addZero(end_days);
		return seasonDate;

	}

	private String addZero(int num) {
		String strReturn = "0";
		if (num < 10) {
			strReturn += num;
		} else {
			strReturn = String.valueOf(num);
		}
		return strReturn;
	}

	/**  
	 * 获取某年某月的最后一天  
	 *   
	 * @param year  
	 *            年  
	 * @param month  
	 *            月  
	 * @return 最后一天  
	 */
	private int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**========================================== 周六日 操作  =========================================================*/
	/**  
	 * 获得本周星期日的日期  
	 *   
	 * @return  
	 */
	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**  
	 * 获得当前日期与本周日相差的天数  
	 *   
	 * @return  
	 */
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......   
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1   
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**  
	 * 获得本周一的日期  
	 *   
	 * @return  
	 */
	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**  
	 * 获得相应周的周六的日期  
	 *   
	 * @return  
	 */
	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**  
	 * 获得上周星期日的日期  
	 *   
	 * @return  
	 */
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**  
	 * 获得上周星期一的日期  
	 *   
	 * @return  
	 */
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**  
	 * 获得下周星期一的日期  
	 */
	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**  
	 * 获得下周星期日的日期  
	 */
	public String getNextSunday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	@SuppressWarnings("unused")
	private int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天   
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天   
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	/**========================================== 月份英文格式化 操作  =========================================================*/
	/**
	 * 获取日期中的英文月份缩写
	 * @return String
	 */
	public static String getMonthShortEnNameOfDate(String date) {
		Calendar cd = Calendar.getInstance();
		String result = "";
		try {
			cd.setTime(FORMAT_DEFAULT_DATE.parse(date));
			int month = cd.get(Calendar.MONTH) + 1;
			switch (month) {
			case 1:
				result = "JAN";
				break;
			case 2:
				result = "FEB";
				break;
			case 3:
				result = "MAR";
				break;
			case 4:
				result = "APR";
				break;
			case 5:
				result = "MAY";
				break;
			case 6:
				result = "JUN";
				break;
			case 7:
				result = "JUL";
				break;
			case 8:
				result = "AUG";
				break;
			case 9:
				result = "SEP";
				break;
			case 10:
				result = "OCT";
				break;
			case 11:
				result = "NOV";
				break;
			case 12:
				result = "DEC";
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 获取日期中的数字 月份缩写
	 * @Data  "04 Jan 2013"   FORMAT_YYYY_MM_DD_SS
	 * @return String
	 */
	public static String getMonthByEnNameOfDate(String EnMonthStr) {
		String UCEnMonthStr = EnMonthStr.toUpperCase();
		String[] UCEnMonthStrList = UCEnMonthStr.split("\\s+");
		String dateStr = UCEnMonthStrList[2] +"-"+ getNumMonthByEN(UCEnMonthStrList[1]) +"-"+ UCEnMonthStrList[0];
		Date dateFormat = string2Date(dateStr, FORMAT_YYYY_MM_DD);
		return FORMAT_DEFAULT_DATE_SS.format(dateFormat);
	}
	
	//根据英文月份获取数字月
	public static String getNumMonthByEN(String monthStr){
		String month = "";
		switch (monthStr) {
			case "JAN":
				month = "01";
				break;
			case "FEB":
				month = "02";
				break;
			case "MAR":
				month = "03";
				break;
			case "APR":
				month = "04";
				break;
			case "MAY":
				month = "05";
				break;
			case "JUN":
				month = "06";
				break;
			case "JUL":
				month = "07";
				break;
			case "AUG":
				month = "08";
				break;
			case "SEP":
				month = "09";
				break;
			case "OCT":
				month = "10";
				break;
			case "NOV":
				month = "11";
				break;
			case "DEC":
				month = "12";
				break;
		}
		return month;
	}

	/**==========================================时间比较操作=========================================================*/
	/** 
	 * 返回指定时间距离当前时间的中文信息
	 * @param time 
	 * @return String
	 * @描述 —— 指定时间距离当前时间的中文信息 
	 */
	public static String getLnow(long time) {
		Calendar cal = Calendar.getInstance();
		long timel = cal.getTimeInMillis() - time;
		if (timel / 1000 < 60) {
			return "1分钟以内";
		} else if (timel / 1000 / 60 < 60) {
			return timel / 1000 / 60 + "分钟前";
		} else if (timel / 1000 / 60 / 60 < 24) {
			return timel / 1000 / 60 / 60 + "小时前";
		} else {
			return timel / 1000 / 60 / 60 / 24 + "天前";
		}
	}

	/**==========================================加减操作=========================================================*/
	/**
	 * 取得日期前/后n个月所在月的第一天
	 * @param date  日期
	 * @param delta 相差的月数，正数表示往后，负数表示往前
	 * @return
	 */
	public static Date getFirstDayOfMonthDelta(Date date, Integer delta) {
		Calendar c = parse2Calendar(date);
		c.add(Calendar.MONTH, delta);
		Date consultDate = c.getTime(); //date前/后n月的日期   
		return getFirstMonthDay(consultDate);
	}

	/**
	 * 取得日期前/后n个月所在月的最后一天
	 * @param date  日期
	 * @param delta 相差的月数，正数表示往后，负数表示往前
	 * @return
	 */
	public static Date getLastDayOfMonthDelta(Date date, Integer delta) {
		Calendar c = parse2Calendar(date);
		c.add(Calendar.MONTH, delta);
		Date consultDate = c.getTime(); //date前/后n月的日期   
		return getLastMonthDay(consultDate);
	}

	/**
	 * 取得日期前/后n周的第一天
	 * @param date  日期
	 * @param delta 相差的周数，正数表示往后，负数表示往前
	 * @return
	 */
	public static Date getFirstDayOfWeekDelta(Date date, Integer delta) {
		Calendar c = parse2Calendar(date);
		c.add(Calendar.WEEK_OF_MONTH, delta);
		Date consultDate = c.getTime(); //date前/后n周的日期   
		return getFirstWeekDay(consultDate);
	}

	/**
	 * 取得日期前/后n周的最后一天
	 * @param date  日期
	 * @param delta 相差的周数，正数表示往后，负数表示往前
	 * @return
	 */
	public static Date getLastDayOfWeekDelta(Date date, Integer delta) {
		Calendar c = parse2Calendar(date);
		c.add(Calendar.WEEK_OF_MONTH, delta);
		Date consultDate = c.getTime(); //date前/后n周的日期   
		return getLastWeekDay(consultDate);
	}

	/**返回指定的日期加上n年后的日期*/
	public static Date addYear(Date date, Integer year) {
		Calendar c = parse2Calendar(date);
		c.add(Calendar.YEAR, year);
		return c.getTime();
	}

	/**返回指定的日期加上n个月后的日期*/
	public static Date addMonth(Date date, Integer months) {
		Calendar c = parse2Calendar(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}

	/**返回指定的日期加上n天后的日期*/
	public static Date addDay(Date date, Integer day) {
		Calendar c = parse2Calendar(date);
		c.set(Calendar.DATE, c.get(Calendar.DATE) + day);
		return c.getTime();
	}

	/** 
	 * 返回指定的日期加上n小时后的日期
	 * @param startDate 要处理的时间，Null则为当前时间 
	 * @param hours 加减的小时 
	 * @return Date 
	 */
	public static Date dateAddHours(Date startDate, int hours) {
		if (startDate == null) {
			startDate = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
		return c.getTime();
	}

	/**
	 * 返回指定的日期加减n分钟后的日期时间
	 * @param startDate 要处理的时间，Null则为当前时间 
	 * @param minutes 加减的分钟
	 * @return
	 */
	public static Date dateAddMinutes(Date startDate, int minutes) {
		if (startDate == null) {
			startDate = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minutes);
		return c.getTime();
	}

	/**
	 * 返回指定的日期加减n秒数后的日期时间
	 * @param startDate 要处理的时间，Null则为当前时间 
	 * @param minutes 加减的秒数
	 * @return
	 */
	public static Date dateAddSeconds(Date startDate, int seconds) {
		if (startDate == null) {
			startDate = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
		return c.getTime();
	}

	/**  
	 * 是否闰年  
	 *   
	 * @param year  
	 *            年  
	 * @return  
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**  
	 * 是否闰年  
	 *   
	 * @param year  
	 * @return  
	 */
	public static boolean isLeapYear2(int year) {
		return new GregorianCalendar().isLeapYear(year);
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天   
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天   
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。   
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	public static void main(String args[]) { 
        String date = getMonthByEnNameOfDate("04 Jan 2013");
        System.out.println(date);
    } 

}
