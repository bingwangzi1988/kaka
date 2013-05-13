package com.opms.conmg.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.conmg.po.Eaoperaterole;
import com.opms.conmg.vo.EaoperateroleQuery;

public interface EaoperateroleService extends BaseService<Eaoperaterole,String>{

    List<Eaoperaterole> searchEaoperaterole(EaoperateroleQuery eaoperaterole,CountOrder countOrder,boolean isRole,List<String> menulst);

    Long countEaoperaterole(EaoperateroleQuery eaoperaterole,boolean isRole,List<String> menulst);
    
    public Eaoperaterole searchEaoperateroleByTablename(String tableName);
    
    public List searchUserRole(String userid);

}