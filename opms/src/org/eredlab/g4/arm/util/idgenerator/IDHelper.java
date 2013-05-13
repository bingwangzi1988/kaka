package org.eredlab.g4.arm.util.idgenerator;

import org.eredlab.g4.ccl.id.PrefixGenerator;
import org.eredlab.g4.ccl.id.generator.DefaultIDGenerator;
import org.eredlab.g4.ccl.id.prefix.DefaultPrefixGenerator;

/**
 * ID������ ��̬�������̲߳�����������ID������
 * �����һ��ʵ������ִ�����е�static����飬����밴�������ЩID����������Ӧ��һ��IDдһ����̬��Ϳ���
 * 
 * @author XiongChun
 * @since 2010-09-16s
 */
public class IDHelper {

	/**
	 * �¼�����ID
	 */
	private static DefaultIDGenerator defaultIDGenerator_eventid = null;

	/**
	 * SpringBean���ID
	 */
	private static DefaultIDGenerator defaultIDGenerator_monitorid = null;

	/**
	 * ��ĿID(���Ա�)
	 */
	private static DefaultIDGenerator defaultIDGenerator_xmid = null;

	/**
	 * CODEID
	 */
	private static DefaultIDGenerator defaultIDGenerator_codeid = null;

	/**
	 * EXCEPTIONID
	 */
	private static DefaultIDGenerator defaultIDGenerator_exceptionid = null;
	
	/**
	 * AUTHORIZEID_ROLE
	 */
	private static DefaultIDGenerator defaultIDGenerator_authorizeid_role = null;
	
	/**
	 * PARAMID
	 */
	private static DefaultIDGenerator defaultIDGenerator_paramid = null;
	
	/**
	 * ROLEID
	 */
	private static DefaultIDGenerator defaultIDGenerator_roleid = null;
	
	/**
	 * AUTHORIZEID_USERMENUMAP
	 */
	private static DefaultIDGenerator defaultIDGenerator_authorizeid_usermenumap = null;
	
	/**
	 * AUTHORIZEID_USER
	 */
	private static DefaultIDGenerator defaultIDGenerator_authorizeid_user = null;
	
	/**
	 * USERID
	 */
	private static DefaultIDGenerator defaultIDGenerator_userid = null;
	
	/**
	 * FILEID
	 */
	private static DefaultIDGenerator defaultIDGenerator_fileid = null;
	
	/**
	 * TESTID
	 */
	private static DefaultIDGenerator defaultIDGenerator_testid = null;
	
	/**
	 * USERROLEID
	 */
	private static DefaultIDGenerator defaultIDGenerator_userroleid = null;

	
	static {
		IdGenerator idGenerator_eventid = new IdGenerator();
		idGenerator_eventid.setFieldname("AUTHORIZEID_USERROLE");
		defaultIDGenerator_userroleid = idGenerator_eventid.getDefaultIDGenerator();
	}

	static {
		IdGenerator idGenerator_eventid = new IdGenerator();
		idGenerator_eventid.setFieldname("EVENTID");
		defaultIDGenerator_eventid = idGenerator_eventid.getDefaultIDGenerator();
	}

	static {
		IdGenerator idGenerator_monitorid = new IdGenerator();
		idGenerator_monitorid.setFieldname("MONITORID");
		defaultIDGenerator_monitorid = idGenerator_monitorid.getDefaultIDGenerator();
	}

	static {
		IdGenerator idGenerator_xmid = new IdGenerator();
		idGenerator_xmid.setFieldname("XMID");
		defaultIDGenerator_xmid = idGenerator_xmid.getDefaultIDGenerator();
	}

	static {
		IdGenerator idGenerator_codeid = new IdGenerator();
		idGenerator_codeid.setFieldname("CODEID");
		defaultIDGenerator_codeid = idGenerator_codeid.getDefaultIDGenerator();
	}

	static {
		IdGenerator idGenerator_exceptionid = new IdGenerator();
		idGenerator_exceptionid.setFieldname("EXCEPTIONID");
		defaultIDGenerator_exceptionid = idGenerator_exceptionid.getDefaultIDGenerator();
	}
	
	static {
		IdGenerator idGenerator_authorizeid_role = new IdGenerator();
		idGenerator_authorizeid_role.setFieldname("AUTHORIZEID_ROLE");
		defaultIDGenerator_authorizeid_role = idGenerator_authorizeid_role.getDefaultIDGenerator();
	}
	
