package com.opms.batch.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.batch.dao.BatchJobExecutionDao;
import com.opms.batch.po.BatchJobExecution;
import com.opms.batch.service.BatchJobExecutionService;
import com.opms.batch.vo.BatchJobExecutionQuery;

@Service("batchJobExecutionService")
public class BatchJobExecutionServiceImpl extends BaseServiceImpl<BatchJobExecution,BigDecimal> implements BatchJobExecutionService{

	private BatchJobExecutionDao batchJobExecutionDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setBatchJobExecutionDao(BatchJobExecutionDao dao) {
		this.batchJobExecutionDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.batchJobExecutionDao;
	}

    public List<BatchJobExecution> searchBatchJobExecution(BatchJobExecutionQuery batchJobExecution,CountOrder countOrder){
        return batchJobExecutionDao.searchBatchJobExecution(batchJobExecution,countOrder);
    }
    
    public List<BatchJobExecution> searchBatchJobExecution(String jobGrpId) {
    	return batchJobExecutionDao.searchBatchJobExecution(jobGrpId);
    }

    public Long countBatchJobExecution(BatchJobExecutionQuery batchJobExecution){
        return batchJobExecutionDao.countBatchJobExecution(batchJobExecution);
    }

}
