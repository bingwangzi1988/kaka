package com.opms.conmg.service;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.conmg.po.OpmsHostCfg;
import com.opms.conmg.vo.OpmsHostCfgQuery;

import java.util.List;

public interface OpmsHostCfgService extends BaseService<OpmsHostCfg,String>{

    List<OpmsHostCfg> searchOpmsHostCfg(OpmsHostCfgQuery opmsHostCfg,CountOrder countOrder);

    Long countOpmsHostCfg(OpmsHostCfgQuery opmsHostCfg);

}