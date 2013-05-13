package com.opms.batch.dao;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

import java.math.BigDecimal;
import java.util.List;

import net.neptune.batch.dao.batchjobexecution.BatchJobExecution;

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
import com.opms.batch.vo.BatchJobExecutionQuery;

@Repository
public class BatchJobExecutionDao extends BaseHibernateDao<BatchJobExecution,BigDecimal>{

	public Class getEntityClass() {
		return BatchJobExecution.class;
	}
	
	public List searchBatchJobExecution(final BatchJobExecutionQuery batchJobExecution,final CountOrder countOrder){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(batchJobExecution.getName())) {
              criteria.add(Restrictions.like("name", "%"+batchJobExecution.getName()+"%"));
        }
        if(isNotEmpty(batchJobExecution.getStartTimeBegin())) {
            criteria.add(Restrictions.ge("startTime", batchJobExecution.getStartTimeBegin()));
        }
        if(isNotEmpty(batchJobExecution.getStartTimeEnd())) {
            criteria.add(Restrictions.le("startTime", batchJobExecution.getStartTimeEnd()));
        }
        if(isNotEmpty(batchJobExecution.getEndTimeBegin())) {
            criteria.add(Restrictions.ge("endTime", batchJobExecution.getEndTimeBegin()));
        }
        if(isNotEmpty(batchJobExecution.getEndTimeEnd())) {
            criteria.add(Restrictions.le("endTime", batchJobExecution.getEndTimeEnd()));
        }
        if(isNotEmpty(batchJobExecution.getStatus())) {
              criteria.add(Restrictions.like("status", "%"+batchJobExecution.getStatus()+"%"));
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

	public Long countBatchJobExecution(final BatchJobExecutionQuery batchJobExecution){
        return (Long)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(batchJobExecution.getName())) {
              criteria.add(Restrictions.like("name", "%"+batchJobExecution.getName()+"%"));
        }
        if(isNotEmpty(batchJobExecution.getStartTimeBegin())) {
            criteria.add(Restrictions.ge("startTime", batchJobExecution.getStartTimeBegin()));
        }
        if(isNotEmpty(batchJobExecution.getStartTimeEnd())) {
            criteria.add(Restrictions.le("startTime", batchJobExecution.getStartTimeEnd()));
        }
        if(isNotEmpty(batchJobExecution.getEndTimeBegin())) {
            criteria.add(Restrictions.ge("endTime", batchJobExecution.getEndTimeBegin()));
        }
        if(isNotEmpty(batchJobExecution.getEndTimeEnd())) {
            criteria.add(Restrictions.le("endTime", batchJobExecution.getEndTimeEnd()));
        }
        if(isNotEmpty(batchJobExecution.getStatus())) {
              criteria.add(Restrictions.like("status", "%"+batchJobExecution.getStatus()+"%"));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }

	public List<com.opms.batch.po.BatchJobExecution> searchBatchJobExecution(final String jobGrpId) {
		 return (List<com.opms.batch.po.BatchJobExecution>)getHibernateTemplate().executeFind(new HibernateCallback() {
	            public List<com.opms.batch.po.BatchJobExecution> doInHibernate(Session session) throws HibernateException {
	            	Query query=session.createQuery("from BatchJobExecution where jobGrpId="+jobGrpId);
	            	return query.list();
	            }
		 });
	}
}