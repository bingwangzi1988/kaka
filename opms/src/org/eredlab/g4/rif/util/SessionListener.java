package org.eredlab.g4.rif.util;

import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.arm.service.MonitorService;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.base.IReader;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * Session������ ��ɶ�Seesion�Ự��Դ��ʵʱ���
 * 
 * @author �ܴ�
 * @since 2010-09-03
 * @see HttpSessionBindingListener
 */
public class SessionListener implements HttpSessionListener {
	
	private static Log log = LogFactory.getLog(SessionListener.class);
	
	// ���϶��󣬱���session���������
	static Hashtable ht = new Hashtable();

	/**
	 * ʵ��HttpSessionListener�ӿڣ����session�����¼�����
	 * ˵������ʱ��Session״̬Ϊ��Ч�Ự��ֻ���û��ɹ���¼ϵͳ��Ž���Sessionд��EAHTTPSESSION����Ϊ��ЧSESSION������
	 */
	public void sessionCreated(HttpSessionEvent event) {
		//HttpSession session = arg0.getSession();
		//ht.put(session.getId(), session);
		//log.info("������һ��Session����:" + session.getId() + " " + Util.getCurrentTime());
	}

	/**
	 * ʵ��HttpSessionListener�ӿڣ����session�����¼�����
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		SessionContainer sessionContainer =  (SessionContainer)session.getAttribute("SessionContainer");
		sessionContainer.setUserInfo(null); //���RequestFilter��������
		sessionContainer.cleanUp();
		MonitorService monitorService = (MonitorService)SpringBeanLoader.getSpringBean("monitorService");
		Dto dto = new BaseDto();
		dto.put("sessionid", session.getId());
		monitorService.deleteHttpSession(dto);
		ht.remove(session.getId());
		log.info("������һ��Session����:" + session.getId() + " " + G4Utils.getCurrentTime());
	}
	
	/**
	 * ����һ����ЧSession
	 * @param session
	 */
	static public void addSession(HttpSession session, UserInfoVo userInfo) {
		ht.put(session.getId(), session);
		IReader g4Reader = (IReader)SpringBeanLoader.getSpringBean("g4Reader");
		MonitorService monitorService = (MonitorService)SpringBeanLoader.getSpringBean("monitorService");
		UserInfoVo usInfo = (UserInfoVo)g4Reader.queryForObject("queryHttpSessionsByID", session.getId());
		if(G4Utils.isEmpty(usInfo)){
			monitorService.saveHttpSession(userInfo);
		}
	}

	/**
	 * ����ȫ��session���󼯺�
	 * @return
	 */
	static public Iterator getSessions() {
		return ht.values().iterator();
	}

	/**
	 * ����session id����ָ����session����
	 * @param sessionId
	 * @return
	 */
	static public HttpSession getSessionByID(String sessionId) {
		return (HttpSession) ht.get(sessionId);
	}
}
