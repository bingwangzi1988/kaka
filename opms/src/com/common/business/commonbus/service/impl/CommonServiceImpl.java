package com.common.business.commonbus.service.impl;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.business.commonbus.dao.CommonDao;
import com.common.business.commonbus.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-28
 * Time: ÏÂÎç5:19
 * desc:
 */
@Service("commonService")
public class CommonServiceImpl extends BaseServiceImpl implements CommonService {

    private CommonDao commonDao;

    public void setCommonDao(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    @Override
    protected EntityDao getEntityDao() {
        return commonDao;
    }


    public List getForeignLinks(String tableNmae, String field, String code, String codedesc, String fieldvalue,String orderby,String ordertype) {
        return commonDao.getForeignLinks(tableNmae, field, code, codedesc, fieldvalue, orderby, ordertype);
    }

	public List getRoleUser(String menuId) {
		return commonDao.getRoleUser(menuId);
	}

	public List getRoleUserByAccount(String account) {
		return commonDao.getRoleUserByAccount(account);
	}
}
