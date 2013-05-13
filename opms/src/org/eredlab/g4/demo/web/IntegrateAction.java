package org.eredlab.g4.demo.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.demo.service.DemoService;
import org.eredlab.g4.rif.report.jasper.ReportData;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * �ۺ�ʵ��Action
 * 
 * @author XiongChun
 * @since 2010-11-30
 * @see BaseAction
 */
public class IntegrateAction extends BaseAction {

	private DemoService demoService = (DemoService) getService("demoService");

	/**
	 * ��ѯʵ��1
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryDemo1Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("queryDemo1View");
	}
	
	/**
	 * �洢���̵��ó�ʼ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward callPrcInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("callPrcView");
	}

	/**
	 * ��ѯʵ��2
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryDemo2Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("queryDemo2View");
	}

	/**
	 * ��ѯҽԺ��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryBalanceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		List list = g4Reader.queryForPage("queryBalanceInfo2", dto);
		Integer countInteger = (Integer) g4Reader.queryForObject("countBalanceInfo2", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, GlobalConstants.FORMAT_Date);
		super.write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * ���ݲɼ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward collectDataInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("collectDataView");
	}

	/**
	 * ����ά��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward manageDataInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("manageDataView");
	}
	
	/**
	 * ����ά��(4��1)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward manageData4In1Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("manageData4In1View");
	}

	/**
	 * ���ݲɼ�(����ģʽ)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward collectDataByWindowInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("collectDataByWindowView");
	}

	/**
	 * ��ѯ�շ���Ŀ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward querySfxm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto inDto = cForm.getParamAsDto(request);
		Dto outDto = new BaseDto();
		Dto dto = (BaseDto) g4Reader.queryForObject("queryCatalogs2", inDto);
		if (G4Utils.isEmpty(dto)) {
			outDto.put("msg", "û�в�ѯ������");
		} else {
			outDto.putAll(dto);
			outDto.put("msg", "ok");
		}
		write(JsonHelper.encodeDto2FormLoadJson(outDto, GlobalConstants.FORMAT_Date), response);
		return mapping.findForward(null);
	}

	/**
	 * �����շ���Ŀ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateSfxm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto inDto = cForm.getParamAsDto(request);
		inDto.put("ggsj", inDto.getAsTimestamp("ggsj"));
		demoService.updateSfxmDomain(inDto);
		Dto dto = new BaseDto();
		dto.put("success", new Boolean(true));
		dto.put("msg", "�����޸ĳɹ�!");
		write(JsonHelper.encodeObject2Json(dto), response);
		return mapping.findForward(null);
	}

	/**
	 * �����շ���Ŀ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveSfxmDomain(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		inDto.put("ggsj", inDto.getAsTimestamp("ggsj"));
		inDto.put("yybm", "03010001");
		Dto outDto = demoService.saveSfxmDomain(inDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�շ���Ŀ���ݱ���ɹ�!");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
	
	/**
	 * ��ѯҽԺ�շ���Ŀ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward querySfxmDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		List list = g4Reader.queryForPage("queryCatalogsForGridDemo", dto);
		Integer countInteger = (Integer) g4Reader.queryForObject("countCatalogsForGridDemo", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, GlobalConstants.FORMAT_Date);
		super.write(jsonString, response);
		return mapping.findForward(null);
	}
	
	/**
	 * ɾ���շ���Ŀ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteSfxm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto inDto = aForm.getParamAsDto(request);
		Dto outDto = demoService.deleteSfxm(inDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�շ���Ŀɾ���ɹ�!");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
	
	/**
	 * ���챨�����ݶ���
	 */
	public ActionForward buildReportDataObject(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Dto inDto = (BaseDto)getSessionAttribute(request, "QUERYCATALOGS4PRINT_QUERYDTO");
		List catalogList = catalogList = g4Reader.queryForList("queryCatalogsForPrintLimitRows4DemoWithMysql", inDto);
		Dto dto = new BaseDto();
		//�Ʊ���
		dto.put("zbr", getSessionContainer(request).getUserInfo().getUsername());
		//�Ʊ�ʱ��
		dto.put("zbsj", G4Utils.getCurrentTime());
		ReportData reportData = new ReportData();
		reportData.setParametersDto(dto);
		reportData.setFieldsList(catalogList);
		reportData.setReportFilePath("/report/jasper/app/hisCatalogReport4App.jasper");
		getSessionContainer(request).setReportData("hisCatalogReport4App", reportData);
		return mapping.findForward(null);
	}
	
	/**
	 * ���ô洢����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward callPrc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (G4Utils.isMysql()) {
			Dto outDto = new BaseDto();
		    outDto.put("success", new Boolean(true));
			outDto.put("msg", "������ʹ�÷�Oracle���ݿ��Ʒ, G4��ǰ�汾ֻ֧��Oracle�洢���̵���");
			write(JsonHelper.encodeObject2Json(outDto), response);
			return mapping.findForward(null);
		}
		CommonActionForm cForm = (CommonActionForm) form;
		Dto inDto = cForm.getParamAsDto(request);
		inDto.put("myname", getSessionContainer(request).getUserInfo().getUsername());
	    Dto outDto = demoService.callPrc(inDto);
	    String result = outDto.getAsString("result");
	    result = result + " " + inDto.getAsString("number1") + "+" + inDto.getAsString("number2") + "=" + outDto.getAsString("sum");
	    outDto.put("result", result);
	    outDto.put("success", new Boolean(true));
		outDto.put("msg", "�洢���̵��óɹ�");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
	
	/**
	 * SQL���������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchSql(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		Dto inDto = cForm.getParamAsDto(request);
		inDto.put("count", "3");
		inDto.put("ggsj", inDto.getAsTimestamp("ggsj"));
		inDto.put("yybm", "03010001");
		demoService.batchSaveSfxmDomains(inDto);
        Dto outDto = new BaseDto();
	    outDto.put("success", new Boolean(true));
	    outDto.put("msg", "����ɹ�(��batch��ʽһ���������ݿ�����������ύ��3��SQL���)");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
}
