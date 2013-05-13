package com.opms.batch.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.batch.po.BatchStep;
import com.opms.batch.vo.BatchStepQuery;

public interface BatchStepService extends BaseService<BatchStep,String>{

    List<BatchStep> searchBatchStep(BatchStepQuery batchStep,CountOrder countOrder);

    Long countBatchStep(BatchStepQuery batchStep);

}