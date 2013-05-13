package com.common.base;

import org.eredlab.g4.bmf.aop.SpringBeanAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<E, PK extends Serializable> implements BaseService<E, PK> {

    protected abstract EntityDao getEntityDao();

    public E getById(PK id) throws DataAccessException {
        return (E) getEntityDao().getById(id);
    }

    public List<E> findAll() throws DataAccessException {
        return getEntityDao().findAll();
    }

    /**
     * 根据id检查是否插入或是更新数据
     */
    public void saveOrUpdate(E entity) throws DataAccessException {
        getEntityDao().saveOrUpdate(entity);
    }

    /**
     * 插入数据
     */
    public void save(E entity) throws DataAccessException {
        getEntityDao().save(entity);
    }

    public void removeById(PK id) throws DataAccessException {
        getEntityDao().deleteById(id);
    }

    public void update(E entity) throws DataAccessException {
        getEntityDao().update(entity);
    }

    public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException {
        return getEntityDao().isUnique(entity, uniquePropertyNames);
    }

    public String isUnique(E entity, String uniquePropertyCodes, String uniquePropertyNames) throws DataAccessException {
        boolean flag = isUnique(entity, uniquePropertyCodes);
        if (!flag) {
            String names = uniquePropertyNames;
            int i = uniquePropertyCodes.indexOf(",");
            if (i > 0) {
                names = "[" + names + "]组合";
            } else {
                names = "[" + names + "]";
            }
            return "违反唯一约束," + names + "不能重复.";
        } else
            return null;
    }
}
