package com.opms.batch.dao;

import com.common.base.BaseHibernateDao;
import com.common.util.CountOrder;
import com.opms.batch.vo.BatchJobgrpExecutionQuery;

import net.neptune.batch.dao.batchjobgrpexecution.BatchJobgrpExecution;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

@Repository
public class BatchJobgrpExecutionDao extends BaseHibernateDao<BatchJobgrpExecution,BigDecimal>{

	public Class getEntityClass() {
		return BatchJobgrpExecution.class;
	}
	
	public List searchBatchJobgrpExecution(final BatchJobgrpExecutionQuery batchJobgrpExecution,final CountOrder countOrder){
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
//                Criteria criteria = session.createCriteria(getEntityClass());
//        if(isNotEmpty(batchJobgrpExecution.getName())) {
//              criteria.add(Restrictions.like("name", "%"+batchJobgrpExecution.getName()+"%"));
//        }
//        if(isNotEmpty(batchJobgrpExecution.getBatchNo())) {
//              criteria.add(Restrictions.like("batchNo", "%"+batchJobgrpExecution.getBatchNo()+"%"));
//        }
//        if(isNotEmpty(batchJobgrpExecution.getStartTimeBegin())) {
//            criteria.add(Restrictions.ge("startTime", batchJobgrpExecution.getStartTimeBegin()));
//        }
//        if(isNotEmpty(batchJobgrpExecution.getStartTimeEnd())) {
//            criteria.add(Restrictions.le("startTime", batchJobgrpExecution.getStartTimeEnd()));
//        }
//        if(isNotEmpty(batchJobgrpExecution.getEndTimeBegin())) {
//            criteria.add(Restrictions.ge("endTime", batchJobgrpExecution.getEndTimeBegin()));
//        }
//        if(isNotEmpty(batchJobgrpExecution.getEndTimeEnd())) {
//            criteria.add(Restrictions.le("endTime", batchJobgrpExecution.getEndTimeEnd()));
//        }
//        if(isNotEmpty(batchJobgrpExecution.getStatus())) {
//              criteria.add(Restrictions.like("status", "%"+batchJobgrpExecution.getStatus()+"%"));
//        }
//                if(isNotEmpty(countOrder)) {
//                   criteria.setFirstResult(countOrder.getStart());
//                   criteria.setMaxResults(countOrder.getLimit());
//                if(isNotEmpty(countOrder.getOrderby())) {
//                   if("desc".equalsIgnoreCase(countOrder.getDir()))
//                   criteria.addOrder(Order.desc(countOrder.getOrderby()));
//                    else
//                   criteria.addOrder(Order.asc(countOrder.getOrderby()));
//                }
//               }
//               return criteria.list();
               
               
               Query query=session.createQuery("from BatchJobgrpExecution");
				query.setFirstResult(1);
				query.setMaxResults(10);
				return query.list();
            }
        });
    }

	public Long countBatchJobgrpExecution(final BatchJobgrpExecutionQuery batchJobgrpExecution){
        return (Long)getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(getEntityClass());
        if(isNotEmpty(batchJobgrpExecution.getName())) {
              criteria.add(Restrictions.like("name", "%"+batchJobgrpExecution.getName()+"%"));
        }
        if(isNotEmpty(batchJobgrpExecution.getBatchNo())) {
              criteria.add(Restrictions.like("batchNo", "%"+batchJobgrpExecution.getBatchNo()+"%"));
        }
        if(isNotEmpty(batchJobgrpExecution.getStartTimeBegin())) {
            criteria.add(Restrictions.ge("startTime", batchJobgrpExecution.getStartTimeBegin()));
        }
        if(isNotEmpty(batchJobgrpExecution.getStartTimeEnd())) {
            criteria.add(Restrictions.le("startTime", batchJobgrpExecution.getStartTimeEnd()));
        }
        if(isNotEmpty(batchJobgrpExecution.getEndTimeBegin())) {
            criteria.add(Restrictions.ge("endTime", batchJobgrpExecution.getEndTimeBegin()));
        }
        if(isNotEmpty(batchJobgrpExecution.getEndTimeEnd())) {
            criteria.add(Restrictions.le("endTime", batchJobgrpExecution.getEndTimeEnd()));
        }
        if(isNotEmpty(batchJobgrpExecution.getStatus())) {
              criteria.add(Restrictions.like("status", "%"+batchJobgrpExecution.getStatus()+"%"));
        }
                criteria.setProjection(Projections.rowCount());
                return criteria.list();
            }
        }).iterator().next();
    }


}