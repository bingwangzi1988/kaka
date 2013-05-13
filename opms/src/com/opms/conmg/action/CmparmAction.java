package com.opms.conmg.action;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.arm.vo.UserInfoVo;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.rif.util.WebUtils;

import com.common.base.BaseAction;
import com.common.util.CountOrder;
import com.common.util.CountOrderUtil;
import com.common.util.QueryUitl;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opms.conmg.po.Cmparm;
import com.opms.conmg.service.CmparmService;
import com.opms.conmg.vo.CmparmQuery;

public class CmparmAction extends BaseAction implements Preparable, ModelDriven {

	CmparmService cmparmService = (CmparmService) SpringBeanLoader.getSpringBean("cmparmService");

	private JSONObject resultJson;

	private Cmparm cmparm;

	public JSONObject getResultJson() {
		return resultJson;
	}

	public void setResultJson(JSONObject resultJson) {
		this.resultJson = resultJson;
	}

	public void prepare() throws Exception {
		cmparm = new Cmparm();
	}

	public Object getModel() {
		return cmparm;
	}

	public String list() {
		CountOrder countOrder = CountOrderUtil.getCountOrder();

		CmparmQuery cmparmQuery = QueryUitl.newQuery(CmparmQuery.class);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		UserInfoVo userInfoVo = WebUtils.getSessionContainer(request).getUserInfo();
		boolean isRole=true;
		if("developer".equals(userInfoVo.getAccount())){
			isRole=false;
		}
		
		List<Cmparm> list = cmparmService.searchCmparm(cmparmQuery, countOrder,isRole);
		Long pageCount = cmparmService.countCmparm(cmparmQuery,isRole);

		JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list, pageCount.intValue(), null);
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String find() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = new String(request.getParameter("id"));
		Cmparm cmparm = cmparmService.getById(id);

		JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(cmparm, null);
		setResultJson(jsonObject);
		return SUCCESS;
	}

	public String save() {
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject returnObject = new JSONObject();
		try {
			if (isEmpty(cmparm.getId()))
				cmparm.setId(null);
//			String unique0 = cmparmService.isUnique(cmparm, "parmId", "������ʶ");
//			if (unique0 != null) {
//				returnObject.put("SUCCESS", false);
//				returnObject.put("msg", unique0);
//				setResultJson(returnObject);
//				return SUCCESS;
//			}
			boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
			
			Cmparm cm=cmparmService.getCmparmByParmId(cmparm.getParmId());
			if(cm!=null){
				String msg="����";
				if("0".equals(cm.getMemo()))
				{
					msg+="[ҵ���������]�˵���";
				}else{
					msg+="[������������]�˵���";
				}
				msg+="����";
				if (!isEdit)
				{
					returnObject.put("SUCCESS", false);
					returnObject.put("msg", "������ʶ�����ظ�,"+msg);
					setResultJson(returnObject);
					return SUCCESS;
				}
			}
			
			if (isEdit) {
				cmparmService.update(cmparm);
			} else {
 				cmparmService.save(cmparm);
			}
			returnObject.element(SUCCESS, true);
			returnObject.element("msg", "�����ɹ�");

		} catch (Exception e) {
			returnObject.put("failure", true);
			returnObject.put("msg", e.getMessage());
			e.printStackTrace();
		}

		setResultJson(returnObject);
		return SUCCESS;
	}

	public String delete() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String ids = request.getParameter("ids");
		String[] idarray = ids.split(",");

		JSONObject returnObject = new JSONObject();
		try {
			for (int i = 0; i < idarray.length; i++) {
				String id = new String(idarray[i]);
				cmparmService.removeById(id);
			}
			returnObject.put("success", true);
			returnObject.put("msg", "ɾ���ɹ�");
		} catch (Exception e) {
			returnObject.put("failure", true);
			returnObject.put("msg", e.getMessage());
			e.printStackTrace();
		}
		setResultJson(returnObject);
		return SUCCESS;
	}

