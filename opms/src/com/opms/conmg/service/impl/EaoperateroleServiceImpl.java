package com.opms.conmg.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.conmg.dao.EaoperateroleDao;
import com.opms.conmg.po.Eaoperaterole;
import com.opms.conmg.service.EaoperateroleService;
import com.opms.conmg.vo.EaoperateroleQuery;

@Service("eaoperateroleService")
public class EaoperateroleServiceImpl extends BaseServiceImpl<Eaoperaterole,String> implements EaoperateroleService{

	private EaoperateroleDao eaoperateroleDao;
	/**����setXXXX()����,spring�Ϳ���ͨ��autowire�Զ����ö�������,��ע���Сд*/
	public void setEaoperateroleDao(EaoperateroleDao dao) {
		this.eaoperateroleDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.eaoperateroleDao;
	}

    public List<Eaoperaterole> searchEaoperaterole(EaoperateroleQuery eaoperaterole,CountOrder countOrder,boolean isRole,List<String> menulst){
        return eaoperateroleDao.searchEaoperaterole(eaoperaterole,countOrder,isRole,menulst);
    }

    public Long countEaoperaterole(EaoperateroleQuery eaoperaterole,boolean isRole,List<String> menulst){
        return eaoperateroleDao.countEaoperaterole(eaoperaterole,isRole,menulst);
    }
	public Eaoperaterole searchEaoperateroleByTablename(String tableName) {
		return eaoperateroleDao.searchEaoperateroleByTablename(tableName);
	}
	public List searchUserRole(String userid) {
		return eaoperateroleDao.searchUserRole(userid);
	}

}
