package com.opms.osgi.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.osgi.po.OpmsHostCfg;
import com.opms.osgi.vo.OpmsHostCfgQuery;

public interface OpmsHostCfgService extends BaseService<OpmsHostCfg,String>{

    List<OpmsHostCfg> searchOpmsHostCfg(OpmsHostCfgQuery opmsHostCfg,CountOrder countOrder);

    Long countOpmsHostCfg(OpmsHostCfgQuery opmsHostCfg);

}