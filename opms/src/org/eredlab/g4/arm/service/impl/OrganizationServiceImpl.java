package org.eredlab.g4.arm.service.impl;

import java.util.List;

import org.eredlab.g4.arm.service.OrganizationService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.arm.util.idgenerator.IdGenerator;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * ��֯����ģ��ҵ��ʵ����
 * 
 * @author XiongChun
 * @since 2010-01-13
 */
public class OrganizationServiceImpl extends BaseServiceImpl implements
		OrganizationService {

	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param pDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Dto getUserInfo(Dto pDto) {
		Dto outDto = new BaseDto();
//		pDto.put("lock", ArmConstants.LOCK_N);
		UserInfoVo userInfo = (UserInfoVo) g4Dao.queryForObject("getUserInfo",
				pDto);
		outDto.put("userInfo", userInfo);
		return outDto;
	}

	/**
	 * ��ѯ������Ϣ���ɲ�����
	 * 
	 * @param pDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Dto queryDeptItems(Dto pDto) {
		Dto outDto = new BaseDto();
		List deptList = g4Dao.queryForList("queryDeptItemsByDto", pDto);
		Dto deptDto = new BaseDto();
		for (int i = 0; i < deptList.size(); i++) {
			deptDto = (BaseDto) deptList.get(i);
			if (deptDto.getAsString("leaf").equals(ArmConstants.LEAF_Y))
				deptDto.put("leaf", new Boolean(true));
			else
				deptDto.put("leaf", new Boolean(false));
			if (deptDto.getAsString("id").length() == 6)
				deptDto.put("expanded", new Boolean(true));
		}
		outDto.put("jsonString", JsonHelper.encodeObject2Json(deptList));
		return outDto;
	}

	/**
	 * ���沿��
	 * 
	 * @param pDto
	 * @return
	 */
	public synchronized Dto saveDeptItem(Dto pDto) {
		String deptid = IdGenerator.getDeptIdGenerator(pDto
				.getAsString("parentid"));
		pDto.put("deptid", deptid);
		pDto.put("leaf", ArmConstants.LEAF_Y);
		// MYSQL��int�����ֶβ��ܲ�����ַ�
		pDto.put("sortno",
				G4Utils.isEmpty(pDto.getAsString("sortno")) ? Integer
						.valueOf("0") : pDto.getAsString("sortno"));
		g4Dao.insert("saveDeptItem", pDto);
		Dto updateDto = new BaseDto();
		updateDto.put("deptid", pDto.getAsString("parentid"));
		updateDto.put("leaf", ArmConstants.LEAF_N);
		g4Dao.update("updateLeafFieldInEaDept", updateDto);
		return null;
	}

	/**
	 * �޸Ĳ���
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto updateDeptItem(Dto pDto) {
		if (G4Utils.isEmpty(pDto.getAsString("sortno"))) {
			pDto.put("sortno", "0");
		}
		if (pDto.getAsString("parentid").equals(
				pDto.getAsString("parentid_old"))) {
			pDto.remove("parentid");
			g4Dao.update("updateDeptItem", pDto);
		} else {
			g4Dao.delete("deleteEadeptItem", pDto);
			saveDeptItem(pDto);
			pDto.put("parentid", pDto.getAsString("parentid_old"));
			updateLeafOfDeletedParent(pDto);
		}
		return null;
	}

	/**
	 * ������ɾ�����ŵ�ֱϵ�������ŵ�Leaf����
	 * 
	 * @param pDto
	 */
	private void updateLeafOfDeletedParent(Dto pDto) {
		String parentid = pDto.getAsString("parentid");
		pDto.put("deptid", parentid);
		Integer countInteger = (Integer) g4Dao.queryForObject(
				"prepareChangeLeafOfDeletedParentForEadept", pDto);
		if (countInteger.intValue() == 0) {
			pDto.put("leaf", ArmConstants.LEAF_Y);
		} else {
			pDto.put("leaf", ArmConstants.LEAF_N);
		}
		g4Dao.update("updateLeafFieldInEaDept", pDto);
	}

	/**
	 * ɾ��������
	 * 
	 * @param pDto
	 * @return
	 */
	public Dto deleteDeptItems(Dto pDto) {
		Dto dto = new BaseDto();
		if (pDto.getAsString("type").equals("1")) {
			// �б�ѡɾ��
			String[] arrChecked = pDto.getAsString("strChecked").split(",");
			for (int i = 0; i < arrChecked.length; i++) {
				dto.put("deptid", arrChecked[i]);
				deleteDept(dto);
			}
		} else {
			// �������Ҽ�ɾ��
			dto.put("deptid", pDto.getAsString("deptid"));
			deleteDept(dto);
		}
		return null;
	}

	/**
	 * ɾ������ ���ڲ�����
	 * 
	 * @param pDto
	 */
	private void deleteDept(Dto pDto) {
		Dto changeLeafDto = new BaseDto();
		Dto tempdDto = (BaseDto) g4Dao.queryForObject("queryDeptItemsByDto",
				pDto);
		if (G4Utils.isNotEmpty(tempdDto)) {
			changeLeafDto.put("parentid", tempdDto.getAsString("parentid"));
		}
		g4Dao.delete("deleteEaroleAuthorizeInDeptManage", pDto);
		g4Dao.delete("deleteEaroleInDeptManage", pDto);
		g4Dao.delete("deleteEauserauthorizeInDeptManage", pDto);
		g4Dao.delete("deleteEauserauthorizeInDeptManage2", pDto);
		g4Dao.delete("deleteEauserInDeptManage", pDto);
		g4Dao.delete("deleteEausermenumapInDeptManage", pDto);
		g4Dao.delete("deleteEadeptItem", pDto);
		if (G4Utils.isNotEmpty(tempdDto)) {
			updateLeafOfDeletedParent(changeLeafDto);
		}
	}

	/**
	 * �����û��������ű�Ų�ѯ���Ŷ���<br>
	 * ���ڹ�����֯�������ĸ��ڵ�
	 * 
	 * @param
	 * @return
	 */
	public Dto queryDeptinfoByDeptid(Dto pDto) {
		Dto outDto = new BaseDto();
		outDto.putAll((BaseDto) g4Dao.queryForObject("queryDeptinfoByDeptid",
				pDto));
		outDto.put("success", new Boolean(true));
		return outDto;
	}

	/**
	 * �����û�������Ϣ
	 * 
	 * @param pDto
	 */
	public Dto saveUserTheme(Dto pDto) {
		Dto outDto = new BaseDto();
		g4Dao.update("saveUserTheme", pDto);
		outDto.put("success", new Boolean(true));
		return outDto;
	}

	/**
	 * ��ѯ�û���Ȩ�Ĳ˵�
	 * 
	 * @param pDto
	 * */
	public List queryUsersMenusByUserId(Dto pDto) {
		return g4Dao.queryForList("queryGrantedUserRoleMenusByUserId", pDto);
	}

	/**
	 * ��ѯ��ɫ��Ȩ�Ĳ˵�
	 * 
	 * @param pDto
	 * */
	public List queryUsersMenusByRoleId(Dto pDto) {
		return g4Dao.queryForList("queryGrantedUserRoleMenusByRoleId", pDto);
	}

	/**
	 * ��ѯ�û��Ľ�ɫ
	 * 
	 * @param pDto
	 * */
	public List queryUsersRoleByUserId(Dto pDto) {
		return g4Dao.queryForList("queryGrantedRolesByUserId", pDto);
	}

	public Dto getMenuInfoByMenuid(Dto pDto) {
		Object obj = g4Dao.queryForObject("getMenuInfoByMenuid", pDto);
		if (obj != null) {
			Dto outDto = new BaseDto();
			outDto.putAll((BaseDto) obj);
			outDto.put("success", new Boolean(true));
			return outDto;
		}
		return null;
	}

}
