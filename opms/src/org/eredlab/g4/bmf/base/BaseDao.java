package org.eredlab.g4.bmf.base;

import java.util.List;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.PKey;
import org.eredlab.g4.ccl.datastructure.impl.BasePo;

/**
 * ���ݷ��ʽӿ�(��װ��)<br>
 * ����iBatisʵ��,֧��ʵ���������ݲ���
 * <b>Ϊ�˼�Dao����Ŀ���,�Ѿ��������ַ�װ��ʽ�����Ƽ�ʹ�á�<br>
 * �Ժ��Ƽ�ʹ��
 * 
 * @author XiongChun
 * @since 2009-07-18
 * @see com.ibatis.dao.client.Dao
 */
public interface BaseDao {
	/**
	 * ����һ����¼
	 * 
	 * @param domain
	 *            Ҫ�����ʵ�����
	 */
	public void insertDomain(BasePo domain);

	/**
	 * ��������ɾ��һ����¼
	 * 
	 * @param PKey
	 *            ����
	 */
	public void deleteDomainByKey(PKey key);

	/**
	 * ����������ѯһ������
	 * 
	 * @param PKey
	 *            ����
	 * @return object ���ص�ʵ�����
	 */
	public Object queryDomainByKey(PKey key);

	/**
	 * ����Dto��ѯ����
	 * 
	 * @param dto
	 *            �����Dto��ѯ����Dto����
	 * @return List ���ص�List��¼��
	 */
	public List queryDomainsByDto(Dto dto);

	/**
	 * ����Key����һ����¼
	 * 
	 * @param domain
	 *            ʵ���������
	 */
	public void updateDomainByKey(BasePo domain);
	
	/**
	 * ����ҳ��ѯ
	 * 
	 * @param SQL���ID��
	 * @param parameterObject
	 *            ��ѯ��������(map javaBean)
	 */
	public List queryForPage(String statementName, Dto qDto);

}
