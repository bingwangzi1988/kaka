package net.neptune.batch.core.invoker;

import java.util.ArrayList;
import java.util.Date;

import javax.jws.WebService;

import net.neptune.batch.dao.batchjobexecution.BatchJobExecution;
import net.neptune.batch.dao.batchjobgrpexecution.BatchJobgrpExecution;
import net.neptune.batch.dao.batchstepexecution.BatchStepExecution;

@WebService
public interface BatchExecutionService {

	public String getJobgrpJson(String node);
	
	public String getBatchJobgrpExecution(String name, String type);
	
	public ArrayList<BatchJobgrpExecution> findBatchJobgrpExecutionList(String jobGroupName, String batchNo, Date beginDate, Date endDate, String status, int start, int limit);
	
	public Long countBatchJobgrpExecution(String jobGroupName, String batchNo, Date beginDate, Date endDate, String status);
	
	public ArrayList<BatchJobgrpExecution> searchBatchJobgrpExecution();
	
	public ArrayList<BatchJobExecution> searchBatchJobExecution(String jobGrpId);
	
	public ArrayList<BatchStepExecution> searchBatchStepExecution(String jobId);
	
}
