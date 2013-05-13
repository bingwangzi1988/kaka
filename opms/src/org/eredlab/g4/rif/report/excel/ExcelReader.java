package org.eredlab.g4.rif.report.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;

/**
 * Excel���ݶ�ȡ��
 * 
 * @author XiongChun
 * @since 2010-08-12
 */
public class ExcelReader {
	private String metaData = null;
	private InputStream is = null;
	
	public ExcelReader(){};
	
	/**
	 * ���캯��
	 * @param pMetaData Ԫ����
	 * @param pIs Excel������
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public ExcelReader(String pMetaData, InputStream pIs){
		setIs(pIs);
		setMetaData(pMetaData);
	}
	
	/**
	 * ��ȡExcel����
	 * @param pBegin �ӵڼ��п�ʼ������<br>
	 * <b>ע���±�������0��ʼ��Ŷ!
	 * @return ��List<BaseDTO>��ʽ��������
	 * @throws BiffException
	 * @throws IOException
	 */
	public List read(int pBegin) throws BiffException, IOException{
		List list = new ArrayList();
		Workbook workbook = Workbook.getWorkbook(getIs());
		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		for (int i = pBegin; i < rows; i++) {
			Dto rowDto = new BaseDto();
			Cell[] cells = sheet.getRow(i);
			for (int j = 0; j < cells.length; j++) {
				String key = getMetaData().trim().split(",")[j];
				if(G4Utils.isNotEmpty(key)) 
					rowDto.put(key, cells[j].getContents());
			}
			list.add(rowDto);
		}
		return list;
	}
	
	/**
	 * ��ȡExcel����
	 * @param pBegin �ӵڼ��п�ʼ������<br>
	 * <b>ע���±�������0��ʼ��Ŷ!</b>
	 * @param pBack ������ĩβ��ȥ������
	 * @return ��List<BaseDTO>��ʽ��������
	 * @throws BiffException
	 * @throws IOException
	 */
	public List read(int pBegin, int pBack) throws BiffException, IOException{
		List list = new ArrayList();
		Workbook workbook = Workbook.getWorkbook(getIs());
		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		for (int i = pBegin; i < rows - pBack; i++) {
			Dto rowDto = new BaseDto();
			Cell[] cells = sheet.getRow(i);
			String[] arrMeta = getMetaData().trim().split(",");
			for (int j = 0; j < arrMeta.length; j++) {
				String key = arrMeta[j];
				if(G4Utils.isNotEmpty(key)) 
					rowDto.put(key, cells[j].getContents());
			}
			list.add(rowDto);
		}
		return list;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	};
}
