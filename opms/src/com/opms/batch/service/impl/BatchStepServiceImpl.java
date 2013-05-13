package com.opms.batch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.batch.dao.BatchStepDao;
import com.opms.batch.po.BatchStep;
import com.opms.batch.service.BatchStepService;
import com.opms.batch.vo.BatchStepQuery;

@Service("batchStepService")
public class BatchStepServiceImpl extends BaseServiceImpl<BatchStep,String> implements BatchStepService{

	private BatchStepDao batchStepDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setBatchStepDao(BatchStepDao dao) {
		this.batchStepDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.batchStepDao;
	}

    public List<BatchStep> searchBatchStep(BatchStepQuery batchStep,CountOrder countOrder){
        return batchStepDao.searchBatchStep(batchStep,countOrder);
    }

    public Long countBatchStep(BatchStepQuery batchStep){
        return batchStepDao.countBatchStep(batchStep);
    }

}
