package org.eredlab.g4.bmf.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ���ݶ�ȡ��<br>
 * ����iBatisʵ��,ֻ��queryȨ��,�ṩ��Action��ʹ��
 * @author XiongChun
 * @since 2009-07-23
 * @see com.ibatis.dao.client.Dao
 */
public interface IReader {
	
	/**
	 * ��ѯһ����¼
	 * @param SQL���ID��
	 * @param parameterObject ��ѯ��������(map javaBean)
	 */
	public Object queryForObject(String statementName, Object parameterObject);
	
	/**
	 * ��ѯһ����¼
	 * @param SQL���ID��
	 */
	public Object queryForObject(String statementName);
	
	/**
	 * ��ѯ��¼����
	 * @param SQL���ID��
	 * @param parameterObject ��ѯ��������(map javaBean)
	 */
	public List queryForList(String statementName, Object parameterObject);
	
	/**
	 * ��ѯ��¼����
	 * @param SQL���ID��
	 */
	public List queryForList(String statementName);

	/**
	 * ����ҳ��ѯ
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ��ѯ��������(map javaBean)
	 */
	public List queryForPage(String statementName,  Dto qDto);
	
	/**
	 * ��ȡConnection����<br>
	 * ˵������Ȼ��Dao���Ѷ˱�¶�˻�ȡConnection����ķ�����������ֱ�ӻ�ȡConnection�������JDBC����
	 * 
	 * @return ����Connection����
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;
	
	/**
	 * ��ȡDataSource����<br>
	 * ˵������Ȼ��Dao���Ѷ˱�¶�˻�ȡDataSource����ķ�����������ֱ�ӻ�ȡDataSource�������JDBC����
	 * 
	 * @return ����DataSource����
	 * @throws SQLException
	 */
	public DataSource getDataSourceFromSqlMap() throws SQLException;
}
