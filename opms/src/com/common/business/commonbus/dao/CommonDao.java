package com.common.business.commonbus.dao;

import com.common.base.BaseHibernateDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-28
 * Time: ÏÂÎç5:13
 * desc:
 */
@Repository
public class CommonDao extends BaseHibernateDao {
    @Override
    public Class getEntityClass() {
        return null;
    }

    public List getForeignLinks(final String tableNmae, final String field, final String code, final String codedesc, final String fieldvalue,final String orderby,final String ordertype) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                StringBuffer hql = new StringBuffer("");
                hql.append("select ").append(code).append(",").append(codedesc).append(" from ").append(tableNmae);
                if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(fieldvalue))
                    hql.append(" where ").append(field).append(" = '").append(fieldvalue).append("'");
                if (StringUtils.isNotBlank(orderby) && StringUtils.isNotBlank(ordertype))
                	hql.append(" order by ").append(orderby+" ").append(ordertype);
                Query query = session.createSQLQuery(hql.toString()).addScalar(code, StandardBasicTypes.STRING).addScalar(codedesc, StandardBasicTypes.STRING);
              
                return query.list();
            }
        });
        return list;
    }
    
    public List getRoleUser(final String menuId) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                String sql="select account,username from eauser where userid in(select userid from EAUSERAUTHORIZE where roleid in(select roleid from eauserrole ";
                if(menuId!=null && menuId.trim().length()>0)
                {
                	sql+=" where menuid='"+menuId+"' ";
                }
                sql+=" group by roleid))and locked='0' order by account";
                Query query = session.createSQLQuery(sql);
                return query.list();
            }
        });
        return list;
    }
    
    public List getRoleUserByAccount(final String account) {
        List list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                String sql="select account,username from eauser where account like '%"+account+"%'";
                Query query = session.createSQLQuery(sql);
                return query.list();
            }
        });
        return list;
    }
}
