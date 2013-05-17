package net.neptune.opms.model;

/**
 * ��ȡ�������Ϣ
 * 
 * @author Administrator
 * 2013-5-16 ����9:43:31
 */
public class JAVAInfo {

	/** JVM����ʹ�õ����ڴ�*/
	private long jvmTotalMemory;
	/** JVM����ʹ�õ�ʣ���ڴ�*/
	private long jvmFreeMemory;
	/** JVM����ʹ�õĴ���������*/
	private int availableProcessors;
	/** Java�����л����汾*/
	private String version;
	/** Java�����л�����Ӧ��*/
	private String vendor;
	/** Java��Ӧ�̵�URL*/
	private String vendorUrl;
	/** Java�İ�װ·��*/
	private String home;
	
	public JAVAInfo() {
		
	}
	public JAVAInfo(long jvmTotalMemory, long jvmFreeMemory,
			int availableProcessors, String version, String vendor,
			String vendorUrl, String home) {
		super();
		this.jvmTotalMemory = jvmTotalMemory;
		this.jvmFreeMemory = jvmFreeMemory;
		this.availableProcessors = availableProcessors;
		this.version = version;
		this.vendor = vendor;
		this.vendorUrl = vendorUrl;
		this.home = home;
	}
	
	public long getJvmTotalMemory() {
		return jvmTotalMemory;
	}
	public void setJvmTotalMemory(long jvmTotalMemory) {
		this.jvmTotalMemory = jvmTotalMemory;
	}
	public long getJvmFreeMemory() {
		return jvmFreeMemory;
	}
	public void setJvmFreeMemory(long jvmFreeMemory) {
		this.jvmFreeMemory = jvmFreeMemory;
	}
	public int getAvailableProcessors() {
		return availableProcessors;
	}
	public void setAvailableProcessors(int availableProcessors) {
		this.availableProcessors = availableProcessors;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getVendorUrl() {
		return vendorUrl;
	}
	public void setVendorUrl(String vendorUrl) {
		this.vendorUrl = vendorUrl;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	
}