	static {
		IdGenerator idGenerator_paramid = new IdGenerator();
		idGenerator_paramid.setFieldname("PARAMID");
		defaultIDGenerator_paramid = idGenerator_paramid.getDefaultIDGenerator();
	}
	
	static {
		IdGenerator idGenerator_roleid = new IdGenerator();
		idGenerator_roleid.setFieldname("ROLEID");
		defaultIDGenerator_roleid = idGenerator_roleid.getDefaultIDGenerator();
	}
	
	static {
		IdGenerator idGenerator_authorizeid_usermenumap = new IdGenerator();
		idGenerator_authorizeid_usermenumap.setFieldname("AUTHORIZEID_USERMENUMAP");
		defaultIDGenerator_authorizeid_usermenumap = idGenerator_authorizeid_usermenumap.getDefaultIDGenerator();
	}
	
	static {
		IdGenerator idGenerator_authorizeid_user = new IdGenerator();
		idGenerator_authorizeid_user.setFieldname("AUTHORIZEID_USER");
		defaultIDGenerator_authorizeid_user = idGenerator_authorizeid_user.getDefaultIDGenerator();
	}
	
	static {
		IdGenerator idGenerator_userid = new IdGenerator();
		idGenerator_userid.setFieldname("USERID");
		defaultIDGenerator_userid = idGenerator_userid.getDefaultIDGenerator();
	}
	
	static {
		IdGenerator idGenerator_fileid = new IdGenerator();
		idGenerator_fileid.setFieldname("FILEID");
		defaultIDGenerator_fileid = idGenerator_fileid.getDefaultIDGenerator();
	}

	static {
		IdGenerator idGenerator_testid = new IdGenerator();
		idGenerator_testid.setFieldname("TESTID");
		defaultIDGenerator_testid = idGenerator_testid.getDefaultIDGenerator();
	}

	
	
	
	
	/**
	 * ������Ȩ�˵�ID
	 * 
	 * @return
	 */
	public static String getUserRoleidID() {
		return defaultIDGenerator_userroleid.create();
	}
	
	/**
	 * �����¼�����ID
	 * 
	 * @return
	 */
	public static String getEventID() {
		return defaultIDGenerator_eventid.create();
	}

	/**
	 * ����SpringBean���ID
	 * 
	 * @return
	 */
	public static String getMonitorID() {
		return defaultIDGenerator_monitorid.create();
	}

	/**
	 * ������ĿID
	 * 
	 * @return
	 */
	public static String getXmID() {
		return defaultIDGenerator_xmid.create();
	}

	/**
	 * ����CODEID
	 * 
	 * @return
	 */
	public static String getCodeID() {
		return defaultIDGenerator_codeid.create();
	}
	
	/**
	 * ����ExceptionID
	 * 
	 * @return
	 */
	public static String getExceptionID() {
		return defaultIDGenerator_exceptionid.create();
	}
	
	/**
	 * ����AUTHORIZEID_ROLE
	 * 
	 * @return
	 */
	public static String getAuthorizeid4Role() {
		return defaultIDGenerator_authorizeid_role.create();
	}
	
	/**
	 * ����PARAMID
	 * 
	 * @return
	 */
	public static String getParamID() {
		return defaultIDGenerator_paramid.create();
	}
	
	/**
	 * ����ROLEID
	 * 
	 * @return
	 */
	public static String getRoleID() {
		return defaultIDGenerator_roleid.create();
	}
	
	/**
	 * ����AUTHORIZEID_USERMENUMAP
	 * 
	 * @return
	 */
	public static String getAuthorizeid4Usermenumap() {
		return defaultIDGenerator_authorizeid_usermenumap.create();
	}
	
	/**
	 * ����AUTHORIZEID_USER
	 * 
	 * @return
	 */
	public static String getAuthorizeid4User() {
		return defaultIDGenerator_authorizeid_user.create();
	}
	
	/**
	 * ����USERID
	 * 
	 * @return
	 */
	public static String getUserID() {
		return defaultIDGenerator_userid.create();
	}
	
	/**
	 * ����FILEID
	 * 
	 * @return
	 */
	public static String getFileID() {
		return defaultIDGenerator_fileid.create();
	}
	/**
	 * ����TESTID
	 *
	 * @return
	 */
	public static String getTestID() {
		return defaultIDGenerator_testid.create();
	}
}
