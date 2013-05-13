package com.opms.conmg.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.conmg.dao.CmbankDao;
import com.opms.conmg.po.Cmbank;
import com.opms.conmg.service.CmbankService;
import com.opms.conmg.vo.CmbankQuery;

@Service("cmbankService")
public class CmbankServiceImpl extends BaseServiceImpl<Cmbank, String> implements CmbankService {

	private CmbankDao cmbankDao;

	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写 */
	public void setCmbankDao(CmbankDao dao) {
		this.cmbankDao = dao;
	}

	public EntityDao getEntityDao() {
		return this.cmbankDao;
	}

	public List<Cmbank> searchCmbank(CmbankQuery cmbank, CountOrder countOrder) {
		return cmbankDao.searchCmbank(cmbank, countOrder);
	}

	public Long countCmbank(CmbankQuery cmbank) {
		return cmbankDao.countCmbank(cmbank);
	}

	public List searchCmbank() {
		return cmbankDao.searchCmbank();
	}

	public List<Cmbank> getBrchCode(String orderby, String ordertype) {
		return cmbankDao.getBrchCode(orderby, ordertype);
	}
}
