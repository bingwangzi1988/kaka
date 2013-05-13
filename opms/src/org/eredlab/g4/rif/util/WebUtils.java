package org.eredlab.g4.rif.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * ��Web����ص�ʵ�ù�����
 * 
 * @author �ܴ�
 * @since 2008-09-22
 */
public class WebUtils {
	/**
	 * ��ȡһ��SessionContainer����,���Ϊnull�򴴽�֮
	 * 
	 * @param form
	 * @param obj
	 */
	public static SessionContainer getSessionContainer(HttpServletRequest request) {
		SessionContainer sessionContainer = (SessionContainer) request.getSession().getAttribute("SessionContainer");
		if (sessionContainer == null) {
			sessionContainer = new SessionContainer();
			HttpSession session = request.getSession(true);
			session.setAttribute("SessionContainer", sessionContainer);
		}
		return sessionContainer;
	}

	/**
	 * ��ȡһ��SessionContainer����,���Ϊnull�򴴽�֮
	 * 
	 * @param form
	 * @param obj
	 */
	public static SessionContainer getSessionContainer(HttpSession session) {
		SessionContainer sessionContainer = (SessionContainer) session.getAttribute("SessionContainer");
		if (sessionContainer == null) {
			sessionContainer = new SessionContainer();
			session.setAttribute("SessionContainer", sessionContainer);
		}
		return sessionContainer;
	}

	/**
	 * ��ȡһ��Session���Զ���
	 * 
	 * @param request
	 * @param sessionName
	 * @return
	 */
	public static Object getSessionAttribute(HttpServletRequest request, String sessionKey) {
		Object objSessionAttribute = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			objSessionAttribute = session.getAttribute(sessionKey);
		}
		return objSessionAttribute;
	}

	/**
	 * ����һ��Session���Զ���
	 * 
	 * @param request
	 * @param sessionName
	 * @return
	 */
	public static void setSessionAttribute(HttpServletRequest request, String sessionKey, Object objSessionAttribute) {
		HttpSession session = request.getSession();
		if (session != null)
			session.setAttribute(sessionKey, objSessionAttribute);
	}

	/**
	 * �Ƴ�Session��������ֵ
	 * 
	 * @param request
	 * @param sessionName
	 * @return
	 */
	public static void removeSessionAttribute(HttpServletRequest request, String sessionKey) {
		HttpSession session = request.getSession();
		if (session != null)
			session.removeAttribute(sessionKey);
	}

	/**
	 * �����������װΪDto
	 * 
	 * @param request
	 * @return
	 */
	public static Dto getPraramsAsDto(HttpServletRequest request) {
		Dto dto = new BaseDto();
		Map map = request.getParameterMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			dto.put(key, value);
		}
		// ��ҳ����
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int startInt = 0;
		if (G4Utils.isNotEmpty(start)) {
			startInt = Integer.parseInt(start);
			if (G4Utils.isOracle()) {
				dto.put("start", startInt + 1);
			} else if (G4Utils.isMysql()) {
				dto.put("start", startInt);
			} else {
				dto.put("start", startInt);
			}
		}
		if (G4Utils.isNotEmpty(limit)) {
			int limitInt = Integer.parseInt(limit);
			if (G4Utils.isOracle()) {
				dto.put("end", limitInt + startInt);
			} else if (G4Utils.isMysql()) {
				dto.put("end", limitInt);
			} else {
				dto.put("end", limitInt);
			}
		}
		return dto;
	}

	/**
	 * �����������װΪDto
	 *
	 * @param request
	 * @return
	 */
	public static Dto getPagePraramsAsDto(HttpServletRequest request) {
		Dto dto = new BaseDto();
		// ��ҳ����
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int startInt = 0;
		if (G4Utils.isNotEmpty(start)) {
			startInt = Integer.parseInt(start);
			if (G4Utils.isOracle()) {
				dto.put("start", startInt + 1);
			} else if (G4Utils.isMysql()) {
				dto.put("start", startInt);
			} else {
				dto.put("start", startInt);
			}
		}
		if (G4Utils.isNotEmpty(limit)) {
			int limitInt = Integer.parseInt(limit);
			if (G4Utils.isOracle()) {
				dto.put("end", limitInt + startInt);
			} else if (G4Utils.isMysql()) {
				dto.put("end", limitInt);
			} else {
				dto.put("end", limitInt);
			}
		}
		return dto;
	}

	/**
	 * ��ȡ�������ֵ
	 * 
	 * @param field
	 *            �������
	 * @param code
	 *            ����ֵ
	 * @param request
	 * @return
	 */
	public static String getCodeDesc(String pField, String pCode, HttpServletRequest request) {
		List codeList = (List) request.getSession().getServletContext().getAttribute("EACODELIST");
		String codedesc = null;
		for (int i = 0; i < codeList.size(); i++) {
			Dto codeDto = (BaseDto) codeList.get(i);
			if (pField.equalsIgnoreCase(codeDto.getAsString("field"))
					&& pCode.equalsIgnoreCase(codeDto.getAsString("code")))
				codedesc = codeDto.getAsString("codedesc");
		}
		return codedesc;
	}

	/**
	 * ���ݴ�������ȡ������б�
	 * 
	 * @param codeType
	 * @param request
	 * @return
	 */
	public static List getCodeListByField(String pField, HttpServletRequest request) {
		List codeList = (List) request.getSession().getServletContext().getAttribute("EACODELIST");
		List lst = new ArrayList();
		for (int i = 0; i < codeList.size(); i++) {
			Dto codeDto = (BaseDto) codeList.get(i);
			if (codeDto.getAsString("field").equalsIgnoreCase(pField)) {
				lst.add(codeDto);
			}
		}
		return lst;
	}

	/**
	 * ��ȡȫ�ֲ���ֵ
	 * 
	 * @param pParamKey
	 *            ��������
	 * @return
	 */
	public static String getParamValue(String pParamKey, HttpServletRequest request) {
		String paramValue = "";
		ServletContext context = request.getSession().getServletContext();
		if (G4Utils.isEmpty(context)) {
			return "";
		}
		List paramList = (List) context.getAttribute("EAPARAMLIST");
		for (int i = 0; i < paramList.size(); i++) {
			Dto paramDto = (BaseDto) paramList.get(i);
			if (pParamKey.equals(paramDto.getAsString("paramkey"))) {
				paramValue = paramDto.getAsString("paramvalue");
			}
		}
		return paramValue;
	}

	/**
	 * ��ȡȫ�ֲ���
	 * 
	 * @return
	 */
	public static List getParamList(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		if (G4Utils.isEmpty(context)) {
			return new ArrayList();
		}
		return (List) context.getAttribute("EAPARAMLIST");
	}

}
