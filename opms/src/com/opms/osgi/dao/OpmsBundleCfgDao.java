package com.opms.osgi.dao;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.common.base.BaseHibernateDao;
import com.common.util.CountOrder;
import com.opms.osgi.po.OpmsBundleCfg;
import com.opms.osgi.vo.OpmsBundleCfgQuery;

@Repository
public class OpmsBundleCfgDao extends BaseHibernateDao<OpmsBundleCfg,String>{

	public Class getEntityClass() {
		return OpmsBundleCfg.class;
	}
	
	public OpmsBundleCfg searchOpmsBundleCfg(final String systemId, final String bundleName){
		return getHibernateTemplate().execute(new HibernateCallback<OpmsBundleCfg>() {
            public OpmsBundleCfg doInHibernate(Session session) throws HibernateException {
            	return (OpmsBundleCfg) session.createQuery("FROM OpmsBundleCfg t WHERE t.systemId=? and t.bundleName = ?")
            								  .setString(0, systemId)
            								  .setString(1, bundleName)
            								  .uniqueResult();
            }
        });
    }
	
	public List searchOpmsBundleCfg(final OpmsBundleCfgQuery opmsBundleCfg,final CountOrder countOrder){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(opmsBundleCfg.getBundleName())) {
              criteria.add(Restrictions.like("bundleName", "%"+opmsBundleCfg.getBundleName()+"%"));
        }
        if(isNotEmpty(opmsBundleCfg.getBundleType())) {
              criteria.add(Restrictions.like("bundleType", "%"+opmsBundleCfg.getBundleType()+"%"));
        }
        if(isNotEmpty(opmsBundleCfg.getSystemId())) {
              criteria.add(Restrictions.like("systemId", "%"+opmsBundleCfg.getSystemId()+"%"));
        }
                if(isNotEmpty(countOrder)) {
                   criteria.setFirstResult(countOrder.getStart());
                   criteria.setMaxResults(countOrder.getLimit());
                if(isNotEmpty(countOrder.getOrderby())) {
                   if("desc".equalsIgnoreCase(countOrder.getDir()))
                   criteria.addOrder(Order.desc(countOrder.getOrderby()));
                    else
                   criteria.addOrder(Order.asc(countOrder.getOrderby()));
                }
               }
               return criteria.list();
            }
        });
    }

	public Long countOpmsBundleCfg(final OpmsBundleCfgQuery opmsBundleCfg){
        return (Long)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(opmsBundleCfg.getBundleName())) {
              criteria.add(Restrictions.like("bundleName", "%"+opmsBundleCfg.getBundleName()+"%"));
        }
        if(isNotEmpty(opmsBundleCfg.getBundleType())) {
              criteria.add(Restrictions.like("bundleType", "%"+opmsBundleCfg.getBundleType()+"%"));
        }
        if(isNotEmpty(opmsBundleCfg.getSystemId())) {
              criteria.add(Restrictions.like("systemId", "%"+opmsBundleCfg.getSystemId()+"%"));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }


}