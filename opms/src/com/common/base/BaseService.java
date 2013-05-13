package com.common.base;

import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-22
 * Time: 下午6:07
 * desc:
 */
public interface BaseService<E, PK extends Serializable> {

    public E getById(PK id) throws DataAccessException;

    public List<E> findAll() throws DataAccessException;

    /**
     * 根据id检查是否插入或是更新数据
     */
    public void saveOrUpdate(E entity) throws DataAccessException;

    /**
     * 插入数据
     */
    public void save(E entity) throws DataAccessException;

    public void removeById(PK id) throws DataAccessException;

    void update(E entity) throws DataAccessException;

    boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException;

    String isUnique(E entity, String uniquePropertyCodes,String uniquePropertyNames) throws DataAccessException;

}
