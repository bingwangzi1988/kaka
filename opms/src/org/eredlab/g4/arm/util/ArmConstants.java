package org.eredlab.g4.arm.util;

import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * ������
 * @author XiongChun
 * @since 2010-01-13
 */
public interface ArmConstants extends GlobalConstants{
	
	/**
	 * ����״̬<br>
	 * 1:����
	 */
	public static final String ENABLED_Y = "1"; 
	
	/**
	 * ����״̬<br>
	 * 0:ͣ��
	 */
	public static final String ENABLED_N = "0";
	
	/**
	 * �༭ģʽ<br>
	 * 1:�ɱ༭
	 */
	public static final String EDITMODE_Y = "1"; 
	
	/**
	 * �༭ģʽ<br>
	 * 0:ֻ��
	 */
	public static final String EDITMODE_N = "0";
	
	/**
	 * ����̬<br>
	 * 1:����
	 */
	public static final String LOCK_Y = "1"; 
	
	/**
	 * ����״̬<br>
	 * 0:����
	 */
	public static final String LOCK_N = "0";
	
	/**
	 * ǿ�������<br>
	 * 1:ǿ��
	 */
	public static final String FORCELOAD_Y = "1"; 
	
	/**
	 * ǿ�������<br>
	 * 0:��ǿ��
	 */
	public static final String FORCELOAD_N = "0";
	
	/**
	 * ���ڵ�����<br>
	 * 1:Ҷ�ӽڵ�
	 */
	public static final String LEAF_Y = "1"; 
	
	/**
	 * ���ڵ�����<br>
	 * 0:��֦�ڵ�
	 */
	public static final String LEAF_N = "0";
	
	/**
	 * ��ɫ����<br>
	 * 1:ҵ���ɫ
	 */
	public static final String ROLETYPE_BUSINESS = "1";
	
	/**
	 * ��ɫ����<br>
	 * 2:�����ɫ
	 */
	public static final String ROLETYPE_ADMIN = "2";
	
	/**
	 * ��ɫ����<br>
	 * 3:ϵͳ���ý�ɫ
	 */
	public static final String ROLETYPE_EMBED = "3";
	
	/**
	 * Ȩ�޼���<br>
	 * 1:����Ȩ��
	 */
	public static final String AUTHORIZELEVEL_ACCESS = "1"; 
	
	/**
	 * Ȩ�޼���<br>
	 * 2:����Ȩ��
	 */
	public static final String AUTHORIZELEVEL_ADMIN = "2";
	
	/**
	 * �û�����<br>
	 * 1:����Ա
	 */
	public static final String USERTYPE_BUSINESS = "1";
	
	/**
	 * �û�����<br>
	 * 2:����Ա
	 */
	public static final String USERTYPE_ADMIN = "2";
	
	/**
	 * �û�����<br>
	 * 3:ϵͳ�����û�
	 */
	public static final String USERTYPE_EMBED = "3";
	
	/**
	 * ���ڵ�ID<br>
	 * 01:�˵���
	 */
	public static final String ROORID_MENU = "01";
	
	/**
	 * �ʻ�����<br>
	 * 1:�����ʻ�
	 */
	public static final String ACCOUNTTYPE_NORMAL = "1";
	
	/**
	 * �ʻ�����<br>
	 * 2:SUPER�ʻ�
	 */
	public static final String ACCOUNTTYPE_SUPER = "2";
	
	/**
	 * �ʻ�����<br>
	 * 3:DEVELOPER�ʻ�
	 */
	public static final String ACCOUNTTYPE_DEVELOPER = "3";
	
	/**
	 * ����Ա�¼����ټ�ؿ���[1:��;0:�ر�]<br>
	 * 1:��
	 */
	public static final String EVENTMONITOR_ENABLE_Y = "1";
	
	/**
	 * ����Ա�¼����ټ�ؿ���[1:��;0:�ر�]<br>
	 * 0:�ر�
	 */
	public static final String EVENTMONITOR_ENABLE_N = "0";
	
	/**
	 * ���������[1:BPO����;2:DAO����]<br>
	 * 1:BPO����
	 */
	public static final String POINTCUTTYPE_BPO = "1";
	
	/**
	 * ���������[1:BPO����;2:DAO����]<br>
	 * 2:DAO����
	 */
	public static final String POINTCUTTYPE_DAO = "2";
	
	/**
	 * ֪ͨ����[1:��������֪ͨ;2:�쳣����֪ͨ]<br>
	 * 1:��������֪ͨ
	 */
	public static final String ADVISETYPE_CALL = "1";
	
	/**
	 * ֪ͨ����[1:��������֪ͨ;2:�쳣����֪ͨ]<br>
	 * 2:�쳣����֪ͨ
	 */
	public static final String ADVISETYPE_CATCH = "2";
}
