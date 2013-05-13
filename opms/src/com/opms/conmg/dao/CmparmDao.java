package com.opms.conmg.dao;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.common.base.BaseHibernateDao;
import com.common.util.CountOrder;
import com.opms.conmg.po.Cmparm;
import com.opms.conmg.vo.CmparmQuery;

@Repository
public class CmparmDao extends BaseHibernateDao<Cmparm,String>{

	public Class getEntityClass() {
		return Cmparm.class;
	}
	
	public List searchCmparm(final CmparmQuery cmparm,final CountOrder countOrder,final boolean isRole){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(cmparm.getParmId())) {
              criteria.add(Restrictions.ilike("parmId", "%"+cmparm.getParmId()+"%"));
        }
        if(isNotEmpty(cmparm.getParmFlag())) {
            criteria.add(Restrictions.eq("parmFlag", cmparm.getParmFlag()));
        }
        if(isNotEmpty(cmparm.getParmName())) {
              criteria.add(Restrictions.like("parmName", "%"+cmparm.getParmName()+"%"));
        }
        if(isRole){
        	criteria.add(Restrictions.ne("parmId", "PwdDay"));
        }
        if(isNotEmpty(cmparm.getMemo())) {
            criteria.add(Restrictions.eq("memo", cmparm.getMemo()));
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

	public Long countCmparm(final CmparmQuery cmparm,final boolean isRole){
        return (Long)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(cmparm.getParmId())) {
              criteria.add(Restrictions.like("parmId", "%"+cmparm.getParmId()+"%"));
        }
        if(isNotEmpty(cmparm.getParmFlag())) {
            criteria.add(Restrictions.eq("parmFlag", cmparm.getParmFlag()));
        }
        if(isNotEmpty(cmparm.getParmName())) {
              criteria.add(Restrictions.like("parmName", "%"+cmparm.getParmName()+"%"));
        }
        if(isRole){
        	criteria.add(Restrictions.ne("parmId", "PwdDay"));
        }
        if(isNotEmpty(cmparm.getMemo())) {
            criteria.add(Restrictions.eq("memo", cmparm.getMemo()));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }

	@SuppressWarnings("unchecked")
	public List getCardTxnParm(final String parmFlag){
//		return getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException {
//				 List<Object[]> list = session.createQuery("select parmId , parmValue from Cmparm where parmFlag = :flag" )
//								.setString("flag", parmFlag)
//								.list();
//				return list;
//			}
//		});
		
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                StringBuffer hql = new StringBuffer("select parm_Id , parm_Value from Cmparm where parm_Flag = :flag");
                Query query = session.createSQLQuery(hql.toString()).setString("flag", parmFlag);
              
                return query.list();
            }
        });
        return list;
	}
	
	public Cmparm getCmparmByParmId(final String parmId){
		return getHibernateTemplate().execute(new HibernateCallback<Cmparm>() {
			public Cmparm doInHibernate(Session session)
					throws HibernateException, SQLException {
				 Criteria criteria = session.createCriteria(getEntityClass());
				 criteria.add(Restrictions.eq("parmId", parmId));
				return (Cmparm) criteria.uniqueResult();
			}
		});
	}

}