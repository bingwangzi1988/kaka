package com.opms.conmg.dao;

import com.common.base.BaseHibernateDao;
import com.common.util.CountOrder;
import com.opms.conmg.po.OpmsHostCfg;
import com.opms.conmg.vo.OpmsHostCfgQuery;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

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
        if(isNotEmpty(opmsHostCfg.getHostPort())) {
            criteria.add(Restrictions.eq("hostPort", opmsHostCfg.getHostPort()));
        }
        if(isNotEmpty(opmsHostCfg.getCfgType())) {
            criteria.add(Restrictions.eq("cfgType", opmsHostCfg.getCfgType()));
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
        if(isNotEmpty(opmsHostCfg.getHostPort())) {
            criteria.add(Restrictions.eq("hostPort", opmsHostCfg.getHostPort()));
        }
        if(isNotEmpty(opmsHostCfg.getCfgType())) {
            criteria.add(Restrictions.eq("cfgType", opmsHostCfg.getCfgType()));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }


}