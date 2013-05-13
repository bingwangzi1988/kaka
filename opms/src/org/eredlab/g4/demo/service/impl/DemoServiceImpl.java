package org.eredlab.g4.demo.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.demo.service.DemoService;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * ϵͳ��ʾʵ����
 * 
 * @author XiongChun
 * @since 2010-02-13
 */
public class DemoServiceImpl extends BaseServiceImpl implements DemoService {

	/**
	 * ����һ���շ���Ŀ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto saveSfxmDomain(Dto pDto) {
		Dto outDto = new BaseDto();
		String xmid = IDHelper.getXmID();
		pDto.put("xmid", xmid);
		g4Dao.insert("insertEz_sfxmDomain", pDto);
		outDto.put("xmid", xmid);
		return outDto;
	}

	/**
	 * ����һ���շ���Ŀ(JDBC��������ʾ)
	 * 
	 * @param pDto
	 * @return
	 * @throws SQLException
	 */
	public Dto batchSaveSfxmDomains(final Dto pDto){
		Dto outDto = new BaseDto();
		g4Dao.getSqlMapClientTpl().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (int i = 0; i < pDto.getAsInteger("count").intValue(); i++) {
					Dto dto = new BaseDto();
					String xmid = IDHelper.getXmID();
					dto.put("xmid", xmid);
					dto.put("sfdlbm", "99");
					executor.insert("insertEz_sfxmDomain", dto);
				}
				executor.executeBatch();
				return null;
			}
		});
		return outDto;
	}

	/**
	 * �޸�һ���շ���Ŀ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto updateSfxmDomain(Dto pDto) {
		Dto outDto = new BaseDto();
		g4Dao.update("updatesfxm", pDto);
		return outDto;
	}

	/**
	 * ɾ��һ���շ���Ŀ
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto deleteSfxm(Dto pDto) {
		Dto outDto = new BaseDto();
		g4Dao.delete("deleteSfxm", pDto);
		return outDto;
	}

	/**
	 * ���ô洢������ʾ
	 * 
	 * @return
	 */
	public Dto callPrc(Dto inDto) {
		Dto prcDto = new BaseDto();
		prcDto.put("myname", inDto.getAsString("myname"));
		prcDto.put("number1", inDto.getAsBigDecimal("number1"));
		prcDto.put("number2", inDto.getAsBigDecimal("number2"));
		g4Dao.callPrc("g4_prc_demo", prcDto);
		return prcDto;
	}

	/**
	 * ��ʾ����ʽ��������
	 */
	public Dto doTransactionTest() {
		Dto dto = new BaseDto();
		dto.put("sxh", "BJLK1000000000935");
		dto.put("fyze", new BigDecimal(300));
		g4Dao.update("updateByjsb", dto);
		// �������������һ���쳣,���񽫱��ع�
		dto.put("fyze", new BigDecimal(300));
		g4Dao.update("updateByjsb1", dto);
		Dto outDto = (Dto) g4Dao.queryForObject("queryBalanceInfo", dto);
		return outDto;
	}

	/**
	 * �쳣����
	 */
	public Dto doError() {
		Dto dto = new BaseDto();
		dto.put("sxh", "BJLK1000000000935");
		Dto outDto = (Dto) g4Dao.queryForObject("queryBalanceInfo1", dto);
		return outDto;
	}

	/**
	 * �����ļ��ϴ�����
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto doUpload(Dto pDto) {
		pDto.put("fileid", IDHelper.getFileID());
		pDto.put("uploaddate", G4Utils.getCurrentTimestamp());
		g4Dao.insert("insertEa_demo_uploadPo", pDto);
		return new BaseDto();
	}

	/**
	 * ɾ���ļ�����
	 * 
	 * @param pFileId
	 * @return
	 */
	public Dto delFile(String pFileId) {
		g4Dao.delete("delFileByFileID", pFileId);
		return null;
	}

}
