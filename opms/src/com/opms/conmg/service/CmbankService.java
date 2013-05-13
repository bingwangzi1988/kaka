package com.opms.conmg.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.conmg.po.Cmbank;
import com.opms.conmg.vo.CmbankQuery;

public interface CmbankService extends BaseService<Cmbank, String> {

	List<Cmbank> searchCmbank(CmbankQuery cmbank, CountOrder countOrder);

	Long countCmbank(CmbankQuery cmbank);
	
	public List searchCmbank();

	public List<Cmbank> getBrchCode(String orderby, String ordertype);
}