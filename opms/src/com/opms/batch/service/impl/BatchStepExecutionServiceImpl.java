package com.opms.batch.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.batch.dao.BatchStepExecutionDao;
import com.opms.batch.po.BatchStepExecution;
import com.opms.batch.service.BatchStepExecutionService;
import com.opms.batch.vo.BatchStepExecutionQuery;

@Service("batchStepExecutionService")
public class BatchStepExecutionServiceImpl extends BaseServiceImpl<BatchStepExecution,BigDecimal> implements BatchStepExecutionService{

	private BatchStepExecutionDao batchStepExecutionDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setBatchStepExecutionDao(BatchStepExecutionDao dao) {
		this.batchStepExecutionDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.batchStepExecutionDao;
	}

    public List<BatchStepExecution> searchBatchStepExecution(BatchStepExecutionQuery batchStepExecution,CountOrder countOrder){
        return batchStepExecutionDao.searchBatchStepExecution(batchStepExecution,countOrder);
    }

    public Long countBatchStepExecution(BatchStepExecutionQuery batchStepExecution){
        return batchStepExecutionDao.countBatchStepExecution(batchStepExecution);
    }
	public List<BatchStepExecution> searchBatchStepExecution(String nodeid) {
		return batchStepExecutionDao.searchBatchStepExecution(nodeid);
	}

}
