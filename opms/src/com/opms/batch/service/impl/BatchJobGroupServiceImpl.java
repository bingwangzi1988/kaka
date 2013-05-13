package com.opms.batch.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.batch.dao.BatchJobGroupDao;
import com.opms.batch.po.BatchJobGroup;
import com.opms.batch.service.BatchJobGroupService;
import com.opms.batch.vo.BatchJobGroupQuery;

@Service("batchJobGroupService")
public class BatchJobGroupServiceImpl extends BaseServiceImpl<BatchJobGroup,String> implements BatchJobGroupService{

	private BatchJobGroupDao batchJobGroupDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setBatchJobGroupDao(BatchJobGroupDao dao) {
		this.batchJobGroupDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.batchJobGroupDao;
	}

    public List<BatchJobGroup> searchBatchJobGroup(BatchJobGroupQuery batchJobGroup,CountOrder countOrder){
        return batchJobGroupDao.searchBatchJobGroup(batchJobGroup,countOrder);
    }

    public Long countBatchJobGroup(BatchJobGroupQuery batchJobGroup){
        return batchJobGroupDao.countBatchJobGroup(batchJobGroup);
    }

}
