package org.eredlab.g4.bmf.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.exception.G4Exception;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * ���ݶ�ȡ��<br>
 * ����iBatisʵ��,ֻ��queryȨ��,�ṩ��Action��ʹ��
 * 
 * @author XiongChun
 * @since 2009-07-23
 * @see org.springframework.orm.ibatis.support.SqlMapClientDaoSupport
 */
public class IReaderImpl extends SqlMapClientDaoSupport implements IReader {

	/**
	 * ��ѯһ����¼
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ��ѯ��������(map javaBean)
	 */
	public Object queryForObject(String statementName, Object parameterObject) {
		return super.getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
	}

	/**
	 * ��ѯһ����¼
	 * 
	 * @param SQL���ID��
	 */
	public Object queryForObject(String statementName) {
		return super.getSqlMapClientTemplate().queryForObject(statementName, new BaseDto());
	}

	/**
	 * ��ѯ��¼����
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ��ѯ��������(map javaBean)
	 */
	public List queryForList(String statementName, Object parameterObject) {
		return super.getSqlMapClientTemplate().queryForList(statementName, parameterObject);
	}

	/**
	 * ����ҳ��ѯ
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ��ѯ��������(map javaBean)
	 */
	public List queryForPage(String statementName, Dto qDto) {
		Integer start = qDto.getAsInteger("start");
		Integer end = qDto.getAsInteger("end");
		if (G4Utils.isEmpty(start) || G4Utils.isEmpty(end)) {
			try {
				throw new G4Exception(GlobalConstants.ERR_MSG_QUERYFORPAGE_STRING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getSqlMapClientTemplate().queryForList(statementName, qDto, start.intValue(), end.intValue());
	}

	/**
	 * ��ѯ��¼����
	 * 
	 * @param SQL���ID��
	 */
	public List queryForList(String statementName) {
		return super.getSqlMapClientTemplate().queryForList(statementName, new BaseDto());
	}

	/**
	 * ��ȡConnection����<br>
	 * ˵������Ȼ��Dao���Ѷ˱�¶�˻�ȡConnection����ķ�����������ֱ�ӻ�ȡConnection�������JDBC����
	 * 
	 * @return ����Connection����
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return getSqlMapClientTemplate().getDataSource().getConnection();
	}

	/**
	 * ��ȡDataSource����<br>
	 * ˵������Ȼ��Dao���Ѷ˱�¶�˻�ȡDataSource����ķ�����������ֱ�ӻ�ȡDataSource�������JDBC����
	 * 
	 * @return ����DataSource����
	 */
	public DataSource getDataSourceFromSqlMap() throws SQLException {
		return getSqlMapClientTemplate().getDataSource();
	}
}
