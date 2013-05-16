package net.neptune.opms.model;

/**
 * 内存信息
 * 
 * @author Administrator
 * 2013-5-15 下午4:23:09
 */
public class MEMInfo {

	/** 内存总量*/
	private long memTotal;
	/** 当前内存使用量*/
	private long memUsed;
	/** 当前内存剩余量*/
	private long memFree;
	/** 交换区总量*/
	private long swapTotal;
	/** 当前交换区使用量*/
	private long swapUsed;
	/** 当前交换区剩余量*/
	private long swapFree;
	
	public MEMInfo() {
		
	}
	public MEMInfo(long memTotal, long memUsed, long memFree, long swapTotal, long swapUsed, long swapFree) {
		super();
		this.memTotal = memTotal;
		this.memUsed = memUsed;
		this.memFree = memFree;
		this.swapTotal = swapTotal;
		this.swapUsed = swapUsed;
		this.swapFree = swapFree;
	}
	public long getMemTotal() {
		return memTotal;
	}
	public void setMemTotal(long memTotal) {
		this.memTotal = memTotal;
	}
	public long getMemUsed() {
		return memUsed;
	}
	public void setMemUsed(long memUsed) {
		this.memUsed = memUsed;
	}
	public long getMemFree() {
		return memFree;
	}
	public void setMemFree(long memFree) {
		this.memFree = memFree;
	}
	public long getSwapTotal() {
		return swapTotal;
	}
	public void setSwapTotal(long swapTotal) {
		this.swapTotal = swapTotal;
	}
	public long getSwapUsed() {
		return swapUsed;
	}
	public void setSwapUsed(long swapUsed) {
		this.swapUsed = swapUsed;
	}
	public long getSwapFree() {
		return swapFree;
	}
	public void setSwapFree(long swapFree) {
		this.swapFree = swapFree;
	}
	
}
