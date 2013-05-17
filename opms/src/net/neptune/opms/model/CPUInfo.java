package net.neptune.opms.model;

/**
 * ÿһ��CPU��Ϣ
 * 
 * @author Administrator
 * 2013-5-15 ����4:22:48
 */
public class CPUInfo {

	/** CPU������MHz*/
	private int mhz;
	/** CPU������*/
	private String vendor;
	/** CPU���*/
	private String model;
	/** CPU�û�ʹ����*/
	private String user;
	/** CPUϵͳʹ����*/
	private String sys;
	/** CPU��ǰ�ȴ���*/
	private String wait;
	/** CPU��ǰ������*/
	private String nice;
	/** CPU��ǰ������*/
	private String idle;
	/** CPU�ܵ�ʹ����*/
	private String combined;
	
	public CPUInfo() {
		
	}
	public CPUInfo(int mhz, String vendor, String model, String user,
			String sys, String wait, String nice, String idle, String combined) {
		super();
		this.mhz = mhz;
		this.vendor = vendor;
		this.model = model;
		this.user = user;
		this.sys = sys;
		this.wait = wait;
		this.nice = nice;
		this.idle = idle;
		this.combined = combined;
	}
	public int getMhz() {
		return mhz;
	}
	public void setMhz(int mhz) {
		this.mhz = mhz;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSys() {
		return sys;
	}
	public void setSys(String sys) {
		this.sys = sys;
	}
	public String getWait() {
		return wait;
	}
	public void setWait(String wait) {
		this.wait = wait;
	}
	public String getNice() {
		return nice;
	}
	public void setNice(String nice) {
		this.nice = nice;
	}
	public String getIdle() {
		return idle;
	}
	public void setIdle(String idle) {
		this.idle = idle;
	}
	public String getCombined() {
		return combined;
	}
	public void setCombined(String combined) {
		this.combined = combined;
	}
	
}
