package com.opms.conmg.dao;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.common.base.BaseHibernateDao;
import com.common.util.CountOrder;
import com.opms.conmg.po.Eaoperaterole;
import com.opms.conmg.vo.EaoperateroleQuery;

@Repository
public class EaoperateroleDao extends BaseHibernateDao<Eaoperaterole, String> {

	public Class getEntityClass() {
		return Eaoperaterole.class;
	}

	public List searchEaoperaterole(final EaoperateroleQuery eaoperaterole,
			final CountOrder countOrder,final boolean isRole,final List<String> menulst) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (isNotEmpty(eaoperaterole.getTablename())) {
					criteria.add(Restrictions.eq("tablename", eaoperaterole
							.getTablename()));
				}
				criteria.add(Restrictions.in("tablename", menulst));
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
				if(!isRole){
					criteria.add(Restrictions.ne("tablename", "Eaoperaterole"));
				}
				return criteria.list();
			}
		});
	}

	public Long countEaoperaterole(final EaoperateroleQuery eaoperaterole,final boolean isRole,final List<String> menulst) {
		return (Long) getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = session
								.createCriteria(getEntityClass());
						if (isNotEmpty(eaoperaterole.getTablename())) {
							criteria.add(Restrictions.eq("tablename",
									eaoperaterole.getTablename()));
						}
						criteria.add(Restrictions.in("tablename", menulst));
						if(!isRole){
							criteria.add(Restrictions.ne("tablename", "Eaoperaterole"));
						}
						criteria.setProjection(Projections.rowCount());
						return criteria.list();
					}
				}).iterator().next();
	}

	public Eaoperaterole searchEaoperateroleByTablename(final String tableName) {
		return getHibernateTemplate().execute(
				new HibernateCallback<Eaoperaterole>() {

					public Eaoperaterole doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session
								.createCriteria(getEntityClass());
						if (isNotEmpty(tableName)) {
							criteria.add(Restrictions
									.eq("tablename", tableName));
						}
						criteria.setMaxResults(1);
						return (Eaoperaterole) criteria.uniqueResult();
					}
				});
	}
	
	public List searchUserRole(final String userid){
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				//查询当前用户能授权的菜单SQL
//				String sql="select request from eamenu where menuid in(select menuid from eauserrole where roleid=(select roleid from EAUSERAUTHORIZE where userid= ? ))";
				//查询当前用户角色能授权的菜单SQL
				String sql="select request from eamenu where menuid in(SELECT menuid  FROM EAROLEAUTHORIZE where roleid in (SELECT roleid  FROM EAUSERAUTHORIZE where userid= ? ) and authorizelevel='2')";
				SQLQuery query=session.createSQLQuery(sql);
				query.setString(0, userid);
				return query.list();
			}
		});
	}

}