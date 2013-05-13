package com.opms.osgi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.osgi.dao.OpmsSystemCfgDao;
import com.opms.osgi.po.OpmsSystemCfg;
import com.opms.osgi.service.OpmsSystemCfgService;
import com.opms.osgi.vo.OpmsSystemCfgQuery;

@Service("opmsSystemCfgService")
public class OpmsSystemCfgServiceImpl extends BaseServiceImpl<OpmsSystemCfg,String> implements OpmsSystemCfgService{

	private OpmsSystemCfgDao opmsSystemCfgDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setOpmsSystemCfgDao(OpmsSystemCfgDao dao) {
		this.opmsSystemCfgDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.opmsSystemCfgDao;
	}

    @SuppressWarnings("unchecked")
	public List<OpmsSystemCfg> searchOpmsSystemCfg(OpmsSystemCfgQuery opmsSystemCfg,CountOrder countOrder){
        return opmsSystemCfgDao.searchOpmsSystemCfg(opmsSystemCfg,countOrder);
    }

    @SuppressWarnings("unchecked")
	public List<String> searchOpmsSystemId() {
    	return opmsSystemCfgDao.searchOpmsSystemId();
    }
    
    public Long countOpmsSystemCfg(OpmsSystemCfgQuery opmsSystemCfg){
        return opmsSystemCfgDao.countOpmsSystemCfg(opmsSystemCfg);
    }

}
