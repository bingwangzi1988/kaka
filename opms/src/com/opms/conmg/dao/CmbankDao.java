package com.opms.conmg.dao;

import static org.eredlab.g4.ccl.util.G4Utils.isNotEmpty;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.common.base.BaseHibernateDao;
import com.common.util.CountOrder;
import com.opms.conmg.po.Cmbank;
import com.opms.conmg.vo.CmbankQuery;

@Repository
public class CmbankDao extends BaseHibernateDao<Cmbank, String> {

	public Class getEntityClass() {
		return Cmbank.class;
	}

	public List searchCmbank(final CmbankQuery cmbank, final CountOrder countOrder) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (isNotEmpty(cmbank.getBranch())) {
					criteria.add(Restrictions.like("branch", "%" + cmbank.getBranch() + "%"));
				}
				if (isNotEmpty(cmbank.getBranchName())) {
					criteria.add(Restrictions.like("branchName", "%" + cmbank.getBranchName() + "%"));
				}
				if (isNotEmpty(cmbank.getAttachedTo())) {
					criteria.add(Restrictions.like("attachedTo", "%" + cmbank.getAttachedTo() + "%"));
				}
				if (isNotEmpty(countOrder)) {
					criteria.setFirstResult(countOrder.getStart());
					criteria.setMaxResults(countOrder.getLimit());
					if (isNotEmpty(countOrder.getOrderby())) {
						if ("desc".equalsIgnoreCase(countOrder.getDir()))
							criteria.addOrder(Order.desc(countOrder.getOrderby()));
						else
							criteria.addOrder(Order.asc(countOrder.getOrderby()));
					}
				}
				return criteria.list();
			}
		});
	}

	public Long countCmbank(final CmbankQuery cmbank) {
		return (Long) getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(getEntityClass());
				if (isNotEmpty(cmbank.getBranch())) {
					criteria.add(Restrictions.like("branch", "%" + cmbank.getBranch() + "%"));
				}
				if (isNotEmpty(cmbank.getBranchName())) {
					criteria.add(Restrictions.like("branchName", "%" + cmbank.getBranchName() + "%"));
				}
				if (isNotEmpty(cmbank.getAttachedTo())) {
					criteria.add(Restrictions.like("attachedTo", "%" + cmbank.getAttachedTo() + "%"));
				}
				criteria.setProjection(Projections.rowCount());
				return criteria.list();
			}
		}).iterator().next();
	}

	// 查询分行信息 分行为：2位分行号+ 00
	public List searchCmbank() {
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria cr = session.createCriteria(getEntityClass());
				cr.add(Restrictions.like("branch", "00", MatchMode.END));
				return cr.list();
			}
		});
	}

	/**
	 * 查询总行,分行信息
	 * 
	 * @parm orderby 需要排序的字段
	 * @parm ordertype 排序的方式如：asc，desc
	 * @return List<Cmbank>
	 */
	@SuppressWarnings("unchecked")
	public List<Cmbank> getBrchCode(final String orderby, final String ordertype) {
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Cmbank>>() {
			public List<Cmbank> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Cmbank where hierarchyCode='2' or hierarchyCode='5' or hierarchy_code is null";
				if (ordertype != null && ordertype.trim().length() > 0) {
					hql += " order by " + orderby + " " + ordertype;
				}
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}

}