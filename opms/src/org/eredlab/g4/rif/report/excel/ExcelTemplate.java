package org.eredlab.g4.rif.report.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.util.GlobalConstants;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * ����Excel��ģ�����
 * 
 * @author XiongChun
 * @since 2010-08-12
 */
public class ExcelTemplate {

	private Log log = LogFactory.getLog(ExcelTemplate.class);

	private List staticObject = null;
	private List parameterObjct = null;
	private List fieldObjct = null;
	private List variableObject = null;
	private String templatePath = null;

	public ExcelTemplate(String pTemplatePath) {
		templatePath = pTemplatePath;
	}
	
	public ExcelTemplate() {
	}
	
	/**
	 * ����Excelģ��
	 */
	public void parse(HttpServletRequest request) {
		staticObject = new ArrayList();
		parameterObjct = new ArrayList();
		fieldObjct = new ArrayList();
		variableObject = new ArrayList();
		if(G4Utils.isEmpty(templatePath)){
			log.error(GlobalConstants.Exception_Head + "Excelģ��·������Ϊ��!");
		}
		templatePath = request.getSession().getServletContext().getRealPath(templatePath);
		File file = new File(templatePath);
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(0);
		if (G4Utils.isNotEmpty(sheet)) {
			int rows = sheet.getRows();
			for (int k = 0; k < rows; k++) {
				Cell[] cells = sheet.getRow(k);
				for (int j = 0; j < cells.length; j++) {
					String cellContent = cells[j].getContents().trim();
					if (!G4Utils.isEmpty(cellContent)) {
						if (cellContent.indexOf("$P") != -1 || cellContent.indexOf("$p") != -1) {
							parameterObjct.add(cells[j]);
						} else if (cellContent.indexOf("$F") != -1 || cellContent.indexOf("$f") != -1) {
							fieldObjct.add(cells[j]);
						} else if(cellContent.indexOf("$V") != -1 || cellContent.indexOf("$v") != -1) {
							variableObject.add(cells[j]);
						}else {
							staticObject.add(cells[j]);
						}
					}
				}
			}
		} else {
			log.error("ģ�幤���������Ϊ��!");
		}
	}

	/**
	 * ����һ����̬�ı�����
	 */
	public void addStaticObject(Cell cell) {
		staticObject.add(cell);
	}

	/**
	 * ����һ����������
	 */
	public void addParameterObjct(Cell cell) {
		parameterObjct.add(cell);
	}

	/**
	 * ����һ���ֶζ���
	 */
	public void addFieldObjct(Cell cell) {
		fieldObjct.add(cell);
	}


	public List getStaticObject() {
		return staticObject;
	}

	public List getParameterObjct() {
		return parameterObjct;
	}

	public List getFieldObjct() {
		return fieldObjct;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public List getVariableObject() {
		return variableObject;
	}

}
