package org.eredlab.g4.bmf.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.exception.PrcException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * ���ݷ��ʽӿ�(ԭ��)<br>
 * ����iBatisʵ��,֧���Զ�������ݲ���
 * 
 * @author XiongChun
 * @since 2009-07-23
 * @see com.ibatis.dao.client.Dao
 */
public interface IDao {
	
	/**
	 * ����һ����¼
	 * @param SQL���ID��
	 * @param parameterObject Ҫ����Ķ���(map javaBean)
	 */
	public void insert(String statementName, Object parameterObject);
	
	/**
	 * ����һ����¼
	 * @param SQL���ID��
	 */
	public void insert(String statementName);
	
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
	public List queryForPage(String statementName, Dto qDto);
	
	/**
	 * ���¼�¼
	 * @param SQL���ID��
	 * @param parameterObject ���¶���(map javaBean)
	 */
	public int update(String statementName, Object parameterObject);
	
	/**
	 * ���¼�¼
	 * @param SQL���ID��
	 */
	public int update(String statementName);
	
	/**
	 * ɾ����¼
	 * @param SQL���ID��
	 * @param parameterObject ���¶���(map javaBean)
	 */
	public int delete(String statementName, Object parameterObject);
	
	/**
	 * ɾ����¼
	 * @param SQL���ID��
	 */
	public int delete(String statementName);
	
	/**
	 * ���ô洢����<br>
	 * �洢���̳ɹ����ر�־ȱʡ��appCode=1
	 * 
	 * @param prcName �洢����ID��
	 * @param parameterObject ��������(��Ρ�����)
	 * @throws PrcException �洢���̵����쳣
	 */
	public void callPrc(String prcName, Dto prcDto) throws PrcException;
	
	/**
	 * ���ô洢����<br>
	 * �洢���̳ɹ����ر�־�Զ��壺appCode=successFlag(�Զ���Ĵ������)
	 * 
	 * @param prcName �洢����ID��
	 * @param parameterObject ��������(��Ρ�����)
	 * @param prcName �洢���̵��óɹ����صĳɹ�����ֵ
	 * @throws PrcException �洢���̵����쳣
	 */
	public void callPrc(String prcName, Dto prcDto, String successFlag) throws PrcException;
	
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
	
	/**
	 * ��ȡSqlMapClientTemplate����<br>
	 * 
	 * @return ����SqlMapClientTemplate����
	 */
	public SqlMapClientTemplate getSqlMapClientTpl();
	
}
