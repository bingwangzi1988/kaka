package com.opms.conmg.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.conmg.dao.CmparmDao;
import com.opms.conmg.po.Cmparm;
import com.opms.conmg.service.CmparmService;
import com.opms.conmg.vo.CmparmQuery;

@Service("cmparmService")
public class CmparmServiceImpl extends BaseServiceImpl<Cmparm,String> implements CmparmService{

	private CmparmDao cmparmDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setCmparmDao(CmparmDao dao) {
		this.cmparmDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.cmparmDao;
	}

    public List<Cmparm> searchCmparm(CmparmQuery cmparm,CountOrder countOrder,boolean isRole){
        return cmparmDao.searchCmparm(cmparm,countOrder,isRole);
    }

    public Long countCmparm(CmparmQuery cmparm,boolean isRole){
        return cmparmDao.countCmparm(cmparm,isRole);
    }
	public String getPwd() {
		Cmparm cmparm = cmparmDao.findByProperty("parmId", "PWDCRDFILE");
		return cmparm.getParmValue();
	}

	public List<Object[]> getCardTxnParm(String parmFlag) {
		return cmparmDao.getCardTxnParm(parmFlag);
	}
	public Cmparm getCmparmByParmId(String parmId) {
		return cmparmDao.getCmparmByParmId(parmId);
	}
}
