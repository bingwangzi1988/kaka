package net.neptune.opms.model;

/**
 * 每一块CPU信息
 * 
 * @author Administrator
 * 2013-5-15 下午4:22:48
 */
public class CPUInfo {

	/** CPU的总量MHz*/
	private int mhz;
	/** CPU生产商*/
	private String vendor;
	/** CPU类别*/
	private String model;
	/** CPU用户使用率*/
	private String user;
	/** CPU系统使用率*/
	private String sys;
	/** CPU当前等待率*/
	private String wait;
	/** CPU当前错误率*/
	private String nice;
	/** CPU当前空闲率*/
	private String idle;
	/** CPU总的使用率*/
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
