package com.opms.conmg.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.conmg.dao.EaoperatercdDao;
import com.opms.conmg.po.Eaoperatercd;
import com.opms.conmg.service.EaoperatercdService;
import com.opms.conmg.vo.EaoperatercdQuery;

@Service("eaoperatercdService")
public class EaoperatercdServiceImpl extends BaseServiceImpl<Eaoperatercd,String> implements EaoperatercdService{

	private EaoperatercdDao eaoperatercdDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setEaoperatercdDao(EaoperatercdDao dao) {
		this.eaoperatercdDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.eaoperatercdDao;
	}

    public List<Eaoperatercd> searchEaoperatercd(EaoperatercdQuery eaoperatercd,CountOrder countOrder){
        return eaoperatercdDao.searchEaoperatercd(eaoperatercd,countOrder);
    }

    public Long countEaoperatercd(EaoperatercdQuery eaoperatercd){
        return eaoperatercdDao.countEaoperatercd(eaoperatercd);
    }

}