//	public String getCardTxnParm() {
//		String parmFlag = "99"; // �����ײ�����ѯ
//		List<Object[]> cardTxnParmList = cmparmService.getCardTxnParm(parmFlag);
//		JSONObject returnObject = new JSONObject();
//		for (Object[] cardTxnParm : cardTxnParmList) {
//			returnObject.put(cardTxnParm[0], cardTxnParm[1]);
//		}
//
//		// �羳��־ 1 ���� 3 ����
//		// �޶����� 0 ���� 1 �� 2 �� 3 ��
//		// �������� IC1001 ȡ�� ��IC1004 ת�ˣ�IC1007 ����
//		// ���� 02 ATM��03 POS
//
//		TicKindLimitQuery klmt = new TicKindLimitQuery();
//		klmt.setAreaFlag("1");// �羳��־
//		klmt.setChnlId("02");// ��������
//		klmt.setTxnCode("IC1001");// ���״���
//		klmt.setPeriod("0");// �޶�����
//		String AtmLmt = getDepLmt("atm", klmt);// AtmLmt ATM����ȡ���޶�
//
//		klmt.setAreaFlag("1");// �羳��־
//		klmt.setChnlId("02");// ��������
//		klmt.setTxnCode("IC1001");// ���״���
//		klmt.setPeriod("1");// �޶�����
//		String AtmLmtDay = getDepLmt("atm", klmt);// AtmLmtDay ATM���ڵ����ۼ�ȡ���޶�
//
//		klmt.setAreaFlag("1");// �羳��־
//		klmt.setChnlId("02");// ��������
//		klmt.setTxnCode("IC1004");// ���״���
//		klmt.setPeriod("0");// �޶�����
//		String AtmTurnLmt = getDepLmt("atm", klmt);// AtmTurnLmt ATM����ת���޶�
//
//		klmt.setAreaFlag("1");// �羳��־
//		klmt.setChnlId("02");// ��������
//		klmt.setTxnCode("IC1004");// ���״���
//		klmt.setPeriod("1");// �޶�����
//		String AtmTurnDay = getDepLmt("count", klmt);// AtmTurnDay ATM�����ۼ�ת�˴���
//
//		klmt.setAreaFlag("1");// �羳��־
//		klmt.setChnlId("02");// ��������
//		klmt.setTxnCode("IC1001");// ���״���
//		klmt.setPeriod("1");// �޶�����
//		String AtmTakeLmtDay = getDepLmt("count", klmt);// AtmTakeLmtDay ATM�����ۼ�ȡ�����
//
//		klmt.setAreaFlag("1");// �羳��־
//		klmt.setChnlId("02");// ��������
//		klmt.setTxnCode("IC1004");// ���״���
//		klmt.setPeriod("1");// �޶�����
//		String NotEndorseLmtDay = getDepLmt("atm", klmt);// NotEndorseLmtDay ATMδǩԼ�ͻ�ÿ���ۼ�ת���޶�
//
//		klmt.setAreaFlag("1");// �羳��־
//		klmt.setChnlId("02");// ��������
//		klmt.setTxnCode("IC1004");// ���״���
//		klmt.setPeriod("1");// �޶�����
//		String EndorseLmtDay = getDepLmt("atm", klmt);// EndorseLmtDay ATMǩԼ�ͻ�ÿ���ۼ�ת���޶�
//
//		klmt.setAreaFlag("3");// �羳��־
//		klmt.setChnlId("02");// ��������
//		klmt.setTxnCode("IC1001");// ���״���
//		klmt.setPeriod("1");// �޶�����
//		String AtmAreaFlagLmt = getDepLmt("atm", klmt);// AtmAreaFlagLmt ATM���ⵥ���ۼ�ȡ���޶�
//
//		klmt.setAreaFlag("1");// �羳��־
//		klmt.setChnlId("03");// ��������
//		klmt.setTxnCode("IC1007");// ���״���
//		klmt.setPeriod("1");// �޶�����
//		String PosInLmt = getDepLmt("atm", klmt);// PosInLmt POS���ڵ����ۼ������޶�
//
//		klmt.setAreaFlag("3");// �羳��־
//		klmt.setChnlId("03");// ��������
//		klmt.setTxnCode("IC1007");// ���״���
//		klmt.setPeriod("1");// �޶�����
//		String PosOutLmt = getDepLmt("atm", klmt);// PosOutLmt POS���ⵥ���ۼ������޶�
//
//		returnObject.put("AtmLmt", AtmLmt);
//		returnObject.put("AtmLmtDay", AtmLmtDay);
//		returnObject.put("AtmTurnLmt", AtmTurnLmt);
//		returnObject.put("AtmTurnDay", AtmTurnDay);
//		returnObject.put("AtmTakeLmtDay", AtmTakeLmtDay);
//		returnObject.put("NotEndorseLmtDay", NotEndorseLmtDay);
//		returnObject.put("EndorseLmtDay", EndorseLmtDay);
//		returnObject.put("AtmAreaFlagLmt", AtmAreaFlagLmt);
//		returnObject.put("PosInLmt", PosInLmt);
//		returnObject.put("PosOutLmt", PosOutLmt);
//
//		returnObject.put("success", true);
//		setResultJson(returnObject);
//		return SUCCESS;
//	}
//
//	public String getDepLmt(String type, TicKindLimitQuery klmt) {
//		TicKindLimit lmt = ticKindLimitService.searchTicKindLimit(klmt);
//		if (lmt != null) {
//			if (type.trim().toLowerCase().equals("atm") && lmt.getDepLmtAmt() != null)
//				return lmt.getDepLmtAmt().toString();
//			if (type.trim().toLowerCase().equals("count") && lmt.getDepLmtCount() != null)
//				return lmt.getDepLmtCount().toString();
//		}
//		return "0";
//	}
}
