package org.eredlab.g4.arm.vo;

import java.util.Date;

import org.eredlab.g4.ccl.datastructure.impl.BaseVo;

/**
 * ���û���ص��ֶ�,���ڴ洢��Session�����е�ֵ����
 * 
 * @author XiongChun
 * @since 2010-01-13
 */
public class UserInfoVo extends BaseVo {

	/**
	 * �û���ţ�varchar2(8) <Primary Key>
	 */
	private String userid;

	/**
	 * �û�����varchar2(20)
	 */
	private String username;

	/**
	 * ��½�ʻ���varchar2(20)
	 */
	private String account;

	/**
	 * ���룺varchar2(20)
	 */
	private String password;

	/**
	 * �Ա�(0:δ֪;1:��;2:Ů)��varchar2(2)
	 */
	private String sex;

	/**
	 * ���ű�ţ�varchar2(8)
	 */
	private String deptid;
	private String deptname;

	/**
	 * ������־(0:����;1:����)��varchar2(2)
	 */
	private String locked;

	/**
	 * �Զ����ű��
	 */
	private String customId;
	
	/**
	 * �Զ�������
	 */
	private String theme;
	
	/**
	 * �Զ���portal
	 */
	private String portal;

	/**
	 * �ỰID
	 */
	private String sessionID;
	
	/**
	 * �Ự����ʱ��
	 */
	private String sessionCreatedTime;
	
	/**
	 * ��¼IP
	 */
	private String loginIP;
	
	/**
	 * �����
	 */
	private String explorer;

	/**
	 * �����޸�ʱ��
	 * */
	private Date chgPwdDate;
	
	/**
	 * ����
	 * */
	private String branch;
	
	/**
	 * Constractor
	 */
	public UserInfoVo() {
	}

	/**
	 * Constractor
	 * 
	 * @param <code>userid</code>
	 */
	public UserInfoVo(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDeptid() {
		return this.deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getLocked() {
		return this.locked;
	}

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getSessionCreatedTime() {
		return sessionCreatedTime;
	}

	public void setSessionCreatedTime(String sessionCreatedTime) {
		this.sessionCreatedTime = sessionCreatedTime;
	}

	public String getExplorer() {
		return explorer;
	}

	public void setExplorer(String explorer) {
		this.explorer = explorer;
	}

    public Date getChgPwdDate() {
		return chgPwdDate;
	}

	public void setChgPwdDate(Date chgPwdDate) {
		this.chgPwdDate = chgPwdDate;
	}

	public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
    
}
