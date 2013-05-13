package com.opms.osgi.service;

import java.util.List;

import com.common.base.BaseService;
import com.common.util.CountOrder;
import com.opms.osgi.po.OpmsBundleCfg;
import com.opms.osgi.vo.OpmsBundleCfgQuery;

public interface OpmsBundleCfgService extends BaseService<OpmsBundleCfg,String>{
	
	OpmsBundleCfg searchOpmsBundleCfg(String systemId, String bundleName);
	
	List<OpmsBundleCfg> searchOpmsBundleCfg(OpmsBundleCfgQuery opmsBundleCfg,CountOrder countOrder);

    Long countOpmsBundleCfg(OpmsBundleCfgQuery opmsBundleCfg);

}