package com.common.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.eredlab.g4.bmf.aop.SpringBeanAspect;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public abstract class BaseHibernateDao<E, PK extends Serializable> extends HibernateDaoSupport implements EntityDao<E, PK> {
    /**
     * Logger for subclass
     */
	private static Logger log = LoggerFactory.getLogger(BaseHibernateDao.class);

    public long queryForLong(final String queryString) {
        return queryForLong(queryString, new Object[]{});
    }

    public long queryForLong(final String queryString, Object value) {
        return queryForLong(queryString, new Object[]{value});
    }

    public long queryForLong(final String queryString, Object[] values) {
        return DataAccessUtils.longResult(getHibernateTemplate().find(queryString, values));
    }

    public void save(E entity) {
        getHibernateTemplate().save(entity);
    }

    public List<E> findAll() {
        return getHibernateTemplate().loadAll(getEntityClass());
    }

    public E getById(PK id) {
        return (E) getHibernateTemplate().get(getEntityClass(), id);
    }

    public void delete(Object entity) {
        getHibernateTemplate().delete(entity);
    }

    public void delete(Serializable entity) {
        getHibernateTemplate().delete(entity);
    }

    public void deleteById(PK id) {
        Object entity = getById(id);
        if (entity == null) {
            throw new ObjectRetrievalFailureException(getEntityClass(), id);
        }
        getHibernateTemplate().delete(entity);
    }

    public void update(E entity) {
        getHibernateTemplate().update(entity);
    }

    public void saveOrUpdate(E entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void refresh(Object entity) {
        getHibernateTemplate().refresh(entity);
    }

    public void flush() {
        getHibernateTemplate().flush();
    }

    public void evict(Object entity) {
        getHibernateTemplate().evict(entity);
    }

    public void saveAll(Collection<E> entities) {
        for (Iterator<E> it = entities.iterator(); it.hasNext(); ) {
            save(it.next());
        }
    }

    public void deleteAll(Collection entities) {
        getHibernateTemplate().deleteAll(entities);
    }

    public E findByProperty(final String propertyName, final Object value) {

        return (E) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createCriteria(getEntityClass())
                        .add(Restrictions.eq(propertyName, value))
                        .uniqueResult();
            }
        });
    }

    public List<E> findAllBy(final String propertyName, final Object value) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createCriteria(getEntityClass())
                        .add(Restrictions.eq(propertyName, value))
                        .list();
            }
        });
    }

    /**
     * 判断对象某些属性的值在数据库中是否唯一.
     *
     * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
     */
    public boolean isUnique(E entity, String uniquePropertyNames) {
        Assert.hasText(uniquePropertyNames);
        Criteria criteria = getSession().createCriteria(getEntityClass()).setProjection(Projections.rowCount());
        String[] nameList = uniquePropertyNames.split(",");
        try {
            // 循环加入唯一列
            for (int i = 0; i < nameList.length; i++) {
                criteria.add(Restrictions.eq(nameList[i], PropertyUtils.getProperty(entity, nameList[i])));
            }

            // 以下代码为了如果是update的情况,排除entity自身.

            String idName = getSessionFactory().getClassMetadata(entity.getClass()).getIdentifierPropertyName();
            if (idName != null) {
                // 取得entity的主键值
                Serializable id = (Serializable) PropertyUtils.getProperty(entity, idName);

                // 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
                if (id != null)
                    criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
            }
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }
        return ((Number) criteria.uniqueResult()).intValue() == 0;
    }

    public abstract Class getEntityClass();

}
