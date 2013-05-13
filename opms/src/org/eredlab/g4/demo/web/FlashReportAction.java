package org.eredlab.g4.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.rif.report.fcf.Categorie;
import org.eredlab.g4.rif.report.fcf.CategoriesConfig;
import org.eredlab.g4.rif.report.fcf.DataSet;
import org.eredlab.g4.rif.report.fcf.FcfDataMapper;
import org.eredlab.g4.rif.report.fcf.GraphConfig;
import org.eredlab.g4.rif.report.fcf.Set;
import org.eredlab.g4.rif.web.BaseAction;

/**
 * FlashReport��׼�����߽̳�Action
 * 
 * @author XiongChun
 * @since 2010-10-13
 * @see BaseAction
 */
public class FlashReportAction extends BaseAction {
	/**
	 * FCF 2D��״ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcf2DColumnInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//X����������
		graphConfig.setXAxisName("�¶�");
		//����ֵǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο���eRedG4����ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); //��״ͼ��ɫ
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("2dColumnView");
	}
	
	/**
	 * FCF 3D��״ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcf3DColumnInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); //��״ͼ��ɫ
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("3dColumnView");
	}
	
	/**
	 * FCF ����ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcfLineInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		//����ˮƽ�ָ��ߵ���ɫ
		graphConfig.put("divLineColor", "008ED6");
		//����ˮƽ�ָ��ߵ�͸����
		graphConfig.put("divLineAlpha", "20");
		//���ö�ˮƽ�ָ�����ʹ�ð�����
		graphConfig.put("showAlternateHGridColor", "1");
		//���ð�������ɫ
		graphConfig.put("AlternateHGridColor", "BFFFFF");
		//���ð����Ƶ�͸����
		graphConfig.put("alternateHGridAlpha", "20");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); //��״ͼ��ɫ
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("lineView");
	}
	
	/**
	 * FCF 2D��ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcf2DPieInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		graphConfig.setShowNames(new Boolean(true));
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); //��״ͼ��ɫ
			set.setIsSliced(dto.getAsString("issliced"));//����
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("2dPieView");
	}
	
	/**
	 * FCF 3D��ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcf3DPieInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); //��״ͼ��ɫ
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("3dPieView");
	}
	
	/**
	 * FCF ���ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcfAreaInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//graphConfig.setShowValues(true);
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		//����ˮƽ�ָ��ߵ���ɫ
		graphConfig.put("divLineColor", "008ED6");
		//����ˮƽ�ָ��ߵ�͸����
		graphConfig.put("divLineAlpha", "10");
		//���ö�ˮƽ�ָ�����ʹ�ð�����
		graphConfig.put("showAlternateHGridColor", "1");
		//���ð�������ɫ
		graphConfig.put("AlternateHGridColor", "BFFFFF");
		//���ð����Ƶ�͸����
		graphConfig.put("alternateHGridAlpha", "10");
		//graphConfig.put("areaAlpha", "60");
		//graphConfig.put("areaBorderColor", "441570");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			//set.setColor(dto.getAsString("color")); 
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("areaView");
	}
	
	/**
	 * FCF ��״ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcfCircularityInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		graphConfig.setShowNames(new Boolean(true));
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); 
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("circularityView");
	}
	
	/**
	 * FCF ©��ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcfFunnelInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        qDto.put("rownum", new Integer(7));
        //��ѯԭʼ����
		List list = null;
		if (G4Utils.isOracle()) {
			list = g4Reader.queryForList("getFcfDataList", qDto);
		}else if(G4Utils.isMysql()){
			list = g4Reader.queryForList("getFcfDataListMysql", qDto);
		}
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); 
			set.setAlpha("80");
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("funnelView");
	}
	
	/**
	 * 2D�������ͼ��ʼ��
	 * �ۺ�ͼ��ǰ��ĵ�һͼʹ�õ�Ԫ���ݸ�ʽ�ǲ�һ����,����ע�����ǵ�����
	 * @param
	 * @return
	 */
	public ActionForward fcf2DColumnMsInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		graphConfig.setCanvasBorderThickness(new Boolean(true));
		//ʵ��������������ö���
		CategoriesConfig categoriesConfig = new CategoriesConfig();
		List cateList = new ArrayList();
		cateList.add(new Categorie("һ��"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		categoriesConfig.setCategories(cateList);
		List list = getFcfDataList4Group(new BaseDto());
		String xmlString = FcfDataMapper.toFcfXmlData(list, graphConfig, categoriesConfig);
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("2dColumnMsView");
	}
	
	/**
	 * 3D�������ͼ��ʼ��
	 * �ۺ�ͼ��ǰ��ĵ�һͼʹ�õ�Ԫ���ݸ�ʽ�ǲ�һ����,����ע�����ǵ�����
	 * @param
	 * @return
	 */
	public ActionForward fcf3DColumnMsInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		graphConfig.setCanvasBorderThickness(new Boolean(true));
		//ʵ��������������ö���
		CategoriesConfig categoriesConfig = new CategoriesConfig();
		List cateList = new ArrayList();
		cateList.add(new Categorie("һ��"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		categoriesConfig.setCategories(cateList);
		List list = getFcfDataList4Group(new BaseDto());
		String xmlString = FcfDataMapper.toFcfXmlData(list, graphConfig, categoriesConfig);
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("3dColumnMsView");
	}
	
	/**
	 * ������ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcfAreaMsInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		graphConfig.setCanvasBorderThickness(new Boolean(true));
		//ʵ��������������ö���
		CategoriesConfig categoriesConfig = new CategoriesConfig();
		List cateList = new ArrayList();
		cateList.add(new Categorie("һ��"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("ʮ��"));
		cateList.add(new Categorie("ʮһ��"));
		cateList.add(new Categorie("ʮ����"));
		categoriesConfig.setCategories(cateList);
		List list = getFcfDataList4AreaGroup(new BaseDto());
		String xmlString = FcfDataMapper.toFcfXmlData(list, graphConfig, categoriesConfig);
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("areaMsView");
	}
	
	/**
	 * �������ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcfLineMsInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		graphConfig.setCanvasBorderThickness(new Boolean(true));
		//ʵ��������������ö���
		CategoriesConfig categoriesConfig = new CategoriesConfig();
		List cateList = new ArrayList();
		cateList.add(new Categorie("һ��"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("ʮ��"));
		cateList.add(new Categorie("ʮһ��"));
		cateList.add(new Categorie("ʮ����"));
		categoriesConfig.setCategories(cateList);
		List list = getFcfDataList4LineGroup(new BaseDto());
		String xmlString = FcfDataMapper.toFcfXmlData(list, graphConfig, categoriesConfig);
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("lineMsView");
	}
	
	/**
	 * 2D����ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcf2DLineColumnInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		graphConfig.setCanvasBorderThickness(new Boolean(true));
		//ʵ��������������ö���
		CategoriesConfig categoriesConfig = new CategoriesConfig();
		List cateList = new ArrayList();
		cateList.add(new Categorie("һ��"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("ʮ��"));
		cateList.add(new Categorie("ʮһ��"));
		cateList.add(new Categorie("ʮ����"));
		categoriesConfig.setCategories(cateList);
		List list = getFcfDataList4JCT(new BaseDto());
		String xmlString = FcfDataMapper.toFcfXmlData(list, graphConfig, categoriesConfig);
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("2dLineColumnView");
	}
	
	/**
	 * 3D����ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcf3DLineColumnInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		graphConfig.setCanvasBorderThickness(new Boolean(true));
		//ʵ��������������ö���
		CategoriesConfig categoriesConfig = new CategoriesConfig();
		List cateList = new ArrayList();
		cateList.add(new Categorie("һ��"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("ʮ��"));
		cateList.add(new Categorie("ʮһ��"));
		cateList.add(new Categorie("ʮ����"));
		categoriesConfig.setCategories(cateList);
		List list = getFcfDataList4JCT(new BaseDto());
		String xmlString = FcfDataMapper.toFcfXmlData(list, graphConfig, categoriesConfig);
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("3dLineColumnView");
	}
	
	/**
	 * FCF ����ͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcfAdvancedInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		graphConfig.setSubcaption("������ӿ�������Ч��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
        Dto qDto = new BaseDto();		
        qDto.put("product", "2");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); //��״ͼ��ɫ
			set.setJsFunction("fnMyJs(\"xiongchun\")"); //���õ�JS����
			//set.setLink("���������һ�������url����");
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("3dColumnView");
	}
	
	/**
	 * ��ȡFlashReportԪ���� (����ͼ)
	 * @param pDto
	 * @return
	 */
	private List getFcfDataList4JCT(Dto pDto){
		pDto.put("rownum", "12");
		List dataList = new ArrayList();
		DataSet dataSet1 = new DataSet();
		dataSet1.setSeriesname("��ƷA");
		dataSet1.setColor("FDC12E");
		dataSet1.setShowValues(new Boolean(false));
		pDto.put("product", "1");
		List alist = null;
		if (G4Utils.isOracle()) {
			alist = g4Reader.queryForList("getFcfDataList", pDto);
		}else if(G4Utils.isMysql()){
			alist = g4Reader.queryForList("getFcfDataListMysql", pDto);
		}
		List aSetList = new ArrayList();
		for (int i = 0; i < alist.size(); i++) {
			Dto dto = (BaseDto)alist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			aSetList.add(set);
		}
		dataSet1.setData(aSetList);
		dataList.add(dataSet1);
		
		DataSet dataSet2 = new DataSet();
		dataSet2.setSeriesname("��ƷB");
		dataSet2.setColor("44BC2F");
		dataSet2.setShowValues(new Boolean(false));
		pDto.put("product", "2");
		List blist = null;
		if (G4Utils.isOracle()) {
			blist = g4Reader.queryForList("getFcfDataList", pDto);
		}else if(G4Utils.isMysql()){
			blist = g4Reader.queryForList("getFcfDataListMysql", pDto);
		}
		List bSetList = new ArrayList();
		for (int i = 0; i < blist.size(); i++) {
			Dto dto = (BaseDto)blist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			bSetList.add(set);
		}
		dataSet2.setData(bSetList);
		dataList.add(dataSet2);
		
		DataSet dataSet3 = new DataSet();
		dataSet3.setSeriesname("�ϼ�");
		dataSet3.setColor("3CBBD7");
		dataSet3.setShowValues(new Boolean(false));
		dataSet3.setParentYAxis(new Boolean(true));
		List sumlist = g4Reader.queryForList("getFcfSumDataList", pDto);
		List sumSetList = new ArrayList();
		for (int i = 0; i < sumlist.size(); i++) {
			Dto dto = (BaseDto)sumlist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			sumSetList.add(set);
		}
		dataSet3.setData(sumSetList);
		dataList.add(dataSet3);
		return dataList;
	}
	
	/**
	 * ��ȡFlashReportԪ���� (�������ͼ)
	 * @param pDto
	 * @return
	 */
	private List getFcfDataList4LineGroup(Dto pDto){
		pDto.put("rownum", "12");
		List dataList = new ArrayList();
		DataSet dataSet1 = new DataSet();
		dataSet1.setSeriesname("��ƷA");
		dataSet1.setColor("FDC12E");
		pDto.put("product", "1");
		List alist = null;
		if (G4Utils.isOracle()) {
			alist = g4Reader.queryForList("getFcfDataList", pDto);
		}else if(G4Utils.isMysql()){
			alist = g4Reader.queryForList("getFcfDataListMysql", pDto);
		}
		List aSetList = new ArrayList();
		for (int i = 0; i < alist.size(); i++) {
			Dto dto = (BaseDto)alist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			aSetList.add(set);
		}
		dataSet1.setData(aSetList);
		dataList.add(dataSet1);
		
		DataSet dataSet2 = new DataSet();
		dataSet2.setSeriesname("��ƷB");
		dataSet2.setColor("44BC2F");
		pDto.put("product", "2");
		List blist = null;
		if (G4Utils.isOracle()) {
			blist = g4Reader.queryForList("getFcfDataList", pDto);
		}else if(G4Utils.isMysql()){
			blist = g4Reader.queryForList("getFcfDataListMysql", pDto);
		}
		List bSetList = new ArrayList();
		for (int i = 0; i < blist.size(); i++) {
			Dto dto = (BaseDto)blist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			bSetList.add(set);
		}
		dataSet2.setData(bSetList);
		dataList.add(dataSet2);
		return dataList;
	}
	
	/**
	 * ��ȡFlashReportԪ���� (������ͼ)
	 * @param pDto
	 * @return
	 */
	private List getFcfDataList4AreaGroup(Dto pDto){
		pDto.put("rownum", "12");
		List dataList = new ArrayList();
		DataSet dataSet1 = new DataSet();
		dataSet1.setSeriesname("��ƷA");
		dataSet1.setColor("FDC12E");
		dataSet1.setAreaBorderColor("FDC12E");
		dataSet1.setAreaBorderThickness("1");
		dataSet1.setAreaAlpha("70");
		pDto.put("product", "1");
		List alist = null;
		if (G4Utils.isOracle()) {
			alist = g4Reader.queryForList("getFcfDataList", pDto);
		}else if(G4Utils.isMysql()){
			alist = g4Reader.queryForList("getFcfDataListMysql", pDto);
		}
		List aSetList = new ArrayList();
		for (int i = 0; i < alist.size(); i++) {
			Dto dto = (BaseDto)alist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			aSetList.add(set);
		}
		dataSet1.setData(aSetList);
		dataList.add(dataSet1);
		
		DataSet dataSet2 = new DataSet();
		dataSet2.setSeriesname("��ƷB");
		dataSet2.setColor("56B9F9");
		dataSet2.setAreaBorderColor("56B9F9");
		dataSet2.setAreaBorderThickness("2");
		dataSet2.setAreaAlpha("50");
		pDto.put("product", "2");
		List blist = null;
		if (G4Utils.isOracle()) {
			blist = g4Reader.queryForList("getFcfDataList", pDto);
		}else if(G4Utils.isMysql()){
			blist = g4Reader.queryForList("getFcfDataListMysql", pDto);
		}
		List bSetList = new ArrayList();
		for (int i = 0; i < blist.size(); i++) {
			Dto dto = (BaseDto)blist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			bSetList.add(set);
		}
		dataSet2.setData(bSetList);
		dataList.add(dataSet2);
		return dataList;
	}
	
	/**
	 * ��ȡFlashReportԪ���� (��״���ͼ)
	 * @param pDto
	 * @return
	 */
	private List getFcfDataList4Group(Dto pDto){
		pDto.put("rownum", "6");
		List dataList = new ArrayList();
		DataSet dataSet1 = new DataSet();
		dataSet1.setSeriesname("��ƷA");
		dataSet1.setColor("FDC12E");
		pDto.put("product", "1");
		List alist = null;
		if (G4Utils.isOracle()) {
			alist = g4Reader.queryForList("getFcfDataList", pDto);
		}else if(G4Utils.isMysql()){
			alist = g4Reader.queryForList("getFcfDataListMysql", pDto);
		}
		List aSetList = new ArrayList();
		for (int i = 0; i < alist.size(); i++) {
			Dto dto = (BaseDto)alist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			aSetList.add(set);
		}
		dataSet1.setData(aSetList);
		dataList.add(dataSet1);
		
		DataSet dataSet2 = new DataSet();
		dataSet2.setSeriesname("��ƷB");
		dataSet2.setColor("56B9F9");
		pDto.put("product", "2");
		List blist = null;
		if (G4Utils.isOracle()) {
			blist = g4Reader.queryForList("getFcfDataList", pDto);
		}else if(G4Utils.isMysql()){
			blist = g4Reader.queryForList("getFcfDataListMysql", pDto);
		}
		List bSetList = new ArrayList();
		for (int i = 0; i < blist.size(); i++) {
			Dto dto = (BaseDto)blist.get(i);
			Set set = new Set();
			set.setValue(dto.getAsString("value"));
			bSetList.add(set);
		}
		dataSet2.setData(bSetList);
		dataList.add(dataSet2);
		return dataList;
	}
	
	/**
	 * FCF 2D��λͼ��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward fcf2DBarInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//X����������
		graphConfig.setXAxisName("�¶�");
		//����ֵǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο���eRedG4����ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
        Dto qDto = new BaseDto();		
        qDto.put("product", "1");
        //��ѯԭʼ����
		List list = g4Reader.queryForList("getFcfDataList", qDto);
		List dataList = new ArrayList();
		//��ԭʼ���ݶ���ת��Ϊ��ܷ�װ��Set�������ݶ���
		for (int i = 0; i < list.size(); i++) {
			Dto dto = (BaseDto)list.get(i);
			//ʵ����һ��ͼ��Ԫ���ݶ���
			Set set = new Set();
			set.setName(dto.getAsString("name")); //����
			set.setValue(dto.getAsString("value")); //����ֵ
			set.setColor(dto.getAsString("color")); //��״ͼ��ɫ
			dataList.add(set);
		}
		//��ͼ������תΪFlash�ܽ�����XML���ϸ�ʽ
		String xmlString = FcfDataMapper.toFcfXmlData(dataList, graphConfig);
		//��Key��Ӧ��<flashReport />��ǩ��datavar����
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("2dBarView");
	}
	
	/**
	 * 2D��λ���ͼ��ʼ��
	 * �ۺ�ͼ��ǰ��ĵ�һͼʹ�õ�Ԫ���ݸ�ʽ�ǲ�һ����,����ע�����ǵ�����
	 * @param
	 * @return
	 */
	public ActionForward fcf2DBarMsInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ʵ����һ��ͼ�����ö���
		GraphConfig graphConfig = new GraphConfig();
		//������
		graphConfig.setCaption("Google���2010���¶�����ҵ��ͼ��");
		//��������ֵ��ǰ׺
		graphConfig.setNumberPrefix("$");
		//ʹ�����ַ�ʽ���Լ�����û�з�װ��ԭ����������,ԭ�������Բο�������ָ�ϡ�������½�
		//graphConfig.put("propertyName", "value");
		graphConfig.setCanvasBorderThickness(new Boolean(true));
		//ʵ��������������ö���
		CategoriesConfig categoriesConfig = new CategoriesConfig();
		List cateList = new ArrayList();
		cateList.add(new Categorie("һ��"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		cateList.add(new Categorie("����"));
		categoriesConfig.setCategories(cateList);
		List list = getFcfDataList4Group(new BaseDto());
		String xmlString = FcfDataMapper.toFcfXmlData(list, graphConfig, categoriesConfig);
		request.setAttribute("xmlString", xmlString);
		return mapping.findForward("2dBarMsView");
	}
}
