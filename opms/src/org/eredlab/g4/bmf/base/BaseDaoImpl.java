package org.eredlab.g4.bmf.base;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.PKey;
import org.eredlab.g4.ccl.datastructure.impl.BasePo;
import org.eredlab.g4.ccl.properties.PropertiesFactory;
import org.eredlab.g4.ccl.properties.PropertiesFile;
import org.eredlab.g4.ccl.properties.PropertiesHelper;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * ���ݷ���ʵ����(��װ��)<br>
 * ����Spring��iBatis��֧�ֻ���ʵ��,֧��ʵ���������ݲ���
 * <b>Ϊ�˼�Dao����Ŀ���,�Ѿ��������ַ�װ��ʽ�����Ƽ�ʹ�á�<br>
 * �Ժ��Ƽ�ʹ��
 * 
 * @author XiongChun
 * @since 2009-07-21
 * @see org.springframework.orm.ibatis.support.SqlMapClientDaoSupport
 */
public class BaseDaoImpl extends SqlMapClientDaoSupport implements BaseDao {
	Log log = LogFactory.getLog(BaseDaoImpl.class);
	
	protected static PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);

	/**
	 * �������ļ�dao.xml�������Ĳ���
	 */
	private String domainName;

	/**
	 * ��������ɾ��һ����¼
	 * 
	 * @param PKey
	 *            ����
	 */
	public void insertDomain(BasePo domain) {
		super.getSqlMapClientTemplate().insert("insert" + getDomainName(), domain);
	}

	/**
	 * ����һ����¼
	 * 
	 * @param domain
	 *            Ҫ�����ʵ�����
	 */
	public void deleteDomainByKey(PKey key) {
		// �Էǿմ��ζ�����зǿ�У��
		key.validateNullAble();
		super.getSqlMapClientTemplate().delete("delete" + getDomainName() + "ByKey", key);
	}

	/**
	 * ����������ѯһ������
	 * 
	 * @param PKey
	 *            ����
	 * @return Object ���ص�ʵ�����
	 */
	public Object queryDomainByKey(PKey key) {
		// �Էǿմ��ζ�����зǿ�У��
		key.validateNullAble();
		return super.getSqlMapClientTemplate().queryForObject("query" + getDomainName() + "ByKey", key);
	}

	/**
	 * ����Dto��ѯ����
	 * 
	 * @param dto
	 *            �����Dto��ѯ����Dto����
	 * @return List ���ص�List��¼��
	 */
	public List queryDomainsByDto(Dto dto) {
		List lst = super.getSqlMapClientTemplate().queryForList("query" + getDomainName() + "sByDto", dto);
		return lst;
	}
	
	/**
	 * ����ҳ��ѯ
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ��ѯ��������(map javaBean)
	 */
	public List queryForPage(String statementName, Dto qDto) {
		return super.getSqlMapClientTemplate().queryForList(statementName, qDto, qDto.getAsInteger("start").intValue(),
				qDto.getAsInteger("end").intValue());
	}

	/**
	 * ����Key����һ����¼
	 * 
	 * @param domain
	 *            ʵ���������
	 */
	public void updateDomainByKey(BasePo domain) {
		PKey key = domain.getPk();
		// �Էǿմ��ζ�����зǿ�У��
		key.validateNullAble();
		super.getSqlMapClientTemplate().update("update" + getDomainName() + "ByKey", domain);
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
}
