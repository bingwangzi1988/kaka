package com.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ���ڹ���
 *
 */
public class DateUtil {
	
	/**
	 * �����ַ�����ָ����ʽ����
	 * 
	 * @param dateStr
	 *            ����
	 * @param dateFormat
	 *            �Զ����ʽ
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseStrToDate(String dateStr, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date d = sdf.parse(dateStr);
		
		//�����������ݣ�������Ϊ��ǰ��ݻ��ߵ�ǰ��ݼ�1
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
	 * ��ʽ�����ڳ�ָ����ʽ�ַ���
	 * 
	 * @param date
	 * @param strFormat
	 * @return String
	 */
	public static String formatDateToStr(Date date, String strFormat) {
		if(date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			return sdf.format(date);
		} 
		return "";
	}
	
	/**
	 * ���ڼ���, +/-����
	 * 
	 * @param nowDate
	 *            ��ǰ����
	 * @param days
	 *            +/-����
	 * @return Date
	 */
	public static Date getAddDays(Date nowDate, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	
	/**
	 * ���ڼ��㣬 +/-����
	 * 
	 * @param nowDate
	 * 			��ǰ����
	 * @param years
	 * 			+/-����
	 * @return
	 */
	public static Date getAddYears(Date nowDate, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	/**
	 * ��ȡ���ĸ�ʽ����
	 * 
	 * ��ʽ��xxxx��xx��xx�� ����x xxʱxx��xx��
	 * 
	 */
	public static String getCHDate(Date date) {
		StringBuffer dateStr = new StringBuffer();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		dateStr.append(year).append("��");
		if(month < 10) {
			dateStr.append("0");
		}
		dateStr.append(month).append("��");
		if(day < 10) {
			dateStr.append("0");
		}
		dateStr.append(day).append("�� ");
		
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		String week = "";
		switch (weekDay) {
		case 1:
			week = "������";
			break;
		case 2:
			week = "����һ";
			break;
		case 3:
			week = "���ڶ�";
			break;
		case 4:
			week = "������";
			break;
		case 5:
			week = "������";
			break;
		case 6:
			week = "������";
			break;
		case 7:
			week = "������";
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
		dateStr.append(hour).append("ʱ");
		if(minute < 10) {
			dateStr.append("0");
		}
		dateStr.append(minute).append("��");
		if(second < 10) {
			dateStr.append("0");
		}
		dateStr.append(second).append("��");
		
		return dateStr.toString();
	}
	
	/**
	 * �õ�ĳ��ĳ�µĵ�һ��
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
	 * �õ�ĳ��ĳ�µ����һ��
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
//	 * �õ�ĳ��ĳ�µĵ�һ��
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
//	 * �õ�ĳ��ĳ�µ����һ��
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
	 * ���ڱȽϣ�yyyyMMdd��ʽ�Ƚϣ�
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
	 * �ж�ʱ���Ƿ�Ϊ������
	 *
	 * @param nowDate 
	 * 			���ڣ���Ҫ��֤�����ڣ�
	 * @return boolean  
	 * 			true ��ʾ��������  false ��ʾ����������
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
	 * �ж�ʱ���Ƿ�Ϊ��ĩ
	 * @param nowDate ���ڣ���Ҫ��֤�����ڣ�
	 * @return boolean  true ��ʾ����ĩ  false ��ʾ��Ϊ��ĩ
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
	 * �ж�ʱ���Ƿ�Ϊ��ĩ
	 * 
	 * @param nowDate 
	 * 			���ڣ���Ҫ��֤�����ڣ�
	 * @return boolean  
	 * 			true ��ʾ�Ǽ�ĩ  false ��ʾ���Ǽ�ĩ
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
	 * �ж�ʱ���Ƿ�Ϊ��ĩ
	 * 
	 * @param nowDate 
	 * 			���ڣ���Ҫ��֤�����ڣ�
	 * @return boolean  
	 * 			true ��ʾ����ĩ  false ��ʾ��Ϊ��ĩ
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
	 * ������������֮��
	 * 
	 * @param nowDate
	 *            (��)
	 * @param passDate
	 *            (С)
	 * @return �����������
	 */
	public static long getTimeMinus(Date nowDate, Date passDate) {
		long a = (nowDate.getTime() - passDate.getTime()) / (1000 * 60 * 60 * 24);
		return a;
	}

	/**
	 * ��ȡʱ���
	 * 
	 * @param startDate
	 * 			��ʼʱ��
	 * @param endDate
	 * 			����ʱ��
	 * @return
	 * 		?��?Сʱ?��?��
	 */
	public static String getTimeLag(Date startDate, Date endDate) {
		StringBuffer strBuffer = new StringBuffer();
		
		long lag = endDate.getTime() - startDate.getTime();
		long day = lag/(24*60*60*1000);
		long hour = lag/(60*60*1000) - day*24;
		long min = lag/(60*1000) - day*24*60 - hour*60;
		long second = lag/1000 - day*24*60*60 - hour*60*60 - min*60;
		if(day != 0) {
			strBuffer.append(day).append("��");
		}
		if(hour != 0) {
			strBuffer.append(hour).append("Сʱ");
		}
		if(min != 0) {
			strBuffer.append(min).append("��");
		}
		if(second != 0) {
			strBuffer.append(second).append("��");
		}
		if("".equals(strBuffer.toString())) {
			strBuffer.append("0��");
		}
		
		return strBuffer.toString();
	}
	
}
