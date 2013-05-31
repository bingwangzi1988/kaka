package com.opms.conmg.service.impl;

import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.conmg.dao.OpmsHostCfgDao;
import com.opms.conmg.po.OpmsHostCfg;
import com.opms.conmg.service.OpmsHostCfgService;
import com.opms.conmg.vo.OpmsHostCfgQuery;
import org.springframework.stereotype.Service;
import com.common.base.BaseServiceImpl;

import java.util.List;

@Service("opmsHostCfgService")
public class OpmsHostCfgServiceImpl extends BaseServiceImpl<OpmsHostCfg,String> implements OpmsHostCfgService{

	private OpmsHostCfgDao opmsHostCfgDao;
	/**����setXXXX()����,spring�Ϳ���ͨ��autowire�Զ����ö�������,��ע���Сд*/
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
