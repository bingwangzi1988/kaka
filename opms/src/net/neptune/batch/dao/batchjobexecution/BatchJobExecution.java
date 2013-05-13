package net.neptune.batch.dao.batchjobexecution;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.neptune.batch.dao.batchjobgrpexecution.BatchJobgrpExecution;
import net.neptune.batch.dao.batchstepexecution.BatchStepExecution;

public class BatchJobExecution implements java.io.Serializable {
	
	private static final long serialVersionUID = -6827640936449183405L;

	private Integer id;
	private String name;
	private String ipAddr;
	private Date startTime;
	private Date endTime;
	private String status;
	private String errMsg;
	private BatchJobgrpExecution batchJobgrpExecution;
	private ArrayList<BatchStepExecution> batchStepExecutionList;

	public BatchJobExecution() {
	}

	public BatchJobExecution(String name, Date startTime, String status) {
		this.name = name;
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
	public String getErrMsg() {
		return this.errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public ArrayList<BatchStepExecution> getBatchStepExecutionList() {
		return batchStepExecutionList;
	}
	public void setBatchStepExecutionList(
			List<BatchStepExecution> batchStepExecutionList) {
		if(batchStepExecutionList instanceof ArrayList){
			this.batchStepExecutionList = (ArrayList<BatchStepExecution>) batchStepExecutionList;
		}else{
			this.batchStepExecutionList = new ArrayList<BatchStepExecution>();
			this.batchStepExecutionList.addAll(batchStepExecutionList);
		}
	}
	public BatchJobgrpExecution getBatchJobgrpExecution() {
		return batchJobgrpExecution;
	}
	public void setBatchJobgrpExecution(
			BatchJobgrpExecution batchJobgrpExecution) {
		this.batchJobgrpExecution = batchJobgrpExecution;
	}
}