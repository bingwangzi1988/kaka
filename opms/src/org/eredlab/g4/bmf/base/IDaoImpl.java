package org.eredlab.g4.bmf.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.exception.G4Exception;
import org.eredlab.g4.ccl.exception.PrcException;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * ���ݷ���ʵ����(ԭ��)<br>
 * ����Spring��iBatis��֧�ֻ���ʵ��,֧���Զ�������ݲ���
 * 
 * @author XiongChun
 * @since 2009-07-23
 * @see org.springframework.orm.ibatis.support.SqlMapClientDaoSupport
 */
public class IDaoImpl extends SqlMapClientDaoSupport implements IDao {

	/**
	 * ����һ����¼
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            Ҫ����Ķ���(map javaBean)
	 */
	public void insert(String statementName, Object parameterObject) {
		getSqlMapClientTemplate().insert(statementName, parameterObject);
	}

	/**
	 * ����һ����¼
	 * 
	 * @param SQL���ID��
	 */
	public void insert(String statementName) {
		getSqlMapClientTemplate().insert(statementName, new BaseDto());
	}

	/**
	 * ��ѯһ����¼
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ��ѯ��������(map javaBean)
	 */
	public Object queryForObject(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
	}

	/**
	 * ��ѯһ����¼
	 * 
	 * @param SQL���ID��
	 */
	public Object queryForObject(String statementName) {
		return getSqlMapClientTemplate().queryForObject(statementName, new BaseDto());
	}

	/**
	 * ��ѯ��¼����
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ��ѯ��������(map javaBean)
	 */
	public List queryForList(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().queryForList(statementName, parameterObject);
	}

	/**
	 * ��ѯ��¼����
	 * 
	 * @param SQL���ID��
	 */
	public List queryForList(String statementName) {
		return getSqlMapClientTemplate().queryForList(statementName, new BaseDto());
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
	 * ���¼�¼
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ���¶���(map javaBean)
	 */
	public int update(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().update(statementName, parameterObject);
	}

	/**
	 * ���¼�¼
	 * 
	 * @param SQL���ID��
	 */
	public int update(String statementName) {
		return getSqlMapClientTemplate().update(statementName, new BaseDto());
	}

	/**
	 * ɾ����¼
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ���¶���(map javaBean)
	 */
	public int delete(String statementName, Object parameterObject) {
		return getSqlMapClientTemplate().delete(statementName, parameterObject);
	}

	/**
	 * ɾ����¼
	 * 
	 * @param SQL���ID��
	 */
	public int delete(String statementName) {
		return getSqlMapClientTemplate().delete(statementName, new BaseDto());
	}

	/**
	 * ���ô洢����<br>
	 * �洢���̳ɹ����ر�־ȱʡ��appCode=1
	 * 
	 * @param prcName
	 *            �洢����ID��
	 * @param parameterObject
	 *            ��������(��Ρ�����)
	 * @throws PrcException
	 *             �洢���̵����쳣
	 */
	public void callPrc(String prcName, Dto prcDto) throws PrcException {
		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);
		String callPrcSuccessFlag = pHelper.getValue("callPrcSuccessFlag", "1");
		getSqlMapClientTemplate().queryForObject(prcName, prcDto);
		if (G4Utils.isEmpty(prcDto.getAppCode()) || !prcDto.getAppCode().equals(callPrcSuccessFlag)) {
			throw new PrcException(prcName, prcDto.getAppCode(), prcDto.getErrorMsg());
		}
	}

	/**
	 * ���ô洢����<br>
	 * �洢���̳ɹ����ر�־�Զ��壺appCode=successFlag(�Զ���Ĵ������)
	 * 
	 * @param prcName
	 *            �洢����ID��
	 * @param parameterObject
	 *            ��������(��Ρ�����)
	 * @param prcName
	 *            �洢���̵��óɹ����صĳɹ�����ֵ
	 * @throws PrcException
	 *             �洢���̵����쳣
	 */
	public void callPrc(String prcName, Dto prcDto, String successFlag) throws PrcException {
		getSqlMapClientTemplate().queryForObject(prcName, prcDto);
		if (G4Utils.isEmpty(prcDto.getAppCode()) || !prcDto.getAppCode().equals(successFlag)) {
			throw new PrcException(prcName, prcDto.getAppCode(), prcDto.getErrorMsg());
		}
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

	/**
	 * ��ȡSqlMapClientTemplate����<br>
	 * 
	 * @return ����SqlMapClientTemplate����
	 */
	public SqlMapClientTemplate getSqlMapClientTpl() {
		return getSqlMapClientTemplate();
	}
}
