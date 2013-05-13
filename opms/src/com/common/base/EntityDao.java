package com.common.base;

import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;

public interface EntityDao<E, PK extends Serializable> {

    public Object getById(PK id) throws DataAccessException;

    public void deleteById(PK id) throws DataAccessException;

    /**
     * ��������
     */
    public void save(E entity) throws DataAccessException;

    /**
     * ��������
     */
    public void update(E entity) throws DataAccessException;

    /**
     * ����id����Ƿ������Ǹ�������
     */
    public void saveOrUpdate(E entity) throws DataAccessException;

    public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException;

    /**
     * ����hibernate.flush() ��Щdaoʵ�ֲ���Ҫʵ�ִ���
     */
    public void flush() throws DataAccessException;

    public List<E> findAll() throws DataAccessException;

}
