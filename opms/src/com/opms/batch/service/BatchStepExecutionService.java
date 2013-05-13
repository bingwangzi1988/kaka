package com.opms.batch.service;

import java.math.BigDecimal;
import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.batch.po.BatchStepExecution;
import com.opms.batch.vo.BatchStepExecutionQuery;

public interface BatchStepExecutionService extends BaseService<BatchStepExecution,BigDecimal>{

    List<BatchStepExecution> searchBatchStepExecution(BatchStepExecutionQuery batchStepExecution,CountOrder countOrder);

    Long countBatchStepExecution(BatchStepExecutionQuery batchStepExecution);

	List<BatchStepExecution> searchBatchStepExecution(String nodeid);

}