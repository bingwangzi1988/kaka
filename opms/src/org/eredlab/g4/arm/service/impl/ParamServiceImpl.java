package org.eredlab.g4.arm.service.impl;

import org.eredlab.g4.arm.service.ParamService;
import org.eredlab.g4.arm.util.idgenerator.IDHelper;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;

/**
 * ȫ�ֲ������ݷ���ʵ��
 * 
 * @author XiongChun
 * @since 2010-05-13
 * @see BaseServiceImpl
 */
public class ParamServiceImpl extends BaseServiceImpl implements ParamService{

	/**
	 * ���������Ϣ��
	 */
	public Dto saveParamItem(Dto pDto){
		pDto.put("paramid", IDHelper.getParamID());
		g4Dao.insert("saveParamItem", pDto);
		return null;
	}

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param pDto
	 */
	public Dto deleteParamItem(Dto pDto){
		Dto dto = new BaseDto();
		String[] arrChecked = pDto.getAsString("strChecked").split(",");
		for(int i = 0; i < arrChecked.length; i++){
			dto.put("paramid", arrChecked[i]);
			g4Dao.delete("deletParamItem", dto);
		}
		return null;
	}

	/**
	 * �޸Ĳ�����Ϣ
	 * 
	 * @param pDto
	 */
	public Dto updateParamItem(Dto pDto){
		g4Dao.update("updateParamItem", pDto);
		return null;
	}

}
