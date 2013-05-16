package net.neptune.opms.model;

/**
 * 操作系统信息
 * 
 * @author Administrator
 * 2013-5-15 下午4:23:17
 */
public class OSInfo {

	/** 操作系统内核类型 */
	private String arch;
	/** "操作系统的描述*/
	private String description;
	/** 操作系统名称*/
	private String vendorName;
	/** 操作系统的版本号*/
	private String version;
	
	public OSInfo() {
		
	}
			
	public OSInfo(String arch, String description, String vendorName, String version) {
		super();
		this.arch = arch;
		this.description = description;
		this.vendorName = vendorName;
		this.version = version;
	}
	public String getArch() {
		return arch;
	}
	public void setArch(String arch) {
		this.arch = arch;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
