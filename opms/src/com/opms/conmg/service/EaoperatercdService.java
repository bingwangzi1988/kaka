package com.opms.conmg.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.conmg.po.Eaoperatercd;
import com.opms.conmg.vo.EaoperatercdQuery;

public interface EaoperatercdService extends BaseService<Eaoperatercd,String>{

    List<Eaoperatercd> searchEaoperatercd(EaoperatercdQuery eaoperatercd,CountOrder countOrder);

    Long countEaoperatercd(EaoperatercdQuery eaoperatercd);

}