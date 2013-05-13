package org.eredlab.g4.demo.web;

import java.io.File;
import java.io.FileOutputStream;

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
import org.eredlab.g4.rif.web.BaseAction;
import org.eredlab.g4.rif.web.CommonActionForm;

/**
 * ��ʾ�� Action
 * 
 * @author XiongChun
 * @since 2010-06-13
 * @see BaseAction
 */
public class TestAction extends BaseAction {

	/**
	 * ����ҳ��1��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test1Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("test1View");
	}

	/**
	 * ����ҳ��2��ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test2Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("ID", "����");
		return mapping.findForward("test2View");
	}

	/**
	 * ����3ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test3Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test3View");
	}

	/**
	 * ����4ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test4Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test4View");
	}

	/**
	 * ����5ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test5Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test5View");
	}

	/**
	 * ����6ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test6Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward("test6View");
	}

	/**
	 * ����7ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test7Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test7View");
	}

	/**
	 * ����8ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test8Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test8View");
	}

	/**
	 * ����9ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test9Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test9View");
	}

	/**
	 *����10ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test10Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test10View");
	}

	/**
	 * ����11ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test11Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test11View");
	}

	/**
	 * ����12ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test12Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test12View");
	}

	/**
	 * ����13ҳ���ʼ��
	 * 
	 * @param
	 * @return
	 */
	public ActionForward test13Init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("test13View");
	}
	
	/**
	 * �ļ��ϴ�����
	 * 
	 * @param
	 * @return
	 */
	public ActionForward doUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		CommonActionForm cForm = (CommonActionForm) form;
		FormFile myFile = cForm.getSwfUploadFile();
		// ��ȡwebӦ�ø�·��,Ҳ����ֱ��ָ�������������̷�·��
		// String savePath = getServlet().getServletContext().getRealPath("/") +
		// "upload/201104";
		String savePath = "d:/upload/";
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
		}
		Dto outDto = new BaseDto();
		outDto.put("success", new Boolean(true));
		outDto.put("msg", "�ļ��ϴ��ɹ�");
		write(JsonHelper.encodeObject2Json(outDto), response);
		return mapping.findForward(null);
	}
	
    /**
     * ���ļ�д���ļ�ϵͳ
     * @param formFile
     * @param fileName
     * @throws Exception
     */
	public void saveFileToFileSystem(FormFile formFile) throws Exception {

	}
	
	
	
}
