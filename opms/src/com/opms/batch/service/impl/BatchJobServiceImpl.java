package com.opms.batch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.batch.dao.BatchJobDao;
import com.opms.batch.po.BatchJob;
import com.opms.batch.service.BatchJobService;
import com.opms.batch.vo.BatchJobQuery;

@Service("batchJobService")
public class BatchJobServiceImpl extends BaseServiceImpl<BatchJob,String> implements BatchJobService{

	private BatchJobDao batchJobDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setBatchJobDao(BatchJobDao dao) {
		this.batchJobDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.batchJobDao;
	}

    public List<BatchJob> searchBatchJob(BatchJobQuery batchJob,CountOrder countOrder){
        return batchJobDao.searchBatchJob(batchJob,countOrder);
    }

    public Long countBatchJob(BatchJobQuery batchJob){
        return batchJobDao.countBatchJob(batchJob);
    }

}
