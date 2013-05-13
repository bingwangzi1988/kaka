package com.opms.osgi.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.osgi.po.OpmsSystemCfg;
import com.opms.osgi.vo.OpmsSystemCfgQuery;

public interface OpmsSystemCfgService extends BaseService<OpmsSystemCfg,String>{

    List<OpmsSystemCfg> searchOpmsSystemCfg(OpmsSystemCfgQuery opmsSystemCfg,CountOrder countOrder);

    List<String> searchOpmsSystemId();
    
    Long countOpmsSystemCfg(OpmsSystemCfgQuery opmsSystemCfg);

}