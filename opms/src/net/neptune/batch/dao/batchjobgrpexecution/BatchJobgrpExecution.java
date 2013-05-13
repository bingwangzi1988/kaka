package net.neptune.batch.dao.batchjobgrpexecution;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.neptune.batch.dao.batchjobexecution.BatchJobExecution;

public class BatchJobgrpExecution implements java.io.Serializable {

	private static final long serialVersionUID = 5121697045380748188L;
	
	private Integer id;
	private String name;
	private String batchNo;
	private String ipAddr;
	private Date startTime;
	private Date endTime;
	private String status;
	private ArrayList<BatchJobExecution> batchJobExecutionList;

	public BatchJobgrpExecution() {
	}

	public BatchJobgrpExecution(String name, String batchNo, Date startTime, String status) {
		this.name = name;
		this.batchNo = batchNo;
		this.startTime = startTime;
		this.status = status;
	}

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBatchNo() {
		return this.batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getIpAddr() {
		return this.ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Date getStartTime() {
		return this.startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return this.endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<BatchJobExecution> getBatchJobExecutionList() {
		return batchJobExecutionList;
	}
	public void setBatchJobExecutionList(
			List<BatchJobExecution> batchJobExecutionList) {
		if(batchJobExecutionList instanceof ArrayList){
			this.batchJobExecutionList = (ArrayList<BatchJobExecution>) batchJobExecutionList;
		}else{
			this.batchJobExecutionList = new ArrayList<BatchJobExecution>();
			this.batchJobExecutionList.addAll(batchJobExecutionList);
		}
	}
}