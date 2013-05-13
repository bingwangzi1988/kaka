package com.opms.osgi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.osgi.dao.OpmsHostCfgDao;
import com.opms.osgi.po.OpmsHostCfg;
import com.opms.osgi.service.OpmsHostCfgService;
import com.opms.osgi.vo.OpmsHostCfgQuery;

@Service("opmsHostCfgService")
public class OpmsHostCfgServiceImpl extends BaseServiceImpl<OpmsHostCfg,String> implements OpmsHostCfgService{

	private OpmsHostCfgDao opmsHostCfgDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setOpmsHostCfgDao(OpmsHostCfgDao dao) {
		this.opmsHostCfgDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.opmsHostCfgDao;
	}

    public List<OpmsHostCfg> searchOpmsHostCfg(OpmsHostCfgQuery opmsHostCfg,CountOrder countOrder){
        return opmsHostCfgDao.searchOpmsHostCfg(opmsHostCfg,countOrder);
    }

    public Long countOpmsHostCfg(OpmsHostCfgQuery opmsHostCfg){
        return opmsHostCfgDao.countOpmsHostCfg(opmsHostCfg);
    }

}
