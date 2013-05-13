package com.opms.batch.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.batch.dao.BatchJobgrpExecutionDao;
import com.opms.batch.po.BatchJobgrpExecution;
import com.opms.batch.service.BatchJobgrpExecutionService;
import com.opms.batch.vo.BatchJobgrpExecutionQuery;

@Service("batchJobgrpExecutionService")
public class BatchJobgrpExecutionServiceImpl extends BaseServiceImpl<BatchJobgrpExecution,BigDecimal> implements BatchJobgrpExecutionService{

	private BatchJobgrpExecutionDao batchJobgrpExecutionDao;
	/**����setXXXX()����,spring�Ϳ���ͨ��autowire�Զ����ö�������,��ע���Сд*/
	public void setBatchJobgrpExecutionDao(BatchJobgrpExecutionDao dao) {
		this.batchJobgrpExecutionDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.batchJobgrpExecutionDao;
	}

    public List<BatchJobgrpExecution> searchBatchJobgrpExecution(BatchJobgrpExecutionQuery batchJobgrpExecution,CountOrder countOrder){
        return batchJobgrpExecutionDao.searchBatchJobgrpExecution(batchJobgrpExecution,countOrder);
    }

    public Long countBatchJobgrpExecution(BatchJobgrpExecutionQuery batchJobgrpExecution){
        return batchJobgrpExecutionDao.countBatchJobgrpExecution(batchJobgrpExecution);
    }

}
