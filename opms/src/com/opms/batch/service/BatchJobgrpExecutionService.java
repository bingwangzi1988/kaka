package com.opms.batch.service;

import java.math.BigDecimal;
import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.batch.po.BatchJobgrpExecution;
import com.opms.batch.vo.BatchJobgrpExecutionQuery;

public interface BatchJobgrpExecutionService extends BaseService<BatchJobgrpExecution,BigDecimal>{

    List<BatchJobgrpExecution> searchBatchJobgrpExecution(BatchJobgrpExecutionQuery batchJobgrpExecution,CountOrder countOrder);

    Long countBatchJobgrpExecution(BatchJobgrpExecutionQuery batchJobgrpExecution);

}