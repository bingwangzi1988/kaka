package org.eredlab.g4.rif.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.actions.DispatchAction;
import org.eredlab.g4.bmf.base.IReader;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;
import org.eredlab.g4.rif.util.SessionContainer;
import org.eredlab.g4.rif.util.WebUtils;

/**
 * DispatchAction����
 * 
 * @author XiongChun
 * @since 2009-09-03
 * @see DispatchAction
 */
public class BaseAction extends DispatchAction {

	private static Log log = LogFactory.getLog(BaseAction.class);

	/**
	 * Action�����и����౩¶��һ��DAO�ӿ�<br>
	 * <b>ֻ����Action������ʹ�ô�Dao�ӿڽ��з�������صĲ���</b>:���ܽ��в�ѯ����
	 */
	protected IReader g4Reader = (IReader) getService("g4Reader");

	protected static PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);

	/**
	 * �ӷ��������л�ȡ�������
	 * 
	 * @param pBeanName
	 *            :BeanID
	 * @return Object
	 */
	protected Object getService(String pBeanId) {
		Object springBean = SpringBeanLoader.getSpringBean(pBeanId);
		return springBean;
	}

	/**
	 * ��ȡһ��Session���Զ���
	 * 
	 * @param request
	 * @param sessionName
	 * @return
	 */
	protected Object getSessionAttribute(HttpServletRequest request, String sessionKey) {
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
	protected void setSessionAttribute(HttpServletRequest request, String sessionKey, Object objSessionAttribute) {
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
	protected void removeSessionAttribute(HttpServletRequest request, String sessionKey) {
		HttpSession session = request.getSession();
		if (session != null)
			session.removeAttribute(sessionKey);
	}

	/**
	 * ��ȡһ��SessionContainer����,���Ϊnull�򴴽�֮
	 * 
	 * @param form
	 * @param obj
	 */
	protected SessionContainer getSessionContainer(HttpServletRequest request) {
		SessionContainer sessionContainer = (SessionContainer) this.getSessionAttribute(request, "SessionContainer");
		if (sessionContainer == null) {
			sessionContainer = new SessionContainer();
			HttpSession session = request.getSession(true);
			session.setAttribute("SessionContainer", sessionContainer);
		}
		return sessionContainer;
	}

	/**
	 * �����������װΪDto
	 * 
	 * @param request
	 * @return
	 */
	protected static Dto getPraramsAsDto(HttpServletRequest request) {
		return WebUtils.getPraramsAsDto(request);
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
	protected String getCodeDesc(String pField, String pCode, HttpServletRequest request) {
		return WebUtils.getCodeDesc(pField, pCode, request);
	}

	/**
	 * ���ݴ�������ȡ������б�
	 * 
	 * @param field
	 * @param request
	 * @return
	 */
	protected List getCodeListByField(String pField, HttpServletRequest request) {
		return WebUtils.getCodeListByField(pField, request);
	}

	/**
	 * ��ȡȫ�ֲ���ֵ
	 * 
	 * @param pParamKey
	 *            ��������
	 * @return
	 */
	protected String getParamValue(String pParamKey, HttpServletRequest request) {
		return WebUtils.getParamValue(pParamKey, request);
	}

	/**
	 * �����Ӧ
	 * 
	 * @param str
	 * @throws IOException
	 */
	protected void write(String str, HttpServletResponse response) throws IOException {
		response.getWriter().write(str);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * ֱ�ӽ�ListתΪ��ҳ����Ҫ��Json���ϸ�ʽ
	 * 
	 * @param list
	 *            ��Ҫ�����List����
	 * @param totalCount
	 *            ��¼����
	 * @param pDataFormat
	 *            ʱ�����ڸ�ʽ��,��null�����List����������ʱ������
	 */
	protected String encodeList2PageJson(List list, Integer totalCount, String dataFormat) {
		return JsonHelper.encodeList2PageJson(list, totalCount, dataFormat);
	}

	/**
	 * ������ϵ�л�Ϊ��������������Json��ʽ
	 * 
	 * @param pObject
	 *            ��ϵ�л��Ķ���
	 * @param pFormatString
	 *            ����ʱ���ʽ��,���Ϊnull����Ϊû������ʱ�����ֶ�
	 * @return
	 */
	protected String encodeDto2FormLoadJson(Dto pDto, String pFormatString) {
		return JsonHelper.encodeDto2FormLoadJson(pDto, pFormatString);
	}

	/**
	 * ������ϵ�л�ΪJson��ʽ
	 * 
	 * @param pObject
	 *            ��ϵ�л��Ķ���
	 * @param pFormatString
	 *            ����ʱ���ʽ��,���Ϊnull����Ϊû������ʱ�����ֶ�
	 * @return
	 */
	protected String encodeObject2Json(Object pObject, String pFormatString) {
		return JsonHelper.encodeObject2Json(pObject, pFormatString);
	}

	/**
	 * ������ϵ�л�ΪJson��ʽ
	 * 
	 * @param pObject
	 *            ��ϵ�л��Ķ���
	 * @return
	 */
	protected String encodeObjectJson(Object pObject) {
		return JsonHelper.encodeObject2Json(pObject);
	}

	protected Map getKeyMethodMap() {
		return null;
	}
}
