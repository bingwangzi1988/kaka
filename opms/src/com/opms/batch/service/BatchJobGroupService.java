package com.opms.batch.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.batch.po.BatchJobGroup;
import com.opms.batch.vo.BatchJobGroupQuery;

public interface BatchJobGroupService extends BaseService<BatchJobGroup,String>{

    List<BatchJobGroup> searchBatchJobGroup(BatchJobGroupQuery batchJobGroup,CountOrder countOrder);

    Long countBatchJobGroup(BatchJobGroupQuery batchJobGroup);

}