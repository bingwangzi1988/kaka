package com.opms.batch.dao;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

import java.math.BigDecimal;
import java.util.List;

import net.neptune.batch.dao.batchstepexecution.BatchStepExecution;

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
import com.opms.batch.vo.BatchStepExecutionQuery;

@Repository
public class BatchStepExecutionDao extends BaseHibernateDao<BatchStepExecution,BigDecimal>{

	public Class getEntityClass() {
		return BatchStepExecution.class;
	}
	
	public List searchBatchStepExecution(final BatchStepExecutionQuery batchStepExecution,final CountOrder countOrder){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(batchStepExecution.getName())) {
              criteria.add(Restrictions.like("name", "%"+batchStepExecution.getName()+"%"));
        }
        if(isNotEmpty(batchStepExecution.getStartTimeBegin())) {
            criteria.add(Restrictions.ge("startTime", batchStepExecution.getStartTimeBegin()));
        }
        if(isNotEmpty(batchStepExecution.getStartTimeEnd())) {
            criteria.add(Restrictions.le("startTime", batchStepExecution.getStartTimeEnd()));
        }
        if(isNotEmpty(batchStepExecution.getEndTimeBegin())) {
            criteria.add(Restrictions.ge("endTime", batchStepExecution.getEndTimeBegin()));
        }
        if(isNotEmpty(batchStepExecution.getEndTimeEnd())) {
            criteria.add(Restrictions.le("endTime", batchStepExecution.getEndTimeEnd()));
        }
        if(isNotEmpty(batchStepExecution.getStatus())) {
              criteria.add(Restrictions.like("status", "%"+batchStepExecution.getStatus()+"%"));
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

	public Long countBatchStepExecution(final BatchStepExecutionQuery batchStepExecution){
        return (Long)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(batchStepExecution.getName())) {
              criteria.add(Restrictions.like("name", "%"+batchStepExecution.getName()+"%"));
        }
        if(isNotEmpty(batchStepExecution.getStartTimeBegin())) {
            criteria.add(Restrictions.ge("startTime", batchStepExecution.getStartTimeBegin()));
        }
        if(isNotEmpty(batchStepExecution.getStartTimeEnd())) {
            criteria.add(Restrictions.le("startTime", batchStepExecution.getStartTimeEnd()));
        }
        if(isNotEmpty(batchStepExecution.getEndTimeBegin())) {
            criteria.add(Restrictions.ge("endTime", batchStepExecution.getEndTimeBegin()));
        }
        if(isNotEmpty(batchStepExecution.getEndTimeEnd())) {
            criteria.add(Restrictions.le("endTime", batchStepExecution.getEndTimeEnd()));
        }
        if(isNotEmpty(batchStepExecution.getStatus())) {
              criteria.add(Restrictions.like("status", "%"+batchStepExecution.getStatus()+"%"));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }

	public List<com.opms.batch.po.BatchStepExecution> searchBatchStepExecution(
			final String nodeid) {
		 return (List<com.opms.batch.po.BatchStepExecution>)getHibernateTemplate().executeFind(new HibernateCallback() {
	            public List<com.opms.batch.po.BatchStepExecution> doInHibernate(Session session) throws HibernateException {
	            	Query query=session.createQuery("from BatchStepExecution where jobId="+nodeid);
	            	return query.list();
	            }
		 });
	}


}