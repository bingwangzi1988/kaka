package org.eredlab.g4.ccl.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.assistant.TypeCaseHelper;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;

/**
 * �����ĸ�����
 * 
 * @author XiongChun
 * @since 2009-07-15
 */
public class G4Utils {

	private static Log log = LogFactory.getLog(G4Utils.class);

	/**
	 * DES�㷨��Կ
	 */
	private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128, -65 };

	private static String HanDigiStr[] = new String[] { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };

	private static String HanDiviStr[] = new String[] { "", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��",
			"ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ" };

	private static PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);

	/**
	 * �ж϶����Ƿ�Empty(null��Ԫ��Ϊ0)<br>
	 * ʵ���ڶ����¶������ж�:String Collection�������� Map��������
	 * 
	 * @param pObj
	 *            ��������
	 * @return boolean ���صĲ���ֵ
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж϶����Ƿ�ΪNotEmpty(!null��Ԫ��>0)<br>
	 * ʵ���ڶ����¶������ж�:String Collection�������� Map��������
	 * 
	 * @param pObj
	 *            ��������
	 * @return boolean ���صĲ���ֵ
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
			if(((String) pObj).trim().length() == 0){
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �ж�һ���ַ����Ƿ������֡���ĸ��������ĸ���
	 * 
	 * @param pStr
	 *            ��Ҫ�жϵ��ַ���
	 * @param pStyle
	 *            �жϹ���
	 * @return boolean ���صĲ���ֵ
	 */
	public static boolean isTheStyle(String pStr, String pStyle) {
		for (int i = 0; i < pStr.length(); i++) {
			char c = pStr.charAt(i);
			if (pStyle.equals(GlobalConstants.S_STYLE_N)) {
				if (!Character.isDigit(c))
					return false;
			} else if (pStyle.equals(GlobalConstants.S_STYLE_L)) {
				if (!Character.isLetter(c))
					return false;
			} else if (pStyle.equals(GlobalConstants.S_STYLE_NL)) {
				if (Character.isLetterOrDigit(c))
					return false;
			}
		}
		return true;
	}

	/**
	 * JavaBean֮���������ֵ����
	 * 
	 * @param pFromObj
	 *            BeanԴ����
	 * @param pToObj
	 *            BeanĿ�����
	 */
	public static void copyPropBetweenBeans(Object pFromObj, Object pToObj) {
		if (pToObj != null) {
			try {
				BeanUtils.copyProperties(pToObj, pFromObj);
			} catch (Exception e) {
				log.error("==������Ա��ע��:==\n JavaBean֮�������ֵ��������������!" + "\n��ϸ������Ϣ����:");
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��JavaBean�����е�����ֵ������Dto����
	 * 
	 * @param pFromObj
	 *            JavaBean����Դ
	 * @param pToDto
	 *            DtoĿ�����
	 */
	public static void copyPropFromBean2Dto(Object pFromObj, Dto pToDto) {
		if (pToDto != null) {
			try {
				pToDto.putAll(BeanUtils.describe(pFromObj));
				// BeanUtils�Զ�������һ��KeyΪclass�ļ�ֵ,�ʽ����Ƴ�
				pToDto.remove("class");
			} catch (Exception e) {
				log.error("==������Ա��ע��:==\n ��JavaBean����ֵ������Dto������������!" + "\n��ϸ������Ϣ����:");
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����������֤�������У�飬������һ����Ӧ��18λ���֤
	 * 
	 * @param personIDCode
	 *            ���֤����
	 * @return String ʮ��λ���֤����
	 * @throws ��Ч�����֤��
	 */
	public static String getFixedPersonIDCode(String personIDCode) throws Exception {
		if (personIDCode == null)
			throw new Exception("��������֤����Ч������");

		if (personIDCode.length() == 18) {
			if (isIdentity(personIDCode))
				return personIDCode;
			else
				throw new Exception("��������֤����Ч������");
		} else if (personIDCode.length() == 15)
			return fixPersonIDCodeWithCheck(personIDCode);
		else
			throw new Exception("��������֤����Ч������");
	}

	/**
	 * �޲�15λ�������֤����Ϊ18λ����У��15λ���֤��Ч��
	 * 
	 * @param personIDCode
	 *            ʮ��λ���֤����
	 * @return String ʮ��λ���֤����
	 * @throws ��Ч�����֤��
	 */
	public static String fixPersonIDCodeWithCheck(String personIDCode) throws Exception {
		if (personIDCode == null || personIDCode.trim().length() != 15)
			throw new Exception("��������֤�Ų���15λ������");

		if (!isIdentity(personIDCode))
			throw new Exception("��������֤����Ч������");

		return fixPersonIDCodeWithoutCheck(personIDCode);
	}

	/**
	 * �޲�15λ�������֤����Ϊ18λ����У�����֤��Ч��
	 * 
	 * @param personIDCode
	 *            ʮ��λ���֤����
	 * @return ʮ��λ���֤����
	 * @throws ���֤�Ų�������15λ
	 */
	public static String fixPersonIDCodeWithoutCheck(String personIDCode) throws Exception {
		if (personIDCode == null || personIDCode.trim().length() != 15)
			throw new Exception("��������֤�Ų���15λ������");

		String id17 = personIDCode.substring(0, 6) + "19" + personIDCode.substring(6, 15); // 15λ���֤��'19'

		char[] code = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // 11��У�����ַ�
		int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 }; // 18����Ȩ����
		int[] idcd = new int[18];
		int sum; // ���ݹ�ʽ ��(ai��Wi) ����
		int remainder; // ��18λУ����
		for (int i = 0; i < 17; i++) {
			idcd[i] = Integer.parseInt(id17.substring(i, i + 1));
		}
		sum = 0;
		for (int i = 0; i < 17; i++) {
			sum = sum + idcd[i] * factor[i];
		}
		remainder = sum % 11;
		String lastCheckBit = String.valueOf(code[remainder]);
		return id17 + lastCheckBit;
	}

	/**
	 * �ж��Ƿ�����Ч��18λ��15λ�������֤����
	 * 
	 * @param identity
	 *            18λ��15λ�������֤����
	 * @return �Ƿ�Ϊ��Ч�����֤����
	 */
	public static boolean isIdentity(String identity) {
		if (identity == null)
			return false;
		if (identity.length() == 18 || identity.length() == 15) {
			String id15 = null;
			if (identity.length() == 18)
				id15 = identity.substring(0, 6) + identity.substring(8, 17);
			else
				id15 = identity;
			try {
				Long.parseLong(id15); // У���Ƿ�Ϊ�����ַ���

				String birthday = "19" + id15.substring(6, 12);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				sdf.parse(birthday); // У���������
				if (identity.length() == 18 && !fixPersonIDCodeWithoutCheck(id15).equals(identity))
					return false; // У��18λ���֤
			} catch (Exception e) {
				return false;
			}
			return true;
		} else
			return false;
	}

	/**
	 * �����֤���л�ȡ�������ڣ����֤�ſ���Ϊ15λ��18λ
	 * 
	 * @param identity
	 *            ���֤��
	 * @return ��������
	 * @throws ���֤�ų������ڶ�����
	 */
	public static Timestamp getBirthdayFromPersonIDCode(String identity) throws Exception {
		String id = getFixedPersonIDCode(identity);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Timestamp birthday = new Timestamp(sdf.parse(id.substring(6, 14)).getTime());
			return birthday;
		} catch (ParseException e) {
			throw new Exception("������Ч�����֤�ţ�����");
		}
	}

	/**
	 * �����֤�Ż�ȡ�Ա�
	 * 
	 * @param identity
	 *            ���֤��
	 * @return �Ա����
	 * @throws Exception
	 *             ��Ч�����֤����
	 */
	public static String getGenderFromPersonIDCode(String identity) throws Exception {
		String id = getFixedPersonIDCode(identity);
		char sex = id.charAt(16);
		return sex % 2 == 0 ? "2" : "1";
	}

	/**
	 * ������ת��Ϊ��д��ʽ(���ڲ�����)
	 * 
	 * @param val
	 * @return String
	 */
	private static String PositiveIntegerToHanStr(String NumStr) {
		// �����ַ���������������ֻ����ǰ���ո�(�����Ҷ���)��������ǰ����
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // �ڡ����λǰ����ֵ���
		int len, n;
		len = NumStr.length();
		if (len > 15)
			return "��ֵ����!";
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ')
				continue;
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9)
				return "���뺬�������ַ�!";

			if (n != 0) {
				if (lastzero)
					RMBStr += HanDigiStr[0]; // ���������������ֵ��ֻ��ʾһ����
				// ��������ǰ���㲻��������
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) )
				// ��ʮ��λǰ����Ҳ����Ҽ���ô���
				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) // ʮ��λ���ڵ�һλ����Ҽ��
					RMBStr += HanDigiStr[n];
				RMBStr += HanDiviStr[i]; // ����ֵ��ӽ�λ����λΪ��
				hasvalue = true; // �����λǰ��ֵ���

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // ����֮������з���ֵ����ʾ��
					RMBStr += HanDiviStr[i]; // ���ڡ�����
			}
			if (i % 8 == 0)
				hasvalue = false; // ���λǰ��ֵ��Ƿ��ڸ�λ
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0)
			return HanDigiStr[0]; // ������ַ���"0"������"��"
		return RMBStr;
	}

	/**
	 * ������ת��Ϊ��д��ʽ
	 * 
	 * @param val
	 *            ���������
	 * @return String ���ص�����Ҵ�д��ʽ�ַ���
	 */
	public static String numToRMBStr(double val) {
		String SignStr = "";
		String TailStr = "";
		long fraction, integer;
		int jiao, fen;

		if (val < 0) {
			val = -val;
			SignStr = "��";
		}
		if (val > 99999999999999.999 || val < -99999999999999.999)
			return "��ֵλ������!";
		// �������뵽��
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0) {
			TailStr = "��";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0)
				TailStr += "��";
			// ��Ԫ��д�㼸��
			if (integer == 0 && jiao == 0)
				TailStr = "";
			if (fen != 0)
				TailStr += HanDigiStr[fen] + "��";
		}
		// ��һ�п����ڷ�������ڳ��ϣ�0.03ֻ��ʾ�����֡������ǡ���Ԫ���֡�
		// if( !integer ) return SignStr+TailStr;
		return SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "Ԫ" + TailStr;
	}

	/**
	 * ��ȡָ����ݺ��·ݶ�Ӧ������
	 * 
	 * @param year
	 *            ָ�������
	 * @param month
	 *            ָ�����·�
	 * @return int ��������
	 */
	public static int getDaysInMonth(int year, int month) {
		if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10)
				|| (month == 12)) {
			return 31;
		} else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
			return 30;
		} else {
			if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {
				return 29;
			} else {
				return 28;
			}
		}
	}

	/**
	 * ������������ֹʱ����������������
	 * 
	 * @param startDate
	 *            ��ʼʱ��
	 * @param endDate
	 *            ����ʱ��
	 * @return int ���ؼ������
	 */
	public static int getIntervalDays(java.sql.Date startDate, java.sql.Date endDate) {
		long startdate = startDate.getTime();
		long enddate = endDate.getTime();
		long interval = enddate - startdate;
		int intervalday = (int) (interval / (1000 * 60 * 60 * 24));
		return intervalday;
	}

	/**
	 * ������������ֹʱ����������������
	 * 
	 * @param startDate
	 *            ��ʼʱ��
	 * @param endDate
	 *            ����ʱ��
	 * @return int ���ؼ������
	 */
	public static int getIntervalMonths(java.sql.Date startDate, java.sql.Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		int startDateM = startCal.MONTH;
		int startDateY = startCal.YEAR;
		int enddatem = endCal.MONTH;
		int enddatey = endCal.YEAR;
		int interval = (enddatey * 12 + enddatem) - (startDateY * 12 + startDateM);
		return interval;
	}

	/**
	 * ���ص�ǰ����ʱ���ַ���<br>
	 * Ĭ�ϸ�ʽ:yyyy-mm-dd hh:mm:ss
	 * 
	 * @return String ���ص�ǰ�ַ���������ʱ��
	 */
	public static String getCurrentTime() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	/**
	 * �����Զ����ʽ�ĵ�ǰ����ʱ���ַ���
	 * 
	 * @param format
	 *            ��ʽ����
	 * @return String ���ص�ǰ�ַ���������ʱ��
	 */
	public static String getCurrentTime(String format) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	/**
	 * ���ص�ǰ�ַ���������
	 * 
	 * @return String ���ص��ַ���������
	 */
	public static String getCurDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpledateformat.format(calendar.getTime());
		return strDate;
	}

	/**
	 * ����TimeStamp����
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		Object obj = TypeCaseHelper.convert(getCurrentTime(), "Timestamp", "yyyy-MM-dd HH:mm:ss");
		if (obj != null)
			return (Timestamp) obj;
		else
			return null;
	}

	/**
	 * ���ַ���������ת��Ϊ������
	 * 
	 * @param strDate
	 *            �ַ���������
	 * @param srcDateFormat
	 *            Դ���ڸ�ʽ
	 * @param dstDateFormat
	 *            Ŀ�����ڸ�ʽ
	 * @return Date ���ص�util.Date������
	 */
	public static Date stringToDate(String strDate, String srcDateFormat, String dstDateFormat) {
		Date rtDate = null;
		Date tmpDate = (new SimpleDateFormat(srcDateFormat)).parse(strDate, new ParsePosition(0));
		String tmpString = null;
		if (tmpDate != null)
			tmpString = (new SimpleDateFormat(dstDateFormat)).format(tmpDate);
		if (tmpString != null)
			rtDate = (new SimpleDateFormat(dstDateFormat)).parse(tmpString, new ParsePosition(0));
		return rtDate;
	}

	/**
	 * �ϲ��ַ�������
	 * 
	 * @param a
	 *            �ַ�������0
	 * @param b
	 *            �ַ�������1
	 * @return ���غϲ�����ַ�������
	 */
	public static String[] mergeStringArray(String[] a, String[] b) {
		if (a.length == 0 || isEmpty(a))
			return b;
		if (b.length == 0 || isEmpty(b))
			return a;
		String[] c = new String[a.length + b.length];
		for (int m = 0; m < a.length; m++) {
			c[m] = a[m];
		}
		for (int i = 0; i < b.length; i++) {
			c[a.length + i] = b[i];
		}
		return c;
	}

	/**
	 * ���ļ���������ص������ļ������б��� ���θ���������汾�Ĳ�����
	 */
	public static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) {
		String agent = request.getHeader("USER-AGENT");
		try {
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				pFileName = URLEncoder.encode(pFileName, "utf-8");
			} else {
				pFileName = new String(pFileName.getBytes("utf-8"), "iso8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pFileName;
	}

	/**
	 * �������ڻ�ȡ����
	 * 
	 * @param strdate
	 * @return
	 */
	public static String getWeekDayByDate(String strdate) {
		final String dayNames[] = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		try {
			date = sdfInput.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return dayNames[dayOfWeek];
	}

	/**
	 * �ж��Ƿ���IE�����
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean isIE(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isIe = true;
		int index = userAgent.indexOf("msie");
		if (index == -1) {
			isIe = false;
		}
		return isIe;
	}

	/**
	 * �ж��Ƿ���Chrome�����
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean isChrome(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isChrome = true;
		int index = userAgent.indexOf("chrome");
		if (index == -1) {
			isChrome = false;
		}
		return isChrome;
	}

	/**
	 * �ж��Ƿ���Firefox�����
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean isFirefox(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isFirefox = true;
		int index = userAgent.indexOf("firefox");
		if (index == -1) {
			isFirefox = false;
		}
		return isFirefox;
	}

	/**
	 * ��ȡ�ͻ�������
	 * 
	 * @param userAgent
	 * @return
	 */
	public static String getClientExplorerType(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		String explorer = "�����������";
		if (isIE(request)) {
			int index = userAgent.indexOf("msie");
			explorer = userAgent.substring(index, index + 8);
		} else if (isChrome(request)) {
			int index = userAgent.indexOf("chrome");
			explorer = userAgent.substring(index, index + 12);
		} else if (isFirefox(request)) {
			int index = userAgent.indexOf("firefox");
			explorer = userAgent.substring(index, index + 11);
		}
		return explorer.toUpperCase();
	}

	/**
	 * ����MD5�㷨�ĵ������
	 * 
	 * @param strSrc
	 *            ����
	 * @return ��������
	 */
	public static String encryptBasedMd5(String strSrc) {
		String outString = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] outByte = md5.digest(strSrc.getBytes("UTF-8"));
			outString = outByte.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outString;
	}

	/**
	 * ���ݼ��ܣ��㷨��DES��
	 * 
	 * @param data
	 *            Ҫ���м��ܵ�����
	 * @return ���ܺ������
	 */
	public static String encryptBasedDes(String data) {
		String encryptedData = null;
		try {
			// DES�㷨Ҫ����һ�������ε������Դ
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����һ��SecretKey����
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// ���ܶ���
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// ���ܣ������ֽ����������ַ���
			encryptedData = Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			log.error("���ܴ��󣬴�����Ϣ��", e);
			throw new RuntimeException("���ܴ��󣬴�����Ϣ��", e);
		}
		return encryptedData;
	}

	/**
	 * ���ݽ��ܣ��㷨��DES��
	 * 
	 * @param cryptData
	 *            ��������
	 * @return ���ܺ������
	 */
	public static String decryptBasedDes(String cryptData) {
		String decryptedData = null;
		try {
			// DES�㷨Ҫ����һ�������ε������Դ
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����һ��SecretKey����
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// ���ܶ���
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// ���ַ�������Ϊ�ֽ����飬������
			decryptedData = new String(cipher.doFinal(Base64.decodeBase64(cryptData)));
		} catch (Exception e) {
			log.error("���ܴ��󣬴�����Ϣ��", e);
			throw new RuntimeException("���ܴ��󣬴�����Ϣ��", e);
		}
		return decryptedData;
	}

	/**
	 * �ж�JDBC���ͣ�Oracle
	 * 
	 * @return
	 */
	public static boolean isOracle() {
		boolean out = false;
		String jdbcType = System.getProperty("eRedg4.JdbcType");
		if (jdbcType.equalsIgnoreCase("oracle")) {
			out = true;
		}
		return out;
	}

	/**
	 * �ж�JDBC���ͣ�Mysql
	 * 
	 * @return
	 */
	public static boolean isMysql() {
		boolean out = false;
		String jdbcType = System.getProperty("eRedg4.JdbcType");
		if (jdbcType.equalsIgnoreCase("mysql")) {
			out = true;
		}
		return out;
	}
	
	/**
	 * JS�������\n�����⴦��
	 * 
	 * @param pStr
	 * @return
	 */
	public static String replace4JsOutput(String pStr) {
		pStr = pStr.replace("\r\n", "<br/>&nbsp;&nbsp;");
		pStr = pStr.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		pStr = pStr.replace(" ", "&nbsp;");
		return pStr;
	}

}