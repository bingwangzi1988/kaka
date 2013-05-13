package org.eredlab.g4.demo.service;

import java.sql.SQLException;

import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

/**
 * ϵͳ��ʾ�ӿ�
 * 
 * @author XiongChun
 * @since 2010-02-13
 */
public interface DemoService extends BaseService {

	/**
	 * ����һ���շ���Ŀ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveSfxmDomain(Dto pDto);
	
	/**
	 * ����һ���շ���Ŀ(JDBC��������ʾ)
	 * @param pDto
	 * @return
	 * @throws SQLException 
	 */
	public Dto batchSaveSfxmDomains(Dto pDto);

	/**
	 * �޸�һ���շ���Ŀ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto updateSfxmDomain(Dto pDto);

	/**
	 * ɾ��һ���շ���Ŀ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto deleteSfxm(Dto pDto);

	/**
	 * ���ô洢������ʾ
	 * 
	 * @return
	 */
	public Dto callPrc(Dto inDto);

	/**
	 * ��ʾ����ʽ��������
	 */
	public Dto doTransactionTest();

	/**
	 * �쳣����
	 */
	public Dto doError();

	/**
	 * �����ļ��ϴ�����
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto doUpload(Dto pDto);

	/**
	 * ɾ���ļ�����
	 * 
	 * @param pFileId
	 * @return
	 */
	public Dto delFile(String pFileId);

}
