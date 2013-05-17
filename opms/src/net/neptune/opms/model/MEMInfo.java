package net.neptune.opms.model;

/**
 * �ڴ���Ϣ
 * 
 * @author Administrator
 * 2013-5-15 ����4:23:09
 */
public class MEMInfo {

	/** �ڴ�����*/
	private long memTotal;
	/** ��ǰ�ڴ�ʹ����*/
	private long memUsed;
	/** ��ǰ�ڴ�ʣ����*/
	private long memFree;
	/** ����������*/
	private long swapTotal;
	/** ��ǰ������ʹ����*/
	private long swapUsed;
	/** ��ǰ������ʣ����*/
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
