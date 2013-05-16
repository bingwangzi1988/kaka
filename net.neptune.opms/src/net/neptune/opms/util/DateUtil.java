package net.neptune.opms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 *
 */
public class DateUtil {
	
	/**
	 * 解析字符串成指定格式日期
	 * 
	 * @param dateStr
	 *            日期
	 * @param dateFormat
	 *            自定义格式
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseStrToDate(String dateStr, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date d = sdf.parse(dateStr);
		
		//如果不包括年份，将其设为当前年份或者当前年份减1
		if(dateFormat.indexOf("yy") == -1){
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			int year = cal.get(Calendar.YEAR);
			cal.setTime(d);
			cal.set(Calendar.YEAR, year);
			d = cal.getTime();
			if(now.compareTo(d)<0){
				cal.set(Calendar.YEAR, year-1);
			}
			d = cal.getTime();
		}
		
		return d;
	}

	/**
	 * 格式化日期成指定格式字符串
	 * 
	 * @param date
	 * @param strFormat
	 * @return String
	 */
	public static String formatDateToStr(Date date, String strFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		return sdf.format(date);
	}
	
	/**
	 * 日期计算, +/-天数
	 * 
	 * @param nowDate
	 *            当前日期
	 * @param days
	 *            +/-天数
	 * @return Date
	 */
	public static Date getAddDays(Date nowDate, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	
	/**
	 * 日期计算， +/-年数
	 * 
	 * @param nowDate
	 * 			当前日期
	 * @param years
	 * 			+/-年数
	 * @return
	 */
	public static Date getAddYears(Date nowDate, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	/**
	 * 获取中文格式日期
	 * 
	 * 格式：xxxx年xx月xx日 星期x xx时xx分xx秒
	 * 
	 */
	public static String getCHDate(Date date) {
		StringBuffer dateStr = new StringBuffer();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		dateStr.append(year).append("年");
		if(month < 10) {
			dateStr.append("0");
		}
		dateStr.append(month).append("月");
		if(day < 10) {
			dateStr.append("0");
		}
		dateStr.append(day).append("日 ");
		
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		String week = "";
		switch (weekDay) {
		case 1:
			week = "星期日";
			break;
		case 2:
			week = "星期一";
			break;
		case 3:
			week = "星期二";
			break;
		case 4:
			week = "星期三";
			break;
		case 5:
			week = "星期四";
			break;
		case 6:
			week = "星期五";
			break;
		case 7:
			week = "星期六";
			break;
		default:
			break;
		}
		dateStr.append(week).append(" ");
		
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		if(hour < 10) {
			dateStr.append("0");
		}
		dateStr.append(hour).append("时");
		if(minute < 10) {
			dateStr.append("0");
		}
		dateStr.append(minute).append("分");
		if(second < 10) {
			dateStr.append("0");
		}
		dateStr.append(second).append("秒");
		
		return dateStr.toString();
	}
	
	/**
	 * 得到某年某月的第一天
	 * @param dateStr
	 * @return
	 */
//	public static String getFirstDayOfMonth(String dateStr){
//		String s1 = dateStr.substring(0, 4);
//		int year = Integer.parseInt(s1);
//		String s2 = dateStr.substring(5, 7);
//		int month = Integer.parseInt(s2);
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, year);
//		cal.set(Calendar.MONTH, month-1);
//		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
//		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//	}
	
	/**
	 * 得到某年某月的最后一天
	 * @param dateStr
	 * @return
	 */
//	public static String getLastDayOfMonth(String dateStr){
//		String s1 = dateStr.substring(0, 4);
//		int year = Integer.parseInt(s1);
//		String s2 = dateStr.substring(5, 7);
//		int month = Integer.parseInt(s2);
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, year);
//		cal.set(Calendar.MONTH, month-1);
//		cal.set(Calendar.DAY_OF_MONTH, 1);
//		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//		cal.set(Calendar.DAY_OF_MONTH, value);
//		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
//	}

//	/**
//	 * 得到某年某月的第一天
//	 * @param dateStr (yyyyMMdd)
//	 * @return Date
//	 */
//	public static Date getFirstDayOfMonth(String dateStr){
//		String s1 = dateStr.substring(0, 4);
//		int year = Integer.parseInt(s1);
//		String s2 = dateStr.substring(4, 6);
//		int month = Integer.parseInt(s2);
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, year);
//		cal.set(Calendar.MONTH, month-1);
//		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
//		return cal.getTime();
//	}
//	
//	/**
//	 * 得到某年某月的最后一天
//	 * @param dateStr (yyyyMMdd)
//	 * @return Date
//	 */
//	public static Date getLastDayOfMonth(String dateStr){
//		String s1 = dateStr.substring(0, 4);
//		int year = Integer.parseInt(s1);
//		String s2 = dateStr.substring(4, 6);
//		int month = Integer.parseInt(s2);
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, year);
//		cal.set(Calendar.MONTH, month-1);
//		cal.set(Calendar.DAY_OF_MONTH, 1);
//		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//		cal.set(Calendar.DAY_OF_MONTH, value);
//		return cal.getTime();
//	}
//	
	
	/**
	 * 日期比较（yyyyMMdd格式比较）
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		boolean flag = false;
		String date1Str = formatDateToStr(date1, "yyyyMMdd");
		String date2Str = formatDateToStr(date2, "yyyyMMdd");
		if (date1Str.equals(date2Str)) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断时间是否为星期天
	 *
	 * @param nowDate 
	 * 			日期（需要验证的日期）
	 * @return boolean  
	 * 			true 表示是星期天  false 表示不是星期天
	 */
	public static boolean isSunday(Date nowDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		if (weekDay == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断时间是否为月末
	 * @param nowDate 日期（需要验证的日期）
	 * @return boolean  true 表示是月末  false 表示不为月末
	 * */
	public static boolean isMonthEnd(Date nowDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断时间是否为季末
	 * 
	 * @param nowDate 
	 * 			日期（需要验证的日期）
	 * @return boolean  
	 * 			true 表示是季末  false 表示不是季末
	 */
	public static boolean isQuarterEnd(Date nowDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		if (day == 1 && (month==3 || month==6 || month==9 || month==12)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断时间是否为年末
	 * 
	 * @param nowDate 
	 * 			日期（需要验证的日期）
	 * @return boolean  
	 * 			true 表示是年末  false 表示不为年末
	 */
	public static boolean isYearEnd(Date nowDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		if (day == 1 && month == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 计算两个日期之差
	 * 
	 * @param nowDate
	 *            (大)
	 * @param passDate
	 *            (小)
	 * @return 日期相差天数
	 */
	public static long getTimeMinus(Date nowDate, Date passDate) {
		long a = (nowDate.getTime() - passDate.getTime()) / (1000 * 60 * 60 * 24);
		return a;
	}

	/**
	 * 获取时间差
	 * 
	 * @param startDate
	 * 			开始时间
	 * @param endDate
	 * 			结束时间
	 * @return
	 * 		?天?小时?分?秒
	 */
	public static String getTimeLag(Date startDate, Date endDate) {
		StringBuffer strBuffer = new StringBuffer();
		
		long lag = endDate.getTime() - startDate.getTime();
		long day = lag/(24*60*60*1000);
		long hour = lag/(60*60*1000) - day*24;
		long min = lag/(60*1000) - day*24*60 - hour*60;
		long second = lag/1000 - day*24*60*60 - hour*60*60 - min*60;
		if(day != 0) {
			strBuffer.append(day).append("天");
		}
		if(hour != 0) {
			strBuffer.append(hour).append("小时");
		}
		if(min != 0) {
			strBuffer.append(min).append("分");
		}
		if(second != 0) {
			strBuffer.append(second).append("秒");
		}
		if("".equals(strBuffer.toString())) {
			strBuffer.append("0秒");
		}
		
		return strBuffer.toString();
	}
	
}
