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
import com.opms.osgi.po.OpmsHostCfg;
import com.opms.osgi.vo.OpmsHostCfgQuery;

@Repository
public class OpmsHostCfgDao extends BaseHibernateDao<OpmsHostCfg,String>{

	public Class getEntityClass() {
		return OpmsHostCfg.class;
	}
	
	public List searchOpmsHostCfg(final OpmsHostCfgQuery opmsHostCfg,final CountOrder countOrder){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(opmsHostCfg.getHostName())) {
              criteria.add(Restrictions.like("hostName", "%"+opmsHostCfg.getHostName()+"%"));
        }
        if(isNotEmpty(opmsHostCfg.getHostIp())) {
              criteria.add(Restrictions.like("hostIp", "%"+opmsHostCfg.getHostIp()+"%"));
        }
        if(isNotEmpty(opmsHostCfg.getState())) {
              criteria.add(Restrictions.like("state", "%"+opmsHostCfg.getState()+"%"));
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

	public Long countOpmsHostCfg(final OpmsHostCfgQuery opmsHostCfg){
        return (Long)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(opmsHostCfg.getHostName())) {
              criteria.add(Restrictions.like("hostName", "%"+opmsHostCfg.getHostName()+"%"));
        }
        if(isNotEmpty(opmsHostCfg.getHostIp())) {
              criteria.add(Restrictions.like("hostIp", "%"+opmsHostCfg.getHostIp()+"%"));
        }
        if(isNotEmpty(opmsHostCfg.getState())) {
              criteria.add(Restrictions.like("state", "%"+opmsHostCfg.getState()+"%"));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }


}