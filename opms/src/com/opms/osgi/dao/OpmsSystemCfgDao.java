package com.opms.osgi.dao;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.common.base.BaseHibernateDao;
import com.common.util.CountOrder;
import com.opms.osgi.po.OpmsSystemCfg;
import com.opms.osgi.vo.OpmsSystemCfgQuery;

@Repository
public class OpmsSystemCfgDao extends BaseHibernateDao<OpmsSystemCfg,String>{

	public Class getEntityClass() {
		return OpmsSystemCfg.class;
	}
	
	public List searchOpmsSystemId(){
		return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
               Criteria criteria = session.createCriteria(getEntityClass());
               ProjectionList projectionList = Projections.projectionList();  
               projectionList.add(Projections.property("systemId"));
               criteria.setProjection(Projections.distinct(projectionList));
               return criteria.list();
            }
        });
	}
	
	public List searchOpmsSystemCfg(final OpmsSystemCfgQuery opmsSystemCfg,final CountOrder countOrder){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(opmsSystemCfg.getHostIp())) {
              criteria.add(Restrictions.like("hostIp", "%"+opmsSystemCfg.getHostIp()+"%"));
        }
        if(isNotEmpty(opmsSystemCfg.getHostPort())) {
            criteria.add(Restrictions.eq("hostPort", opmsSystemCfg.getHostPort()));
        }
        if(isNotEmpty(opmsSystemCfg.getSystemId())) {
              criteria.add(Restrictions.like("systemId", "%"+opmsSystemCfg.getSystemId()+"%"));
        }
        if(isNotEmpty(opmsSystemCfg.getState())) {
              criteria.add(Restrictions.like("state", "%"+opmsSystemCfg.getState()+"%"));
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

	public Long countOpmsSystemCfg(final OpmsSystemCfgQuery opmsSystemCfg){
        return (Long)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(opmsSystemCfg.getHostIp())) {
              criteria.add(Restrictions.like("hostIp", "%"+opmsSystemCfg.getHostIp()+"%"));
        }
        if(isNotEmpty(opmsSystemCfg.getHostPort())) {
            criteria.add(Restrictions.eq("hostPort", opmsSystemCfg.getHostPort()));
        }
        if(isNotEmpty(opmsSystemCfg.getSystemId())) {
              criteria.add(Restrictions.like("systemId", "%"+opmsSystemCfg.getSystemId()+"%"));
        }
        if(isNotEmpty(opmsSystemCfg.getState())) {
              criteria.add(Restrictions.like("state", "%"+opmsSystemCfg.getState()+"%"));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }


}