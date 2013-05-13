package com.opms.batch.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.batch.po.BatchJob;
import com.opms.batch.vo.BatchJobQuery;

public interface BatchJobService extends BaseService<BatchJob,String>{

    List<BatchJob> searchBatchJob(BatchJobQuery batchJob,CountOrder countOrder);

    Long countBatchJob(BatchJobQuery batchJob);

}