package com.opms.conmg.dao;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

import java.util.Date;
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
import com.opms.conmg.po.Eaoperatercd;
import com.opms.conmg.vo.EaoperatercdQuery;

@Repository
public class EaoperatercdDao extends BaseHibernateDao<Eaoperatercd, String> {

	public Class getEntityClass() {
		return Eaoperatercd.class;
	}

	public List searchEaoperatercd(final EaoperatercdQuery eaoperatercd,
			final CountOrder countOrder) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@SuppressWarnings("deprecation")
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (isNotEmpty(eaoperatercd.getOperatedateBegin())) {
					criteria.add(Restrictions.ge("operatedate", eaoperatercd
							.getOperatedateBegin()));
				}
				if (isNotEmpty(eaoperatercd.getOperatedateEnd())) {
					Date date = eaoperatercd.getOperatedateEnd();
					date.setHours(23);
					date.setMinutes(59);
					date.setSeconds(59);
					criteria.add(Restrictions.le("operatedate", date));
				}
				if (isNotEmpty(eaoperatercd.getOperatetype())) {
					criteria.add(Restrictions.eq("operatetype",eaoperatercd.getOperatetype()));
				}
				if (isNotEmpty(countOrder)) {
					criteria.setFirstResult(countOrder.getStart());
					criteria.setMaxResults(countOrder.getLimit());
					if (isNotEmpty(countOrder.getOrderby())) {
						if ("desc".equalsIgnoreCase(countOrder.getDir()))
							criteria.addOrder(Order.desc(countOrder
									.getOrderby()));
						else
							criteria.addOrder(Order
									.asc(countOrder.getOrderby()));
					}
				}
				criteria.addOrder(Order.desc("operatedate"));
				return criteria.list();
			}
		});
	}

	public Long countEaoperatercd(final EaoperatercdQuery eaoperatercd) {
		return (Long) getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = session
								.createCriteria(getEntityClass());
						if (isNotEmpty(eaoperatercd.getOperatedateBegin())) {
							criteria.add(Restrictions.ge("operatedate",
									eaoperatercd.getOperatedateBegin()));
						}
						if (isNotEmpty(eaoperatercd.getOperatedateEnd())) {
							criteria.add(Restrictions.le("operatedate",
									eaoperatercd.getOperatedateEnd()));
						}
						if (isNotEmpty(eaoperatercd.getOperatetype())) {
							criteria.add(Restrictions.eq("operatetype",eaoperatercd.getOperatetype()));
						}
						criteria.setProjection(Projections.rowCount());
						return criteria.list();
					}
				}).iterator().next();
	}

}