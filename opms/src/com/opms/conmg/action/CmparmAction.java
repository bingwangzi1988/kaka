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
//			String unique0 = cmparmService.isUnique(cmparm, "parmId", "参数标识");
//			if (unique0 != null) {
//				returnObject.put("SUCCESS", false);
//				returnObject.put("msg", unique0);
//				setResultJson(returnObject);
//				return SUCCESS;
//			}
			boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
			
			Cmparm cm=cmparmService.getCmparmByParmId(cmparm.getParmId());
			if(cm!=null){
				String msg="请在";
				if("0".equals(cm.getMemo()))
				{
					msg+="[业务参数管理]菜单中";
				}else{
					msg+="[技术参数管理]菜单中";
				}
				msg+="查找";
				if (!isEdit)
				{
					returnObject.put("SUCCESS", false);
					returnObject.put("msg", "参数标识不能重复,"+msg);
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
			returnObject.element("msg", "操作成功");

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
			returnObject.put("msg", "删除成功");
		} catch (Exception e) {
			returnObject.put("failure", true);
			returnObject.put("msg", e.getMessage());
			e.printStackTrace();
		}
		setResultJson(returnObject);
		return SUCCESS;
	}

//	public String getCardTxnParm() {
//		String parmFlag = "99"; // 卡交易参数查询
//		List<Object[]> cardTxnParmList = cmparmService.getCardTxnParm(parmFlag);
//		JSONObject returnObject = new JSONObject();
//		for (Object[] cardTxnParm : cardTxnParmList) {
//			returnObject.put(cardTxnParm[0], cardTxnParm[1]);
//		}
//
//		// 跨境标志 1 境内 3 境外
//		// 限额周期 0 单笔 1 日 2 周 3 月
//		// 交易名称 IC1001 取款 ，IC1004 转账，IC1007 消费
//		// 渠道 02 ATM，03 POS
//
//		TicKindLimitQuery klmt = new TicKindLimitQuery();
//		klmt.setAreaFlag("1");// 跨境标志
//		klmt.setChnlId("02");// 渠道代码
//		klmt.setTxnCode("IC1001");// 交易代码
//		klmt.setPeriod("0");// 限额周期
//		String AtmLmt = getDepLmt("atm", klmt);// AtmLmt ATM单笔取款限额
//
//		klmt.setAreaFlag("1");// 跨境标志
//		klmt.setChnlId("02");// 渠道代码
//		klmt.setTxnCode("IC1001");// 交易代码
//		klmt.setPeriod("1");// 限额周期
//		String AtmLmtDay = getDepLmt("atm", klmt);// AtmLmtDay ATM境内单日累计取款限额
//
//		klmt.setAreaFlag("1");// 跨境标志
//		klmt.setChnlId("02");// 渠道代码
//		klmt.setTxnCode("IC1004");// 交易代码
//		klmt.setPeriod("0");// 限额周期
//		String AtmTurnLmt = getDepLmt("atm", klmt);// AtmTurnLmt ATM单笔转账限额
//
//		klmt.setAreaFlag("1");// 跨境标志
//		klmt.setChnlId("02");// 渠道代码
//		klmt.setTxnCode("IC1004");// 交易代码
//		klmt.setPeriod("1");// 限额周期
//		String AtmTurnDay = getDepLmt("count", klmt);// AtmTurnDay ATM单日累计转账次数
//
//		klmt.setAreaFlag("1");// 跨境标志
//		klmt.setChnlId("02");// 渠道代码
//		klmt.setTxnCode("IC1001");// 交易代码
//		klmt.setPeriod("1");// 限额周期
//		String AtmTakeLmtDay = getDepLmt("count", klmt);// AtmTakeLmtDay ATM单日累计取款次数
//
//		klmt.setAreaFlag("1");// 跨境标志
//		klmt.setChnlId("02");// 渠道代码
//		klmt.setTxnCode("IC1004");// 交易代码
//		klmt.setPeriod("1");// 限额周期
//		String NotEndorseLmtDay = getDepLmt("atm", klmt);// NotEndorseLmtDay ATM未签约客户每日累计转账限额
//
//		klmt.setAreaFlag("1");// 跨境标志
//		klmt.setChnlId("02");// 渠道代码
//		klmt.setTxnCode("IC1004");// 交易代码
//		klmt.setPeriod("1");// 限额周期
//		String EndorseLmtDay = getDepLmt("atm", klmt);// EndorseLmtDay ATM签约客户每日累计转账限额
//
//		klmt.setAreaFlag("3");// 跨境标志
//		klmt.setChnlId("02");// 渠道代码
//		klmt.setTxnCode("IC1001");// 交易代码
//		klmt.setPeriod("1");// 限额周期
//		String AtmAreaFlagLmt = getDepLmt("atm", klmt);// AtmAreaFlagLmt ATM境外单日累计取款限额
//
//		klmt.setAreaFlag("1");// 跨境标志
//		klmt.setChnlId("03");// 渠道代码
//		klmt.setTxnCode("IC1007");// 交易代码
//		klmt.setPeriod("1");// 限额周期
//		String PosInLmt = getDepLmt("atm", klmt);// PosInLmt POS境内单日累计消费限额
//
//		klmt.setAreaFlag("3");// 跨境标志
//		klmt.setChnlId("03");// 渠道代码
//		klmt.setTxnCode("IC1007");// 交易代码
//		klmt.setPeriod("1");// 限额周期
//		String PosOutLmt = getDepLmt("atm", klmt);// PosOutLmt POS境外单日累计消费限额
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
