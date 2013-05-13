package com.opms.batch.service;

import java.math.BigDecimal;
import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.batch.po.BatchJobExecution;
import com.opms.batch.vo.BatchJobExecutionQuery;

public interface BatchJobExecutionService extends BaseService<BatchJobExecution,BigDecimal>{

    List<BatchJobExecution> searchBatchJobExecution(BatchJobExecutionQuery batchJobExecution,CountOrder countOrder);

    Long countBatchJobExecution(BatchJobExecutionQuery batchJobExecution);

    List<BatchJobExecution> searchBatchJobExecution(String jobGrpId);
    
}