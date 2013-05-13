package org.eredlab.g4.demo.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.json.JsonHelper;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.demo.service.DemoService;
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * ������׼�����߽̳�Action �ļ�����
 * 
 * @author XiongChun
 * @since 2011-04-09
 * @see BaseAction
 */
public class OtherAction extends BaseAction {

	private DemoService demoService = (DemoService) getService("demoService");

	/**
	 * �ļ�����ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward uploadInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("fileUploadView");
	}

	/**
	 * ��ѯ�ļ��б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryFileDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		String sqlid = G4Utils.isOracle() ? "queryFiles4Oracle" : "queryFiles";
		List list = g4Reader.queryForPage(sqlid, dto);
		Integer countInteger = (Integer) g4Reader.queryForObject("countFiles", dto);
		String jsonString = JsonHelper.encodeList2PageJson(list, countInteger, GlobalConstants.FORMAT_DateTime);
		super.write(jsonString, response);
		return mapping.findForward(null);
	}

	/**
	 * Web���ļ��ϴ� ����/����ͬ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward doUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		// �����ļ�,����Ƕ����cForm.getFile2()....֧�����5���ļ�
		FormFile myFile = cForm.getFile1();
		// ��ȡwebӦ�ø�·��,Ҳ����ֱ��ָ�������������̷�·��
		String savePath = getServlet().getServletContext().getRealPath("/") + "uploaddata/";
		//String savePath = "d:/upload/";
		// ���·���Ƿ����,����������򴴽�֮
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		// �ļ�����鵵
		savePath = savePath + G4Utils.getCurDate() + "/";
		File file1 = new File(savePath);
		if (!file1.exists()) {
			file1.mkdir();
		}
		// �ļ���ʵ�ļ���
		String fileName = myFile.getFileName();
		// ����һ������ĳ����������������������
		// String fileName = ;
		File fileToCreate = new File(savePath, fileName);
		// ���ͬ���ļ��Ƿ����,���������ļ���д���ļ�����ϵͳ
		if (!fileToCreate.exists()) {
			FileOutputStream os = new FileOutputStream(fileToCreate);
			os.write(myFile.getFileData());
			os.flush();
			os.close();
		} else {
			// ��·�����Ѵ���ͬ���ļ�,�Ƿ�Ҫ���ǻ���ͻ�����ʾ��Ϣ�����Լ�����
			FileOutputStream os = new FileOutputStream(fileToCreate);
			os.write(myFile.getFileData());
			os.flush();
			os.close();
		}
		// ����ͨ�����������ļ��������Ϣ�־û������ݿ�
		Dto inDto = cForm.getParamAsDto(request);
		inDto.put("title", G4Utils.isEmpty(inDto.getAsString("title")) ? fileName : inDto.getAsString("title"));
		inDto.put("filesize", myFile.getFileSize());
		inDto.put("path", savePath + fileName);
		Dto outDto = demoService.doUpload(inDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�ļ��ϴ��ɹ�");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delFiles(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		String[] strChecked = dto.getAsString("strChecked").split(",");
		for (int i = 0; i < strChecked.length; i++) {
			String fileid = strChecked[i];
			Dto fileDto = (BaseDto) g4Reader.queryForObject("queryFileByFileID", fileid);
			String path = fileDto.getAsString("path");
			File file = new File(path);
			file.delete();
			demoService.delFile(fileid);
		}
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�ļ�ɾ���ɹ�");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}

	/**
	 * �����ļ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm aForm = (CommonActionForm) form;
		Dto dto = aForm.getParamAsDto(request);
		String fileid = dto.getAsString("fileid");
		Dto fileDto = (BaseDto) g4Reader.queryForObject("queryFileByFileID", fileid);
		//����������Ż�,�����ļ����Ͷ�̬���ô�����
		//response.setContentType("application/vnd.ms-excel");
		String filename = G4Utils.encodeChineseDownloadFileName(request, fileDto.getAsString("title"));
		response.setHeader("Content-Disposition", "attachment; filename=" + filename + ";");
		String path = fileDto.getAsString("path");
		File file = new File(path);
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));  
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);  
        byte[] temp = new byte[1024];  
        int size = 0;  
        while ((size = in.read(temp)) != -1) {  
            out.write(temp, 0, size);  
        }  
        in.close();
		ServletOutputStream os = response.getOutputStream();
		os.write(out.toByteArray());
		os.flush();
		os.close();
		return mapping.findForward(null);
	}
	
	/**
	 * Flash����ļ��ϴ� 
	 * �����������ͻ��˵�SWF��ѭ���������������
	 * 
	 * @param
	 * @return
	 */
	public ActionForward doUploadBasedFlah(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommonActionForm cForm = (CommonActionForm) form;
		FormFile myFile = cForm.getSwfUploadFile();
		// ��ȡwebӦ�ø�·��,Ҳ����ֱ��ָ�������������̷�·��
		String savePath = getServlet().getServletContext().getRealPath("/") + "uploaddata/";
		//String savePath = "d:/upload/";
		// ���·���Ƿ����,����������򴴽�֮
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		// �ļ�����鵵
		savePath = savePath + G4Utils.getCurDate() + "/";
		File file1 = new File(savePath);
		if (!file1.exists()) {
			file1.mkdir();
		}
		// �ļ���ʵ�ļ���
		String fileName = myFile.getFileName();
		// ����һ������ĳ����������������������
		// String fileName = ;
		File fileToCreate = new File(savePath, fileName);
		// ���ͬ���ļ��Ƿ����,���������ļ���д���ļ�����ϵͳ
		if (!fileToCreate.exists()) {
			FileOutputStream os = new FileOutputStream(fileToCreate);
			os.write(myFile.getFileData());
			os.flush();
			os.close();
		} else {
			// ��·�����Ѵ���ͬ���ļ�,�Ƿ�Ҫ���ǻ���ͻ�����ʾ��Ϣ�����Լ�����
			FileOutputStream os = new FileOutputStream(fileToCreate);
			os.write(myFile.getFileData());
			os.flush();
			os.close();
		}
		// ����ͨ�����������ļ��������Ϣ�־û������ݿ�
		Dto inDto = cForm.getParamAsDto(request);
		inDto.put("title", G4Utils.isEmpty(inDto.getAsString("title")) ? fileName : inDto.getAsString("title"));
		inDto.put("filesize", myFile.getFileSize());
		inDto.put("path", savePath + fileName);
		Dto outDto = demoService.doUpload(inDto);
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�ļ��ϴ��ɹ�");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
}
