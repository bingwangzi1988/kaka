package net.neptune.batch.dao.batchstepexecution;

import java.util.Date;

import net.neptune.batch.dao.batchjobexecution.BatchJobExecution;

public class BatchStepExecution implements java.io.Serializable {

	private static final long serialVersionUID = 8451581914672622453L;
	
	private Integer id;
	private String name;
	private Date startTime;
	private Date endTime;
	private String status;
	private String errMsg;
	private BatchJobExecution batchJobExecution;

	public BatchStepExecution() {
	}

	public BatchStepExecution(String name, Date startTime, String status) {
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
	public BatchJobExecution getBatchJobExecution() {
		return batchJobExecution;
	}
	public void setBatchJobExecution(BatchJobExecution batchJobExecution) {
		this.batchJobExecution = batchJobExecution;
	}
}