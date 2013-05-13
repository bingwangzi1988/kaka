package com.opms.conmg.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.conmg.po.Cmparm;
import com.opms.conmg.vo.CmparmQuery;

public interface CmparmService extends BaseService<Cmparm,String>{

    List<Cmparm> searchCmparm(CmparmQuery cmparm,CountOrder countOrder,boolean isRole);

    Long countCmparm(CmparmQuery cmparm,boolean isRole);

	String getPwd();
	/**
	 * 
	 * @param parmFlag
	 * @return 列表数组 String[]{parmId,parmValue}
	 */
	List<Object[]> getCardTxnParm(String parmFlag);
	
	public Cmparm getCmparmByParmId(String parmId);

}