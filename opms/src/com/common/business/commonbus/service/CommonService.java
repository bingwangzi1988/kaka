package com.common.business.commonbus.service;

import com.common.base.BaseService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-28
 * Time: обнГ5:18
 * desc:
 */
public interface CommonService extends BaseService {

    List getForeignLinks(String tableNmae, String field, String code, String codedesc, String fieldvalue,String orderby,String ordertype);
    
    public List getRoleUser(String menuId);
    
    public List getRoleUserByAccount(String account);

}
