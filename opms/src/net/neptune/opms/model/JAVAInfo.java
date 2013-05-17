package net.neptune.opms.model;

/**
 * 获取虚拟机信息
 * 
 * @author Administrator
 * 2013-5-16 上午9:43:31
 */
public class JAVAInfo {

	/** JVM可以使用的总内存*/
	private long jvmTotalMemory;
	/** JVM可以使用的剩余内存*/
	private long jvmFreeMemory;
	/** JVM可以使用的处理器个数*/
	private int availableProcessors;
	/** Java的运行环境版本*/
	private String version;
	/** Java的运行环境供应商*/
	private String vendor;
	/** Java供应商的URL*/
	private String vendorUrl;
	/** Java的安装路径*/
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
