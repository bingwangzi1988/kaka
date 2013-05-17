package net.neptune.opms.model;

/**
 * ����ϵͳ��Ϣ
 * 
 * @author Administrator
 * 2013-5-15 ����4:23:17
 */
public class OSInfo {

	/** ����ϵͳ�ں����� */
	private String arch;
	/** "����ϵͳ������*/
	private String description;
	/** ����ϵͳ����*/
	private String vendorName;
	/** ����ϵͳ�İ汾��*/
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
