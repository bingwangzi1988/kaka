package com.opms.batch.dao;

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
import com.opms.batch.po.BatchJob;
import com.opms.batch.vo.BatchJobQuery;

@Repository
public class BatchJobDao extends BaseHibernateDao<BatchJob,String>{

	public Class getEntityClass() {
		return BatchJob.class;
	}
	
	public List searchBatchJob(final BatchJobQuery batchJob,final CountOrder countOrder){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(batchJob.getName())) {
              criteria.add(Restrictions.like("name", "%"+batchJob.getName()+"%"));
        }
        if(isNotEmpty(batchJob.getJobGroupId())) {
            criteria.add(Restrictions.eq("jobGroupId", batchJob.getJobGroupId()));
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

	public Long countBatchJob(final BatchJobQuery batchJob){
        return (Long)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(batchJob.getName())) {
              criteria.add(Restrictions.like("name", "%"+batchJob.getName()+"%"));
        }
        if(isNotEmpty(batchJob.getJobGroupId())) {
            criteria.add(Restrictions.eq("jobGroupId", batchJob.getJobGroupId()));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }


}