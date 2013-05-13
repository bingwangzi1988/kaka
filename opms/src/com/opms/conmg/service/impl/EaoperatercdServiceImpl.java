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
	/**����setXXXX()����,spring�Ϳ���ͨ��autowire�Զ����ö�������,��ע���Сд*/
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
