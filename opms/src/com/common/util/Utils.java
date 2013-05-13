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
	 * ���ַ���ת����Long������
	 * */
	public static Long getLong(String val) {
		if (val != null && val.trim().length() > 0)
			return Long.parseLong(val);
		return null;
	}

	/**
	 * ���ַ���ת����BigDecimal������
	 * */
	public static BigDecimal getBigDecimal(String val) {
		if (val != null && val.trim().length() > 0)
			return new BigDecimal(val);
		return null;
	}
	
	/**
	 * ���ַ���ת����Integer������
	 * */
	public static Integer getInteger(String val) {
		if (val != null && val.trim().length() > 0)
			return Integer.parseInt(val);
		return null;
	}

	/**
	 * ���ַ����͵�����ת���� yyyy-MM-dd �����ڸ�ʽ
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
	 * ���ַ����͵�����ת���� �Զ��� �����ڸ�ʽ
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
	 * ������ת���� �Զ��� ���ַ���ʽ
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
	 * ���ַ�д��ȥ
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
	 * ��ȡ��Դ�ļ�
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
	 * ����λ��0
	 * 
	 * @param no
	 *            ���к�
	 * @param len
	 *            �ܹ�λ��
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
	
	
	//��ȡIP��ַ
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
	 * ���ڼ���,��������
	 * 
	 * @param nowDate
	 *            ��ǰ����
	 * @param days
	 *            ��������
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
