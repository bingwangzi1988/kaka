package com.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public class Utils {
	/**
	 * 将字符串转换成Long型数据
	 * */
	public static Long getLong(String val) {
		if (val != null && val.trim().length() > 0)
			return Long.parseLong(val);
		return null;
	}

	/**
	 * 将字符串转换成BigDecimal型数据
	 * */
	public static BigDecimal getBigDecimal(String val) {
		if (val != null && val.trim().length() > 0)
			return new BigDecimal(val);
		return null;
	}
	
	/**
	 * 将字符串转换成Integer型数据
	 * */
	public static Integer getInteger(String val) {
		if (val != null && val.trim().length() > 0)
			return Integer.parseInt(val);
		return null;
	}

	/**
	 * 将字符类型的日期转换成 yyyy-MM-dd 的日期格式
	 * */
	public static Date getDateYYYYMMDD(String time) {
		if (time != null && time.trim().length() > 0) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 将字符类型的日期转换成 自定义 的日期格式
	 * */
	public static Date stringToDate(String time, String format) {
		if (time != null && time.trim().length() > 0) {
			try {
				if (format != null && format.trim().length() > 0)
					return new SimpleDateFormat(format).parse(time);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 将日期转换成 自定义 的字符格式
	 * */
	public static String dateToString(Date date, String format) {
		if (date != null) {
			try {
				if (format != null && format.trim().length() > 0)
					return new SimpleDateFormat(format).format(date);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 将字符写出去
	 * */
	public static void PrintWrite(HttpServletResponse response, String str) {
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(str);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取资源文件
	 * */

	@SuppressWarnings("unchecked")
	public static Map<String, String> readProperties(String fileName) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// Properties props = new Properties();
			// InputStream in = new BufferedInputStream(new
			// FileInputStream(filePath));
			// props.load(in);

			Properties props = PorpertiesUtil.getProperties(fileName);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String property = props.getProperty(key);
				// System.out.println("key==" + key + "   property=" +
				// property);
				map.put(key, property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 不足位补0
	 * 
	 * @param no
	 *            序列号
	 * @param len
	 *            总共位数
	 * */
	public static String getNo(String no, int len) {
		if (no != null && no.trim().length() > 0) {
			int n = len - no.length();
			StringBuffer sb = new StringBuffer();
			if (n > 0) {
				for (int i = 0; i < n; i++) {
					sb.append("0");
				}
			}
			sb.append(no);
			return sb.toString();
		}
		return null;
	}
	
	
	//获取IP地址
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 日期计算,增加天数
	 * 
	 * @param nowDate
	 *            当前日期
	 * @param days
	 *            增加天数
	 * @return Date
	 */
	public static Date getAddDate(Date nowDate, Integer days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	public static void main(String[] args) {

		// Date d = stringToDate("2012-03-07 15:32:26", "yyyy-MM-dd HH:mm:ss");
		// System.out.println("d===" + d);
		// String str = dateToString(d, "yyyy-MM-dd HH:mm:ss");
		// System.out.println("str===" + str);

		// Map<String, String> map = readProperties("jdbc.properties");
		// System.out.println("key==g4.jdbc.driverClassName   property=" +
		// map.get("g4.jdbc.driverClassName"));
		// System.out.println("key==g4.jdbc.url   property=" +
		// map.get("g4.jdbc.url"));
		// System.out.println("key==g4.jdbc.username   property=" +
		// map.get("g4.jdbc.username"));
		// System.out.println("key==g4.jdbc.password   property=" +
		// map.get("g4.jdbc.password"));
	}
}
