package com.opms.osgi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.common.base.BaseServiceImpl;
import com.common.base.EntityDao;
import com.common.util.CountOrder;
import com.opms.osgi.dao.OpmsBundleCfgDao;
import com.opms.osgi.po.OpmsBundleCfg;
import com.opms.osgi.service.OpmsBundleCfgService;
import com.opms.osgi.vo.OpmsBundleCfgQuery;

@Service("opmsBundleCfgService")
public class OpmsBundleCfgServiceImpl extends BaseServiceImpl<OpmsBundleCfg,String> implements OpmsBundleCfgService{

	private OpmsBundleCfgDao opmsBundleCfgDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setOpmsBundleCfgDao(OpmsBundleCfgDao dao) {
		this.opmsBundleCfgDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.opmsBundleCfgDao;
	}

	public OpmsBundleCfg searchOpmsBundleCfg(String systemId, String bundleName) {
		return opmsBundleCfgDao.searchOpmsBundleCfg(systemId, bundleName);
	}

	public List<OpmsBundleCfg> searchOpmsBundleCfg(OpmsBundleCfgQuery opmsBundleCfg,CountOrder countOrder){
        return opmsBundleCfgDao.searchOpmsBundleCfg(opmsBundleCfg,countOrder);
    }

    public Long countOpmsBundleCfg(OpmsBundleCfgQuery opmsBundleCfg){
        return opmsBundleCfgDao.countOpmsBundleCfg(opmsBundleCfg);
    }

}
